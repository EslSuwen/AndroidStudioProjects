package com.example.studentinfo;

import java.io.Serializable;

public class StuInfo implements Serializable {
    StuInfo(){
        this.name="Name";
        this.major="Major";
    }
    private String name;
    private String major;

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

