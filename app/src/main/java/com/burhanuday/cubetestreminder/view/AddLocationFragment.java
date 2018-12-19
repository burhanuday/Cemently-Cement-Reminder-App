package com.burhanuday.cubetestreminder.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.interfaces.LocationSaveButtonListener;
import com.burhanuday.cubetestreminder.model.Cube;
import com.burhanuday.cubetestreminder.model.Location;
import com.burhanuday.cubetestreminder.util.DateUtils;
import com.burhanuday.cubetestreminder.util.LocationDao;
import com.burhanuday.cubetestreminder.util.LocationDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by burhanuday on 13-12-2018.
 */
public class AddLocationFragment extends Fragment {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LocationDao locationDao;
    private LocationDatabase locationDatabase;
    private static final String TAG = "AddLocationFragment";
    private LocationSaveButtonListener locationSaveButtonListener;

    @BindView(R.id.btn_save_location)
    Button saveLocation;
    
    @BindView(R.id.et_location)
    EditText location;
    
    @BindView(R.id.et_doc)
    EditText doc;
    
    @BindView(R.id.et_grade)
    EditText grade;

    @BindView(R.id.btn_cancel)
    Button cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        locationDatabase = LocationDatabase.getInstance(getContext());
        locationDao = locationDatabase.locationDao();
        locationSaveButtonListener = (LocationSaveButtonListener) getActivity();

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });

        doc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    datePicker();
                }
                return true;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationSaveButtonListener.saveButtonClicked();
            }
        });
    }

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        doc.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    
    private boolean validateFields(){
        if (TextUtils.isEmpty(location.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(grade.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(doc.getText().toString())){
            return false;
        }
        return true;
    }

    private void addLocation(){
        if (!validateFields()){
            Toasty.error(getContext(), "All fields are required", Toast.LENGTH_SHORT, true).show();
            return;
        }
        List<Cube> cubeList = new ArrayList<>();

        Location location = new Location(this.location.getText().toString(), cubeList, DateUtils.convertToDate(doc.getText().toString()),
                    grade.getText().toString());
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                locationDao.insertLocation(location);
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
                        Log.i(TAG, "onComplete: location added successfully to room");
                        locationSaveButtonListener.saveButtonClicked();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: error adding location");
                        locationSaveButtonListener.saveButtonClicked();
                    }
                });
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
