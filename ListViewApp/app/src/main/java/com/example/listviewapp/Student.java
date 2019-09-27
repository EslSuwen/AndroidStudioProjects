package com.example.listviewapp;

import android.animation.PropertyValuesHolder;

import java.io.Serializable;


public class Student implements Serializable {

    private String name;
    private String college;
    private String[] courses;
    private String major;

    public Student(String name, String college, String[] courses, String major, int age, String sex) {
        this.name = name;
        this.college = college;
        this.courses = courses;
        this.major = major;
        this.age = age;
        this.sex = sex;
    }

    private int age;
    private String sex;


    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    Student() {
        this.name = "Name";
        this.major = "Major";
    }

    Student(String name, String major) {
        this.name = name;
        this.major = major;
    }

    public Student(String name, String major, int age, String sex) {
        this.name = name;
        this.major = major;
        this.age = age;
        this.sex = sex;
    }


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

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }
}

