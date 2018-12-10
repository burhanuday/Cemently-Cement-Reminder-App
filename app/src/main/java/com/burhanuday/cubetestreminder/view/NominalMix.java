package com.burhanuday.cubetestreminder.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.model.GradeObject;

import java.util.ArrayList;
import java.util.List;
import es.dmoral.toasty.Toasty;

public class NominalMix extends AppCompatActivity {
    TextView cem, fineAgg, coarseAgg;
    Spinner grade;
    List<GradeObject> allGrades = new ArrayList<>();
    GradeObject gradeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominal_mix);
        casting();
        gradeObject = new GradeObject();
        allGrades = gradeObject.getAllGrades();
        spinnerChange();
    }

    private void casting(){
        grade = findViewById(R.id.spinner_grade);
        cem = findViewById(R.id.tv_cement);
        fineAgg = findViewById(R.id.tv_fineAgg);
        coarseAgg = findViewById(R.id.tv_coarseAgg);
    }

    public void calculate(){
        float totalparts = gradeObject.getCement() + gradeObject.getFine() + gradeObject.getCoarse();
        float vol_1_part = (float)(1/5.5) * totalparts;
        float cement = gradeObject.getCement() * vol_1_part;
        float fineAgg = gradeObject.getFine() * vol_1_part;
        float coarseAgg = gradeObject.getCoarse() * vol_1_part;
    }

    public String formatFloat(float f){
        if (f == (long) f){
            return String.format("%d", (long)f);
        }else {
            return String.format("%s", f);
        }
    }

    public boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }

    public List<String> getGradeList(){
        List<String> gradeList = new ArrayList<>();
        for (int i=0; i<allGrades.size(); i++){
            gradeList.add(allGrades.get(i).getGrade());
        }
        return gradeList;
    }

    public void spinnerChange(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getGradeList());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(arrayAdapter);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeObject = allGrades.get(position);
                calculate();
                cem.setText(formatFloat(gradeObject.getCement()));
                fineAgg.setText(formatFloat(gradeObject.getFine()));
                coarseAgg.setText(formatFloat(gradeObject.getCoarse()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toasty.warning(NominalMix.this, "Select a grade", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
}