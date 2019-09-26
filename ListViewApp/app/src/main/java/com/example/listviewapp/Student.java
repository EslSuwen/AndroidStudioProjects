package com.example.listviewapp;

import java.io.Serializable;


public class Student implements Serializable {
    Student() {
        this.name = "Name";
        this.major = "Major";
    }

    Student(String name, String major) {
        this.name = name;
        this.major = major;
    }

    private String name;
    private String major;

    public Student(String name, String major, int age, String sex) {
        this.name = name;
        this.major = major;
        this.age = age;
        this.sex = sex;
    }

    private int age;
    private String sex;

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() { return name; }

    public String getMajor() {
        return major;
    }
}

