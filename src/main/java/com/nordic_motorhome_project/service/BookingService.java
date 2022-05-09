package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.Booking;
import com.nordic_motorhome_project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookings()
    {
        return bookingRepository.getBookings();
    }

}