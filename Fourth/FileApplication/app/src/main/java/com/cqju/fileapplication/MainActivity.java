package com.cqju.fileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_lg;
    private Button btn_ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_lg=(Button)findViewById(R.id.btn_lg);
        btn_ph=(Button)findViewById(R.id.btn_ph);
        btn_lg.setOnClickListener(this);
        btn_ph.setOnClickListener(this);
        Intent intent=new Intent();
        intent.setClass(this,PhotoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.btn_lg:
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ph:
                intent.setClass(this,PhotoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
