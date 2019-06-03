package com.example.tianqi;

/**
 * 查询结果显示页面
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;


import com.example.tianqi.Fragment.PicCoutActivity;
import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindResultActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private TextView city_name;
    private TextView g_date;
    private TextView temperature;
    private TextView weather;
    private TextView pm_25;
    private TextView quality;
    private TextView jianyi;
    private TextView jiany2;
    private Button change;
    private static Handler handler=new Handler();
    public int flag = 0;
    public String SO2="16";
    public String PM25="16";
    public String O3="16";
    public String CO="16";
    public String NO2="16";
    public String PM10="16";
    public String AQI="0";
    public String AQI1="0";
    public String AQI2="0";
    public String AQI3="0";
    public String IDS;  //本次查询Id
    private String s_city_name;
    private String s_g_date;
    private String s_pm_25;
    private String s_temperature;
    private String s_weather;
    private String s_quality;
    private String s_jianyan;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        change = findViewById(R.id.change_chart);
        city_name= (TextView)findViewById(R.id.city_name);
        g_date = (TextView)findViewById(R.id.g_date);
        temperature = (TextView)findViewById(R.id.temperature);
        weather = (TextView)findViewById(R.id.weather);
        pm_25 = (TextView)findViewById(R.id.pm_25);
        quality = (TextView)findViewById(R.id.quality);
        jianyi = (TextView)findViewById(R.id.jianyi);
        jiany2 = (TextView)findViewById(R.id.jianyi2) ;
        Intent intent =getIntent();;
        final String place = intent.getStringExtra("et2");
        final String time = intent.getStringExtra("et1");
        final String kind = intent.getStringExtra("et3");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jianyi.getText().toString().equals("暂无相关信息!")||jianyi.getText().toString().equals("")) {
                    Toast.makeText(FindResultActivity.this,"无信息!",Toast.LENGTH_SHORT).show();
                }else{
                    final Intent intent = new Intent(FindResultActivity.this, PicCoutActivity.class);
                intent.putExtra("t1", SO2);
                intent.putExtra("t2", NO2);
                intent.putExtra("t3", PM10);
                intent.putExtra("t4", PM25);
                intent.putExtra("t5", CO);
                intent.putExtra("t6", O3);
                intent.putExtra("t7", AQI);
                intent.putExtra("t8", AQI1);
                intent.putExtra("t9", AQI2);
                intent.putExtra("t10", AQI3);
                startActivity(intent);
                }
            }
        });
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try{
                   // Class.forName("com.mysql.jdbc.Driver");
                   //String url="jdbc:mysql://192.168.1.108:3306/db_pollution?characterEncoding=UTF-8";
                   // Connection conn = DriverManager.getConnection(url,"root","");
                    Connection conn = MyDBOpenHelper.getConn();
                    if(conn!=null){
                        Log.d("调试","连接成功");
                        Statement stmt = conn.createStatement();
                        String sql = "select * from pollution " + "where 区县 =\"" + place + "\" and 时间= \"" + time +"\"";
                        ResultSet rs = stmt.executeQuery(sql);
                        String result="";
                        s_quality = "";
                        while(rs.next()&&flag<=10) {
                            s_city_name = rs.getString(4);
                            s_g_date  = rs.getString(7);
                            s_temperature = rs.getString(16);
                            s_quality = rs.getString(8);
                            result += "\n站点名称：" + rs.getString(1)+ " \n";
                            result += "设备类型：" + rs.getString(2) + " \n";
                            result += "源类型：" + rs.getString(3) + " \n";
                            result += "经度: " + rs.getString(5) + " \n";
                            result += "纬度:" + rs.getString(6) + " \n";
                            result += "时间: " + rs.getString(7) + " \n";
                            result += "首要污染物: " + rs.getString(15) + " \n";
                            result += "湿度: " + rs.getString(17) + " \n\n";
                            IDS = rs.getString(18);
                            SO2 = rs.getString(11);
                            NO2 = rs.getString(12);
                            O3 = rs.getString(13);
                            CO = rs.getString(14);
                            PM25 = rs.getString(10);
                            PM10 = rs.getString(9);
                            AQI = rs.getString(8);
                            if(kind.equals("选择指标:AQI")) {
                                result += "AQI: " + rs.getString(8) + " \n\n";
                                s_pm_25 =  "AQI: " + rs.getString(8);
                            }
                            else if(kind.equals("PM10"))
                            {
                                result += "PM10: " + rs.getString(9) + " \n\n";
                                s_pm_25 = "PM10: " + rs.getString(9);
                            }
                            else if(kind.equals("PM25")) {
                                result += "PM25: " + rs.getString(10) + " \n\n";
                                s_pm_25 = "PM25: " + rs.getString(10);
                            }
                            else if(kind.equals("SO2")) {
                                result += "SO2: " + rs.getString(11) + " \n\n";
                                s_pm_25 = "SO2: " + rs.getString(11);
                            }
                            else if(kind.equals("NO2")) {
                                result += "NO2: " + rs.getString(12) + " \n\n";
                                s_pm_25 = "NO2: " + rs.getString(12);
                            }
                            else if(kind.equals("O3")) {
                                result += "O3: " + rs.getString(13) + " \n\n";
                                s_pm_25 = "O3: " + rs.getString(13);
                            }
                            else if(kind.equals("CO")) {
                                result += "CO: " + rs.getString(14) + " \n\n";
                                s_pm_25 = "CO: " + rs.getString(14);
                            }
                            if(Float.parseFloat(rs.getString(8))>300)
                            {
                                s_jianyan="提示:此空气状况健康人群运动耐受力降低，老年人和病人应停留在室内，避免体力消耗，一般人群避免户外活动。\n\n";
                            }else if(Float.parseFloat(rs.getString(8))>200) {
                                s_jianyan="提示:此空气状况心脏病和肺病患者症状显著加剧，运动耐受力降低，儿童、老年人及心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。\n\n";
                            }else if(Float.parseFloat(rs.getString(8))>150) {
                                s_jianyan="提示:此空气状况可能对健康人群心脏、呼吸系统有影响，儿童、老年人及心脏病、呼吸系统疾病患者避免长时间、高强度的户外锻炼，一般人群适量减少户外运动。\n\n";
                            }else if(Float.parseFloat(rs.getString(8))>100) {
                                s_jianyan="提示:此空气状况健康人群出现刺激症状，儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。\n\n";
                            }
                            else if(Float.parseFloat(rs.getString(8))>50){
                                s_jianyan="提示:空气质量较好，极少数敏感人群应减少户外活动。\n\n";
                            }
                            else {
                                s_jianyan="提示:空气质量优，基本无空气污染,各类人群可正常活动。\n\n";
                            }
                            flag++;
                        }
                        final String strr = result;
                        final String scn = s_city_name;
                        final String sgd = s_g_date  ;
                        final String sp2 = s_pm_25 ;
                        final String st = s_temperature + "°C";
                        final String sj = s_jianyan;
                        if(!s_quality.equals(""))
                        {
                            if(Integer.parseInt(s_quality)>150)
                            {
                                 s_weather = "空气: 差!" ;
                            }
                            if(Integer.parseInt(s_quality)>50&&Integer.parseInt(s_quality)<150)
                            {
                                s_weather = "空气: 良!" ;
                            }
                            if(Integer.parseInt(s_quality)<100)
                            {
                                s_weather = "空气: 优!" ;
                            }
                        }
                        else {
                             String sw1 = "";
                        }
                        final String sw = s_weather;
                        final String sq = "空气质量指数: " + s_quality;
                        if(strr.equals(""))
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    jianyi.setText("暂无相关信息!");
                                }
                            });
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    jianyi.setText("完整信息\n"+strr);
                                    city_name.setText(scn);
                                    g_date.setText(sgd);
                                    pm_25.setText(sp2);
                                    temperature.setText(st);
                                    weather.setText(sw);
                                    quality.setText(sq);
                                    jiany2.setText(sj+"");
                                }
                            });
                            String sqls = "select * from pollution " + "where 区县 =\"" + place + "\" and id <="+ IDS +" ORDER BY id DESC";
                            ResultSet rss = stmt.executeQuery(sqls);
                            int ii=0, gg=0;
                            while (rss.next()&&gg<3)
                            {
                                if(ii==0)
                                {
                                    AQI1=rss.getString(8);
                                }else if(ii==1){
                                    AQI2=rss.getString(8);
                                }else if(ii==2){
                                AQI3=rss.getString(8);
                                }
                                ii++;
                                gg++;
                            }
                        }
                    }else{
                        Log.d("调试","连接失败");
                    }
                }catch (SQLException e){
                    Log.i("debug",Log.getStackTraceString(e));
                    final String str =   e.toString();
                   handler.post(new Runnable() {
                           @Override
                          public void run() {
                              // displayView.setText(str);
                               jianyi.setText("查询失败!");
                               }});
                }
                }
        }).start();
    }
}