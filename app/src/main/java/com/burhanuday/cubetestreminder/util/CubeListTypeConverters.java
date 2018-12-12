package com.burhanuday.cubetestreminder.util;

import android.arch.persistence.room.TypeConverter;

import com.burhanuday.cubetestreminder.model.Cube;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by burhanuday on 12-12-2018.
 */
public class CubeListTypeConverters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Cube> stringToCubeList(String data){
        if (data == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Cube>>(){}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String cubeListToString(List<Cube> cubes){
        return gson.toJson(cubes);
    }
}
