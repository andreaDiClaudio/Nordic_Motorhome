package com.nordic_motorhome_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MotorhomeController {

    @GetMapping("/motorhome.html")
    public String vehicle(){
        return "home/motorhome";
    }
}
