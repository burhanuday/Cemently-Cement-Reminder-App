package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.burhanuday.cubetestreminder.model.Location;

/**
 * Created by burhanuday on 12-12-2018.
 */
@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {

    private static volatile LocationDatabase INSTANCE;

    public static LocationDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (LocationDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocationDatabase.class, "locations").build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract LocationDao locationDao();
}
