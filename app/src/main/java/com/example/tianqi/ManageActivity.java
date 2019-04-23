package com.example.tianqi;

//管理界面
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManageActivity extends AppCompatActivity {
    private Button mBtnadd;    //添加
    private Button mBtnrevise;    //修改
    private Button mBtndelete;  //删除
    private Button mBtnesc;     //退出
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        mBtnadd = (Button) findViewById(R.id.btn_count_add);
        mBtnrevise = (Button) findViewById(R.id.btn_count_revise);
        mBtndelete = (Button) findViewById(R.id.btn_count_delete);
        mBtnesc = (Button) findViewById(R.id.btn_count_esc);
        mBtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageActivity.this,"添加模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageActivity.this,"删除模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageActivity.this, DeleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnrevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageActivity.this,"修改模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageActivity.this, ReviseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageActivity.this,"退出！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}