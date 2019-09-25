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

