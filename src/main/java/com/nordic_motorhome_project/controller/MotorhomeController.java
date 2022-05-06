package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MotorhomeController {

    @Autowired
    private MotorhomeService motorhomeService;

    @GetMapping("/motorhome.html")
    public String vehicle(Model model){
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);
        return "home/motorhome";
    }
}
