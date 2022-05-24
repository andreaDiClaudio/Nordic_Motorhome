package com.nordic_motorhome_project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Here we are in Model area of MVC design pattern.
//The Model works as a container that contains data of the application.
//When working with DBs  it is important to annotate the model class with the annotation @Entity.
//An Entity represents a table stored in a DB. Every instance of the entity represent a row in the table.

@Entity
public class MotorhomeModel {

    @Id
    @NotEmpty(message = "License Plate cannot be empty")
    @Size(min = 7, max = 7, message = "License Plate must be 7 characters")
    private String license_plate;
    private int type;
    private int id;
    private int number_of_persons;
    private String brand;
    private String isLuxury;
    private int base_price;

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_persons() {
        return number_of_persons;
    }

    public void setNumber_of_persons(int number_of_persons) {
        this.number_of_persons = number_of_persons;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIsLuxury() {
        return isLuxury;
    }

    public void setIsLuxury(String luxury) {
        isLuxury = luxury;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    @Override
    public String toString() {
        return "MotorhomeModel{" +
                "license_plate='" + license_plate + '\'' +
                ", type=" + type +
                '}';
    }
}
