package com.nordic_motorhome_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VehicleController {

    @GetMapping("/motorhome")
    public String vehicle(){
        return "home/vehicle";
    }
}
