package com.example.listviewapp;


import java.io.Serializable;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class Student implements Serializable {

    private String name;
    private String age;
    private String sex;
    private String college;
    private String major;
    private String admissionDate;
    private List<String> courses;


    public Student() {
    }

    public Student(String name, String age, String sex, String college, String major, String admissionDate, List<String> courses) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.college = college;
        this.major = major;
        this.admissionDate = admissionDate;
        this.courses = courses;
    }

    public String mandatory(){
        if (name.equals(null) || name == "" || name.length() < 2) {
            return "姓名";
        }
        if (major.equals("请选择专业")) {
            return "专业";
        }
        if (!isNumeric(age))
            return "年龄";
        int ageInt = Integer.parseInt(age);
        if (ageInt < 10 || ageInt > 100) {
            return "年龄";
        }
        return "null";

    }

    public static boolean isNumeric(String str) {
        if (str.length() < 2)
            return false;
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}

