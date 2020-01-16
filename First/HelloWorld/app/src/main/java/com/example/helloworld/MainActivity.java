package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.t1);
    }

    public void click_1(View view) {
        if (tv1.getText() == "点一下试试？")
            tv1.setText("你在点你马呢！");
        else
            tv1.setText("点一下试试？");
    }
}
