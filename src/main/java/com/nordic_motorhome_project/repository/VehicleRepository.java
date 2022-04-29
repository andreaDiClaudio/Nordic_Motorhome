package com.nordic_motorhome_project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.nordic_motorhome_project.model.VehicleModel;



import java.util.List;

//The classes annotated with @Repository provide the mechanism for storage, retrieval, update, delete, and search operation on objects.
@Repository
public class VehicleRepository {

    //@Autowired annotation is used for dependency injection, and it is used to auto-wire a bean into another bean.
    //A bean is a Java object that is created by Spring framework when the application starts.
    //Dependency injection of JdbcTemplate to run SQL-queries
    @Autowired
    private JdbcTemplate template;

    //Example (the same goes for all CRUD):
    /*
    public VehicleModel addVehicle(VehicleModel vehicle){
        String sql= "INSERT INTO vehicles (licensePlate, brand, isLuxury, price) VALUES (?, ?, ?)";
        template.update(sql, vehicle.getLicensePlate(), ..., vehicle.getPrice());
        return null;
    }
     */

}
