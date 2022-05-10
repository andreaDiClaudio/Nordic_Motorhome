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
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MotorhomeService motorhomeService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/booking")
    public String booking(Model model){
        //bookings
        List<Booking> bookings = bookingRepository.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);
        return "home/booking";
    }

    @PostMapping("/booking")
    public String addBooking(Model model, @RequestParam String brand, @RequestParam String client,
                             @RequestParam String dateStart, @RequestParam String dateEnd,
                             @RequestParam int numberOfPpl, @RequestParam boolean type)
    {
        Booking booking = new Booking();
        System.out.println(brand+client+dateStart+dateEnd+numberOfPpl+type);
        return "redirect:/booking";
    }
}
