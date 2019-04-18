package com.example.tianqi;

//用来登录活动
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FindActivity extends AppCompatActivity {
    private EditText etPlace;
    private EditText etTime;
    private Button mBtnFind;
    private Spinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        etPlace=findViewById(R.id.Place);
        etTime=findViewById(R.id.Time);
        // 初始化控件
        mSpinner = (Spinner) findViewById(R.id.Kind);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.spinnername);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        mSpinner.setAdapter(_Adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
                Toast.makeText(FindActivity.this, "你点击的是:"+str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name=etUserName.getText().toString().trim();
                //String pass=etPasswd.getText().toString().trim();
                //int result=SqliteDB.getInstance(getApplicationContext()).Quer(pass,name);
                Toast.makeText(FindActivity.this,"查询成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FindActivity.this, FindResultActivity.class);
                startActivity(intent);
            }
        });
    }
}