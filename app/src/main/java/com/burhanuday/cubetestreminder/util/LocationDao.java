package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.burhanuday.cubetestreminder.model.Location;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by burhanuday on 12-12-2018.
 */
@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(Location location);

    @Update
    void updateLocation(Location... location);

    @Delete
    void deleteLocation(Location location);

    @Query("SELECT * FROM locations")
    Single<List<Location>> getAll();

    @TypeConverters(DateConverter.class)
    @Query("SELECT * FROM locations WHERE date IN (:dates)")
    Single<List<Location>> getByDate(List<Date> dates);

    @Query("SELECT * FROM locations WHERE id = :id")
    Single<Location> getLocationById(int id);

    @TypeConverters(DateConverter.class)
    @Query("SELECT * FROM locations WHERE date BETWEEN :start AND :end")
    Single<List<Location>> getLocationsByMonth(Date start, Date end);

    @Query("SELECT * FROM locations WHERE name LIKE :string")
    Single<List<Location>> getLocationByName(String string);
}
