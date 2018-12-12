package com.burhanuday.cubetestreminder.util;

import com.burhanuday.cubetestreminder.model.Location;

import java.util.List;

/**
 * Created by burhanuday on 12-12-2018.
 */
public interface LocationDatabaseCallback {

    void onLocationAdded();

    void onLocationsLoaded(List<Location> locations);

    void onLocationDeleted();

    void onLocationUpdated();

    void onDataNotAvailable();
}
