package com.burhanuday.cubetestreminder.model;

import java.io.Serializable;

/**
 * Created by burhanuday on 10-12-2018.
 */
public class Cube implements Serializable {
    private float day3Strength = 0;
    private float day5Strength = 0;
    private float day7Strength = 0;
    private float day14Strength = 0;
    private float day21Strength = 0;
    private float day28Strength = 0;
    private float day56Strength = 0;

    public Cube(){}

    public float getDay3Strength() {
        return day3Strength;
    }

    public void setDay3Strength(float day3Strength) {
        this.day3Strength = day3Strength;
    }

    public float getDay5Strength() {
        return day5Strength;
    }

    public void setDay5Strength(float day5Strength) {
        this.day5Strength = day5Strength;
    }

    public float getDay7Strength() {
        return day7Strength;
    }

    public void setDay7Strength(float day7Strength) {
        this.day7Strength = day7Strength;
    }

    public float getDay14Strength() {
        return day14Strength;
    }

    public void setDay14Strength(float day14Strength) {
        this.day14Strength = day14Strength;
    }

    public float getDay21Strength() {
        return day21Strength;
    }

    public void setDay21Strength(float day21Strength) {
        this.day21Strength = day21Strength;
    }

    public float getDay28Strength() {
        return day28Strength;
    }

    public void setDay28Strength(float day28Strength) {
        this.day28Strength = day28Strength;
    }

    public float getDay56Strength() {
        return day56Strength;
    }

    public void setDay56Strength(float day56Strength) {
        this.day56Strength = day56Strength;
    }
}
