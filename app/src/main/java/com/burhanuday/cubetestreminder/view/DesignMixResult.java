package com.burhanuday.cubetestreminder.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.burhanuday.cubetestreminder.R;

import java.util.ArrayList;
import java.util.List;

public class DesignMixResult extends AppCompatActivity {
    int cement, flyash, water_content, _10mm_agg, _20mm_agg, crushed_sand, admix_vol;
    int considerPlasti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mix_result);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        cement = Integer.parseInt(getIntent().getStringExtra("cement"));
        flyash = Integer.parseInt(getIntent().getStringExtra("flyash"));
        water_content = Integer.parseInt(getIntent().getStringExtra("water_content"));
        _10mm_agg = Integer.parseInt(getIntent().getStringExtra("10mm_agg"));
        _20mm_agg = Integer.parseInt(getIntent().getStringExtra("20mm_agg"));
        crushed_sand = Integer.parseInt(getIntent().getStringExtra("crushed_sand"));
        try {
            considerPlasti = getIntent().getIntExtra("considerPlasti" , 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (considerPlasti == 1){
            admix_vol = Integer.parseInt(getIntent().getStringExtra("plasti"));
        }else {
            admix_vol = 0;
        }

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        dataEntries.add(new ValueDataEntry("Cement = " + cement, cement));
        dataEntries.add(new ValueDataEntry("Fly Ash = " + flyash, flyash));
        dataEntries.add(new ValueDataEntry("Water Content = " + water_content, water_content));
        dataEntries.add(new ValueDataEntry("10mm Aggregate = " + _10mm_agg, _10mm_agg));
        dataEntries.add(new ValueDataEntry("20mm Aggregate = " + _20mm_agg, _20mm_agg));
        dataEntries.add(new ValueDataEntry("Crushed Sand = " + crushed_sand, crushed_sand));
        if (considerPlasti == 1){
            dataEntries.add(new ValueDataEntry("Plasticiser = " + admix_vol, admix_vol));
        }
        pie.data(dataEntries);
        pie.title("Concrete composition (in kg/cum)");
        pie.labels().position("outside");
        pie.legend().title().enabled(false);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.VERTICAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);

    }
}
