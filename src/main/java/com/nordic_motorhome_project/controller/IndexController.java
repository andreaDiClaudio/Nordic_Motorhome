package com.nordic_motorhome_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//MVC (Model-View-Controller) is a design pattern which separates the application in three areas.

//Here we are in the Controller area. The Controller controls the data flow into model object and updates the view
//whenever data change. It keeps View and Model separated.
//The following annotation indicates that the annotated class is a controller. The @Controller annotation is a
//specialization of the generic @Component annotation, which allows a class to be recognized as a Spring-managed
//component.
@Controller
public class IndexController {

    //@GetMapping annotation is for mapping http GET requests onto specific handler methods.
    //Specifically, @GetMapping is a composed annotation that acts as shortcut for @RequestMapping(method = RequestMethod.GET)
    // When the client asks for "/" the method will return "home/index" which is the html file inside resources>templates.home>index.html
    //This mapping is for "each" first time you enter the web page.
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    /*
    //This mapping is for when you click any <a> tag with href=index.html.
    @GetMapping("/index.html")
    public String goBack(){
        return "home/index";
    }

     */
}
