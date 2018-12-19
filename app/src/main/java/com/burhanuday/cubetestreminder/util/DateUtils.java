package com.burhanuday.cubetestreminder.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by burhanuday on 13-12-2018.
 */
public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String getDaysDifference(String doc) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date docDate = null;
        Date todayDate = getTodaysDate();
        try {
            docDate = dateFormat.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = 0;
        if (todayDate != null && docDate != null) {
            diff = todayDate.getTime() - docDate.getTime();
        }
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return ("" + (int) dayCount);
    }

    public static String afterDaysDate(String doc, int afterDays){
        Date todayDate = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            todayDate = format.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.add(Calendar.DAY_OF_YEAR, afterDays);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (day + "-" + (month + 1) + "-" + year);
    }

    private static Date beforeDaysDate(Date doc, int beforeDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(doc);
        calendar.add(Calendar.DAY_OF_YEAR, -beforeDays);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return DateUtils.convertToDate(day + "-" + (month + 1) + "-" + year);
    }

    private static Date getTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return DateUtils.convertToDate(day + "-" + (month + 1) + "-" + year);
    }

    public static List<Date> getTodayDates(){
        List<Date> dates = new ArrayList<>();
        dates.add(beforeDaysDate(getTodaysDate(), 0));
        dates.add(beforeDaysDate(getTodaysDate(), 3));
        dates.add(beforeDaysDate(getTodaysDate(), 5));
        dates.add(beforeDaysDate(getTodaysDate(), 7));
        dates.add(beforeDaysDate(getTodaysDate(), 14));
        dates.add(beforeDaysDate(getTodaysDate(), 21));
        dates.add(beforeDaysDate(getTodaysDate(), 28));
        dates.add(beforeDaysDate(getTodaysDate(), 56));
        return dates;
    }

    public static List<Date> getMonthDates(int month, int year){
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        while (month==calendar.get(Calendar.MONTH)) {
            dates.add(DateUtils.convertToDate(getDate(calendar)));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    public static String getDate(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day + "-" + (month + 1) + "-" + year;
    }

    public static Calendar getCalendar(String date){
        Calendar cal = Calendar.getInstance();
        Date parsedString = null;
        try {
            parsedString = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(parsedString);
        return cal;
    }

    public static Date convertToDate(String date) {

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToString(Date date){
        return dateFormat.format(date);
    }
}