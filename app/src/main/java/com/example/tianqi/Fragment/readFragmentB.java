package com.example.tianqi.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tianqi.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class readFragmentB extends Fragment {
    private LineChart mLineChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_b,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mLineChart = view.findViewById(R.id.spread_line_chart);
        LineData mLineData = getLineData(12, 100);
        showChart(mLineChart, mLineData, Color.GRAY);
    }

    // 设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.BLACK); // 表格的的颜色，在这里是是给颜色设置一个透明度
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放
        lineChart.setPinchZoom(false);//
        lineChart.setBackgroundColor(getResources().getColor(R.color.blue));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            lineChart.setBackground(getResources().getDrawable(R.drawable.banner1));// 设置背景
//        }
        lineChart.setData(lineData); // 设置数据
        lineChart.getXAxis().setAxisLineColor(Color.BLACK);
        lineChart.getXAxis().setGridColor(Color.GRAY);
        lineChart.getAxisRight().setGridColor(Color.GRAY);
        lineChart.getXAxis().setTextSize(15f);
        lineChart.getXAxis().setTextColor(Color.BLACK);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);//x轴显示到下面
        lineChart.getRendererLeftYAxis().getPaintAxisLine().setColor(Color.BLACK);
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
        lineChart.animateX(2500); // 立即执行的动画,x轴
    }

    /**
     * 生成一个数据
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add(1 + i+"月");
        }
        // y轴的数据随机房价
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range*10) + 6000;
            yValues.add(new Entry(value, i));
        }
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "2016 房价走势图 " /*显示在比例图上*/);
        lineDataSet.setValueTextColor(Color.WHITE);
        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); //线宽
        lineDataSet.setCircleSize(3f);//显示的圆形大小
        lineDataSet.setColor(Color.WHITE);//显示颜色
        lineDataSet.setCircleColor(Color.WHITE);//圆形的颜色
        lineDataSet.setHighLightColor(Color.WHITE); //高亮的线的颜色
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet); //添加数据
        LineData lineData = new LineData(xValues, lineDataSets);
        return lineData;
    }
}
