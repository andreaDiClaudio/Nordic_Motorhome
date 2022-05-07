package com.nordic_motorhome_project.repository;

import com.nordic_motorhome_project.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository2 extends CrudRepository <Booking, Integer> {

}
