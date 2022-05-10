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

    public List<Customer> getCustomerAToZ() { return customerRepo.getCustomerAToZ(); }

    public List<Customer> getCustomerZToA() { return customerRepo.getCustomerZToA(); }

    public List<Customer> getCustomerByDateLast() { return customerRepo.getCustomerByDateLast(); }

    public Customer findCustomerByID(int id) { return customerRepo.findCustomerByID(id); }

    public void updateCustomer(int id, Customer customer) { customerRepo.updateCustomer(id, customer); }

    public void deleteCustomer(int id) { customerRepo.deleteCustomer(id); }

    public void addCustomer(Customer customer) { customerRepo.addCustomer(customer); }
}
