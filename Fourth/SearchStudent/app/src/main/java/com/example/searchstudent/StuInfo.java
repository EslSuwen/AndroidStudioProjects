package com.example.searchstudent;

import java.io.Serializable;

public class StuInfo implements Serializable {
    private String name;
    private String major;
    private String age;
    private String sex;
    private String kecheng;
    private String academy;
    private String date;
    private  int id;

    public StuInfo(String n, String m, String a, String s, String k, String aca, String t) {
        name = n;
        major = m;
        age = a;
        sex = s;
        kecheng = k;
        academy = aca;
        date = t;

    }

    public StuInfo(String n,  String s, String a, String aca,String m, String t) {
        name = n;
        major = m;
        age = a;
        sex = s;
        academy = aca;
        date = t;

    }

    public StuInfo(int i, String n,  String s, String a, String aca,String m, String t) {
        id=i;
        name = n;
        major = m;
        age = a;
        sex = s;
        academy = aca;
        date = t;

    }

    public StuInfo(String n, String s, String a) {
        name = n;
        sex = s;
        age = a;

    }

    public int getId()
    {return id;}

    public void setId(int id)
    {
        this.id=id;
    }
    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getkecheng() {
        return kecheng;
    }

    public String getAcademy() {
        return academy;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setAge(String sex) {
        this.age = sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setKecheng(String krcheng) {
        this.kecheng = krcheng;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
