package com.example.searchstudent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBop {
    private MySQLiteAccess mySQLiteAccess;
    private SQLiteDatabase database;
    public void test(Context context){
        mySQLiteAccess=new MySQLiteAccess(context,3);
        database=mySQLiteAccess.getReadableDatabase();
    }
    public void insert(StuInfo s){
        int age=Integer.parseInt(s.getAge());
        database.execSQL("insert into students values(?,?,?,?,?,?,?" +
                ")",new Object[] {null,s.getName(),s.getSex(),age,s.getAcademy(),s.getMajor(),s.getDate()});
        System.out.println("插入数据成功");
    }

    public void delete(int id){
        Integer I=new Integer(id);
     //  String n=new String(name);
        database.execSQL("delete from students where id=?",new Object[]{I});
    }

    public List<StuInfo> searchByName(String key){
        List<StuInfo> listByName=new ArrayList<StuInfo>();
        key="%"+key+"%";
        Cursor cursor=database.rawQuery("select * from students where name like?",new String[]{key});
        while(cursor.moveToNext()){

         //   int id=cursor.getInt(cursor.getColumnIndex("id"));

            String name=cursor.getString(cursor.getColumnIndex("name"));
            String sex=cursor.getString(cursor.getColumnIndex("sex"));
            String age=cursor.getString(cursor.getColumnIndex("age"));
            listByName.add(new StuInfo(name,sex,age));
        }
        return listByName;
    }
    public List<StuInfo> queryAll(){
        List<StuInfo> stus=new ArrayList<StuInfo>();
        Cursor cursor=database.rawQuery("select * from students",null);
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String sex=cursor.getString(cursor.getColumnIndex("sex"));
            String age=String.valueOf(cursor.getInt(cursor.getColumnIndex("age")));
            String aca=cursor.getString(cursor.getColumnIndex("academy"));
            String major=cursor.getString(cursor.getColumnIndex("major"));
            String date=cursor.getString(cursor.getColumnIndex("date"));
            stus.add(new StuInfo(id,name,sex,age,aca,major,date));
        }
        return stus;
    }


    public void update(StuInfo stu) {
        String age=String.valueOf(stu.getAge());
        database.execSQL("update students set name=?,sex=?,age=?,academy=?,major=?,date=? where id=?"
                ,new Object[] { stu.getName(), stu.getSex(), age,stu.getAcademy(),stu.getMajor(),stu.getDate(),stu.getId() });
    }
    public List<StuInfo> searchByAll(String key){
        List<StuInfo> listByAll=new ArrayList<StuInfo>();
        key="%"+key+"%";
        Cursor cursor=database.rawQuery("select * from students where name like? or academy like? or id like?"
                ,new String[]{key,key,key});
        while(cursor.moveToNext()){
             int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String sex=cursor.getString(cursor.getColumnIndex("sex"));
            String age=String.valueOf(cursor.getInt(cursor.getColumnIndex("age")));
            String aca=cursor.getString(cursor.getColumnIndex("academy"));
            String major=cursor.getString(cursor.getColumnIndex("major"));
            String date=cursor.getString(cursor.getColumnIndex("date"));

            listByAll.add(new StuInfo(id,name,sex,age,aca,major,date));

            System.out.print("查询到数据");
        }
        return listByAll;
    }
}
