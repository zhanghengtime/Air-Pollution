package com.example.tianqi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBOpenHelper {
    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//获取MYSQL驱动
            String url="jdbc:mysql://192.168.1.109:3306/db_pollution?characterEncoding=UTF-8";
            conn = (Connection) DriverManager.getConnection(url,"root","");//获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
