package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.burhanuday.cubetestreminder.model.Location;

/**
 * Created by burhanuday on 12-12-2018.
 */
@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
