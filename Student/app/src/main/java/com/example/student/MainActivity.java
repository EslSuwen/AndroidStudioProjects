package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_major;
    Button btn_add;
    Button btn_send;
    ArrayList<Student>students=new ArrayList<Student>();
    ArrayList<Student>studentlist=new ArrayList<Student>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send=findViewById(R.id.btn_send);
        btn_add=findViewById(R.id.btn_add);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, OtherActivity.class);
                intent.putExtra("studnets", (Serializable) students);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(view);
            }
        });
    }
    public void add(View view){
        et_name=findViewById(R.id.et_name);
        et_major=findViewById(R.id.et_major);
        String major=et_major.getText().toString();
        String name=et_name.getText().toString();
        Student student=new Student(name,major);
        students.add(student);
        Toast.makeText(this,"已经添加"+students.size()+"条记录",Toast.LENGTH_SHORT).show();
        et_name.setText("");
        et_major.setText("");
        et_name.setFocusable(true);
        et_name.setFocusableInTouchMode(true);
        et_name.requestFocus();
    }
}
