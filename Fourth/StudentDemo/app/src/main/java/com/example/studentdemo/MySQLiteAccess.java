package com.example.studentdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;

public class MySQLiteAccess extends SQLiteOpenHelper {

    /**
     * @param context     :上下文
     * @param version：版本号
     */
    public MySQLiteAccess(Context context, int version) {
        super(context, "stu4.db", null, version);
    }

    /**
     * 数据库文件创建成功后调用
     * SQL操作的语句
     *
     * @param db 数据库 stuAdmin.db
     *           列：
     *           id（int类型主键：学号）
     *           name（text）
     *           sex（text）
     *           age（int）
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("数据库创建");
        db.execSQL("create table students(\n" +
                "id integer primary key  autoincrement,\n" +
                "name text,\n" +
                "sex text,\n" +
                "age integer,\n" +
                "academy text,\n" +
                "major text\n," +
                "date text\n" +
                ");");


        db.execSQL("create table courseTable(\n" +
                "stuno text ,\n" +
                "course text \n" +
                ");"
        );
    }


    /**
     * 在更新数据库后调用方法
     *
     * @param db
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("数据库更新");
    }
}
