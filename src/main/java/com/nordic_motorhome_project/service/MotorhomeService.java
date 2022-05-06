package com.nordic_motorhome_project.service;

import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.repository.MotorhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service annotation is used with classes that proved business functionalities.
@Service
public class MotorhomeService {

    @Autowired
    private MotorhomeRepository motorhomeRepository;

    public List<MotorhomeModel> getMotorhomes(){ return motorhomeRepository.getMotorhomes();}
}

