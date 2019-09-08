package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        button = findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, MainActivity.class);
                StuInfo stu = new StuInfo();
                stu.setName("Lucy");
                stu.setMajor("CS");
                intent.putExtra("stu", stu);
                startActivity(intent);
            }
        });
    }


}
