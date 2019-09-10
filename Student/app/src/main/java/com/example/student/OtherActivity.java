package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class OtherActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        tv=(TextView) findViewById(R.id.tv_1);
        Bundle bundle=getIntent().getExtras();
        ArrayList<Student>students=(ArrayList<Student>)bundle.get("stu");
        for(Student s:students)
        {
            String stuInfo="姓名："+s.getName()+"， 专业："+s.getMajor()+"\n";
            tv.append(stuInfo);
        }
    }
}
