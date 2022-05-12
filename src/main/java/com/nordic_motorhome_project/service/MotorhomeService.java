package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.repository.MotorhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service annotation is used with classes that proved business functionalities.
@Service
public class MotorhomeService {

    @Autowired
    private MotorhomeRepository motorhomeRepository;

    public List<MotorhomeModel> getMotorhomes(){ return motorhomeRepository.getMotorhomes();}

    public void createMotorhome(MotorhomeModel motorhomeModel) {motorhomeRepository.createMotorhome(motorhomeModel);}

    public MotorhomeModel findMotorhomeByLicensePlate(String license_plate){ return motorhomeRepository.findMotorhomeByLicensePlate(license_plate); }

    public void updateMotorhome(MotorhomeModel motorhomeModel){motorhomeRepository.updateMotorhome(motorhomeModel);}

    public void deleteMotorhome(String license_plate){motorhomeRepository.deleteMotorhome(license_plate);}

    public List<MotorhomeModel> getMotorhomeTypeDesc(){ return motorhomeRepository.getMotorhomeTypeDesc();}

    public ArrayList<MotorhomeModel> searchMotorhome(String license_plate){
        ArrayList<MotorhomeModel> list=new ArrayList<>();
        list.add(findMotorhomeByLicensePlate(license_plate));
        return list;
    }
}

