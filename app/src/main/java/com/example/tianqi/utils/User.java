package com.example.tianqi.utils;

public class User {
    public int id;
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String username;
    public String userpwd;
    public String toString(){
        String result = "";
        result += "ID：" + this.id + "， ";
        result += "用户名：" + this.username + "， ";
        result += "密码：" + this.userpwd + "， ";
        return result;
    }
}
