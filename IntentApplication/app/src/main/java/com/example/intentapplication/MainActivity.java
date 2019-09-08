package com.example.intentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_start1;
    private Button btn_start2;
    private Button btn_start3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start1 = findViewById(R.id.btn_start1);
        btn_start2 = findViewById(R.id.btn_start2);
        btn_start3 = findViewById(R.id.btn_start3);
        btn_start1.setOnClickListener(this);
        btn_start2.setOnClickListener(this);
        btn_start3.setOnClickListener(this);


    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_start1:
//                intent.setClass(this, AnotherActivity.class);
                intent.setAction("test.open");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                break;
            case R.id.btn_start2:
                intent.setClassName(this, "com.example.intentapplication.AnotherActivity");
                startActivity(intent);
                break;
            case R.id.btn_start3:
                intent.setClassName("com.example.helloworld", "com.example.helloworld.ClockActivity");
                startActivity(intent);
                break;
        }

    }


}
