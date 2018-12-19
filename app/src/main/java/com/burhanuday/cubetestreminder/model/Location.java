package com.burhanuday.cubetestreminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.burhanuday.cubetestreminder.util.CubeListTypeConverters;
import com.burhanuday.cubetestreminder.util.DateConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by burhanuday on 10-12-2018.
 */

@Entity(tableName = "locations")
public class Location implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @TypeConverters(CubeListTypeConverters.class)
    private List<Cube> cubeList;

    @TypeConverters(DateConverter.class)
    private Date date;

    private String grade;

    public Location(){}

    @Ignore
    public Location(String name, List<Cube> cubeList, Date date, String grade) {
        this.name = name;
        this.cubeList = cubeList;
        this.date = date;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cube> getCubeList() {
        return cubeList;
    }

    public void setCubeList(List<Cube> cubeList) {
        this.cubeList = cubeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
