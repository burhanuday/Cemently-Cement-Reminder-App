package com.burhanuday.cubetestreminder.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.model.CementCube;
import com.burhanuday.cubetestreminder.util.DataFetch;

import java.util.ArrayList;
import java.util.List;

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<CementCube> cubes = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;


    public ListViewRemoteViewsFactory(Context context, Intent intent) {

        this.context = context;
       // appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
          //      AppWidgetManager.INVALID_APPWIDGET_ID);

        appWidgetId = Integer.valueOf(intent.getData().getSchemeSpecificPart())
                - TodayWidget.randomNumber;
        populateListItem();
    }

    private void populateListItem() {
        DataFetch dataFetch = new DataFetch(context);
        cubes = dataFetch.getToday();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_row);
        CementCube listItem = cubes.get(position);
        remoteView.setTextViewText(R.id.widget_cube_location, listItem.getLocation());
        remoteView.setTextViewText(R.id.widget_cube_doc, listItem.getDate1());
        remoteView.setTextViewText(R.id.widget_cube_grade, listItem.getConcreteGrade());
        return remoteView;
    }

    @Override
    public int getCount() {
        return cubes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onCreate() {

    }

    public void onDataSetChanged() {
        // Fetching JSON data from server and add them to records arraylist

    }

    @Override
    public void onDestroy() {

    }

    public int getViewTypeCount() {

        return 1;

    }


    public boolean hasStableIds() {

        return true;

    }

    public RemoteViews getLoadingView() {

        return null;

    }

}
