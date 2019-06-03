package com.example.tianqi;

/**
 * 地图模式
 * 实现定位和污染查询
 * 接入百度地图API
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.tianqi.utils.MyDBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private boolean isFirstLocation = true;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private BaiduMap mBaiduMap;
    private double lat;  //纬度
    private double lon; //经度
    private TextView displayView;
    private static Handler handler=new Handler();
    private Button map_find;
    private Spinner mSpinner;
    public String strrr;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        map_find = (Button)findViewById(R.id.btn_map_find);
        displayView = (TextView)findViewById(R.id.map_results);
        mSpinner = (Spinner) findViewById(R.id.map_places);
//获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
//获取BaiduMap对象
        mBaiduMap = mMapView.getMap();
//声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
//注册监听函数
        mLocationClient.registerLocationListener(myListener);
        DemoApplication myApp = (DemoApplication) getApplication();
//配置定位参数

        //监听授权
        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(myApp, Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(myApp, Manifest.
                permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(myApp, Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }else {
            try {
                initLocation();
            }catch (Exception e)
            {
                Toast.makeText(MapActivity.this,"系统出现了一些问题！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            // requestLocation();

        }
        String[] mItems = getResources().getStringArray(R.array.placename);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        mSpinner.setAdapter(_Adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               String str=parent.getItemAtPosition(position).toString();
               strrr = str;
               isFirstLocation=true;
                //intent.putExtra("et3", str);
                //Toast.makeText(MapActivity.this,str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        mLocationClient.start();
//开始定位
        map_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                          //  Class.forName("com.mysql.jdbc.Driver");
                           // String url="jdbc:mysql://192.168.1.107:3306/db_pollution?characterEncoding=UTF-8";
                           // Connection conn = DriverManager.getConnection(url,"root","");
                            Connection conn = MyDBOpenHelper.getConn();
                            if(conn!=null){
                                Log.d("调试","连接成功");
                                Statement stmt = conn.createStatement();
                                String sql = "SELECT * FROM pollution WHERE ABS("+lon+"-经度)<0.05 AND ABS("+lat+"-纬度)<0.05 ORDER BY id DESC"; //经纬度控制在0.05以内
                                ResultSet rs = stmt.executeQuery(sql);
                                String result="";
                                while(rs.next()&&flag<=2) {
                                    result += "时间: " + rs.getString(7) + " \n";
                                    result += "经度: " + rs.getString(5) + " \n";
                                    result += "纬度: " + rs.getString(6) + " \n";
                                    result += "首要污染物: " + rs.getString(15) + " \n";
                                    result += "温度: " + rs.getString(16) + " \n";
                                    result += "湿度: " + rs.getString(17) + " \n";
                                    result += "AQI: " + rs.getString(8) + " \n";
                                    result += "PM10: " + rs.getString(9) + " \n";
                                    result += "PM25: " + rs.getString(10) + " \n";
                                    result += "SO2: " + rs.getString(11) + " \n";
                                    result += "NO2: " + rs.getString(12) + " \n";
                                    result += "O3: " + rs.getString(13) + " \n";
                                    result += "CO: " + rs.getString(14) + " \n\n";
                                    flag++;
                                }
                                final String strr = result;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(strr.equals(""))
                                        {
                                            displayView.setText("暂无相关信息");
                                        }else {
                                            displayView.setText(strr);
                                        }
                                    }
                                });
                            }else{
                                Log.d("调试","连接失败");
                            }
                        //}catch (ClassNotFoundException e){
                       //     e.printStackTrace();
                        }catch (SQLException e){
                            Log.i("debug",Log.getStackTraceString(e));
                            final String str =   e.toString();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText("暂无相关信息");
                                }});
                        }
                    }
                }).start();
            }
        });

    }
    /**
     * 添加marker
     */
    private void setMarker() {
        Log.v("pcw","setMarker : lat : "+ lat+" lon : " + lon);
//定义Maker坐标点
        LatLng point = new LatLng( lat,lon);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ow);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }
    /**
     * 设置中心点
     */
    private void setUserMapCenter() {
        Log.v("pcw","setUserMapCenter : lat : "+ lat+" lon : " + lon);
        LatLng cenpt = new LatLng(lat,lon);
//定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }
    /**
     * 配置定位参数
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    /**
     * 实现定位监听 位置一旦有所改变就会调用这个方法
     * 可以在这个方法里面获取到定位之后获取到的一系列数据
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
//Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(lat);
            sb.append("\nlontitude : ");
            sb.append(lon);
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
//运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            if(strrr.equals("选择:当前位置")) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }else if(strrr.equals("北辰区"))
            {
                lon = 117.065;
                lat = 39.245;
            }else if(strrr.equals("红桥区"))
            {
                lon = 117.165;
                lat = 39.184;
            }else if(strrr.equals("河西区"))
            {
                lon = 117.229;
                lat = 39.115;
            }else if(strrr.equals("和平区"))
            {
                lon = 117.221;
                lat = 39.123;
            }else if(strrr.equals("河北区"))
            {
                lon = 117.203;
                lat = 39.153;
            }else if(strrr.equals("滨海新区"))
            {
                lon = 117.702;
                lat = 39.022;
            }else if(strrr.equals("东丽区"))
            {
                lon = 117.320;
                lat = 39.092;//纬度
            }else if(strrr.equals("西青区"))
            {
                lon = 117.015;
                lat = 39.148;
            }else if(strrr.equals("武清区"))
            {
                lon = 117.051;
                lat = 39.389;
            }else if(strrr.equals("河东区"))
            {
                lon = 117.258;
                lat = 39.135;
            }else {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
//这个判断是为了防止每次定位都重新设置中心点和marker
            if(isFirstLocation){
               isFirstLocation = false;
                setMarker();
                setUserMapCenter();
           }
            Log.v("pcw","lat : " + lat+" lon : " + lon);
        }
    }
    /**
     * 必须要实现
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    /**
     * 必须要实现
     */
    @Override
    protected void onResume() {
        super.onResume();
//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    /**
     * 必须要实现
     */
    @Override
    protected void onPause() {
        super.onPause();
//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}