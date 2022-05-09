package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MotorhomeController {

    @Autowired
    private MotorhomeService motorhomeService;

    @GetMapping("/motorhome")
    public String vehicle(Model model){
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);
        return "home/motorhome";
    }

    @GetMapping("/createMotorhome")
    public String createMotorhome(Model model){
        MotorhomeModel motorhome = new MotorhomeModel();
        model.addAttribute("motorhome", motorhome);
        return "home/createMotorhome";
    }

    @PostMapping("/createMotorhome")
    public String createMotorhomeSubmit(@ModelAttribute("motorhome") MotorhomeModel motorhomeModel, Model model){

        MotorhomeModel motorhome = new MotorhomeModel();
        model.addAttribute("motorhome", motorhome);
        motorhomeService.createMotorhome(motorhome);
        System.out.println(motorhome.toString());

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        motorhomesList.add(motorhome);

        return "home/createMotorhome";
    }
}
