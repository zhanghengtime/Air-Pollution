package com.example.tianqi;

/**用来修改界面
 * 实现修改操作
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;

public class ReviseActivity extends AppCompatActivity {
    private EditText et_count_0;
    private EditText et_count_gaikind;
    private EditText et_count_1;
    private Button btn_count_revise1;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_revise);
        btn_count_revise1 = (Button) findViewById(R.id.btn_count_revise1);
        et_count_1 = findViewById(R.id.et_count_1);
        et_count_gaikind = findViewById(R.id.et_count_gaikind);
        et_count_0 = findViewById(R.id.et_count_0);
        btn_count_revise1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           // Class.forName("com.mysql.jdbc.Driver");
                           // String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                           // java.sql.Connection conn = java.sql.DriverManager.getConnection(url,"root","");
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                android.util.Log.d("调试","连接成功");
                                java.sql.Statement stmt = conn.createStatement();
                                String sql = "update pollution set "+ et_count_gaikind.getText().toString() + "=" + Integer.parseInt(et_count_1.getText().toString()) + " where id =" +  Integer.parseInt(et_count_0.getText().toString());
                                // String sql = "SELECT * FROM pollution WHERE 时间=\"2018/3/1 23:00:00\"";
                                stmt.executeUpdate(sql);
                               // final String strrr;
                              //  strrr = sql;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ReviseActivity.this,"更新成功!",Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(ReviseActivity.this,strrr,Toast.LENGTH_SHORT).show();
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
                        //}catch (ClassNotFoundException e){
                       //     e.printStackTrace();
                        }catch (java.sql.SQLException e){
                            android.util.Log.i("debug",android.util.Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReviseActivity.this,str,Toast.LENGTH_SHORT).show();
                                }});
                        } catch (Exception e)
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReviseActivity.this,"修改失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                    }
                }).start();

            }
        });
    }
}