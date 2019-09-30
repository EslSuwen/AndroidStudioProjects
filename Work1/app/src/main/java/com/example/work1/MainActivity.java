package com.example.work1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button c;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = findViewById(R.id.c);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        c.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.c:
                intent.setClass(this, Calculator.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                intent.setClass(this,Framelayout.class);
                startActivity(intent);
                break;
            case R.id.btn_3:
                intent.setClass(this,Login.class);
                startActivity(intent);
                break;
            case R.id.btn_4:
                intent.setClass(this,TableLayout.class);
                startActivity(intent);
                break;
            case R.id.btn_5:
                intent.setClass(this,Interface.class);
                startActivity(intent);
                break;
        }
    }
}
