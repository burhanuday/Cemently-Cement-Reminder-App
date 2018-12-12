package com.burhanuday.cubetestreminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.burhanuday.cubetestreminder.util.CubeListTypeConverters;

import java.util.List;

/**
 * Created by burhanuday on 10-12-2018.
 */

@Entity(tableName = "locations")
public class Location {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @TypeConverters(CubeListTypeConverters.class)
    private List<Cube> cubeList;

    private String date;
    private String grade;

    public Location(){}

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
