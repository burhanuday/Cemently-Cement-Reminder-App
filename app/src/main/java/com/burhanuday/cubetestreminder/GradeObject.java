package com.burhanuday.cubetestreminder;

import java.util.ArrayList;
import java.util.List;

public class GradeObject {
    public String grade;
    float cement, fine, coarse, standardDeviation;
    List<GradeObject> allGrades = new ArrayList<>();

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
