package com.example.tianqi.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tianqi.R;

public class PicCoutActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            setDefaultFragment();
            fragmentManager =  getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.fl_context,new readFragmentB());
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.fl_context,new readFragmentC());
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.fl_context,new readFragmentA());
                    transaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_cout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setDefaultFragment(){
        fragmentManager =  getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_context,new readFragmentA());
        transaction.commit();
    }
}
