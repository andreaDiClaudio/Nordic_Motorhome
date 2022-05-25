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

    public void addBooking (String motorhome_id, int customer_id, String start, String end, int price)
    {
        String sql = "INSERT INTO nordic_motorhome.booking (motorhome_id, customer_id, date_start, date_end, price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, motorhome_id, customer_id, start, end, price);
    }

    public void deleteBooking(int id)
    {
        String sql = "DELETE FROM nordic_motorhome.booking WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void editBooking(int customerId, String dateStart, String dateEnd, int bookingId, int price)
    {
        String sql = "UPDATE nordic_motorhome.booking t SET t.customer_id = ?, t.date_start = ?, t.date_end = ?, t.price = ? WHERE t.id = ?";
        jdbcTemplate.update(sql, customerId, dateStart, dateEnd, price, bookingId);
    }
}
