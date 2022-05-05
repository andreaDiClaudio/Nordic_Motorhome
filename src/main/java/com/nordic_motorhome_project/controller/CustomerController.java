package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.Customer;
import com.nordic_motorhome_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// @Controller is a type of Annotation.
// Annotations provide data about a program.
// @Controller indicates that this class serves the role of a Controller. Is usually accompanied by GetMapping.
@Controller
public class CustomerController {

    // The @Autowired annotations indicates that Spring is going to take care of creating
    // and managing of objects of class CustomerService.
    // CONTROLLERS call SERVICES who calls REPOSITORIES.
    @Autowired
    private CustomerService customerService;

    // Annotation for mapping GET Requests into specific handler methods.
    @GetMapping("/customer.html")
    public String customer(Model model){
        List<Customer> customerList = customerService.getCustomers();
        // Added the results from the queries to the Database to the Model, for fetching in the .html file.
        model.addAttribute("customers", customerList);
        return "home/customer";
    }
}
