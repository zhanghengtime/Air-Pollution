package com.example.tianqi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        new Thread(runnable).start();
    }
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle data = new Bundle();
            data = msg.getData();
            System.out.println();
        }
    };
    Runnable runnable = new Runnable() {
        private Connection connection = null;
        @Override
        public void run() {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://IPAddress:3306/Databasename", "username", "youpass");
                System.out.print("连接成功");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            try{
                test(connection);     //测试数据库连接
            }catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }

        public void test(Connection con1) throws java.sql.SQLException{
            try{
                String sql = "select * from user";
                Statement stmt = con1.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                Bundle bundle = new Bundle();
                while(rs.next()){
                    bundle.clear();
                    bundle.putString("username",rs.getString("username"));
                    Message msg = new Message();
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                }
                rs.close();
                stmt.close();
            }catch (SQLException e){

            }finally {
                if(con1 != null)
                    try{
                    con1.close();
                    }catch (SQLException e){
            }
        }
    }
    };

};
