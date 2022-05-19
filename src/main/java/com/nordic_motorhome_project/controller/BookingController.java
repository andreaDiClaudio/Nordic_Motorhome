package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.Booking;
import com.nordic_motorhome_project.model.Customer;
import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.repository.BookingRepository;
import com.nordic_motorhome_project.service.CustomerService;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingService;
    @Autowired
    private MotorhomeService motorhomeService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/booking")
    public String booking(Model model){
        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);
        return "home/booking";
    }

    @GetMapping("/addBooking")
    public String addBooking(Model model) {
        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        return "home/addBooking";
    }

    @PostMapping("/addBooking")
    public String addBooking(Model model, @RequestParam String brand, @RequestParam Integer client,
                             @RequestParam String dateStart, @RequestParam String dateEnd,
                             @RequestParam int numberOfPpl, @RequestParam String type, @RequestParam String page) {
        //Passes the list of available motorhomes
        model.addAttribute("booked", bookedMotorhomes(dateStart,dateEnd,brand,numberOfPpl,type));
        //Passes if list of motorhomes is empty
        model.addAttribute("isEmpty", availableExist(bookedMotorhomes(dateStart,dateEnd,brand,numberOfPpl,type)));

        //All motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);

        //All customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        boolean wrongDate = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dStart = sdf.parse(dateStart);
            Date dEnd = sdf.parse(dateEnd);
            if(dStart.after(dEnd))
            {
                wrongDate = true;
            }
        }catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }


        //Pass selected values for footer
        model.addAttribute("brand", brand);
        model.addAttribute("client",client);
        model.addAttribute("dateStart1", dateStart);
        model.addAttribute("dateEnd1", dateEnd);
        model.addAttribute("numberOfPpl", numberOfPpl);
        model.addAttribute("type", type);
        model.addAttribute("dateError", wrongDate);

        //TODO try to make redirect back to page
        if(wrongDate==true)
        {
            if(page.equals("booking"))
            {
                return "home/booking";
            }else if(page.equals("addBooking"))
            {
                return "home/addBooking";
            }else if(page.equals("editBooking"))
            {
                return "home/editBooking";
            }
        }
        return "home/addBooking";
    }


    @PostMapping("/createBooking")
    public String createBooking(@RequestParam String motorhomeId,@RequestParam Integer client, @RequestParam String dateStart, @RequestParam String dateEnd){
        bookingService.addBooking(motorhomeId, client, dateStart, dateEnd);
        return "redirect:/booking";
    }



    @GetMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") int id){
        bookingService.deleteBooking(id);
        return "redirect:/booking";
    }

    @GetMapping("/editBooking/{id}")
    public String editBooking(@PathVariable("id") int id, Model model){
        //passing id of booking we will edit
        model.addAttribute("editId", id);

        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);
        return "home/editBooking";
    }


    @PostMapping("/changeBooking")
    public String changeBooking(@RequestParam int client, @RequestParam String dateStart, @RequestParam String dateEnd,@RequestParam String motorhome, @RequestParam int booking, Model model)
    {

        List<Booking> bookings = bookingService.getBookings();

        boolean wrongDate = false;
        boolean dateTaken = false;

        LocalDate dStart = LocalDate.parse(dateStart);
        LocalDate dEnd = LocalDate.parse(dateStart);

        if(dStart.isAfter(dEnd))
        {
            wrongDate = true;
        }

        for(int i=0;i<bookings.size();i++)
        {
            if(dEnd.isAfter(bookings.get(i).getDate_start())&&dStart.isBefore(bookings.get(i).getDate_end())&&bookings.get(i).getId()==booking)
            {
                dateTaken = true;
            }
        }

        model.addAttribute("dateTaken", dateTaken);
        model.addAttribute("booking", booking);
        model.addAttribute("client", client);
        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);
        model.addAttribute("motorhome", motorhome);
        model.addAttribute("dateError", wrongDate);

        if(wrongDate==false&&dateTaken==false) {
            bookingService.editBooking(client, dateStart, dateEnd, booking);
        }
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        return "home/changeBooking";
    }

    public ArrayList<String> bookedMotorhomes (String dateStart, String dateEnd, String brand, int numberOfPpl, String type)
    {
        //Lists where we will put unavailable by date motorhomes IDs
        ArrayList<String> dateId = new ArrayList<>();

        //List from where we will delete all unavailable motorhomes
        List<MotorhomeModel> motorhomes = motorhomeService.getMotorhomes();

        //List with available motorhomes IDs
        ArrayList<String> available = new ArrayList<>();

        //Converting date from string to local date
        LocalDate start = LocalDate.parse(dateStart);
        LocalDate end = LocalDate.parse(dateEnd);

        //Adding unavailable by date motorhomes to the list
        List<Booking> bookings = bookingService.getBookings();
        for(int i=0;i<bookings.size();i++)
        {
            if(end.isAfter(bookings.get(i).getDate_start())&&start.isBefore(bookings.get(i).getDate_end()))
            {
                dateId.add(bookings.get(i).getMotorhome_id());
            }
        }

        for(int i=0;i<bookings.size();i++)
        {
            if(end.isBefore(bookings.get(i).getDate_end())&&start.isAfter(bookings.get(i).getDate_start()))
            {
                dateId.add(bookings.get(i).getMotorhome_id());
            }
        }

        //Checking by all other parameters
        for(int i=0;i<motorhomes.size();i++)
        {
            if(!brand.equals(motorhomes.get(i).getBrand())||numberOfPpl!=motorhomes.get(i).getNumber_of_persons()||!type.equals(motorhomes.get(i).getIsLuxury()))
            {
                motorhomes.remove(i);
                i--;
            }
        }

        //Deleting unavailable by date from list
        boolean found = false;
        for(int i=0;i<motorhomes.size();i++)
        {
            found = false;
            for(int j=0;j<dateId.size();j++) {
                if (motorhomes.get(i).getLicense_plate().equals(dateId.get(j)))
                {
                    motorhomes.remove(i);
                    found=true;
                }
            }
            if(found==true)
            {
                i--;
            }
        }

        //Converting to only license plate - ID list
        for(int i=0;i<motorhomes.size();i++)
        {
            available.add(motorhomes.get(i).getLicense_plate());
        }

        return available;
    }

    public boolean availableExist (ArrayList<String> bookedMotorhomes)
    {
        return bookedMotorhomes.isEmpty();
    }
}
