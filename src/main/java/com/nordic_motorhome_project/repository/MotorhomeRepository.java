package com.nordic_motorhome_project.repository;

import com.nordic_motorhome_project.model.MotorhomeModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//The classes annotated with @Repository provide the mechanism for storage, retrieval, update, delete, and search operation on objects.
@Repository
public class MotorhomeRepository {

    //@Autowired annotation is used for dependency injection, and it is used to auto-wire a bean into another bean.
    //A bean is a Java object that is created by Spring framework when the application starts.
    //Dependency injection of JdbcTemplate to run SQL-queries
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MotorhomeModel> getMotorhomes(){
        String sql = "SELECT * FROM motorhome JOIN motorhome_type mt on mt.id = motorhome.type";
        RowMapper<MotorhomeModel> rowMapper = new BeanPropertyRowMapper<>(MotorhomeModel.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void createMotorhome(MotorhomeModel motorhomeModel){
    String sql = "INSERT INTO motorhome (license_plate, type) VALUES (?, ?)";
    jdbcTemplate.update(sql, motorhomeModel.getLicense_plate(), motorhomeModel.getType());
    }

    public MotorhomeModel findMotorhomeByLicensePlate(String license_plate){
        String sql = "SELECT * FROM motorhome WHERE license_plate = ?";
        RowMapper<MotorhomeModel> rowMapper = new BeanPropertyRowMapper<>(MotorhomeModel.class);
        MotorhomeModel motorhomeModel = jdbcTemplate.queryForObject(sql, rowMapper, license_plate);
        return motorhomeModel;
    }

    public void updateMotorhome(String license_plate, MotorhomeModel motorhomeModel){
        String sql = "UPDATE motorhome SET license_plate = ?, type = ? WHERE license_plate = ?";
        jdbcTemplate.update(sql, motorhomeModel.getLicense_plate(), motorhomeModel.getType(), motorhomeModel.getLicense_plate());
    }

    public void deleteMotorhome(String license_plate){
        String sql ="DELETE FROM motorhome WHERE license_plate = ?";
        jdbcTemplate.update(sql, license_plate);
    }
}
