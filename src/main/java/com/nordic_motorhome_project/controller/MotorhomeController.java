package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MotorhomeController {

    @Autowired
    private MotorhomeService motorhomeService;

    @GetMapping("/motorhome")
    public String vehicle(Model model){
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        System.out.println(motorhomeService.getMotorhomes().toArray());
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
    public String createMotorhomeSubmit(@ModelAttribute("motorhome") MotorhomeModel motorhomeModel){
        motorhomeService.createMotorhome(motorhomeModel);
        System.out.println(motorhomeModel.toString());
        return "redirect:/motorhome";
    }

    @GetMapping("/editMotorhome/{license_plate}")
    public String editMotorhome(@PathVariable("license_plate") String license_plate, Model model){
        model.addAttribute("motorhome", motorhomeService.findMotorhomeByLicensePlate(license_plate));
        return "home/editMotorhome";
    }

    @PostMapping("/editMotorhome")
    public String editMotorhomeSubmit(@ModelAttribute MotorhomeModel motorhomeModel){
        motorhomeService.updateMotorhome(motorhomeModel.getLicense_plate(), motorhomeModel);
        System.out.println(motorhomeModel.toString());
        return "redirect:/motorhome";
    }

    @GetMapping("/deleteMotorhome/{license_plate}")
    public String deleteMotorhome(@PathVariable("license_plate") String license_plate ){
        motorhomeService.deleteMotorhome(license_plate);
        return "redirect:/motorhome";
    }
}
