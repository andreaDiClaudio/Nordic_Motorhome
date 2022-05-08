package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.Customer;
import com.nordic_motorhome_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/customer")
    public String customer(Model model){
        List<Customer> customerList = customerService.getCustomers();
        // Added the results from the queries to the Database to the Model, for fetching in the .html file.
        model.addAttribute("customers", customerList);
        return "home/customer";
    }

    @GetMapping("/editCustomer/{id}")
    // @PathVariable is used to handle template variables in the URL.
    // In this case the Path Variable is ID
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("customer", customerService.findCustomerByID(id));
        return "home/editCustomer";
    }

    // @PostMapping handles the HTPP Post request.
    @PostMapping("/edit")
    public String edit(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer.getId(), customer);
        // Redirects directly to the Customer page.
        return "redirect:/customer";
    }

    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") int id){
        customerService.deleteCustomer(id);
        return "redirect:/customer";
    }

    @GetMapping("/addCustomer")
    public String addCustomer(){
        return "home/addCustomer";
    }

    @PostMapping("/addCustomer")
    public String add(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }


}
