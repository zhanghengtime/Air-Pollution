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
    private TextView labelView;
    private TextView displayView;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        change = findViewById(R.id.change_chart);
        labelView = (TextView)findViewById(R.id.labelss);
        displayView = (TextView)findViewById(R.id.find_results);
        Intent intent =getIntent();;
        final String place = intent.getStringExtra("et2");
        final String time = intent.getStringExtra("et1");
        final String kind = intent.getStringExtra("et3");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(displayView.getText().toString().equals("暂无相关信息!")||displayView.getText().toString().equals("")) {
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
                        //String sql = "SELECT * FROM pollution WHERE 时间=\"2018/3/1 23:00:00\"";
                      /* final String strrr = sql;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText(strrr);
                            }
                        });*/
                        ResultSet rs = stmt.executeQuery(sql);
                        String result="";
                        while(rs.next()&&flag<=10) {
                            result += "\n站点名称：" + rs.getString(1)+ " \n";
                            result += "设备类型：" + rs.getString(2) + " \n";
                            result += "源类型：" + rs.getString(3) + " \n";
                            result += "区县: " + rs.getString(4) + " \n";
                            result += "经度: " + rs.getString(5) + " \n";
                            result += "纬度:" + rs.getString(6) + " \n";
                            result += "时间: " + rs.getString(7) + " \n";
                            result += "首要污染物: " + rs.getString(15) + " \n";
                            result += "温度: " + rs.getString(16) + " \n";
                            result += "湿度: " + rs.getString(17) + " \n\n";
                            SO2 = rs.getString(11);
                            NO2 = rs.getString(12);
                            O3 = rs.getString(13);
                            CO = rs.getString(14);
                            PM25 = rs.getString(10);
                            PM10 = rs.getString(9);
                            AQI = rs.getString(8);
                            if(kind.equals("选择指标:AQI")) {
                                result += "AQI: " + rs.getString(8) + " \n\n";
                            }
                            else if(kind.equals("PM10"))
                            {
                                result += "PM10: " + rs.getString(9) + " \n\n";
                            }
                            else if(kind.equals("PM25")) {
                                result += "PM25: " + rs.getString(10) + " \n\n";
                            }
                            else if(kind.equals("SO2")) {
                                result += "SO2: " + rs.getString(11) + " \n\n";
                            }
                            else if(kind.equals("NO2")) {
                                result += "NO2: " + rs.getString(12) + " \n\n";
                            }
                            else if(kind.equals("O3")) {
                                result += "O3: " + rs.getString(13) + " \n\n";
                            }
                            else if(kind.equals("CO")) {
                                result += "CO: " + rs.getString(14) + " \n\n";
                            }
                            flag++;
                        }
                       final String strr = result;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(!(strr.equals(""))) {
                                    displayView.setText(strr);
                                }
                                else{
                                    displayView.setText("暂无相关信息!");
                                }
                                }
                            });
                    }else{
                        Log.d("调试","连接失败");
                    }
                /*}catch (ClassNotFoundException e){
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            displayView.setText("查询失败!");
                        }});*/
                }catch (SQLException e){
                    Log.i("debug",Log.getStackTraceString(e));
                    final String str =   e.toString();
                   handler.post(new Runnable() {
                           @Override
                          public void run() {
                              // displayView.setText(str);
                               displayView.setText("查询失败!");
                               }});
                }
                }
        }).start();
    }
}