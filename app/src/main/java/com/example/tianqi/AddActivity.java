package com.example.tianqi;

//用来添加
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tianqi.utils.DatePickerDialog;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private EditText etCount1;
    private EditText etCount2;
    private EditText etCount3;
    private Spinner etCount4;
    private EditText etCount5;
    private EditText etCount6;
    private EditText etCount7;
    private EditText etCount8;
    private EditText etCount9;
    private EditText etCount10;
    private EditText etCount11;
    private EditText etCount12;
    private EditText etCount13;
    private EditText etCount14;
    private EditText etCount15;
    private EditText etCount16;
    private EditText etCount17;
    private Button mBtnadd;
    private Dialog dateDialog;
    private static Handler handler=new Handler();
    private String eTime;
    public String strrrs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_add);
        mBtnadd = (Button) findViewById(R.id.btn_count_add1);
        etCount1 = findViewById(R.id.et_count_1);
        etCount2 = findViewById(R.id.et_count_2);
        etCount3 = findViewById(R.id.et_count_3);
        etCount4 = findViewById(R.id.et_count_4);
        etCount5 = findViewById(R.id.et_count_5);
        etCount6 = findViewById(R.id.et_count_6);
        etCount7 = findViewById(R.id.et_count_7);
        etCount8 = findViewById(R.id.et_count_8);
        etCount9 = findViewById(R.id.et_count_9);
        etCount10 = findViewById(R.id.et_count_10);
        etCount11 = findViewById(R.id.et_count_11);
        etCount12 = findViewById(R.id.et_count_12);
        etCount13 = findViewById(R.id.et_count_13);
        etCount14 = findViewById(R.id.et_count_14);
        etCount15 = findViewById(R.id.et_count_15);
        etCount16 = findViewById(R.id.et_count_16);
        etCount17 = findViewById(R.id.et_count_17);

        String[] mItemss = getResources().getStringArray(R.array.placenames);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapters=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItemss);
        etCount4.setAdapter(_Adapters);
        etCount4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
                //intent.putExtra("et3", str);
                if(str.equals("地区选择:北辰区"))
                {
                    strrrs = "北辰区";
                }else {
                    strrrs = str;
                }
                //Toast.makeText(FindActivity.this,str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        // 建立数据源
        mBtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           // Class.forName("com.mysql.jdbc.Driver");
                            //String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                            //java.sql.Connection conn = java.sql.DriverManager.getConnection(url,"root","");
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                android.util.Log.d("调试","连接成功");
                                java.sql.Statement stmt = conn.createStatement();
                                String sql = "insert into pollution (站点名称,设备类型,源类型,区县,经度,纬度,时间,AQI,PM10,PM25,SO2,NO2,O3,CO,首要污染物,温度,湿度) values (\"" +etCount1.getText().toString()
                                        + "\",\""+etCount2.getText().toString() + "\",\""+etCount3.getText().toString() + "\",\""+ strrrs + "\","+ Float.parseFloat(etCount5.getText().toString()) + ","
                                        + Float.parseFloat(etCount6.getText().toString()) + ",\""+ etCount7.getText().toString() + "\"," + Integer.parseInt(etCount8.getText().toString()) + ","+Integer.parseInt(etCount9.getText().toString()) + ","+Integer.parseInt(etCount10.getText().toString()) + ","
                                        +Integer.parseInt(etCount11.getText().toString()) + ","+Integer.parseInt(etCount12.getText().toString()) + ","+Integer.parseInt(etCount13.getText().toString()) + ","+Integer.parseInt(etCount14.getText().toString()) + ",\""+etCount15.getText().toString() + "\","
                                        + Float.parseFloat(etCount16.getText().toString()) + ","+Float.parseFloat(etCount17.getText().toString()) + ")";
                               // String sql = "SELECT * FROM pollution WHERE 时间=\"2018/3/1 23:00:00\"";
                                stmt.executeUpdate(sql);
                                //final String strrr;
                              // strrr = sql;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                    Toast.makeText(AddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(AddActivity.this,strrr,Toast.LENGTH_SHORT).show();
                                    }
                                });
                        /*String result="";
                       final String strr = result;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText(strr);
                                }
                            });*/
                            }else{
                                android.util.Log.d("调试","连接失败");
                            }
                      //  }catch (ClassNotFoundException e){
                          //  e.printStackTrace();
                        }catch (java.sql.SQLException e){
                            android.util.Log.i("debug",android.util.Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddActivity.this,"添加失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                        catch (Exception e)
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddActivity.this,"添加失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                    }
                }).start();
            }
        });
        etCount7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
                t.setToNow(); // 取得系统时间。
                int year = t.year;
                int month = t.month+1;
                int date = t.monthDay;
                String  mmtimes = year+"-"+month+"-"+date;
                showDateDialog(DateUtil.getDateForString(mmtimes));
            }
        });
    }
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                //  2019-05-04
                eTime=String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                etCount7.setText(eTime);
            }
            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }
}