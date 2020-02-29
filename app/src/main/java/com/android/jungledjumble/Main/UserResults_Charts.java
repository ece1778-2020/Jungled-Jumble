package com.android.jungledjumble.Main;

//package com.truiton.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.jungledjumble.Auth.StartActivity;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.android.jungledjumble.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
//import com.xxmassdeveloper.mpchartexample.custom.MyValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;


public class UserResults_Charts extends AppCompatActivity {

    BarChart chart1;
    //Button button_Replay;
    //Button button_Home;

    TextView textView4;
    TextView textView5;
    TextView textView6;

    int total_val1 =0;
    int total_val2=0;
    int total_val3=0;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        final Intent intent = getIntent ();
        username = intent.getStringExtra ("username");

        chart1 = findViewById(R.id.chart1);
        //button_Replay = findViewById(R.id.button_Replay);
        //button_Home = findViewById(R.id.button_Home);

        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);


        //ArrayList<BarEntry> yVals1 = new ArrayList<>();

        int numberRounds = 5;

        chart1.getDescription().setEnabled(false);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();



        for (int i = 1; i <= numberRounds; i++) {
            //float mult = (numberRounds + 1);
            int val1 = (int) (Math.random()*10);
            int val2 = (int) (Math.random()*100) ;
            int val3 = (int) (Math.random()*100);

            total_val1 = total_val1 + val1;
            total_val2 = total_val2 + val2;
            total_val3 = total_val3 + val3;

            yVals1.add(new BarEntry(i,new float[]{val1, val2, val3}));
        }


        textView4.setText(total_val1 + " Fruits collected!");
        textView5.setText(total_val2 + " Seconds spent!");
        textView6.setText(total_val3/(numberRounds) + "% Concentration!");

        BarDataSet set1;

        if (chart1.getData() != null &&
                chart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            chart1.getData().notifyDataChanged();
            chart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1,"");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"Concentration", "Speed", "Performance"});
            XAxis xLabels = chart1.getXAxis();
            xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
           // xLabels.setPosition(XAxis.XAxisPosition.TOP);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 1));
            data.setValueTextColor(Color.WHITE);


       /*     LegendEntry legendEntryA = new LegendEntry();
            legendEntryA.label = "A";
            legendEntryA.formColor = Color.GREEN;

*/

        /*    LegendEntry legendEntryB= new LegendEntry();
            legendEntryA.label = "B";
            legendEntryA.formColor = Color.BLACK;

            LegendEntry legendEntryC = new LegendEntry();
            legendEntryA.label = "C";
            legendEntryA.formColor = Color.BLUE;

            LegendEntry legendEntryD = new LegendEntry();
            legendEntryA.label = "D";
            legendEntryA.formColor = Color.RED;

            Legend legend = chart1.getLegend();
            legend.setCustom(Arrays.asList(legendEntryA, legendEntryB, legendEntryC, legendEntryD));*/
            chart1.animateXY(500, 500);
            chart1.setData(data);
        }

        chart1.setFitBars(true);
        chart1.invalidate();






    }

    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.JOYFUL_COLORS, 0, colors, 0, 3);


        return colors;
    }

}