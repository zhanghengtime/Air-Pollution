package com.example.tianqi.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tianqi.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class readFragmentC extends Fragment {
    public BarChart barChart;
    //数据集合
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet dataset;
    //Y轴值
    public ArrayList<String> labels = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_c,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.bar_chart);
        //添加数据
        initEntriesData();
        //添加x轴值
        initLableData();
        //设置柱状图显示属性
        show();
    }

    //添加数据
    public void initEntriesData() {
        entries.add(new BarEntry(400f, 0));
        entries.add(new BarEntry(800f, 1));
        entries.add(new BarEntry(600f, 2));
        entries.add(new BarEntry(1200f, 3));
        entries.add(new BarEntry(1800f, 4));
        entries.add(new BarEntry(900f, 5));
        entries.add(new BarEntry(500f, 6));
        entries.add(new BarEntry(600f, 7));
        entries.add(new BarEntry(700f, 8));
        entries.add(new BarEntry(1200f, 9));
        entries.add(new BarEntry(1900f, 10));
        entries.add(new BarEntry(2000f, 11));
    }
    //添加x轴值
    public void initLableData() {
        labels.add("一月");
        labels.add("二月");
        labels.add("三月");
        labels.add("四月");
        labels.add("五月");
        labels.add("六月");
        labels.add("七月");
        labels.add("八月");
        labels.add("九月");
        labels.add("十月");
        labels.add("十一月");
        labels.add("十二月");
    }
    public void show() {
        //装载显示数据
        dataset = new BarDataSet(entries, "书籍销量(本)");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        //封装x轴数据
        BarData data = new BarData(labels, dataset);
        //右侧Y轴关闭
        barChart.getAxisRight().setEnabled(false);
        //x轴显示到下面
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //装载数据
        barChart.setData(data);
        barChart.animateY(2000);
        //设置右下角表文字
        barChart.setDescription("");
        //设置文字字号
        barChart.setDescriptionTextSize(14f);
        //设置表文字颜色
        barChart.setDescriptionColor(Color.BLACK);
        //设置比例图
        Legend mLegend = barChart.getLegend();
        //mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);//设置显示位置
        //mLegend.setForm(Legend.LegendForm.LINE);//设置比例图的形状，默认是方形
        //设置是否显示比例图
        mLegend.setEnabled(false);
    }
}
