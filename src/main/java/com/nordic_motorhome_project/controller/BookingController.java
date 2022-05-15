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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingService;
    @Autowired
    private MotorhomeService motorhomeService;
    @Autowired
    private CustomerService customerService;

//      TODO delete
//    private Booking bookingModel;
//    private MotorhomeModel motorhomeModel;

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

        //TODO delete
//        //Passing parameters that should be displayed in footer
//        model.addAttribute("bookingNow", bookingModel);
//        model.addAttribute("motorhomeNow", motorhomeModel);
        return "home/addBooking";
    }

    @PostMapping("/addBooking")
    public String addBooking(Model model, @RequestParam String brand, @RequestParam Integer client,
                             @RequestParam String dateStart, @RequestParam String dateEnd,
                             @RequestParam int numberOfPpl, @RequestParam String type) {
        //Passes the list of available motorhomes
        model.addAttribute("booked", bookedMotorhomes(dateStart,dateEnd,brand,numberOfPpl,type));

        //All motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);


        //All customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        //Pass selected values for footer
        model.addAttribute("brand", brand);
        model.addAttribute("client",client);
        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);
        model.addAttribute("numberOfPpl", numberOfPpl);
        model.addAttribute("type", type);

//        TODO delete
//        motorhomeModel.setBrand(brand);
//        motorhomeModel.setNumber_of_persons(numberOfPpl);
//        motorhomeModel.setIsLuxury(type);
//        bookingModel.setCustomer_id(client);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//        LocalDate localDate = LocalDate.parse(dateStart, formatter);
//        bookingModel.setDate_start(localDate);
//        localDate = LocalDate.parse(dateEnd, formatter);
//        bookingModel.setDate_end(localDate);

        return "home/addBooking";
    }


    //TODO ask Nico where u get int variable from
    @GetMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") int id){
        bookingService.deleteBooking(id);
        return "redirect:/booking";
    }

    //TODO finish
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
        for(int i=0;i<motorhomes.size();i++)
        {
            if(motorhomes.get(i).getLicense_plate().equals(dateId))
            {
                motorhomes.remove(i);
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
}
