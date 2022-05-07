package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.Booking;
import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.repository.BookingRepository;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MotorhomeService motorhomeService;

    @GetMapping("/booking.html")
    public String booking(Model model){
        //bookings
        List<Booking> bookings = bookingRepository.getBookings();
        model.addAttribute("bookings", bookings);
        //all motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        return "home/booking";
    }
}
