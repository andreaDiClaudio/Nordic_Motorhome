package com.nordic_motorhome_project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//Here we are in Model area of MVC design pattern.
//The Model works as a container that contains data of the application.
//When working with DBs  it is important to annotate the model class with the annotation @Entity.
//An Entity represents a table stored in a DB. Every instance of the entity represent a row in the table.
@Entity
public class MotorhomeModel {

    // Once created the DB change the following field with the column in the table. I'll do it just to give you an idea.

    //The @Id annotation defines the primary key in a table.
    @Id
    private String licensePlate;
    //Each field represent a column in the table, and they must match name and data type in the DB.
    private String brand;
    private boolean isLuxury;
    private int price;

    //Generate getters and setters once we have the correct fields
}
