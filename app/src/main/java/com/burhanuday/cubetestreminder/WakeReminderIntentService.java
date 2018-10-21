package com.burhanuday.cubetestreminder;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.annotation.Nullable;

/**
 * Created by Burhanuddin on 11-06-2018.
 */

public abstract class WakeReminderIntentService extends IntentService {
    abstract void doReminderWork(Intent intent);

    public static final String LOCK_NAME_STATIC = "com.burhanuday.cubetestreminder.Static";
    private static PowerManager.WakeLock lockStatic = null;

    public static void acquireStaticLock(Context context){
        getLock(context).acquire();
    }

    synchronized private static PowerManager.WakeLock getLock(Context context){
        if (lockStatic == null){
            PowerManager mgr = (PowerManager)context.getSystemService(Context.POWER_SERVICE);

            lockStatic = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME_STATIC);
            lockStatic.setReferenceCounted(true);
        }

        return (lockStatic);
    }

    public WakeReminderIntentService(String name){
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            doReminderWork(intent);
        }finally {
            getLock(this).release();
        }
    }
}
