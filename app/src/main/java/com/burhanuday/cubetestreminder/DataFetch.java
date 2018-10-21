package com.burhanuday.cubetestreminder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Burhanuddin on 01-06-2018.
 */

//todo fix sorting

public class DataFetch {
    Context context;
    DatabaseHelper databaseHelper;
    String todayString;

    public DataFetch(Context context){
        this.context = context;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String afterDate = day + "-" + (month + 1) + "-" + year;
        this.todayString = afterDate;
    }

    public ArrayList<CementCube> getAll(){
        ArrayList<CementCube> items = new ArrayList<>();
        ArrayList<CompareCementCube> compareCementCubes = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        long rows = databaseHelper.getRowCount();
        CementCube cementCube;
        for (int i=1; i<=rows; i++){
            cementCube = databaseHelper.getProject(i);
            items.add(cementCube);

        }
        databaseHelper.close();
        return items;
    }

    public ArrayList<CementCube> getHistory(){
        ArrayList<CementCube> items = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        long rows = databaseHelper.getRowCountHistory();
        CementCube cementCube;
        for (int i=1; i<=rows && rows>0; i++){
            cementCube = databaseHelper.getHistory(i);
            items.add(cementCube);
        }
        databaseHelper.close();
        return items;
    }

    public ArrayList<CementCube> getToday(){
        Log.i("today", todayString);
        databaseHelper = new DatabaseHelper(context);
        ArrayList<CementCube> items = databaseHelper.searchDate(todayString, 1);
        databaseHelper.close();
        return items;
    }

    public int getDays(String doc, String today) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date todayDate = null, docDate = null;
        try {
            todayDate = dateFormat.parse(today);
            docDate = dateFormat.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = 0;
        // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        if (todayDate != null && docDate != null) {
            diff = todayDate.getTime() - docDate.getTime();
        }
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return ((int) dayCount);
    }
}
