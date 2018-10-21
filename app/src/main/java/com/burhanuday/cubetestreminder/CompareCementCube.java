package com.burhanuday.cubetestreminder;

/**
 * Created by Burhanuddin on 06-06-2018.
 */

public class CompareCementCube {
    CementCube cementCube;
    int nearestDate;

    public CompareCementCube(CementCube cementCube, int nearestDate){
        this.cementCube = cementCube;
        this.nearestDate = nearestDate;
    }

    public CementCube getCementCube() {
        return cementCube;
    }

    public int getNearestDate() {
        return nearestDate;
    }
}
