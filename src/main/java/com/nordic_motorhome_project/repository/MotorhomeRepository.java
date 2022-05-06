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
}
