package com.burhanuday.cubetestreminder.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.interfaces.CubeDetailsPressedListener;
import com.burhanuday.cubetestreminder.interfaces.TableRowClickListener;
import com.burhanuday.cubetestreminder.model.Cube;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;
import com.burhanuday.cubetestreminder.util.LocationDao;
import com.burhanuday.cubetestreminder.util.LocationDatabase;
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
 * Created by burhanuday on 15-12-2018.
 */
public class LocationDetailsFragment extends Fragment implements TableRowClickListener {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LocationDao locationDao;
    private LocationDatabase locationDatabase;
    private static final String TAG = "LocationDetailsFragment";
    private Location location;
    private TableRowClickListener tableRowClickListener;
    private CubeDetailsPressedListener cubeDetailsPressedListener;
    private int locationClickedPosition;
    public final static int RESULT_CODE_DELETE = 1233;

    @BindView(R.id.tv_details_location_name)
    TextView name;

    @BindView(R.id.tv_details_location_doc)
    TextView doc;

    @BindView(R.id.tv_details_location_grade)
    TextView grade;

    @BindView(R.id.tlGridTable)
    TableLayout cubeGrid;
    
    @BindView(R.id.ll_add_new_cube)
    LinearLayout addNewCube;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        locationDatabase = LocationDatabase.getInstance(getContext());
        locationDao = locationDatabase.locationDao();
        tableRowClickListener = LocationDetailsFragment.this;
        cubeDetailsPressedListener = (CubeDetailsPressedListener) getActivity();

        Bundle bundle = getArguments();
        if (bundle!= null && bundle.containsKey("location_object")){
            location = (Location) bundle.getSerializable("location_object");
            setUpUI();
            Log.i(TAG, "onViewCreated: " + location.getId());
            Log.i(TAG, "onViewCreated: " + location.getCubeList().size());
        }

        addNewCube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: add new cube pressed");
                Cube cube = new Cube();
                location.getCubeList().add(cube);
                cubeGrid.removeAllViews();
                setUpUI();
            }
        });
    }

    private void setUpUI(){
        name.setText(location.getName());
        doc.setText(DateUtils.convertDateToString(location.getDate()));
        grade.setText(location.getGrade());

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow headerRow = new TableRow(getContext());

        headerRow.addView(addToView(layoutInflater, "Cube"));
        headerRow.addView(addToView(layoutInflater, "Day 3"));
        headerRow.addView(addToView(layoutInflater, "Day 5"));
        headerRow.addView(addToView(layoutInflater, "Day 7"));
        headerRow.addView(addToView(layoutInflater, "Day 14"));
        headerRow.addView(addToView(layoutInflater, "Day 21"));
        headerRow.addView(addToView(layoutInflater, "Day 28"));
        headerRow.addView(addToView(layoutInflater, "Day 56"));
        cubeGrid.addView(headerRow);

        for (Cube cube : location.getCubeList()){
            TableRow tableRow = new TableRow(getContext());
            tableRow.addView(addToView(layoutInflater, "Cube " + String.valueOf(location.getCubeList().indexOf(cube) + 1)));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay3Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay5Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay7Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay14Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay21Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay28Strength())));
            tableRow.addView(addToView(layoutInflater, String.valueOf(cube.getDay56Strength())));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tableRowClickListener.onRowClicked(cube);
                    locationClickedPosition = location.getCubeList().indexOf(cube);
                }
            });

            cubeGrid.addView(tableRow, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
    }

    private View addToView(LayoutInflater layoutInflater, String text){
        View view = layoutInflater.inflate(R.layout.cell_cube_grid, null);
        view.setMinimumWidth(30);
        TextView textView = view.findViewById(R.id.cell_cube);
        if (text.equals("0.0")){
            textView.setText("");
        }else {
            textView.setText(text);
        }
        return view;
    }

    private void getLocationById(int id){
        compositeDisposable.add(
                locationDao.getLocationById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        Log.i(TAG, "onSuccess: location: " + location.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: location: " + e.getMessage());
                    }
                })
        );
    }

    private void updateLocation(){
        Log.i(TAG, "updateLocation: ");

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                locationDao.updateLocation(location);
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
                        Log.i(TAG, "onComplete: updated");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK){
            Cube cube = (Cube) data.getSerializableExtra("cube_added");
            newCubeAdded(cube);
        }

        if (requestCode == 123 && resultCode == LocationDetailsFragment.RESULT_CODE_DELETE){
            deleteCube();
        }
    }

    private void deleteCube(){
        location.getCubeList().remove(locationClickedPosition);
        updateLocation();
    }

    private void newCubeAdded(Cube cube){
        location.getCubeList().set(locationClickedPosition, cube);
        updateLocation();
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onRowClicked(Cube cube) {
        Log.i(TAG, "onRowClicked: " );
        cubeDetailsPressedListener.onShowCubeDetails(cube, LocationDetailsFragment.this);
    }
}
