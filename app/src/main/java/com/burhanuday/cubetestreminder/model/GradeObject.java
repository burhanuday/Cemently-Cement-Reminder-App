package com.burhanuday.cubetestreminder.model;

import java.util.ArrayList;
import java.util.List;

public class GradeObject {
    private String grade;
    private float cement, fine, coarse, standardDeviation;
    private List<GradeObject> allGrades = new ArrayList<>();

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getCement() {
        return cement;
    }

    public void setCement(float cement) {
        this.cement = cement;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }

    public float getCoarse() {
        return coarse;
    }

    public void setCoarse(float coarse) {
        this.coarse = coarse;
    }

    public float getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(float standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public void setAllGrades(List<GradeObject> allGrades) {
        this.allGrades = allGrades;
    }

    public GradeObject(){
    }

    public GradeObject(String grade, float cement, float fine, float coarse, float standardDeviation){
        this.grade = grade;
        this.cement = cement;
        this.fine = fine;
        this.coarse = coarse;
        this.standardDeviation = standardDeviation;
    }

    public List<GradeObject> getAllGrades(){
        allGrades.clear();
        addGrade(new GradeObject("M5", 1f, 5f, 10f, 3.5f));
        addGrade(new GradeObject("M7.5", 1f, 4f, 8f,3.5f));
        addGrade(new GradeObject("M10", 1f, 3f, 6f, 3.5f));
        addGrade(new GradeObject("M15", 1f, 2f, 4f, 3.5f));
        addGrade(new GradeObject("M20", 1f, 1.5f, 3f,4f));
        addGrade(new GradeObject("M25", 1f, 1f, 2f, 4f));
        return allGrades;
    }

    public void addGrade(GradeObject gradeObject){
        allGrades.add(gradeObject);
    }
}
