package com.example.datetest;


import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;

import android.app.DatePickerDialog;


import android.content.pm.ActivityInfo;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    // 定义显示时间控件
    private EditText dateEdit;

    // 通过Calendar获取系统时间
    private Calendar calendar;

    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        // 获取对象
        dateEdit = findViewById(R.id.showdate);
        calendar = Calendar.getInstance();
        // 点击"日期"按钮布局 设置日期
        dateEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                dateEdit.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}