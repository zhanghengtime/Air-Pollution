package com.example.tianqi;

/**
 * 数据读入界面，
 * 读入格式为csv文件,
 * 读入后存入数据库
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianqi.utils.MyDBOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

public class ReadActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn_read;
    private Button btn_read_esc;
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        btn_read = (Button) findViewById(R.id.btn_read);
        tv = (TextView) findViewById(R.id.tv22);
        btn_read_esc = (Button)findViewById(R.id.btn_read_esc);
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });
        btn_read_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReadActivity.this,"退出！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                tv.setText(path);
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                readDataCsv(path);
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                readDataCsv(path);
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void readDataCsv(String file){  //读取csv文件
        int flag = 0;
        try {
            File csv = new File(file);//csv文件
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            String lines = "站点名称,设备类型,源类型,区县,经度,纬度\n时间,AQI,PM10,PM25,SO2,NO2,O3,CO\n首要污染物,温度,湿度\n\n                      读入以下数据(仅列三条)\n\n";
            while((line = br.readLine())!=null) {
                final String buffer[] = line.split(",");// 以逗号分隔
                int i = 0;
                while(i<=16&&flag<3)
                {
                    lines +=buffer[i];
                    lines += " ";
                    i++;
                }
                if(flag<4) {
                    flag++;
                    lines += "\n\n";
                }
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //Class.forName("com.mysql.jdbc.Driver");
                            //String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                            //java.sql.Connection conn = java.sql.DriverManager.getConnection(url,"root","");
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                android.util.Log.d("调试","连接成功");
                                java.sql.Statement stmt = conn.createStatement();
                                String sql = "insert into pollution (站点名称,设备类型,源类型,区县,经度,纬度,时间,AQI,PM10,PM25,SO2,NO2,O3,CO,首要污染物,温度,湿度) values (\"" +buffer[0]
                                        + "\",\""+buffer[1] + "\",\""+buffer[2] + "\",\""+buffer[3] + "\","+ Float.parseFloat(buffer[4]) + ","
                                        + Float.parseFloat(buffer[5]) + ",\""+ buffer[6] + "\"," + Integer.parseInt(buffer[7]) + ","+Integer.parseInt(buffer[8]) + ","+Integer.parseInt(buffer[9]) + ","
                                        +Integer.parseInt(buffer[10]) + ","+Integer.parseInt(buffer[11]) + ","+Integer.parseInt(buffer[12]) + ","+Integer.parseInt(buffer[13]) + ",\""+buffer[14] + "\","
                                        + Float.parseFloat(buffer[15]) + ","+Float.parseFloat(buffer[16]) + ")";
                                // String sql = "SELECT * FROM pollution WHERE 时间=\"2018/3/1 23:00:00\"";
                                stmt.executeUpdate(sql);
                                //final String strrr;
                                // strrr = sql;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ReadActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(AddActivity.this,strrr,Toast.LENGTH_SHORT).show();
                                    }
                                });
                        /*String result="";
                       final String strr = result;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText(strr);
                                }
                            });*/
                            }else{
                                android.util.Log.d("调试","连接失败");
                            }
                            //  }catch (ClassNotFoundException e){
                            //  e.printStackTrace();
                        }catch (java.sql.SQLException e){
                            android.util.Log.i("debug",android.util.Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReadActivity.this,"读取失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                        catch (Exception e)
                        { final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReadActivity.this,"读取失败!",Toast.LENGTH_SHORT).show();
                                }});
                        }
                    }
                }).start();
            }
            br.close();
            tv.setText(lines);
        }catch (Exception e){
            Toast.makeText(ReadActivity.this,"读取失败!",Toast.LENGTH_SHORT).show();
        }
    }
}