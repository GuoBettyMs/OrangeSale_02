package com.example.orangesale_02.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OrangeDatabase extends SQLiteOpenHelper {
    public List<UserInfo> list = new ArrayList<UserInfo>();

    public OrangeDatabase(@Nullable Context context){
        super(context, "orange", null, 1);
    }

    //数据库第一次创建时调用该方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table orange_user(id integer primary key autoincrement, " +
                "username varchar(50), password varchar(50), sex varchar(10), city carchar(50))";
        sqLiteDatabase.execSQL(sql);//execSQL 执行一条占位符SQL语句
    }

    //数据库版本号更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //增加数据
    public void insert(OrangeDatabase orangeDatabase, String table, String username,
                       String password, String sex, String city) {
        SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("sex", sex);
        values.put("city", city);
        sqLiteDatabase.insert(table,null,values);
        sqLiteDatabase.close();
    }

    //删除数据
    public void delete(OrangeDatabase orangeDatabase, String table, int id) {
        SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
        sqLiteDatabase.delete(table,"id=?", new String[]{id + ""});
        sqLiteDatabase.close();
    }

    //更新数据
    public void update(OrangeDatabase orangeDatabase, String table, int id, String username,
                       String password, String sex, String city) {
        SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("sex", sex);
        values.put("city", city);
        sqLiteDatabase.update(table, values, "id=?", new String[]{id + ""});
        sqLiteDatabase.close();
    }

    //查询数据库
    public void query(OrangeDatabase orangeDatabase, String table){
        SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
        Cursor c = sqLiteDatabase.query(table, null,
                null, null,null,
                null,null);//游标接口，相当于结果集ResultSet

        while (c.moveToNext()){//移动光标到下一行
            //getColumnIndex，返回指定列的名称，若不存在返回-1
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String username = c.getString(1);
            @SuppressLint("Range") String password = c.getString(2);
            @SuppressLint("Range") String sex = c.getString(3);
            @SuppressLint("Range") String city = c.getString(4);
            list.add(new UserInfo(id,username,password,sex,city));
            Log.i("query result: ",list.toString());
        }
        c.close();//关闭游标
        sqLiteDatabase.close();//关闭数据库
    }

    //查询数据
    public Bundle queryUserInfo(SQLiteDatabase sqLiteDatabase, Bundle bundle){
        String username = bundle.getString("username");
        Cursor cursor = sqLiteDatabase.rawQuery("select * from orange_user where username = ?",
                new String[]{username});
        if (cursor != null){
            while(cursor.moveToNext()){
                bundle.putString("sex", cursor.getString(3));
                bundle.putString("city", cursor.getString(4));
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return bundle;
    }
}
