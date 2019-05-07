package com.example.tianqi.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqliteDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "sqlite_dbname";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    public static SqliteDB sqliteDB;

    private SQLiteDatabase db;

    private SqliteDB(Context context) {
        OpenHelper dbHelper = new OpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static SqliteDB getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new SqliteDB(context);
        }
        return sqliteDB;
    }

    /**
     * 将User实例存储到数据库。
     */
    public int  saveUser(User user) {
        if (user != null) {
           /* ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("userpwd", user.getUserpwd());
            db.insert("User", null, values);*/

            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user.getUsername().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User(username,userpwd) values(?,?) ", new String[]{user.getUsername().toString(), user.getUserpwd().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    public void saveRoot()
    {
        Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{"root"});
        if (cursor.getCount() > 0) {
        } else {
            try {
                db.execSQL("insert into User(username,userpwd,root) values(?,?,1)", new String[]{"root", "12345"});
            } catch (Exception e) {
                Log.d("错误", e.getMessage().toString());
            }
        }
    }



    /**
     * 从数据库读取User信息。
     */
    public List<User> loadUser() {
        List<User> list = new ArrayList<User>();
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor
                        .getColumnIndex("username")));
                user.setUserpwd(cursor.getString(cursor

                        .getColumnIndex("userpwd")));
                list.add(user);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int Quer(String pwd, String name)
    {


        HashMap<String,String> hashmap=new HashMap<String,String>();
        Cursor cursor =db.rawQuery("select * from User where username=?", new String[]{name});
        if (cursor.getCount()>0)
        {
            Cursor pwdcursor =db.rawQuery("select * from User where userpwd=? and username=?",new String[]{pwd,name});
            if (pwdcursor.getCount()>0)
            {
                Cursor rootcursor =db.rawQuery("select * from User where userpwd=? and username=? and root=1",new String[]{pwd,name});
                if(rootcursor.getCount()>0)
                    return 2;
                else {
                    return 1;
                }
            }
            else {
                return -1;
            }
        }
        else {
            return 0;
        }
    }
}