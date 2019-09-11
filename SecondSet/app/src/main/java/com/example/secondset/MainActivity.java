package com.example.secondset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.bt1:
                intent.setClassName("com.example.intentapplication", "com.example.intentapplication.MainActivity");
//                intent.setClassName("com.example.simpleapp", "com.example.simpleapp.MainActivity");

                startActivity(intent);
                break;
            case R.id.bt2:
                intent.setClassName("com.example.simpleapp", "com.example.simpleapp.MainActivity");
                startActivity(intent);
                break;
            case R.id.bt3:
                intent.setClassName("com.example.helloworld", "com.example.helloworld.MainActivity");
                startActivity(intent);
                break;
            case R.id.bt4:
                intent.setClassName("com.example.studentinfo", "com.example.studentinfo.MainActivity");
                startActivity(intent);
                break;

        }
    }

}
