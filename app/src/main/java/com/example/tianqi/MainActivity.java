package com.example.tianqi;

//主界面
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mBtnfind;    //查询
    private Button mBtnread;    //读入
    private Button mBtnmanage;   //管理
    private Button mBtnmap;     //地图
    private Button mBtncount;   //统计
    private Button mBtnwarn;    //预警
    private Button mBtnesc;     //退出
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnfind = (Button) findViewById(R.id.btn_main_find);
        mBtnread = (Button) findViewById(R.id.btn_main_read);
        mBtnmanage = (Button) findViewById(R.id.btn_main_manage);
        mBtnmap = (Button) findViewById(R.id.btn_main_map);
        mBtncount = (Button) findViewById(R.id.btn_main_count);
        mBtnwarn = (Button) findViewById(R.id.btn_main_warn);
        mBtnesc = (Button) findViewById(R.id.btn_main_esc);
        mBtnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"查询模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FindActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"读入数据模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"管理模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"地图模式！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtncount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"统计模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CountActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnwarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"预警模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WarnActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"退出！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}