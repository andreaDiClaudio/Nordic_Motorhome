package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.Booking;
import com.nordic_motorhome_project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookings()
    {
        return bookingRepository.getBookings();
    }

    public void deleteBooking(int id)
    {
        bookingRepository.deleteBooking(id);
    }

    public void addBooking(String motorhome_id, int customer_id, String start, String end)
    {
        bookingRepository.addBooking(motorhome_id, customer_id, start, end);
    }

    public void editBooking(int customerId, String dateStart, String dateEnd, int bookingId)
    {
        bookingRepository.editBooking(customerId, dateStart, dateEnd, bookingId);
    }
}
