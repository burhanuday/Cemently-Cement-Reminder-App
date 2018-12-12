package com.burhanuday.cubetestreminder.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Burhanuddin on 04-06-2018.
 */

public class GlobalPrefs {
    Context context;
    private SharedPreferences sharedPreferences;

    public GlobalPrefs(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.burhanuday.cubetestreminder", Context.MODE_PRIVATE);
    }

    public String getLastRefreshed(){
       return sharedPreferences.getString("refreshed_today", "12-06-2018");
    }

    public void setRefreshed(String date){
        sharedPreferences.edit().putString("refreshed_today", date).apply();
    }

    public void setReminderTime(int hour, int minute){
        sharedPreferences.edit().putInt("hour", hour).apply();
        sharedPreferences.edit().putInt("minute", minute).apply();
    }

    public int getHour(){
        return sharedPreferences.getInt("hour", 10);
    }

    public int getMinute(){
        return sharedPreferences.getInt("minute", 0);
    }

    public void lastFragment(int fragment){
        sharedPreferences.edit().putInt("last_fragment", fragment).apply();
    }

    public int getLastFragment(){
        return sharedPreferences.getInt("last_fragment", 0);
    }

    public int daysPast(){
        return sharedPreferences.getInt("days_past", 0);
    }

    public void incrementDaysPast(){
        sharedPreferences.edit().putInt("days_past", daysPast()+1).apply();
    }

    public boolean getDivide(){
        return sharedPreferences.getBoolean("dividePreference", true);
    }

    public void setDivide(boolean value){
        sharedPreferences.edit().putBoolean("dividePreference", value).apply();
    }

}
