package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MotorhomeController {

    @Autowired
    private MotorhomeService motorhomeService;

    @GetMapping("/motorhome")
    public String motorhome(Model model){
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomesList", motorhomesList);
        model.addAttribute("motorhomeModel", motorhomeModel);
        return "home/motorhome";
    }

    @PostMapping("/motorhome")
    public String motorhomeByLP(String license_plate, Model model){
        List<MotorhomeModel> motorhomesList = motorhomeService.searchMotorhome(license_plate);
        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomesList", motorhomesList);
        model.addAttribute("motorhomeModel", motorhomeModel);
        return "home/motorhome";
    }

    @GetMapping("/createMotorhome")
    public String createMotorhome(Model model){
        MotorhomeModel motorhome = new MotorhomeModel();
        model.addAttribute("motorhome", motorhome);
        return "home/createMotorhome";
    }

    @PostMapping("/createMotorhome")
    public String createMotorhomeSubmit(@Valid @ModelAttribute("motorhome") MotorhomeModel motorhomeModel, BindingResult result, Model model){
        if(result.hasErrors()){
            return "home/createMotorhome";
        }
        motorhomeService.createMotorhome(motorhomeModel);
        System.out.println(motorhomeModel.toString());
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomesList", motorhomesList);
        model.addAttribute("motorhomeModel", motorhomeModel);
        return "home/motorhome";
    }

    @GetMapping("/editMotorhome/{license_plate}")
    public String editMotorhome(@PathVariable("license_plate") String license_plate, Model model){
        model.addAttribute("motorhome", motorhomeService.findMotorhomeByLicensePlate(license_plate));
        return "home/editMotorhome";
    }

    @PostMapping("/editMotorhome")
    public String editMotorhomeSubmit(@Valid @ModelAttribute MotorhomeModel motorhomeModel, BindingResult result, Model model){
        String lp = motorhomeModel.getLicense_plate();
        if(result.hasErrors()){
            return "redirect:/editMotorhome/" + lp;
        }
        motorhomeService.updateMotorhome(motorhomeModel);
        System.out.println(motorhomeModel.toString());
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);
        return "home/motorhome";
    }

    @GetMapping("/deleteMotorhome/{license_plate}")
    public RedirectView deleteMotorhome(@PathVariable("license_plate") String license_plate, Model model ){
        motorhomeService.deleteMotorhome(license_plate);

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);

        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomeModel", motorhomeModel);

        return new RedirectView("/motorhome");
    }

    @GetMapping("/filterTypeDesc")
    public String filterTypeDesc(Model model){
        List<MotorhomeModel> list = motorhomeService.getMotorhomeTypeDesc();
        model.addAttribute("motorhomesList", list);
        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomeModel", motorhomeModel);
        return "home/motorhome";
    }
}
