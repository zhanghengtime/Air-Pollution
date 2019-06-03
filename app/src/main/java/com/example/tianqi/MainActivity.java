package com.example.tianqi;

//主界面
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.tianqi.Fragment.ContainerActivity;
import com.example.tianqi.Fragment.PicCoutActivity;

public class MainActivity extends AppCompatActivity {
    private Button button_determine, button_cancel;      //定义对话框按钮
    private AlertDialog dlg;                               //定义对话框
    private Button mBtnfind;    //查询
    private Button mBtnread;    //读入
    private Button mBtnmanage;   //管理
    private Button mBtnmap;     //地图
    private Button mBtncount;   //统计
    private Button mBtnwarn;    //预警
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
        mBtnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"查询模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });
        mBtnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"读入数据模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent);
            }
        });
        mBtnmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"管理模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });
        mBtnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"地图模式！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        mBtncount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"统计模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CountActivity.class);
                startActivity(intent);
            }
        });
        mBtnwarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"预警模块！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WarnActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断如果单击了返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //创建对话框实例
            dlg = new AlertDialog.Builder(this).create();
            dlg.show();                             //显示对话框
            Window window = dlg.getWindow();        //获取对话框窗口
            window.setGravity(Gravity.CENTER);     //此处设置dialog显示在中心位置
            window.setWindowAnimations(R.style.mystyle);      //添加动画
            window.setContentView(R.layout.dialog_layout);   //设置对话框布局文件
            //获取对话框确定按钮
            button_determine = (Button) window.findViewById(R.id.btn_xitong_esc);
            //获取对话框取消按钮
            button_cancel = (Button) window.findViewById(R.id.btn_xitong_qvxiao);
            initEvent();                                       //调用初始化事件方法
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 该方法出事对话框中按钮的事件，单击确定按钮退出该应用
     * 单击取消按钮，对话框将移动至底部消失
     */
    private void initEvent() {
        button_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();          //对话框移动到底部消失
                finish();               //关闭当前应用
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();          //对话框移动到底部消失
            }
        });
    }
}