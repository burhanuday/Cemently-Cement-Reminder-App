package com.burhanuday.cubetestreminder;

/**
 * Created by Burhanuddin on 16-05-2018.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class EditAlarm extends AppCompatActivity {
    String date = "";
    TextView doc, location, grade;
    EditText days_3, days_7, days_14, days_21, days_28, days_56, days_3_2, days_3_3, days_7_2, days_7_3, days_14_2;
    EditText days_14_3, days_21_2, days_21_3, days_28_2, days_28_3, days_56_2, days_56_3;
    DatabaseHelper databaseHelper;
    CementCube cementCube = null;
    Button save;
    GlobalPrefs globalPrefs;
    Handler handler = new Handler();
    int tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        this.cementCube = (CementCube) getIntent().getParcelableExtra("parcel_data");
        this.tableNumber = getIntent().getIntExtra("table", 0);
        if (tableNumber == 0){
            Toasty.error(EditAlarm.this, "There was an error!", Toast.LENGTH_SHORT, true).show();
        }
        casting();
       // cementCube = databaseHelper.getProject(1);
        setData();
        databaseHelper = new DatabaseHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAlarm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(EditAlarm.this)
                .setTitle("Go back without saving?")
                .setMessage("Your data will not be saved if you go back now \n Press 'SAVE' button to save data")
                .setNegativeButton("GO BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditAlarm.this, MainActivity.class));
                        finish();
                    }
                })
                .setPositiveButton("CANCEL", null)
                .create();

        alertDialog.show();

    }

    public void editAlarm() { //EXTRA PARAMETER ADD KIA HAI ADDSTRENGTH() MAI. CORRECT IT HERE ACCORDINGLY
        if (!isEmpty(days_3)){
            databaseHelper.addStrength(Float.parseFloat(days_3.getText().toString()), 3, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_3_2)){
            databaseHelper.addStrength(Float.parseFloat(days_3_2.getText().toString()), 3, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_3_3)){
            databaseHelper.addStrength(Float.parseFloat(days_3_3.getText().toString()), 3, cementCube.getId(), 3, tableNumber);
        }
        if (!isEmpty(days_7)){
            databaseHelper.addStrength(Float.parseFloat(days_7.getText().toString()), 7, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_7_2)){
            databaseHelper.addStrength(Float.parseFloat(days_7_2.getText().toString()), 7, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_7_3)){
            databaseHelper.addStrength(Float.parseFloat(days_7_3.getText().toString()), 7, cementCube.getId(), 3, tableNumber);
        }
        if (!isEmpty(days_14)){
            databaseHelper.addStrength(Float.parseFloat(days_14.getText().toString()), 14, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_14_2)){
            databaseHelper.addStrength(Float.parseFloat(days_14_2.getText().toString()), 14, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_14_3)){
            databaseHelper.addStrength(Float.parseFloat(days_14_3.getText().toString()), 14, cementCube.getId(), 3, tableNumber);
        }
        if (!isEmpty(days_21)){
            databaseHelper.addStrength(Float.parseFloat(days_21.getText().toString()), 21, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_21_2)){
            databaseHelper.addStrength(Float.parseFloat(days_21_2.getText().toString()), 21, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_21_3)){
            databaseHelper.addStrength(Float.parseFloat(days_21_3.getText().toString()), 21, cementCube.getId(), 3, tableNumber);
        }
        if (!isEmpty(days_28)){
            databaseHelper.addStrength(Float.parseFloat(days_28.getText().toString()), 28, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_28_2)){
            databaseHelper.addStrength(Float.parseFloat(days_28_2.getText().toString()), 28, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_28_3)){
            databaseHelper.addStrength(Float.parseFloat(days_28_3.getText().toString()), 28, cementCube.getId(), 3, tableNumber);
        }
        if (!isEmpty(days_56)){
            databaseHelper.addStrength(Float.parseFloat(days_56.getText().toString()), 56, cementCube.getId(), 1, tableNumber);
        }
        if (!isEmpty(days_56_2)){
            databaseHelper.addStrength(Float.parseFloat(days_56_2.getText().toString()), 56, cementCube.getId(), 2, tableNumber);
        }
        if (!isEmpty(days_56_3)){
            databaseHelper.addStrength(Float.parseFloat(days_56_3.getText().toString()), 56, cementCube.getId(), 3, tableNumber);
        }
        Toasty.success(EditAlarm.this, "Successfully added strengths", Toast.LENGTH_LONG, true).show();
        startActivity(new Intent(EditAlarm.this, MainActivity.class));
        finish();
    }

    private boolean isEmpty(EditText etText) {
        //false means not empty
        //true means empty
        return etText.getText().toString().trim().length() == 0;
    }

    public void casting(){
        doc = findViewById(R.id.tv_doc);
        location = findViewById(R.id.tv_location);
        grade = findViewById(R.id.tv_grade);
        days_3 = findViewById(R.id.et_s3days);
        days_3_2 = findViewById(R.id.et_s3days2);
        days_3_3 = findViewById(R.id.et_s3days3);
        days_7 = findViewById(R.id.et_s7days);
        days_7_2 = findViewById(R.id.et_s7days2);
        days_7_3 = findViewById(R.id.et_s7days3);
        days_14 = findViewById(R.id.et_s14days);
        days_14_2 = findViewById(R.id.et_s14days2);
        days_14_3 = findViewById(R.id.et_s14days3);
        days_21 = findViewById(R.id.et_s21days);
        days_21_2 = findViewById(R.id.et_s21days2);
        days_21_3 = findViewById(R.id.et_s21days3);
        days_28 = findViewById(R.id.et_s28days);
        days_28_2 = findViewById(R.id.et_s28days2);
        days_28_3 = findViewById(R.id.et_s28days3);
        days_56 = findViewById(R.id.et_s56days);
        days_56_2 = findViewById(R.id.et_s56days2);
        days_56_3 = findViewById(R.id.et_s56days3);
        save = findViewById(R.id.bt_save);
        globalPrefs = new GlobalPrefs(this);
    }


    public void setNotZero(EditText editText, float strength){
        if (strength>0.0){
            editText.setText(Float.toString(strength));
        }
    }



    public void setData(){
        doc.setText(cementCube.getDate1());
        location.setText(cementCube.getLocation());
        grade.setText(cementCube.getConcreteGrade());
        setNotZero(days_3, cementCube.getsThree1());
        setNotZero(days_3_2, cementCube.getsThree2());
        setNotZero(days_3_3, cementCube.getsThree3());
        setNotZero(days_7, cementCube.getsSeven1());
        setNotZero(days_7_2, cementCube.getsSeven2());
        setNotZero(days_7_3, cementCube.getsSeven3());
        setNotZero(days_14, cementCube.getsFourteen1());
        setNotZero(days_14_2, cementCube.getsFourteen2());
        setNotZero(days_14_3, cementCube.getsFourteen3());
        setNotZero(days_21, cementCube.getsTwentyOne1());
        setNotZero(days_21_2, cementCube.getsTwentyOne2());
        setNotZero(days_21_3, cementCube.getsTwentyOne3());
        setNotZero(days_28, cementCube.getsTwentyEight1());
        setNotZero(days_28_2, cementCube.getsTwentyEight2());
        setNotZero(days_28_3, cementCube.getsTwentyEight3());
        setNotZero(days_56, cementCube.getsFiftySix1());
        setNotZero(days_56_2, cementCube.getsFiftySix2());
        setNotZero(days_56_3, cementCube.getsFiftySix3());

    }
}
