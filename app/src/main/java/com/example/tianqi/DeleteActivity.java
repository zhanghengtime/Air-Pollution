package com.example.tianqi;

//用来删除界面
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

public class DeleteActivity extends AppCompatActivity {
    private EditText etshankind;
    private Button mBtndelete_esc;
    private Button mBtndelete1;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_delete);
        mBtndelete1 = (Button) findViewById(R.id.btn_count_delete1);
        mBtndelete_esc = (Button) findViewById(R.id.btn_count_delete_esc);
        etshankind = findViewById(R.id.et_count_shankind);
        mBtndelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           // Class.forName("com.mysql.jdbc.Driver");
                           // String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";          //连接数据库
                           // java.sql.Connection conn = java.sql.DriverManager.getConnection(url,"root","");   //mysql账号名 密码
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                android.util.Log.d("调试","连接成功");
                                java.sql.Statement stmt = conn.createStatement();
                                String sql = "delete from pollution  where id =" +  Integer.parseInt(etshankind.getText().toString());  //sql语句
                                stmt.executeUpdate(sql);   //执行sql
                               // final String strrr;
                               // strrr = sql;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DeleteActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(DeleteActivity.this,strrr,Toast.LENGTH_SHORT).show();
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
                       // }catch (ClassNotFoundException e){
                        //    e.printStackTrace();
                        }catch (java.sql.SQLException e){
                            android.util.Log.i("debug",android.util.Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DeleteActivity.this,str,Toast.LENGTH_SHORT).show();
                                }});
                        }
                        catch (Exception e)
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DeleteActivity.this,"删除失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                    }
                }).start();

            }
        });
        mBtndelete_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeleteActivity.this,"退出！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}