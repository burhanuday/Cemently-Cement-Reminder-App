package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.burhanuday.cubetestreminder.model.Location;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by burhanuday on 12-12-2018.
 */
@Dao
public interface LocationDao {
    @Insert
    void insertLocation(Location location);

    @Update
    void updateLocation(Location location);

    @Delete
    void deleteLocation(Location location);

    @Query("SELECT * FROM locations")
    Maybe<List<Location>> getAll();
}
