package com.example.tianqi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tianqi.utils.DatePickerDialog;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CountActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private TextView displayView;
    private EditText etTime;
    private static Handler handler=new Handler();
    public String eTime=null;
    private Dialog dateDialog;
    public String strrrs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts_main);
        displayView = (TextView)findViewById(R.id.display001);
        etTime=findViewById(R.id.et_count_timess);
        Button btn_count_AQI = (Button)findViewById(R.id.btn_count_AQI);
        Button btn_count_PM10 = (Button)findViewById(R.id.btn_count_PM10);
        Button btn_count_PM25 = (Button)findViewById(R.id.btn_count_PM25);
        Button btn_count_SO2 = (Button)findViewById(R.id.btn_count_SO2);
        Button btn_count_wendu = (Button)findViewById(R.id.btn_count_wendu);
        Button btn_count_O3 = (Button)findViewById(R.id.btn_count_O3);
        Button btn_count_CO = (Button)findViewById(R.id.btn_count_CO);
        etTime.setOnClickListener(new View.OnClickListener() {
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
        btn_count_AQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //Class.forName("com.mysql.jdbc.Driver");
                            //String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                           // Connection conn = DriverManager.getConnection(url,"root","");
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select AQI,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY AQI DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                        result += "最大AQI:  " + rs.getString(1) + " \n\n";
                                        if( rs.getString(1)==null)
                                        {
                                            result=null;
                                        }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
                            }else{
                                Log.d("调试","连接失败");
                            }
                      //  }catch (ClassNotFoundException e){
                      //      e.printStackTrace();
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
                                String sql = "select PM10,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY PM10 DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大PM10:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
                                String sql = "select PM25 ,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY PM25 DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大PM25:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
                                String sql = "select SO2 ,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY SO2 DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大SO2:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
                                String sql = "select 温度,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY 温度 DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大温度:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
                                String sql = "select O3,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY O3 DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大O3:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
                                String sql = "select CO ,区县 from pollution " + "where 时间= \"" + etTime.getText().toString() +"\" ORDER BY CO DESC";
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                if(rs.next()) {
                                    result += "区县: " + rs.getString(2)+"\n";
                                    result += "最大CO:  " + rs.getString(1) + " \n\n";
                                    if( rs.getString(1)==null)
                                    {
                                        result=null;
                                    }
                                }
                                final String strr = result;
                                if(strr.equals(""))
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayView.setText("暂无相关信息!");
                                        }
                                    });
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!(strr == null)) {
                                                displayView.setText(strr);
                                            } else {
                                                displayView.setText("无");
                                            }
                                        }
                                    });
                                }
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
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                //  2019-05-04
                eTime=String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                etTime.setText(eTime);
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