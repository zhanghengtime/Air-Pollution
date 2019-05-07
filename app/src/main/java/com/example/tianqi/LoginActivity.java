package com.example.tianqi;

//用来登录活动

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tianqi.utils.SqliteDB;


public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPasswd;
    private Button mBtnLogin;
    private Button mBtnSign2;
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
                String name = etUserName.getText().toString().trim();
                String pass = etPasswd.getText().toString().trim();
                int result = SqliteDB.getInstance(getApplicationContext()).Quer(pass, name);
                if (result == 2) {
                    Toast.makeText(LoginActivity.this, "管理员登录！", Toast.LENGTH_SHORT).show();   //管理员 root 12345
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else if (result == 1) {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (result == 0) {
                    Toast.makeText(LoginActivity.this, "用户名不存在！", Toast.LENGTH_SHORT).show();
                } else if (result == -1) {
                    Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

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
