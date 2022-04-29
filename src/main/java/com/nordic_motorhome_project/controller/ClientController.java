package com.nordic_motorhome_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/client.html")
    public String client(){
        return "home/client";
    }
}
