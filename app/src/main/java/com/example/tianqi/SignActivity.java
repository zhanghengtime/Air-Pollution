package com.example.tianqi;

/**用于注册活动
 * 连接数据库实现注册功能
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPasswd;
    private Button btnRegister;
    private Button mBtnLogin2;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        etPasswd=findViewById(R.id.et_2);
        etUserName=findViewById(R.id.et_1);
        btnRegister=findViewById(R.id.btn_sign);
        mBtnLogin2 = findViewById(R.id.btn_login2);
        btnRegister.setOnClickListener(new View.OnClickListener() {  //注册事件
            @Override
            public void onClick(View view) {
                String name=etUserName.getText().toString().trim();
                String pass=etPasswd.getText().toString().trim();
                if (TextUtils.isEmpty(etUserName.getText().toString())) {
                    Toast.makeText(SignActivity.this, "不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    new  Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Connection conn = MyDBOpenHelper.getConn();
                                if(conn!=null){
                                    Log.d("调试","连接成功");
                                    Statement stmt = conn.createStatement();
                                    String sql = "select * from user " + "where Username =\"" + etUserName.getText().toString() + "\"";
                                    ResultSet rs = stmt.executeQuery(sql);
                                    if(rs.next()) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "用户名已经存在！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else
                                    {
                                        String sqls = "insert into user (Username, Password) values (\"" + etUserName.getText().toString() + "\" , \"" + etPasswd.getText().toString() +"\")";
                                        stmt.executeUpdate(sqls);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SignActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
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
                                        Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();

                }
            }
        });
        mBtnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);  //跳转登录
                startActivity(intent);
                finish();
            }
        });
    }
}

