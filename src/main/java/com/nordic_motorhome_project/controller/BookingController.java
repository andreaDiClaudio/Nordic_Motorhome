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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
                             @RequestParam int numberOfPpl, @RequestParam boolean type) {
        //Passes the list of available motorhomes
        model.addAttribute("booked", bookedMotorhomes(dateStart,dateEnd,brand,numberOfPpl,type));
        System.out.println(dateStart+" "+dateEnd+" "+brand+" "+numberOfPpl+" "+type);

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

        return "home/addBooking";
    }

    @PostMapping()

    public ArrayList<String> bookedMotorhomes (String dateStart, String dateEnd, String brand, int numberOfPpl, boolean type)
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
            if(!brand.equals(motorhomes.get(i).getBrand())||numberOfPpl!=motorhomes.get(i).getNumber_of_persons())
            {
                System.out.println(motorhomes.get(i).getBrand()+" "+motorhomes.get(i).getNumber_of_persons()+" "+motorhomes.get(i).getLuxury());
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
