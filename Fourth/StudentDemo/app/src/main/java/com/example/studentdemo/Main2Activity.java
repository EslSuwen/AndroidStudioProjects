package com.example.studentdemo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    TextView et_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et_re = (TextView) findViewById(R.id.re);
        Bundle bundle = getIntent().getExtras();

        ArrayList<StuInfo> students = (ArrayList<StuInfo>) bundle.get("students");
       for (StuInfo s : students) {
           String reInfo = "姓名：" + s.getName()+"\n年龄："+s.getAge()+"\t\t\t性别："+s.getSex()+ "\n学院:"+s.getAcademy()+"\n专业:" +s.getMajor()+"\n选修课程："+s.getkecheng();
            et_re.append(reInfo);
        }

    }
}
