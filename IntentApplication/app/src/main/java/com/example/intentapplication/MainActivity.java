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

    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_start1:
                intent.setClass(this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_start2:
//                intent.setClassName(this, "com.example.intentapplication.Main2Activity");
                intent = new Intent("Intent.ACTION_DIAL");   //指定系统内置电话动作
                intent.setData(Uri.parse("tel:10086"));    //传递参数数据
                startActivity(intent);
                break;
            case R.id.btn_start3:
                intent.setClassName("com.example.helloworld", "com.example.helloworld.MainActivity");
                startActivity(intent);
                break;
        }

    }


}
