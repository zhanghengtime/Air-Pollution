package com.example.tianqi;

//用于注册活动
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPasswd;
    private Button btnRegister;
    private Button mBtnLogin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        etPasswd=findViewById(R.id.et_2);
        etUserName=findViewById(R.id.et_1);
        btnRegister=findViewById(R.id.btn_sign);
        mBtnLogin2 = findViewById(R.id.btn_login2);
        SqliteDB.getInstance(getApplicationContext()).saveRoot();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etUserName.getText().toString().trim();
                String pass=etPasswd.getText().toString().trim();
                if (TextUtils.isEmpty(etUserName.getText().toString())) {
                    Toast.makeText(SignActivity.this, "不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    User user=new User();
                    user.setUsername(name);
                    user.setUserpwd(pass);
                    int result=SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                    if (result == 1) {
                        Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else if (result == -1) {
                        Toast.makeText(getApplicationContext(), "用户名已经存在！", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }
            }
        });
        mBtnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

