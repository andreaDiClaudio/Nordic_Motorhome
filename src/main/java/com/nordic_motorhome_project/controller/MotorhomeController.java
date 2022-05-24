package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//MVC (Model-View-Controller) is a design pattern which separates the application in three areas.

//Here we are in the Controller area. The Controller controls the data flow into model object and updates the view
//whenever data change. It keeps View and Model separated.
//The following annotation indicates that the annotated class is a controller. The @Controller annotation is a
//specialization of the generic @Component annotation, which allows a class to be recognized as a Spring-managed
//component.
@Controller
public class MotorhomeController {

    //@Autowired annotation is used for dependency injection, and it is used to auto-wire a bean into another bean.
    //A bean is a Java object that is created by Spring framework when the application starts.
    @Autowired
    private MotorhomeService motorhomeService;

    //@GetMapping annotation is for mapping http GET requests onto specific handler methods.
    //Specifically, @GetMapping is a composed annotation that acts as shortcut for
    //@RequestMapping(method = RequestMethod.GET)

    // When the client asks for "/motorhome" the method will return "home/motorhome" which is
    // the html file inside resources>templates>home>motorhome.html
    @GetMapping("/motorhome")
    //Interface that defines a holder for model attributes. Primarily designed for adding attributes to the model.
    public String motorhome(Model model){

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();

        MotorhomeModel motorhomeModel = new MotorhomeModel();
        //model.addAttribute: Add the supplied attribute under the supplied name.
        model.addAttribute("motorhomesList", motorhomesList);
        //the second model is used by the form in "motorhome.html" file (line 45) to work with a MotorhomeModel object.
        //th:field works only with attributes. Any get method is not recognized.
        model.addAttribute("motorhomeModel", motorhomeModel);

        //returns a String that is the view (html File)
        return "home/motorhome";
    }

    //The @PostMapping is specialized version of @RequestMapping annotation that acts as a shortcut for
    //@RequestMapping(method = RequestMethod.POST).
    //The @PostMapping annotated methods in the @Controller annotated classes handle the HTTP POST requests
    @PostMapping("/motorhome")
    public String motorhomeByLP(String license_plate, Model model){

        List<MotorhomeModel> motorhomesList = motorhomeService.searchMotorhome(license_plate);
        model.addAttribute("motorhomesList", motorhomesList);

        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomeModel", motorhomeModel);

        return "home/motorhome";
    }

    @GetMapping("/createMotorhome")
    public String createMotorhome(Model model){

        MotorhomeModel motorhome = new MotorhomeModel();
        model.addAttribute("motorhome", motorhome);

        return "home/createMotorhome";
    }

    //The Java Bean Validation’s @Valid constraint annotation makes sure that when an object is validated,
    //the validation recourses to all fields that are annotated with @Valid.

    //@ModelAttribute is an annotation that binds a method parameter or method return value to a named model attribute,
    //and then exposes it to a web view.

    //General interface that represents binding results. Extends the interface for error registration capabilities,
    //allowing for a Validator to be applied.
    @PostMapping("/createMotorhome")
    public String createMotorhomeSubmit(@Valid @ModelAttribute("motorhome") MotorhomeModel motorhomeModel, BindingResult result, Model model){

        if(result.hasErrors()){
            return "home/createMotorhome";
        }

        motorhomeService.createMotorhome(motorhomeModel);

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomesList", motorhomesList);
        model.addAttribute("motorhomeModel", motorhomeModel);

        return "home/motorhome";
    }

    //@PathVariable: Annotation which indicates that a method parameter should be bound to a URI template variable.
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

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);

        return "home/motorhome";
    }

    @GetMapping("/deleteMotorhome/{license_plate}")
    public RedirectView deleteMotorhome(@PathVariable("license_plate") String license_plate, Model model){

        motorhomeService.deleteMotorhome(license_plate);

        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomesList", motorhomesList);
        MotorhomeModel motorhomeModel = new MotorhomeModel();
        model.addAttribute("motorhomeModel", motorhomeModel);

        //RedirectReview: View that redirects to an absolute, context relative, or current request relative URL
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
