package com.burhanuday.cubetestreminder.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.burhanuday.cubetestreminder.util.OnAlarmReceiver;

import java.util.Calendar;

/**
 * Created by Burhanuddin on 11-06-2018.
 */

public class ReminderManager {
    public Context context;
    private AlarmManager alarmManager;

    public ReminderManager(Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setReminder(Calendar when){
        Intent i = new Intent(context, OnAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
