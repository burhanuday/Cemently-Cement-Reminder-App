package com.burhanuday.cubetestreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Burhanuddin on 11-06-2018.
 */

public class onBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ReminderManager reminderManager = new ReminderManager(context);
        GlobalPrefs globalPrefs = new GlobalPrefs(context);

        int hour = globalPrefs.getHour();
        int minute = globalPrefs.getMinute();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute);

        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DATE, 1);
        }

        reminderManager.setReminder(calendar);
    }
}
