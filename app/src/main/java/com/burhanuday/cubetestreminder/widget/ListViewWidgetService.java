package com.burhanuday.cubetestreminder.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.burhanuday.cubetestreminder.widget.ListViewRemoteViewsFactory;

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new ListViewRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}
