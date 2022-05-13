package com.nordic_motorhome_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Spring Boot @SpringBootApplication annotation is used to mark a configuration class that declares one or more @Bean methods and also triggers auto-configuration and component scanning
@SpringBootApplication
public class NordicMotorhomeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NordicMotorhomeProjectApplication.class, args);
    }

}
