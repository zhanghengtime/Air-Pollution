package com.example.tianqi.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {
    private static final String DB_TABLE = "User";
    private static final int DB_VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "username";
    public static final String KEY_PWD = "userpwd";
    public static final String DB_NAME = "sqlite_dbname";
    /**
     * 数据库版本
     */

    private SQLiteDatabase db;
    private final Context context;
    private OpenHelper dbOpenHelper;
    public DBAdapter(Context _context) {
        context = _context;
    }
    /** Close the database */
    public void close() {
        if (db != null){
            db.close();
            db = null;
        }
    }
    /** Open the database */
    public void open() throws SQLiteException {
        dbOpenHelper = new OpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }
    public long insert(User user) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, user.username);
        newValues.put(KEY_PWD, user.userpwd);
        return db.insert(DB_TABLE, null, newValues);
    }
    public User[] queryAllData() {
        Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME,
                        KEY_PWD},
                null, null, null, null, null);
        return ConvertToUser(results);
    }
    public User[] queryOneData(long id) {
        Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME,
                        KEY_PWD},
                KEY_ID + "=" + id, null, null, null, null);
        return ConvertToUser(results);
    }
    private User[] ConvertToUser(Cursor cursor){
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        User[] users = new User[resultCounts];
        for (int i = 0 ; i<resultCounts; i++){
            users[i] = new User();
            users[i].id = cursor.getInt(0);
            users[i].username =
                    cursor.getString(cursor.getColumnIndex(KEY_NAME));
            users[i].userpwd = cursor.getString(cursor.getColumnIndex(KEY_PWD));
            cursor.moveToNext();
        }
        return users;
    }
    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }
    public long deleteOneData(long id) {
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
    }
    public long updateOneData(long id , User user){
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, user.username);
        updateValues.put(KEY_PWD, user.userpwd);
        return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
    }
}
