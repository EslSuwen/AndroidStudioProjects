package com.example.testcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_1;
    TextView tv1;
    String info = "LOG:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_1 = findViewById(R.id.btn_1);
        tv1 = findViewById(R.id.tv1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivityForResult(intent, 0);
            }
        });
        Log.i("onCreate", "执行onCreate方法");
        info += "\"onCreate\", \"执行onCreate方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart", "执行onStart方法");
        info += "\"onStart\", \"执行onStart方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "执行onResume方法");
        info += "\"onResume\", \"执行onResume方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause", "执行onPause方法");
        info += "\"onPause\", \"执行onPause方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "执行onStop方法");
        info += "\"onStop\", \"执行onStop方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "执行onDestroy方法");
        info += "\"onDestroy\", \"执行onDestroy方法\"\n";
        tv1.setText(info);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("onConfigurationChanged", "执行onConfigurationChanged方法");
        info += "\"onConfigurationChanged\", \"执行onConfigurationChanged方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart", "执行onRestart方法");
        info += "\"onRestart\", \"执行onRestart方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onSaveInstanceState", "执行onSaveInstanceState方法");
        info += "\"onSaveInstanceState\", \"执行onSaveInstanceState方法\"\n";
        tv1.setText(info);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("onRestoreInstanceState", "执行onRestoreInstanceState方法");
        info += "\"onRestoreInstanceState\", \"执行onRestoreInstanceState方法\"\n";
        tv1.setText(info);
    }


}
