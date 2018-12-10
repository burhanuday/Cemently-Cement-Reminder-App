package com.burhanuday.cubetestreminder.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanuday.cubetestreminder.R;

import es.dmoral.toasty.Toasty;

public class DesignMix extends AppCompatActivity {
    Spinner spGrade, spExposure, spConcPlacement, spAggZoning, spMaxAggSize, spSlump;
    EditText etwcRatio, admix_vol;
    Button calculate;

    //variables for calculation
    int valGrade;
    float valwcRatio;
    int maxSizeAgg;
    int slump, zone;
    float cementContent;
    int considerPlasti = 0;
    //0 for pump , 1 for manual
    int concPlace;
    //to display
    float targetStrength;
    float cement, flyAsh;
    float waterContent;
    float volCoarseAgg;
    float volFineAgg;
    float _10mm_agg, _20mm_agg, crushed_sand;
    float plasti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mix);
        casting();
        setSpinners();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etwcRatio.getText().toString().length() == 0){
                    Toasty.error(DesignMix.this, "w/c ratio cannot be empty", Toast.LENGTH_SHORT, true).show();
                }
                if (admix_vol.getText().toString().length() != 0){
                    plasti = Float.parseFloat(admix_vol.getText().toString());
                    considerPlasti = 1;
                }else {
                    considerPlasti = 0;
                }
                valwcRatio = Float.parseFloat(etwcRatio.getText().toString());
                targetStrength = (float)valGrade + 1.65f*5f;
                switch (maxSizeAgg){
                    case 10:
                        waterContent = 208f;
                        break;
                    case 20:
                        waterContent = 186f;
                        break;
                    case 40:
                        waterContent = 165f;
                        break;
                }

                if (slump>50){
                    int i=0, temp = slump;
                    while (temp>50){
                        temp-=25;
                        i++;
                    }
                    waterContent = 3f*(float)i/100f + waterContent;
                }

                cementContent =(float) waterContent/valwcRatio;
                cement = 80f/100f * cementContent;
                flyAsh = 20f/100f * cementContent;

                switch (maxSizeAgg){
                    case 10:
                        switch (zone){
                            case 4:
                                volCoarseAgg = 0.5f;
                                break;
                            case 3:
                                volCoarseAgg = 0.48f;
                                break;
                            case 2:
                                volCoarseAgg = 0.26f;
                                break;
                            case 1:
                                volCoarseAgg = 0.44f;
                                break;
                        }
                        break;
                    case 20:
                        switch (zone){
                            case 4:
                                volCoarseAgg = 0.66f;
                                break;
                            case 3:
                                volCoarseAgg = 0.64f;
                                break;
                            case 2:
                                volCoarseAgg = 0.62f;
                                break;
                            case 1:
                                volCoarseAgg = 0.60f;
                                break;
                        }
                        break;
                    case 40:
                        switch (zone){
                            case 4:
                                volCoarseAgg = 0.75f;
                                break;
                            case 3:
                                volCoarseAgg = 0.73f;
                                break;
                            case 2:
                                volCoarseAgg = 0.71f;
                                break;
                            case 1:
                                volCoarseAgg = 0.69f;
                                break;
                        }
                        break;
                }
                volFineAgg = 1-volCoarseAgg;
                float cement_temp =(float)cement/3.15f/1000f;
                float vol_fly_ash = flyAsh/1000f;
                float vol_water = waterContent/1000f;
                float vol_aggregates = 1f-((float)cement_temp + (float)vol_fly_ash + (float)vol_water);
                float mass_coarse_agg = vol_aggregates*volCoarseAgg*2.74f*1000f;
                _10mm_agg = 55f/100f*mass_coarse_agg;
                _20mm_agg = 45f/100f*mass_coarse_agg;
                //check pump
                //20mm decrease 10 per
                //10mm increase 10 per
                if (concPlace == 0){
                    _10mm_agg += 10f/100f*(float)_10mm_agg;
                    _20mm_agg=_20mm_agg-10f/100f*(float)_20mm_agg;
                }
                crushed_sand = (float)vol_aggregates*(float)volFineAgg*2.74f*1000f;
                if (considerPlasti == 1){
                    float temp = plasti;
                    int i=0;
                    while (temp>0){
                        i++;
                        temp-=0.5;
                    }
                    waterContent = waterContent - 5f*(float)i/100f * waterContent;
                    plasti = plasti/100f * cementContent;
                }
                showCalcDialog();
            }
        });
    }

    public void showCalcDialog(){
        /*
        LayoutInflater inflater = LayoutInflater.from(DesignMix.this);
        View dialog = inflater.inflate(R.layout.dialog_mix_result, null);
        TextView cement_tv = dialog.findViewById(R.id.tv_cement);
        TextView flyash_tv = dialog.findViewById(R.id.tv_fly_ash);
        TextView waterContent_tv = dialog.findViewById(R.id.tv_waterContent);
        TextView _10mmAgg = dialog.findViewById(R.id.tv_10mm_agg);
        TextView _20mmAgg = dialog.findViewById(R.id.tv_20mm_agg);
        TextView crushed_sand_tv = dialog.findViewById(R.id.tv_crushed_sand);

        cement_tv.setText("Cement = " + formatFloat(cement));
        flyash_tv.setText("Fly Ash = " + formatFloat(flyAsh));
        waterContent_tv.setText("Water Content = " + formatFloat(waterContent));
        _10mmAgg.setText("10mm Aggregate = " + formatFloat(_10mm_agg));
        _20mmAgg.setText("20mm Aggregate = " + formatFloat(_20mm_agg));
        crushed_sand_tv.setText("Crushed Sand = " + formatFloat(crushed_sand));

        AlertDialog alertDialog = new AlertDialog.Builder(DesignMix.this)
                .setTitle("Design Mix")
                .setView(dialog)
                .setPositiveButton("CLOSE", null)
                .create();
        alertDialog.show();
        */

        Intent designIntent = new Intent(DesignMix.this, DesignMixResult.class);
        designIntent.putExtra("cement", formatFloat(cement));
        designIntent.putExtra("flyash", formatFloat(flyAsh));
        designIntent.putExtra("water_content", formatFloat(waterContent));
        designIntent.putExtra("10mm_agg", formatFloat(_10mm_agg));
        designIntent.putExtra("20mm_agg", formatFloat(_20mm_agg));
        designIntent.putExtra("crushed_sand", formatFloat(crushed_sand));
        if (considerPlasti == 1){
            designIntent.putExtra("plasti", formatFloat(plasti));
        }
        designIntent.putExtra("considerPlasti", considerPlasti);
        startActivity(designIntent);
    }

    public String formatFloat(float f){
        int value = (int) f;
        return Integer.toString(value);
    }

    public void casting(){
        etwcRatio = findViewById(R.id.et_wcRatio);
        spGrade = findViewById(R.id.sp_grade);
        spExposure = findViewById(R.id.sp_exposure);
        calculate = findViewById(R.id.bt_calculate);
        spConcPlacement = findViewById(R.id.sp_placement);
        spAggZoning = findViewById(R.id.sp_aggregate_zoning);
        spMaxAggSize = findViewById(R.id.sp_aggregate_max_size);
        spSlump = findViewById(R.id.sp_slump);
        admix_vol = findViewById(R.id.et_admixture_vol);
    }

    private void setSpinners(){
        String[] grades = {"M30", "M35", "M40", "M45", "M50", "M55"};
        final int[] gradeValues = {30, 35, 40, 45, 50, 55};
        final float[] wcRatios = {0.45f, 0.45f, 0.40f, 0.40f, 0.40f, 0.40f};
        String[] expConditions = {"Mild", "Moderate", "Severe", "Very severe", "Extreme"};
        String AggZoning[] = {"Zone 1", "Zone 2", "Zone 3", "Zone 4"};
        final int[] AggZoningInt = {1,2,3,4};
        final int[] slumpval = {50,75,100,125,150,175,200};
        String[] slumpname = {"50", "75", "100", "125", "150", "175", "200"};
        String placement[] = {"pump", "manual"};
        final int[] concplaces = {0,1};
        String[] max_agg_size={"10", "20", "40"};
        final int max_size_agg[] = {10,20,40};
        String chemAdmix[] = {};

        setSpinner(spSlump, slumpname);
        spSlump.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slump = slumpval[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                slump = slumpval[0];
            }
        });

        setSpinner(spMaxAggSize, max_agg_size);
        spMaxAggSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maxSizeAgg = max_size_agg[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                maxSizeAgg = max_size_agg[0];
            }
        });

        setSpinner(spAggZoning, AggZoning);
        spAggZoning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zone = AggZoningInt[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                zone = 1;
            }
        });

        setSpinner(spConcPlacement, placement);
        spConcPlacement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                concPlace = concplaces[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                concPlace = 0;
            }
        });

        setSpinner(spGrade, grades);
        spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //update other views here
                valGrade = gradeValues[position];
                etwcRatio.setText(Float.toString(wcRatios[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                valGrade = gradeValues[0];
            }
        });

        setSpinner(spExposure, expConditions);
        spExposure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinner(Spinner spinner, String[] items){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}
