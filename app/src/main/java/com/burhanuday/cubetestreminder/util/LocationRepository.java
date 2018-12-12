package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.burhanuday.cubetestreminder.model.Location;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by burhanuday on 12-12-2018.
 */
public class LocationRepository {
    public static final String DB_NAME = "LocationDatabase";
    private static LocationRepository locationRepository;
    private LocationDatabase locationDatabase;
    private Context context;

    public static LocationRepository getInstance(Context context){
        if (locationRepository == null){
            locationRepository = new LocationRepository(context);
        }
        return locationRepository;
    }

    public LocationRepository(Context context){
        this.context = context;
        locationDatabase = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class,
                DB_NAME).build();
    }

    public void getLocations(final LocationDatabaseCallback callback){
        locationDatabase.locationDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Location>>() {
                    @Override
                    public void accept(List<Location> locations) throws Exception {
                        callback.onLocationsLoaded(locations);
                    }
                });
    }

}
