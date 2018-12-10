package com.burhanuday.cubetestreminder.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.model.CementCube;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class DatabaseHelper extends SQLiteOpenHelper{

    private Context context;

    private static final int DATABASE_VERSION = 29;
    private static final String DATABASE_NAME = "cubeTestReminder.db";
    private static final String TABLE_NAME1 = "ongoing";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CG = "concreteGrade";
    private static final String KEY_THREE = "three";
    private static final String KEY_SEVEN = "seven";
    private static final String KEY_FOURTEEN = "fourteen";
    private static final String KEY_TWENTYONE = "twentyone";
    private static final String KEY_TWENTYEIGHT = "twentyeight";
    private static final String KEY_FIFTYSIX = "fiftysix";
    private static final String KEY_sTHREE1 = "sthree1";
    private static final String KEY_sSEVEN1 = "sseven1";
    private static final String KEY_sFOURTEEN1 = "sfourteen1";
    private static final String KEY_sTWENTYONE1 = "stwentyone1";
    private static final String KEY_sTWENTYEIGHT1 = "stwentyeight1";
    private static final String KEY_sFIFTYSIX1 = "sfiftysix1";
    private static final String KEY_sTHREE2 = "sthree2";
    private static final String KEY_sSEVEN2 = "sseven2";
    private static final String KEY_sFOURTEEN2 = "sfourteen2";
    private static final String KEY_sTWENTYONE2 = "stwentyone2";
    private static final String KEY_sTWENTYEIGHT2 = "stwentyeight2";
    private static final String KEY_sFIFTYSIX2 = "sfiftysix2";
    private static final String KEY_sTHREE3 = "sthre3e";
    private static final String KEY_sSEVEN3 = "sseven3";
    private static final String KEY_sFOURTEEN3 = "sfourteen3";
    private static final String KEY_sTWENTYONE3 = "stwentyone3";
    private static final String KEY_sTWENTYEIGHT3 = "stwentyeight3";
    private static final String KEY_sFIFTYSIX3 = "sfiftysix3";
    private static final String KEY_after3 = "after3";
    private static final String KEY_after7 = "after7";
    private static final String KEY_after14 = "after14";
    private static final String KEY_after21 = "after21";
    private static final String KEY_after28 = "after28";
    private static final String KEY_after56 = "after56";


    private static final String TABLE_NAME2 = "history";
    private static final String TABLE_NAME3 = "forExport";
    private static final String TABLE_NAME4 = "exportIndi";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_NAME1 + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_LOCATION + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_CG + " TEXT, " +
                KEY_THREE + " INTEGER, " +
                KEY_SEVEN + " INTEGER, " +
                KEY_FOURTEEN + " INTEGER, " +
                KEY_TWENTYONE + " INTEGER, " +
                KEY_TWENTYEIGHT + " INTEGER, " +
                KEY_FIFTYSIX + " INTEGER, " +
                KEY_sTHREE1 + " FLOAT, " +
                KEY_sSEVEN1 + " FLOAT, " +
                KEY_sFOURTEEN1 + " FLOAT, " +
                KEY_sTWENTYONE1 + " FLOAT, " +
                KEY_sTWENTYEIGHT1 + " FLOAT, " +
                KEY_sFIFTYSIX1 + " FLOAT, " +
                KEY_sTHREE2 + " FLOAT, " +
                KEY_sSEVEN2 + " FLOAT, " +
                KEY_sFOURTEEN2 + " FLOAT, " +
                KEY_sTWENTYONE2 + " FLOAT, " +
                KEY_sTWENTYEIGHT2 + " FLOAT, " +
                KEY_sFIFTYSIX2 + " FLOAT, " +
                KEY_sTHREE3 + " FLOAT, " +
                KEY_sSEVEN3 + " FLOAT, " +
                KEY_sFOURTEEN3 + " FLOAT, " +
                KEY_sTWENTYONE3 + " FLOAT, " +
                KEY_sTWENTYEIGHT3 + " FLOAT, " +
                KEY_sFIFTYSIX3 + " FLOAT, " +
                KEY_after3 + " TEXT, " +
                KEY_after7 + " TEXT, " +
                KEY_after14 + " TEXT, " +
                KEY_after21 + " TEXT, " +
                KEY_after28 + " TEXT, " +
                KEY_after56 + " TEXT) ";

                db.execSQL(CREATE_TABLE_PROJECTS);


    String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_NAME2 + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_LOCATION + " TEXT, " +
            KEY_DATE + " TEXT, " +
            KEY_CG + " TEXT, " +
            KEY_THREE + " INTEGER, " +
            KEY_SEVEN + " INTEGER, " +
            KEY_FOURTEEN + " INTEGER, " +
            KEY_TWENTYONE + " INTEGER, " +
            KEY_TWENTYEIGHT + " INTEGER, " +
            KEY_FIFTYSIX + " INTEGER, " +
            KEY_sTHREE1 + " FLOAT, " +
            KEY_sSEVEN1 + " FLOAT, " +
            KEY_sFOURTEEN1 + " FLOAT, " +
            KEY_sTWENTYONE1 + " FLOAT, " +
            KEY_sTWENTYEIGHT1 + " FLOAT, " +
            KEY_sFIFTYSIX1 + " FLOAT, " +
            KEY_sTHREE2 + " FLOAT, " +
            KEY_sSEVEN2 + " FLOAT, " +
            KEY_sFOURTEEN2 + " FLOAT, " +
            KEY_sTWENTYONE2 + " FLOAT, " +
            KEY_sTWENTYEIGHT2 + " FLOAT, " +
            KEY_sFIFTYSIX2 + " FLOAT, " +
            KEY_sTHREE3 + " FLOAT, " +
            KEY_sSEVEN3 + " FLOAT, " +
            KEY_sFOURTEEN3 + " FLOAT, " +
            KEY_sTWENTYONE3 + " FLOAT, " +
            KEY_sTWENTYEIGHT3 + " FLOAT, " +
            KEY_sFIFTYSIX3 + " FLOAT, " +
            KEY_after3 + " TEXT, " +
            KEY_after7 + " TEXT, " +
            KEY_after14 + " TEXT, " +
            KEY_after21 + " TEXT, " +
            KEY_after28 + " TEXT, " +
            KEY_after56 + " TEXT) ";

            db.execSQL(CREATE_TABLE_HISTORY);

        String CREATE_TABLE_EXPORT = "CREATE TABLE " + TABLE_NAME3 + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_LOCATION + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_CG + " TEXT, " +
                "Day_3_1" + " FLOAT, " +
                "Day_3_2" + " FLOAT, " +
                "Day_3_3" + " FLOAT, " +
                "Day_7_1" + " FLOAT, " +
                "Day_7_2" + " FLOAT, " +
                "Day_7_3" + " FLOAT, " +
                "Day_14_1" + " FLOAT, " +
                "Day_14_2" + " FLOAT, " +
                "Day_14_3" + " FLOAT, " +
                "Day_21_1" + " FLOAT, " +
                "Day_21_2" + " FLOAT, " +
                "Day_21_3" + " FLOAT, " +
                "Day_28_1" + " FLOAT, " +
                "Day_28_2" + " FLOAT, " +
                "Day_28_3" + " FLOAT, " +
                "Day_56_1" + " FLOAT, " +
                "Day_56_2" + " FLOAT, " +
                "Day_56_3" + " FLOAT) ";

        db.execSQL(CREATE_TABLE_EXPORT);


        String CREATE_TABLE_EXPORTIndi = "CREATE TABLE " + TABLE_NAME4 + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_LOCATION + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_CG + " TEXT, " +
                "Day" + " TEXT, " +
                "Cube_1" + " FLOAT, " +
                "Cube_2" + " FLOAT, " +
                "Cube_3" + " FLOAT, " +
                "Average" + " FLOAT) ";
        db.execSQL(CREATE_TABLE_EXPORTIndi);

}

    public void compare(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String todayString = df.format(c);

        SQLiteDatabase db = this.getReadableDatabase();
        //onCreate(db);
        String q1 = "SELECT " + KEY_after56 + " FROM " + TABLE_NAME1;
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(q1, null);
        //Toast.makeText(context, Integer.toString(n), Toast.LENGTH_LONG).show();

        Date todayDate = null, sqlDate = null;
        int uhh = 0;

        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    uhh++;
                    try {
                        todayDate = df.parse(todayString);
                        sqlDate = df.parse(cursor.getString(0));

                        if(todayDate.after(sqlDate)) {
                            comparehelper();

                            db.execSQL("UPDATE " + TABLE_NAME1 + " SET id = " + (1000 + uhh) + " WHERE " + KEY_after56 + " = \"" + cursor.getString(0) + "\"");

                            String sqlquery = "INSERT INTO " + TABLE_NAME2 + " SELECT * FROM " + TABLE_NAME1 + " WHERE " + KEY_after56 + " = \"" + cursor.getString(0) + "\"";
                            //Toast.makeText(context, sqlquery, Toast.LENGTH_LONG).show();
                            db.execSQL(sqlquery);

                            sqlquery = "DELETE FROM " + TABLE_NAME1 + " WHERE " + KEY_after56 + " = \"" + cursor.getString(0) + "\"";
                            db.execSQL(sqlquery);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        }
        comparehelper();
    }

    void comparehelper(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();

        Cursor c2 = db.rawQuery("SELECT " + KEY_LOCATION + " FROM " + TABLE_NAME2, null);
        Cursor c1 = db.rawQuery("SELECT " + KEY_LOCATION + " FROM " + TABLE_NAME1, null);

        list.clear();
        list1.clear();

        if (c2 != null) {
            // move cursor to first row
            if (c2.moveToFirst()) {
                do {
                    list.add(c2.getString(0));
                } while (c2.moveToNext());
            }
        }

        if (c1 != null) {
            // move cursor to first row
            if (c1.moveToFirst()) {
                do {
                    list1.add(c1.getString(0));
                    //Log.i("compare list - ", list1.toString());
                } while (c1.moveToNext());
            }
        }


        String sql2 = "";

        try {
            for (int i = 1; i <= list.size(); i++) {
                db.execSQL("UPDATE " + TABLE_NAME2 + " SET " + KEY_ID + " = " + i + " WHERE " + KEY_LOCATION + " = \"" + list.get(i - 1) + "\""); //giving an ordered id to the history table
            }

            for (int i = 1; i <= list1.size(); i++) {
                 sql2 = "UPDATE " + TABLE_NAME1 + " SET " + KEY_ID + " = " + i + " WHERE " + KEY_LOCATION + " = \"" + list1.get(i - 1) + "\"";
                db.execSQL(sql2); //giving an ordered id to the projects table
            }
        }
        catch (Exception e) {
            Toast.makeText(context, sql2, Toast.LENGTH_LONG).show();
            Log.i("compare error - ", list1.toString());
        }
    }


    public void moveRow(String loc, String sourceTable, String destinationTable){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + sourceTable + " SET " + KEY_ID + " = " + (1001) + " WHERE " + KEY_LOCATION + " = \"" + loc + "\"");
        String sqlquery = "INSERT INTO " + destinationTable + " SELECT * FROM " + sourceTable + " WHERE " + KEY_LOCATION + " = \"" + loc + "\"";
        db.execSQL(sqlquery);
        sqlquery = "DELETE FROM " + sourceTable + " WHERE " + KEY_LOCATION + " = \"" + loc + "\"";
        db.execSQL(sqlquery);
        comparehelper();
    }



    public ArrayList<CementCube> searchDate(String date1, int tableNumber){
        SQLiteDatabase db = getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor c2 = db.rawQuery("SELECT * FROM " + ((tableNumber == 1)?TABLE_NAME1:TABLE_NAME2), null);

        ArrayList<CementCube> arrayList = new ArrayList<>();
        arrayList.clear();

        if (c2 != null) {
            // move cursor to first row
            if (c2.moveToFirst()) {
                do {
                    for(int i = 27;i <= 33; i++){
                        if(date1.equalsIgnoreCase(c2.getString((i == 27?2:i)))){
                            CementCube cc = new CementCube(
                                    Integer.parseInt(c2.getString(0)),
                                    c2.getString(1),
                                    c2.getString(2),
                                    c2.getString(3),
                                    Integer.parseInt(c2.getString(4)),
                                    Integer.parseInt(c2.getString(5)),
                                    Integer.parseInt(c2.getString(6)),
                                    Integer.parseInt(c2.getString(7)),
                                    Integer.parseInt(c2.getString(8)),
                                    Integer.parseInt(c2.getString(9)),
                                    Float.parseFloat(c2.getString(10)),
                                    Float.parseFloat(c2.getString(11)),
                                    Float.parseFloat(c2.getString(12)),
                                    Float.parseFloat(c2.getString(13)),
                                    Float.parseFloat(c2.getString(14)),
                                    Float.parseFloat(c2.getString(15)),
                                    Float.parseFloat(c2.getString(16)),
                                    Float.parseFloat(c2.getString(17)),
                                    Float.parseFloat(c2.getString(18)),
                                    Float.parseFloat(c2.getString(19)),
                                    Float.parseFloat(c2.getString(20)),
                                    Float.parseFloat(c2.getString(21)),
                                    Float.parseFloat(c2.getString(22)),
                                    Float.parseFloat(c2.getString(23)),
                                    Float.parseFloat(c2.getString(24)),
                                    Float.parseFloat(c2.getString(25)),
                                    Float.parseFloat(c2.getString(26)),
                                    Float.parseFloat(c2.getString(27)),
                                    c2.getString(28),
                                    c2.getString(29),
                                    c2.getString(30),
                                    c2.getString(31),
                                    c2.getString(32),
                                    c2.getString(33));
                            arrayList.add(cc);
                            Log.i("added", cc.getLocation());
                            Log.i("dbhelper", "item to array");
                        }
                    }
                } while (c2.moveToNext());
            }
        }
        return arrayList;
    }

    public boolean addProject(CementCube cc){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_LOCATION, cc.getLocation());
            values.put(KEY_DATE, cc.getDate1());
            values.put(KEY_CG, cc.getConcreteGrade());
            values.put(KEY_THREE, cc.getThree());
            values.put(KEY_SEVEN, cc.getSeven());
            values.put(KEY_FOURTEEN, cc.getFourteen());
            values.put(KEY_TWENTYONE, cc.getTwentyOne());
            values.put(KEY_TWENTYEIGHT, cc.getTwentyEight());
            values.put(KEY_FIFTYSIX, cc.getFiftySix());
            values.put(KEY_sTHREE1, cc.getsThree1());
            values.put(KEY_sSEVEN1, cc.getsSeven1());
            values.put(KEY_sFOURTEEN1, cc.getsFourteen1());
            values.put(KEY_sTWENTYONE1, cc.getsTwentyOne1());
            values.put(KEY_sTWENTYEIGHT1, cc.getsTwentyEight1());
            values.put(KEY_sFIFTYSIX1, cc.getsFiftySix1());
            values.put(KEY_sTHREE2, cc.getsThree2());
            values.put(KEY_sSEVEN2, cc.getsSeven2());
            values.put(KEY_sFOURTEEN2, cc.getsFourteen2());
            values.put(KEY_sTWENTYONE2, cc.getsTwentyOne2());
            values.put(KEY_sTWENTYEIGHT2, cc.getsTwentyEight2());
            values.put(KEY_sFIFTYSIX2, cc.getsFiftySix2());
            values.put(KEY_sTHREE3, cc.getsThree3());
            values.put(KEY_sSEVEN3, cc.getsSeven3());
            values.put(KEY_sFOURTEEN3, cc.getsFourteen3());
            values.put(KEY_sTWENTYONE3, cc.getsTwentyOne3());
            values.put(KEY_sTWENTYEIGHT3, cc.getsTwentyEight3());
            values.put(KEY_sFIFTYSIX3, cc.getsFiftySix3());
            values.put(KEY_after3, cc.getAfter3());
            values.put(KEY_after7, cc.getAfter7());
            values.put(KEY_after14, cc.getAfter14());
            values.put(KEY_after21, cc.getAfter21());
            values.put(KEY_after28, cc.getAfter28());
            values.put(KEY_after56, cc.getAfter56());

        ArrayList<String> list1 = new ArrayList<String>();
        Boolean b = true;
        Cursor c1 = db.rawQuery("SELECT " + KEY_LOCATION + " FROM " + TABLE_NAME1, null);

        list1.clear();

        if (c1 != null) {
            // move cursor to first row
            if (c1.moveToFirst()) {
                do {
                    list1.add(c1.getString(0));
                } while (c1.moveToNext());
            }
        }

        c1 = db.rawQuery("SELECT " + KEY_LOCATION + " FROM " + TABLE_NAME2, null);
        if (c1 != null) {
            // move cursor to first row
            if (c1.moveToFirst()) {
                do {
                    list1.add(c1.getString(0));
                } while (c1.moveToNext());
            }
        }

        for(int i = 1; i <= list1.size(); i++) {
            if(list1.get(i-1).equalsIgnoreCase(cc.getLocation())) {
                b = false;
            }
        }
        if(b)
            db.insert(TABLE_NAME1, null, values);
        return b;
    }


    public void addStrength(float strength, int day, int id, int n, int tableNumber){//where n is the 1st, 2nd or 3rd strength AND where 1 is for ongoing and 2 is for history
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlcom = "";
        String table = tableNumber==1?TABLE_NAME1:TABLE_NAME2;
        //ContentValues values = new ContentValues();
        switch (day){
            case 3:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTHREE1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTHREE2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTHREE3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
            case 7:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sSEVEN1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sSEVEN2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sSEVEN3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
            case 14:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFOURTEEN1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFOURTEEN2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFOURTEEN3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
            case 21:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYONE1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYONE2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYONE3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
            case 28:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYEIGHT1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYEIGHT2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sTWENTYEIGHT3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
            case 56:
                switch (n) {
                    case 1:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFIFTYSIX1 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 2:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFIFTYSIX2 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;

                    case 3:
                        sqlcom = "UPDATE " + table + " SET " + KEY_sFIFTYSIX3 + " = " + strength + " WHERE " + KEY_ID + " = " + id;
                        break;
                }
                break;
        }
        db.execSQL(sqlcom);

    }

    public CementCube getProject(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        CementCube cementCube = new CementCube(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)),
                Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)),
                Float.parseFloat(cursor.getString(10)),
                Float.parseFloat(cursor.getString(11)),
                Float.parseFloat(cursor.getString(12)),
                Float.parseFloat(cursor.getString(13)),
                Float.parseFloat(cursor.getString(14)),
                Float.parseFloat(cursor.getString(15)),
                Float.parseFloat(cursor.getString(16)),
                Float.parseFloat(cursor.getString(17)),
                Float.parseFloat(cursor.getString(18)),
                Float.parseFloat(cursor.getString(19)),
                Float.parseFloat(cursor.getString(20)),
                Float.parseFloat(cursor.getString(21)),
                Float.parseFloat(cursor.getString(22)),
                Float.parseFloat(cursor.getString(23)),
                Float.parseFloat(cursor.getString(24)),
                Float.parseFloat(cursor.getString(25)),
                Float.parseFloat(cursor.getString(26)),
                Float.parseFloat(cursor.getString(27)),
                cursor.getString(28),
                cursor.getString(29),
                cursor.getString(30),
                cursor.getString(31),
                cursor.getString(32),
                cursor.getString(33));
        cursor.close();


        return cementCube;
    }

    public CementCube getHistory(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        CementCube cementCube = new CementCube(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)),
                Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)),
                Float.parseFloat(cursor.getString(10)),
                Float.parseFloat(cursor.getString(11)),
                Float.parseFloat(cursor.getString(12)),
                Float.parseFloat(cursor.getString(13)),
                Float.parseFloat(cursor.getString(14)),
                Float.parseFloat(cursor.getString(15)),
                Float.parseFloat(cursor.getString(16)),
                Float.parseFloat(cursor.getString(17)),
                Float.parseFloat(cursor.getString(18)),
                Float.parseFloat(cursor.getString(19)),
                Float.parseFloat(cursor.getString(20)),
                Float.parseFloat(cursor.getString(21)),
                Float.parseFloat(cursor.getString(22)),
                Float.parseFloat(cursor.getString(23)),
                Float.parseFloat(cursor.getString(24)),
                Float.parseFloat(cursor.getString(25)),
                Float.parseFloat(cursor.getString(26)),
                Float.parseFloat(cursor.getString(27)),
                cursor.getString(28),
                cursor.getString(29),
                cursor.getString(30),
                cursor.getString(31),
                cursor.getString(32),
                cursor.getString(33));
        cursor.close();


        return cementCube;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        onCreate(db);
    }


    public long getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long rows =  DatabaseUtils.queryNumEntries(db, TABLE_NAME1);
        return rows;
    }

    public long getRowCountHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        long rows =  DatabaseUtils.queryNumEntries(db, TABLE_NAME2);

        return rows;
    }


    public void delrow(int id, int tableNumber){ //1 for projects (ongoing projects), 2 for history table
        SQLiteDatabase db = getWritableDatabase();
        if(tableNumber == 1)
            db.execSQL("DELETE FROM " + TABLE_NAME1 + " WHERE " + KEY_ID +  " = " + id);
        else if(tableNumber == 2)
            db.execSQL("DELETE FROM " + TABLE_NAME2 + " WHERE " + KEY_ID +  " = " + id);
        comparehelper();
    }


    public void exportIndi(int id, int tableNumber){
        SQLiteDatabase db = getWritableDatabase();
        //String table = tableNumber==1?TABLE_NAME1:TABLE_NAME2;
        CementCube cc = tableNumber==1?getProject(id):getHistory(id);
        db.execSQL("DELETE FROM "+ TABLE_NAME4);


        //SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION, cc.getLocation());
        values.put(KEY_DATE, cc.getDate1());
        values.put(KEY_CG, cc.getConcreteGrade());
        values.put("Day", "Day_3");
        values.put("Cube_1", cc.getsThree1());
        values.put("Cube_2", cc.getsThree2());
        values.put("Cube_3", cc.getsThree3());
        values.put("Average", (cc.getsThree3()+cc.getsThree2()+cc.getsThree1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();


        values.put("Day", "Day_7");
        values.put("Cube_1", cc.getsSeven1());
        values.put("Cube_2", cc.getsSeven2());
        values.put("Cube_3", cc.getsSeven3());
        values.put("Average", (cc.getsSeven3()+cc.getsSeven2()+cc.getsSeven1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();


        values.put("Day", "Day_14");
        values.put("Cube_1", cc.getsFourteen1());
        values.put("Cube_2", cc.getsFourteen2());
        values.put("Cube_3", cc.getsFourteen3());
        values.put("Average", (cc.getsFourteen3()+cc.getsFourteen2()+cc.getsFourteen1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();


        values.put("Day", "Day_21");
        values.put("Cube_1", cc.getsTwentyOne1());
        values.put("Cube_2", cc.getsTwentyOne2());
        values.put("Cube_3", cc.getsTwentyOne3());
        values.put("Average", (cc.getsTwentyOne3()+cc.getsTwentyOne2()+cc.getsTwentyOne1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();


        values.put("Day", "Day_28");
        values.put("Cube_1", cc.getsFiftySix1());
        values.put("Cube_2", cc.getsFiftySix2());
        values.put("Cube_3", cc.getsFiftySix3());
        values.put("Average", (cc.getsTwentyEight3()+cc.getsTwentyEight2()+cc.getsTwentyEight1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();


        values.put("Day", "Day_56");
        values.put("Cube_1", cc.getsTwentyEight1());
        values.put("Cube_2", cc.getsTwentyEight2());
        values.put("Cube_3", cc.getsTwentyEight3());
        values.put("Average", (cc.getsFiftySix3()+cc.getsFiftySix2()+cc.getsFiftySix1())/3);

        db.insert(TABLE_NAME4, null, values);
        values.clear();



        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(context, DATABASE_NAME, (Environment.getExternalStorageDirectory().getPath() + "/" + context.getString(R.string.app_name) + "/")); //forExport table
        sqliteToExcel.exportSingleTable(TABLE_NAME4, "exportIndividual.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onCompleted(String filePath) {
                Toasty.info(context, "Export Completed", Toast.LENGTH_SHORT, true).show();
            }
            @Override
            public void onError(Exception e) {
                Toasty.error(context, e.toString(), Toast.LENGTH_SHORT, true).show();
            }
        });


    }


    public void makeExcel(int tableNumber){ //1 for projects (ongoing projects), 2 for history table
        String sql = "";
        sql = "INSERT INTO " + TABLE_NAME3 + " SELECT  " + KEY_ID + ", " + KEY_LOCATION + ", " + KEY_DATE + ", "+ KEY_CG + ", " +
                "" + KEY_sTHREE1 + ", " + KEY_sTHREE2 + ", " + KEY_sTHREE3 + ", " +
                "" + KEY_sSEVEN1 + ", " + KEY_sSEVEN2 + ", " + KEY_sSEVEN3 + ", " +
                "" + KEY_sFOURTEEN1 + ", " + KEY_sFOURTEEN2 + ", " + KEY_sFOURTEEN3 + ", " +
                "" + KEY_sTWENTYONE1 + ", " + KEY_sTWENTYONE2 + ", " + KEY_sTWENTYONE3 + ", " +
                "" + KEY_sTWENTYEIGHT1 + ", " + KEY_sTWENTYEIGHT2 + ", " + KEY_sTWENTYEIGHT3 + ", " +
                "" + KEY_sFIFTYSIX1 + ", " + KEY_sFIFTYSIX2 + ", " + KEY_sFIFTYSIX3 + " FROM ";

        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME3);

        if(tableNumber == 1) {
            sql+=TABLE_NAME1;
        }
        else if(tableNumber == 2){
            sql+=TABLE_NAME2;
        }

        try {
            db.execSQL(sql);
        }
        catch (Exception e){
            Toasty.error(context, "Error Occurred", Toast.LENGTH_SHORT, true).show();
        }

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        //Toast.makeText(context, Environment.getExternalStorageDirectory().getPath() + "/Cement Cube", Toast.LENGTH_LONG).show();

        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(context, DATABASE_NAME, (Environment.getExternalStorageDirectory().getPath() + "/" + context.getString(R.string.app_name) + "/")); //forExport table
        sqliteToExcel.exportSingleTable(TABLE_NAME3, "export.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onCompleted(String filePath) {
                Toasty.info(context, "Export Completed", Toast.LENGTH_SHORT, true).show();
            }
            @Override
            public void onError(Exception e) {
                Toasty.error(context, e.toString(), Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public void makeBackup(){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()  + "/" + context.getString(R.string.app_name) + "/", "backups");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        SQLiteToExcel sqliteToExcel1 = new SQLiteToExcel(context, DATABASE_NAME, (Environment.getExternalStorageDirectory().getPath() + "/" + context.getString(R.string.app_name) + "/backups/"));
        sqliteToExcel1.exportSingleTable(TABLE_NAME1, "ongoing.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onCompleted(String filePath) {
                //Toasty.info(context, "Ongoing Table Backed Up", Toast.LENGTH_SHORT, true).show();
            }
            @Override
            public void onError(Exception e) {
                Toasty.error(context, e.toString(), Toast.LENGTH_SHORT, true).show();
            }
        });

        SQLiteToExcel sqliteToExcel2 = new SQLiteToExcel(context, DATABASE_NAME, (Environment.getExternalStorageDirectory().getPath() + "/"+context.getString(R.string.app_name)+"/backups/"));

        sqliteToExcel2.exportSingleTable(TABLE_NAME2, "history.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onCompleted(String filePath) {
                //Toasty.info(context, "History Table Backed Up", Toast.LENGTH_SHORT, true).show();
            }
            @Override
            public void onError(Exception e) {
                Toasty.error(context, e.toString(), Toast.LENGTH_SHORT, true).show();
            }
        });

        Toasty.success(context, "Backup Completed", Toast.LENGTH_SHORT, true).show();
    }

    public void recoverFromBackup(){
        new android.support.v7.app.AlertDialog.Builder(context)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Note")
                .setCancelable(false)
                .setMessage("Your current data in the app will be deleted. Do you want to continue?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ExcelToSQLite excelToSQLite1 = new ExcelToSQLite(context, DATABASE_NAME, true);
                        excelToSQLite1.importFromFile(Environment.getExternalStorageDirectory().getPath() + "/"+context.getString(R.string.app_name)+"/backups/ongoing.xls", new ExcelToSQLite.ImportListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onCompleted(String dbName) {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                        ExcelToSQLite excelToSQLite2 = new ExcelToSQLite(context, DATABASE_NAME, true);

                        excelToSQLite2.importFromFile(Environment.getExternalStorageDirectory().getPath() + "/"+context.getString(R.string.app_name)+"/backups/history.xls", new ExcelToSQLite.ImportListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onCompleted(String dbName) {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                        Toasty.success(context, "Done", Toast.LENGTH_SHORT, true).show();

                    }
                })
                .show();
    }

}
