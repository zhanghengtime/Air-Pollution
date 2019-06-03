package com.example.tianqi;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tianqi.utils.DatePickerDialog;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 此类 底层UI实现 无SDK相关逻辑
 */

public class BaseActivity extends AppCompatActivity implements MainHandlerConstant {
    protected Button mSpeak;
    protected Button mPause;
    protected Button mResume;
    protected Button mStop;
    protected Button[] buttons;
    protected EditText mInput;
    protected EditText mTime;
    protected Handler mainHandler;
    protected Dialog dateDialog;
    protected static Handler handler=new Handler();
    protected String eTime;
    private int flag = 0;


    private static final String TAG = "BaseActivity";

    /*
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn);
        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handle(msg);
            }

        };
        initialView(); // 初始化UI
        initPermission(); // android 6.0以上动态权限申请
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month+1;
        int date = t.monthDay;
        String  mmtimes = year+"-"+month+"-"+date;
        showDateDialog(DateUtil.getDateForString(mmtimes));
    }


    private void initialView() {
        mSpeak = (Button) this.findViewById(R.id.speak);
        mPause = (Button) this.findViewById(R.id.pause);
        mResume = (Button) this.findViewById(R.id.resume);
        mStop = (Button) this.findViewById(R.id.stop);
        buttons = new Button[]{
                mSpeak, mPause, mResume, mStop
        };
        mTime = (EditText) this.findViewById(R.id.et_warn_time);
        mInput = (EditText) this.findViewById(R.id.input);
        mInput.setMovementMethod(new ScrollingMovementMethod());
        mTime.setOnClickListener(new View.OnClickListener() {
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
    }

    protected void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                //  2019-05-04
                eTime=String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                mTime.setText(eTime);
                mInput.setText("");
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           /* Class.forName("com.mysql.jdbc.Driver");
                            String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                            Connection conn = DriverManager.getConnection(url,"root","");*/
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "select * from pollution " + "where 时间= \"" + mTime.getText().toString() +"\" and AQI > 100 ORDER BY AQI DESC";
                                //String sql = "SELECT * FROM pollution WHERE 时间=\"2018/3/1 23:00:00\"";
                      /* final String strrr = sql;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText(strrr);
                            }
                        });*/
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="可能污染区域如下:\n\n";
                                while(rs.next()&&flag<3) {
                                    //result += "站点名称：" + rs.getString(1)+ " \n";
                                    // result += "设备类型：" + rs.getString(2) + " \n";
                                    // result += "源类型：" + rs.getString(3) + " \n";
                                    result += "区县: " + rs.getString(4) + " \n";
                               //result += "经度: " + rs.getString(5) + " \n";
                               // result += "纬度:" + rs.getString(6) + " \n";
                                result += "首要污染物: " + rs.getString(15) + " ,\n";
                                result += "空气质量指数(AQI): " + rs.getString(8) + "\n";
                                if(Float.parseFloat(rs.getString(8))>300)
                                {
                                    result+="提示:此空气状况健康人群运动耐受力降低，老年人和病人应停留在室内，避免体力消耗，一般人群避免户外活动。\n\n";
                                }else if(Float.parseFloat(rs.getString(8))>200) {
                                    result+="提示:此空气状况心脏病和肺病患者症状显著加剧，运动耐受力降低，儿童、老年人及心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。\n\n";
                                }else if(Float.parseFloat(rs.getString(8))>150) {
                                    result+="提示:此空气状况可能对健康人群心脏、呼吸系统有影响，儿童、老年人及心脏病、呼吸系统疾病患者避免长时间、高强度的户外锻炼，一般人群适量减少户外运动。\n\n";
                                }else if(Float.parseFloat(rs.getString(8))>100) {
                                    result+="提示:此空气状况健康人群出现刺激症状，儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。\n\n";
                                }
                                else if(Float.parseFloat(rs.getString(8))>50){
                                    result+="提示:空气质量较好，极少数敏感人群应减少户外活动。\n\n";
                                }
                                else {
                                    result+="提示:空气质量优，基本无空气污染,各类人群可正常活动。\n\n";
                                }
                               // result += "PM10: " + rs.getString(9) + " ,\n";
                               // result += "PM2.5: " + rs.getString(10) + " ,\n";
                              //  result += "二氧化硫: " + rs.getString(11) + " ,\n";
                              //  result += "二氧化碳: " + rs.getString(12) + " ,\n";
                              //  result += "臭氧: " + rs.getString(13) + " ,\n";
                              //  result += "一氧化碳: " + rs.getString(14) + " \n\n";
                               // result += "温度: " + rs.getString(16) + " \n";
                               /* result += "湿度: " + rs.getString(17) + " \n";*/
                               /* if(kind.equals("AQI")) {
                                    result += "AQI: " + rs.getString(8) + " \n\n";
                                }
                                else if(kind.equals("PM10"))
                                {
                                    result += "PM10: " + rs.getString(9) + " \n\n";
                                }
                                else if(kind.equals("PM25")) {
                                    result += "PM25: " + rs.getString(10) + " \n\n";
                                }
                                else if(kind.equals("SO2")) {
                                    result += "SO2: " + rs.getString(11) + " \n\n";
                                }
                                else if(kind.equals("NO2")) {
                                    result += "NO2: " + rs.getString(12) + " \n\n";
                                }
                                else if(kind.equals("O3")) {
                                    result += "O3: " + rs.getString(13) + " \n\n";
                                }
                                else if(kind.equals("CO")) {
                                    result += "CO: " + rs.getString(14) + " \n\n";
                                }
                                flag++;*/
                               flag++;
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!(strr.equals("可能污染区域如下:\n\n"))) {
                                            mInput.setText(strr);
                                        }
                                        else{
                                            mInput.setText("此时间暂无污染超标信息!");
                                        }
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                       // }//catch (ClassNotFoundException e){
                           // e.printStackTrace();
                           // handler.post(new Runnable() {
                             //   @Override
                              //  public void run() {
                             //       mInput.setText("查询失败!");
                             //   }});
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mInput.setText("查询失败!");
                                }});
                        }
                    }
                }).start();
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

    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
                print(msg);
                break;
            case UI_CHANGE_INPUT_TEXT_SELECTION:
                if (msg.arg1 <= mInput.getText().length()) {
                    mInput.setSelection(0, msg.arg1);
                }
                break;
            case UI_CHANGE_SYNTHES_TEXT_SELECTION:
                SpannableString colorfulText = new SpannableString(mInput.getText().toString());
                if (msg.arg1 <= colorfulText.toString().length()) {
                    colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                    mInput.setText(colorfulText);
                }
                break;
            default:
                break;
        }
    }

    protected void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        mainHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            scrollLog(message);
        }
    }

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  mInput.append(colorMessage);
        Layout layout = mInput.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(mInput.getLineCount()) - mInput.getHeight();
            if (scrollAmount > 0) {
                mInput.scrollTo(0, scrollAmount +
                        mInput.getCompoundPaddingBottom());
            } else {
                mInput.scrollTo(0, 0);
            }
        }
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
}
