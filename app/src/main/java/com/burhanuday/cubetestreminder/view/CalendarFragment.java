package com.burhanuday.cubetestreminder.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.adapter.RecyclerAdapter;
import com.burhanuday.cubetestreminder.interfaces.ShowDetailsListener;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;
import com.burhanuday.cubetestreminder.util.LocationDao;
import com.burhanuday.cubetestreminder.util.LocationDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by burhanuday on 18-12-2018.
 */
public class CalendarFragment extends Fragment implements OnDayClickListener, RecyclerAdapter.ListItemClickListener {
    private static final String TAG = "CalendarFragment";
    private List<Date> monthDates = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LocationDao locationDao;
    private LocationDatabase locationDatabase;
    private List<Calendar> calendars = new ArrayList<>();
    private List<Location> dayLocationList = new ArrayList<>();
    private List<Location> monthLocationList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private ShowDetailsListener showDetailsListener;

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @BindView(R.id.rv_calendar_view)
    RecyclerView calendarList;

    private List<EventDay> eventDays = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        locationDatabase = LocationDatabase.getInstance(getContext());
        locationDao = locationDatabase.locationDao();

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        getLocationData(month, year);

        calendarView.setOnDayClickListener(this);

        showDetailsListener = (ShowDetailsListener) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        calendarList.setLayoutManager(linearLayoutManager);
        calendarList.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(dayLocationList, this);
        calendarList.setAdapter(recyclerAdapter);
    }

    private void showEventsDot(){
        Calendar calendar;
        for (Location location : monthLocationList){
            calendar = DateUtils.getCalendar(DateUtils.convertDateToString(location.getDate()));
            eventDays.add(new EventDay(calendar, R.drawable.ic_keyboard_arrow_down));
        }
        calendarView.setEvents(eventDays);
    }

    private void getLocationData(int month, int year){
        monthDates = DateUtils.getMonthDates(month, year);
        getEventsByMonth();
    }

    private void getEventsByMonth(){
        compositeDisposable.add(
                locationDao.getByDate(monthDates)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Location>>(){
                    @Override
                    public void onSuccess(List<Location> locations) {
                        monthLocationList.clear();
                        monthLocationList.addAll(locations);
                        onDayClicked(Calendar.getInstance());
                        showEventsDot();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
        );
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        onDayClicked(eventDay.getCalendar());
    }

    @Override
    public void onItemClicked(Location location) {
        showDetailsListener.showDetailsFragment(location);
        Log.i(TAG, "onItemClicked: " + location.getName());
    }

    private void onDayClicked(Calendar calendar){
        String date = DateUtils.getDate(calendar);
        dayLocationList.clear();
        for (Location location : monthLocationList){
            if (date.equalsIgnoreCase(DateUtils.convertDateToString(location.getDate()))){
                dayLocationList.add(location);
            }
        }
        recyclerAdapter.setHeaderText(DateUtils.getDate(calendar));
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteLocation(Location location) {
        deleteLocationFromDatabase(location);
    }

    private void deleteLocationFromDatabase(Location location){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                locationDao.deleteLocation(location);
                Log.i(TAG, "run: ");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: deleted");
                        getEventsByMonth();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
    }

}
