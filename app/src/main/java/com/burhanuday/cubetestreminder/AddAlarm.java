package com.burhanuday.cubetestreminder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AddAlarm extends AppCompatActivity {

    String date = "";
    int mYear, mMonth, mDay;
    EditText doc, location, grade;
    CheckBox days_3, days_7, days_14, days_21, days_28, days_56;
    DatabaseHelper databaseHelper;
    TextView days3, days7, days14, days21, days28, days56;
    customEncryption ce;
    GlobalPrefs globalPrefs;
    Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        initialise();

        doc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    datePicker();
                }
                return true;
            }
        });

        /*customEncryption encryption = new customEncryption();
        Toast.makeText(this, encryption.decode("ȓȑȗȏȗȎȑȔȐ!ᖛᗆ ᗉᗁᗄᖽᖷᖶ ᖴᗁᖻ"), Toast.LENGTH_SHORT).show();*/
        /*Intent myIntent = new Intent(AddAlarm.this, Backup.class);
        //myIntent.putExtra("key", value); //Optional parameters
        AddAlarm.this.startActivity(myIntent);*/
        //DatabaseHelper test = new DatabaseHelper(this);
        //test.exportIndi(2, 1);
        /*test.fromBackup();
        /*test.moveRow("HMM3", "history", "projects");
        ArrayList<CementCube> arrayList = test.searchDate("19-6-2018", 1);
        String s = "";
        for(int i = 0; i < arrayList.size(); i++){
            CementCube cc = arrayList.get(i);
            s += cc.getId() + ", ";
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        /*
        //test.delrow(1, 2);
        //test.comparehelper();
        /*if(isStoragePermissionGranted())
            test.makeExcel(2);*/
        /*ArrayList<CementCube> list = new ArrayList<CementCube>();
        list.add(new CementCube(0, "block 3c", "31-6-2018", "m31", 1,1 ,1 ,
                1, 1, 1, 31, 12, 24, 45, 34,34,
                " "," ", " ", " ", " ", " "));
        list.add(new CementCube(0, "block 3c", "31-6-2018", "m31", 1,1 ,1 ,
                1, 1, 1, 31, 12, 24, 45, 34,34,
                " "," ", " ", " ", " ", " "));
        test.addProjectbyArray(list);*/
        //test.compare();
        //Toast.makeText(getApplicationContext(), t, Toast.LENGTH_LONG).show();
        //test.onCreate(null);

        isStoragePermissionGranted();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()){
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(AddAlarm.this)
                .setTitle("Go back without saving?")
                .setMessage("Your data will not be saved if you go back now \n Press 'SAVE' button to save data")
                .setNegativeButton("GO BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AddAlarm.this, MainActivity.class));
                        finish();
                    }
                })
                .setPositiveButton("CANCEL", null)
                .create();

        alertDialog.show();
    }

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        doc.setText(date);
                        days3.setText(calcDate(doc.getText().toString(),3));
                        days7.setText(calcDate(doc.getText().toString(),7));
                        days14.setText(calcDate(doc.getText().toString(),14));
                        days21.setText(calcDate(doc.getText().toString(),21));
                        days28.setText(calcDate(doc.getText().toString(),28));
                        days56.setText(calcDate(doc.getText().toString(),56));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void saveAlarm(View view) {
        //handle save button touch here
        if (checkNotEmpty()) {
            //create row in database here
            CementCube cementCube = new CementCube(0, location.getText().toString(), doc.getText().toString(), grade.getText().toString(),
                    checkChecked(), checkChecked(), checkChecked(), checkChecked(), checkChecked(),
                    checkChecked(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    calcDate(doc.getText().toString(), 3), calcDate(doc.getText().toString(), 7),
                    calcDate(doc.getText().toString(), 14), calcDate(doc.getText().toString(), 21),
                    calcDate(doc.getText().toString(), 28), calcDate(doc.getText().toString(), 56));
            boolean b = databaseHelper.addProject(cementCube);
            if(b) {
                databaseHelper.compare();
                Toasty.success(this, "Reminders Added!", Toast.LENGTH_SHORT, true).show();
                startActivity(new Intent(AddAlarm.this, MainActivity.class));
                finish();
            }
            else{
                Toasty.warning(this, "Location Cannot Be Same", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    public void initialise(){
        doc = findViewById(R.id.et_doc);
        location = findViewById(R.id.et_location);
        grade = findViewById(R.id.et_grade);

        days_3 = findViewById(R.id.ch_3days);
        days_7 = findViewById(R.id.ch_7days);
        days_14 = findViewById(R.id.ch_14days);
        days_21 = findViewById(R.id.ch_21days);
        days_28 = findViewById(R.id.ch_28days);
        days_56 = findViewById(R.id.ch_56days);

        days3 = findViewById(R.id.tv_3days);
        days7 = findViewById(R.id.tv_7days);
        days14 = findViewById(R.id.tv_14days);
        days21 = findViewById(R.id.tv_21days);
        days28 = findViewById(R.id.tv_28days);
        days56 = findViewById(R.id.tv_56days);

        databaseHelper = new DatabaseHelper(AddAlarm.this);
        ce = new customEncryption();
        globalPrefs = new GlobalPrefs(this);
    }


    public boolean checkNotEmpty(){
        if (location.getText().toString().isEmpty() || doc.getText().toString().isEmpty() || grade.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please fill all required fields", Toast.LENGTH_SHORT, true).show();
            return false;
        }

        return true;

    }

    public int checkChecked(){
     /*  if (checkBox.isChecked()){
           return 1;
       }
       */
       return 0;
    }

    public String calcDate(String doc, int afterDays){
        Date todayDate = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            todayDate = format.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.add(Calendar.DAY_OF_YEAR, afterDays);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String afterDate = day + "-" + (month + 1) + "-" + year;
        return afterDate;
    }
}
