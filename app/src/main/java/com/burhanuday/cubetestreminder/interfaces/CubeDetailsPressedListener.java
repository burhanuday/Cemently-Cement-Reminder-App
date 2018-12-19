package com.burhanuday.cubetestreminder.interfaces;

import android.support.v4.app.Fragment;

import com.burhanuday.cubetestreminder.model.Cube;

/**
 * Created by burhanuday on 18-12-2018.
 */
public interface CubeDetailsPressedListener {
    void onShowCubeDetails(Cube cube, Fragment fragment);
}
