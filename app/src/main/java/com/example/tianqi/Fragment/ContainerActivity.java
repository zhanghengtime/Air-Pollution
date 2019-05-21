package com.example.tianqi.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tianqi.R;

public class ContainerActivity extends AppCompatActivity {
    private readFragmentB readFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        //实例化Afragment
        readFragment = new readFragmentB();
        //把AFragment添加到Activity中,记得调用commit
        getFragmentManager().beginTransaction().add(R.id.fl_container,readFragment);
    }
}
