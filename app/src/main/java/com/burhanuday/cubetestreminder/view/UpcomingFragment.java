package com.burhanuday.cubetestreminder.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.adapter.RecyclerAdapter;
import com.burhanuday.cubetestreminder.interfaces.AddLocationFabListener;
import com.burhanuday.cubetestreminder.interfaces.ShowDetailsListener;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;
import com.burhanuday.cubetestreminder.util.LocationDao;
import com.burhanuday.cubetestreminder.util.LocationDatabase;
import java.util.ArrayList;
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
 * Created by burhanuday on 12-12-2018.
 */
public class UpcomingFragment extends Fragment implements RecyclerAdapter.ListItemClickListener {
    private static final String TAG = "UpcomingFragment";
    private RecyclerAdapter recyclerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LocationDao locationDao;
    private LocationDatabase locationDatabase;
    private List<Location> locationList = new ArrayList<>();
    private AddLocationFabListener addLocationFabListener;
    private ShowDetailsListener showDetailsListener;

    @BindView(R.id.fab_add_new_location)
    FloatingActionButton addNewLocation;

    @BindView(R.id.rv_upcoming)
    RecyclerView upcoming;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        locationDatabase = LocationDatabase.getInstance(getContext());
        locationDao = locationDatabase.locationDao();

        addLocationFabListener = (AddLocationFabListener) getActivity();
        showDetailsListener = (ShowDetailsListener) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upcoming.setLayoutManager(linearLayoutManager);
        upcoming.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(locationList, this);
        upcoming.setAdapter(recyclerAdapter);

        addNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocationFabListener.addLocationFabClicked();
            }
        });
    }

    private void getTodayLocations(){
        compositeDisposable.add(
                locationDao.getByDate(DateUtils.getTodayDates())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Location>>(){

                    @Override
                    public void onSuccess(List<Location> locations) {
                        locationList.clear();
                        locationList.addAll(locations);
                        for (Location location : locations){
                            Log.i(TAG, "onSuccess: id" + location.getId());
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                })
        );
    }

    private void getAllLocations(){
        compositeDisposable.add(
                locationDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Location>>() {
                    @Override
                    public void onSuccess(List<Location> locations) {
                        locationList.clear();
                        locationList.addAll(locations);
                        for (Location location : locations){
                            Log.i(TAG, "onSuccess: id" + location.getId());
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: locations failed " + e.getMessage());
                    }
                })
        );
    }

    @Override
    public void onResume() {
        getTodayLocations();
        Log.i(TAG, "onResume: method call");
        super.onResume();
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
                        getTodayLocations();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onItemClicked(Location location) {
        showDetailsListener.showDetailsFragment(location);
        Log.i(TAG, "onItemClicked: " + location.getName());
    }

    @Override
    public void deleteLocation(Location location) {
        deleteLocationFromDatabase(location);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }
}
