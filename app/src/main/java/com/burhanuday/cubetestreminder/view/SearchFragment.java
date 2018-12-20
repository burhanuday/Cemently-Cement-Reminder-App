package com.burhanuday.cubetestreminder.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.adapter.RecyclerAdapter;
import com.burhanuday.cubetestreminder.interfaces.ShowDetailsListener;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;
import com.burhanuday.cubetestreminder.util.LocationDao;
import com.burhanuday.cubetestreminder.util.LocationDatabase;

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

import static android.support.constraint.Constraints.TAG;

/**
 * Created by burhanuday on 20-12-2018.
 */

public class SearchFragment extends Fragment implements RecyclerAdapter.ListItemClickListener ,
        SearchView.OnQueryTextListener {

    @BindView(R.id.rv_search_list)
    RecyclerView recyclerView;

    @BindView(R.id.searchView)
    SearchView searchView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LocationDao locationDao;
    private LocationDatabase locationDatabase;
    private List<Location> locationList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private ShowDetailsListener showDetailsListener;
    private static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        locationDatabase = LocationDatabase.getInstance(getContext());
        locationDao = locationDatabase.locationDao();
        showDetailsListener = (ShowDetailsListener) getActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(locationList, this);
        recyclerAdapter.setHeaderText("");
        recyclerView.setAdapter(recyclerAdapter);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        StringBuilder stringBuilder = new StringBuilder("%");
        stringBuilder.append(s);
        stringBuilder.append("%");
        getLocationsByName(stringBuilder.toString());
        recyclerAdapter.setHeaderText(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void getLocationsByName(String s){
        compositeDisposable.add(
                locationDao.getLocationByName(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Location>>(){
                    @Override
                    public void onSuccess(List<Location> locations) {
                        locationList.clear();
                        locationList.addAll(locations);
                        recyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: could not retrieve by name");
                    }
                })
        );
    }
}
