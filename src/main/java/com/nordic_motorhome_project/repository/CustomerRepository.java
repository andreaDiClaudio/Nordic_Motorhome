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

    public List<Customer> getCustomerAToZ(){
        String sql = "SELECT * FROM nordic_motorhome.customer ORDER BY last_name ASC";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public List<Customer> getCustomerZToA(){
        String sql = "SELECT * FROM nordic_motorhome.customer ORDER BY last_name DESC";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public List<Customer> getCustomerByDateLast(){
        String sql = "SELECT * FROM nordic_motorhome.customer ORDER BY id DESC";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public Customer findCustomerByID(int id){
        String sql = "SELECT * FROM nordic_motorhome.customer WHERE id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer customer = template.queryForObject(sql, rowMapper, id);
        return customer;
    }

    public void updateCustomer(int id, Customer customer){
        String sql = "UPDATE nordic_motorhome.customer SET first_name = ?, last_name = ?, dob = ?, email = ?, phone_number = ? WHERE id = ?";
        template.update(sql, customer.getFirst_name(), customer.getLast_name(), customer.getDob(),
                customer.getEmail(), customer.getPhone_number(), customer.getId());
    }

    public void deleteCustomer(int id){
        String sql = "DELETE FROM nordic_motorhome.customer WHERE id = ?";
        template.update(sql, id);
    }

    public void addCustomer(Customer customer){
        String sql = "INSERT INTO nordic_motorhome.customer (first_name, last_name, dob, email, phone_number) values (?, ?, ?, ?, ?)";
        template.update(sql, customer.getFirst_name(), customer.getLast_name(), customer.getDob(), customer.getEmail(), customer.getPhone_number());
    }
}
