package com.example.tianqi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private EditText et_count_qvxia;
    private TextView labelView;
    private TextView displayView;
    private Button btn_count_esc001;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts_main);
        btn_count_esc001 = (Button) findViewById(R.id.btn_count_esc001);
        et_count_qvxia = (EditText)findViewById(R.id.et_count_qvxian);
        labelView = (TextView)findViewById(R.id.label001);
        displayView = (TextView)findViewById(R.id.display001);
        Button btn_count_AQI = (Button)findViewById(R.id.btn_count_AQI);
        Button btn_count_PM10 = (Button)findViewById(R.id.btn_count_PM10);
        Button btn_count_PM25 = (Button)findViewById(R.id.btn_count_PM25);
        Button btn_count_SO2 = (Button)findViewById(R.id.btn_count_SO2);
        Button btn_count_wendu = (Button)findViewById(R.id.btn_count_wendu);
        Button btn_count_O3 = (Button)findViewById(R.id.btn_count_O3);
        Button btn_count_CO = (Button)findViewById(R.id.btn_count_CO);
        btn_count_esc001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}