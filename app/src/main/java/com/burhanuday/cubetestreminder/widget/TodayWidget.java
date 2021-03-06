package com.burhanuday.cubetestreminder.widget;



import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;

import android.appwidget.AppWidgetProvider;

import android.content.Context;

import android.content.Intent;

import android.net.Uri;

import android.widget.RemoteViews;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.view.MainActivity;

import java.util.Random;
//import android.widget.Toast;

public class TodayWidget extends AppWidgetProvider {
    public static int randomNumber = 12345;

    /**
     * this method is called every 30 mins as specified on widgetinfo.xml
     * this method is also called on every phone reboot
     **/

    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager,int[] appWidgetIds) {

        /*int[] appWidgetIds holds ids of multiple instance
         * of your widget
         * meaning you are placing more than one widgets on
         * your homescreen*/

        final int N = appWidgetIds.length;
        for (int i = 0; i<N; ++i) {
            randomNumber = new Random().nextInt(100) + 20;
            RemoteViews remoteViews = updateWidgetListView(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.today_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, ListViewWidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        //svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        svcIntent.setData(Uri.fromParts("content", String.valueOf(appWidgetId+randomNumber), null));
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.lv_widget, svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.lv_widget, R.id.empty_view_card);
        return remoteViews;
    }


}


