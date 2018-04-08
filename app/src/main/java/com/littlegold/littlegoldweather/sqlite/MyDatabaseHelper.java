package com.littlegold.littlegoldweather.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wangqing on 2018/4/7.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREAT_CITYCODE="create table CityCode("+"id integer primary key autoincrement,"+"name text,"+"citycode text)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_CITYCODE);
        Toast.makeText(mContext,"创建数据库成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
