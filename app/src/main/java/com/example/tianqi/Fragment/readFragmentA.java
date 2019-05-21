package com.example.tianqi.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tianqi.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class readFragmentA extends Fragment {
    private PieChart mChart;
    public float SO2,NO2,O3,CO,PM25,PM10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_a,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mChart = view.findViewById(R.id.pie_chart);
        SO2 = Float.parseFloat(getActivity().getIntent().getStringExtra("t1"));
        NO2 = Float.parseFloat(getActivity().getIntent().getStringExtra("t2"));
        O3 = Float.parseFloat(getActivity().getIntent().getStringExtra("t6"));
        CO = Float.parseFloat(getActivity().getIntent().getStringExtra("t5"));
        PM10 = Float.parseFloat(getActivity().getIntent().getStringExtra("t3"));
        PM25 = Float.parseFloat(getActivity().getIntent().getStringExtra("t4"));
        //初始化饼状图数据类
        PieData mPieData = getPieData(SO2, NO2, O3, CO, PM10, PM25);
        showChart(mChart, mPieData);
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);
        //半径
        pieChart.setHoleRadius(60f);
        //半透明圈
        pieChart.setTransparentCircleRadius(64f);
        //pieChart.setHoleRadius(0)//实心圆
        pieChart.setDescription("");
        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        //初始旋转角度
        pieChart.setRotationAngle(90);
        //可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(true);
        //饼状图中间的文字
        pieChart.setCenterText("大气污染");
        pieChart.setCenterTextColor(Color.BLUE);
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextSizePixels(100f);
        //设置数据
        pieChart.setData(pieData);
        //设置比例图
        Legend mLegend = pieChart.getLegend();
        //最右边显示
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        //设置动画
        pieChart.animateXY(1000, 1000);
    }

    private PieData getPieData(float SO2, float NO2, float O3, float CO, float PM10, float PM25) {
//        衣食住行以及其它
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("SO2");
        xValues.add("PM25");
        xValues.add("O3");
        xValues.add("CO");
        xValues.add("NO2");
        xValues.add("PM10");
        ArrayList<Entry> yValues = new ArrayList<>();
        float q1 = SO2/(SO2+NO2+O3+PM10+PM25+CO);
        float q2 = NO2/(SO2+NO2+O3+PM10+PM25+CO);
        float q3 = O3/(SO2+NO2+O3+PM10+PM25+CO);
        float q4 = CO/(SO2+NO2+O3+PM10+PM25+CO);
        float q5 = PM10/(SO2+NO2+O3+PM10+PM25+CO);
        float q6 = PM25/(SO2+NO2+O3+PM10+PM25+CO);
        yValues.add(new Entry(q1,0));
        yValues.add(new Entry(q2,1));
        yValues.add(new Entry(q3,2));
        yValues.add(new Entry(q4,3));
        yValues.add(new Entry(q5,4));
        yValues.add(new Entry(q6,5));
        PieDataSet pieDataSet = new PieDataSet(yValues,"大气污染比");
        pieDataSet.setSliceSpace(1f);
        ArrayList<Integer> colors = new ArrayList<>();
        //饼状图颜色
        colors.add(Color.RED);
        colors.add(Color.BLACK);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.LTGRAY);
        pieDataSet.setColors(colors);
        //设置圆盘文字颜色
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(15f);
        //设置是否显示区域百分比的值
        //设置数据样式
        pieDataSet.setValueFormatter(new ValueFormatter()
        { @Override
        public String getFormattedValue(float value, Entry entry,
                                        int dataSetIndex, ViewPortHandler viewPortHandler)
        { return ""+(int)value+"%"; }
        });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 6 * (metrics.densityDpi / 160f);
        // 选中态多出的长度
        pieDataSet.setSelectionShift(px);
        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }
}
