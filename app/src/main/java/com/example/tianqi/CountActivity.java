package com.example.tianqi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private EditText et_count_qvxia;
    private TextView labelView;
    private TextView displayView;
    private Button btn_count_esc001;
    private static Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts_main);
        btn_count_esc001 = (Button) findViewById(R.id.btn_count_esc001);
        et_count_qvxia = (EditText)findViewById(R.id.et_count_qvxian);
        labelView = (TextView)findViewById(R.id.label001);
        displayView = (TextView)findViewById(R.id.display001);
        Button btn_count_AQI = (Button)findViewById(R.id.btn_count_AQI);
        Button btn_count_PM10 = (Button)findViewById(R.id.btn_count_PM10);
        Button btn_count_PM25 = (Button)findViewById(R.id.btn_count_PM25);
        Button btn_count_SO2 = (Button)findViewById(R.id.btn_count_SO2);
        Button btn_count_wendu = (Button)findViewById(R.id.btn_count_wendu);
        Button btn_count_O3 = (Button)findViewById(R.id.btn_count_O3);
        Button btn_count_CO = (Button)findViewById(R.id.btn_count_CO);
        btn_count_esc001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_count_AQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(AQI) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                        result += "最大AQI: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_PM10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(PM10) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大PM10: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_PM25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(PM25) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大PM25: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_SO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(SO2) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大SO2: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_wendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(温度) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大温度: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_O3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(O3) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大O3: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
        btn_count_CO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.56.1:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select MAX(CO) from pollution " + "where 区县 =\"" + et_count_qvxia.getText().toString()+"\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()) {
                                    result += "最大CO: " + rs.getString(1) + " \n\n";
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(strr);
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText(str);
                                }});
                        }
                    }
                }).start();
            }
        });
    }
}