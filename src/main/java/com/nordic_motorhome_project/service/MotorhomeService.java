package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.repository.MotorhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service annotation is used with classes that proved business functionalities.
//The service layer is a layer in an application that facilitates communication between the controller
// and the persistence layer (layer that has entities for example).
@Service
public class MotorhomeService {

    //@Autowired annotation is used for dependency injection, and it is used to auto-wire a bean into another bean.
    //A bean is a Java object that is created by Spring framework when the application starts.
    @Autowired
    private MotorhomeRepository motorhomeRepository;

    public List<MotorhomeModel> getMotorhomes(){ return motorhomeRepository.getMotorhomes();}

    public void createMotorhome(MotorhomeModel motorhomeModel) {motorhomeRepository.createMotorhome(motorhomeModel);}

    public MotorhomeModel findMotorhomeByLicensePlate(String license_plate){

        return motorhomeRepository.findMotorhomeByLicensePlate(license_plate);
    }

    public void updateMotorhome(MotorhomeModel motorhomeModel){motorhomeRepository.updateMotorhome(motorhomeModel);}

    public void deleteMotorhome(String license_plate){motorhomeRepository.deleteMotorhome(license_plate);}

    public List<MotorhomeModel> getMotorhomeTypeDesc(){ return motorhomeRepository.getMotorhomeTypeDesc();}

    //This method is returning an ArrayList because the view "motorhome.html" has a table which is looping through
    //a list,provided by the model ("motorhome.html" - Line 71), to fill the table cell. In this way the method "motorhomeByLP" can reuse the
    //same view that has as model a list.

    public ArrayList<MotorhomeModel> searchMotorhome(String license_plate){
        ArrayList<MotorhomeModel> list=new ArrayList<>();
        list.add(findMotorhomeByLicensePlate(license_plate));
        return list;
    }
}

