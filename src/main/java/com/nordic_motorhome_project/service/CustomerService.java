package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.Customer;
import com.nordic_motorhome_project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// The "Business Logic" resides in the Service Layer. @Service indicates that this class belongs to that layer.
@Service
public class CustomerService {
    // Autowired will create and manage an CustomerRepository Object.
    @Autowired
    private CustomerRepository customerRepo;

    public List<Customer> getCustomers(){
        return customerRepo.getCustomers();
    }
}
