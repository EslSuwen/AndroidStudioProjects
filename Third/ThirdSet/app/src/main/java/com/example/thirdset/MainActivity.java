package com.example.thirdset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        button1 = findViewById(R.id.btn_1);
        button2 = findViewById(R.id.btn_2);
        button3 = findViewById(R.id.btn_3);
        button4 = findViewById(R.id.btn_4);
        button5 = findViewById(R.id.btn_5);
        button6 = findViewById(R.id.btn_6);
        button7 = findViewById(R.id.btn_7);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_1:
                intent.setClassName("com.example.work1", "com.example.work1.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_2:
                intent.setClassName("com.example.work2", "com.example.work2.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_3:
                intent.setClassName("com.example.listviewapp", "com.example.listviewapp.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_4:
                intent.setClassName("com.example.work4", "com.example.work4.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_5:
                intent.setClassName("com.example.work5", "com.example.work5.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_6:
                intent.setClassName("com.example.work6", "com.example.work6.MainActivity");
                startActivity(intent);
                break;
            case R.id.btn_7:
                intent.setClassName("com.example.work7s", "com.example.work7s.MainActivity");
                startActivity(intent);
                break;


        }
    }
}
