package com.example.tianqi;

//用来登录活动

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPasswd;
    private Button mBtnLogin;
    private Button mBtnSign2;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnSign2 = (Button) findViewById(R.id.btn_sign2);
        etPasswd = findViewById(R.id.et_2);
        etUserName = findViewById(R.id.et_1);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etUserName.getText().toString();
                final String pass = etPasswd.getText().toString();
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select * from user " + "where Username =\"" + name + "\" and Password = \"" + pass + "\"";
                                ResultSet rs = stmt.executeQuery(sql);
                                if(rs.next()) {
                                    if(rs.getString(3).equals("1")) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "管理员！", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    }
                                    else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else
                                {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }else{
                                Log.d("调试","连接失败");
                            }
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        mBtnSign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
