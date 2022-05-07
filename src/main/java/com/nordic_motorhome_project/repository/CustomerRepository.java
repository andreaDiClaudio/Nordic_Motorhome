package com.nordic_motorhome_project.repository;


import com.nordic_motorhome_project.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// Here will be the logic to map data from storage to the business objects (called by Service classes).
@Repository
public class CustomerRepository {
    // JDBCTemplate will allow to run SQL queries.
    @Autowired
    private JdbcTemplate template;

    public List<Customer> getCustomers(){
        String sql = "SELECT * FROM nordic_motorhome.customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
}
