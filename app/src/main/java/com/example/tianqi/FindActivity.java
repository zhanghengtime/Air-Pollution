package com.example.tianqi;

//用来查询活动
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tianqi.utils.DatePickerDialog;
import com.example.tianqi.utils.DateUtil;

import java.lang.*;
import java.util.List;


public class FindActivity extends AppCompatActivity {
    private Spinner etPlace;
    private EditText etTime;
    private Button mBtnFind;
    private Spinner mSpinner;
    public String strrr,strrrs;
    private Dialog dateDialog;
    public String eTime=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        etPlace = findViewById(R.id.Place);
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
                //intent.putExtra("et3", str);
                strrr = str;
                //Toast.makeText(FindActivity.this,str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        // 建立数据源
        String[] mItemss = getResources().getStringArray(R.array.placenames);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapters=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItemss);
        etPlace.setAdapter(_Adapters);
        etPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
                //intent.putExtra("et3", str);
                if(str.equals("地区选择:北辰区"))
                {
                    strrrs = "北辰区";
                }else {
                    strrrs = str;
                }
                //Toast.makeText(FindActivity.this,str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
                t.setToNow(); // 取得系统时间。
                int year = t.year;
                int month = t.month+1;
                int date = t.monthDay;
                String  mmtimes = year+"-"+month+"-"+date;
             showDateDialog(DateUtil.getDateForString(mmtimes));
            }
        });
        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name=etUserName.getText().toString().trim();
                //String pass=etPasswd.getText().toString().trim();
                //int result=SqliteDB.getInstance(getApplicationContext()).Quer(pass,name);
                //Toast.makeText(FindActivity.this,"查询中！",Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(FindActivity.this, FindResultActivity.class);
                intent.putExtra("et1", etTime.getText().toString());
                intent.putExtra("et2", strrrs);
                intent.putExtra("et3", strrr);
                startActivity(intent);
            }
        });
    }
    /**
     * 显示日期
     * @param date
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                //  2019-05-04
                eTime=String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                etTime.setText(eTime);
            }
            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }
}