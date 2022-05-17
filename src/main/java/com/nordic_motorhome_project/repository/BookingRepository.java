package com.nordic_motorhome_project.repository;

import com.nordic_motorhome_project.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Booking> getBookings()
    {
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addBooking (String motorhome_id, int customer_id, String start, String end)
    {
        String sql = "INSERT INTO nordic_motorhome.booking (motorhome_id, customer_id, date_start, date_end) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, motorhome_id, customer_id, start, end);
    }

    public void deleteBooking(int id)
    {
        String sql = "DELETE FROM nordic_motorhome.booking WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
