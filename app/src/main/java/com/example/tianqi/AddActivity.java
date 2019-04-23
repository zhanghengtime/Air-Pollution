package com.example.tianqi;

//用来添加
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText etCount1;
    private EditText etCount2;
    private EditText etCount3;
    private EditText etCount4;
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
    private Button mEsc;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_add);
        mBtnadd = (Button) findViewById(R.id.btn_count_add1);
        mEsc = (Button) findViewById(R.id.btn_count_add_esc);
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
        // 建立数据源
        mBtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            java.sql.Connection conn = java.sql.DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                android.util.Log.d("调试","连接成功");
                                java.sql.Statement stmt = conn.createStatement();
                                String sql = "insert into pollution (站点名称,设备类型,源类型,区县,经度,纬度,时间,AQI,PM10,PM25,SO2,NO2,O3,CO,首要污染物,温度,湿度) values (\"" +etCount1.getText().toString()
                                        + "\",\""+etCount2.getText().toString() + "\",\""+etCount3.getText().toString() + "\",\""+etCount4.getText().toString() + "\","+ Float.parseFloat(etCount5.getText().toString()) + ","
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
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (java.sql.SQLException e){
                            android.util.Log.i("debug",android.util.Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddActivity.this,str,Toast.LENGTH_SHORT).show();
                                }});
                        }
                    }
                }).start();
            }
        });
        mEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this,"退出！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}