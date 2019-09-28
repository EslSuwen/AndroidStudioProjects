package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddActivity extends AppCompatActivity {
    private EditText editTextAddName;
    private EditText editTextAddAge;
    private RadioGroup radioGroupSex;
    private RadioButton radioButtonMan;
    private RadioButton radioButtonWoman;
    private Spinner spinnerAddCollege;
    private AutoCompleteTextView autoCompleteTextViewAddMajor;
    private Button buttonAdd;
    private String sex = "男";
    private EditText editTextAddAdmissionDate;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private LinearLayout checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);

        editTextAddName = findViewById(R.id.add_name);
        editTextAddAge = findViewById(R.id.add_age);
        radioGroupSex = findViewById(R.id.rg_sex);
        radioButtonMan = findViewById(R.id.rb_man);
        radioButtonWoman = findViewById(R.id.rb_woman);
        radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                if (checkedId == radioButtonMan.getId()) {
                    sex = "男";
                } else if (checkedId == radioButtonWoman.getId()) {
                    sex = "女";
                } else {
                    sex = "男";
                }
            }
        });
        spinnerAddCollege = findViewById(R.id.add_college);
        autoCompleteTextViewAddMajor = findViewById(R.id.add_major);
        String[] major_array = {"物联网工程",
                "计算机科学与技术",
                "材料科学与工程",
                "环境科学与工程",
                "化学化工与生物工程"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                major_array);
        //初始化autoCompleteTextView
        autoCompleteTextViewAddMajor.setAdapter(adapter);
        //设置输入多少字符后提示，默认值为2，在此设为１
        autoCompleteTextViewAddMajor.setThreshold(1);
        // 获取对象
        editTextAddAdmissionDate = findViewById(R.id.add_date);
        calendar = Calendar.getInstance();
        // 点击"日期"按钮布局 设置日期
        editTextAddAdmissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                editTextAddAdmissionDate.setText(new StringBuilder()
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

        checkbox = findViewById(R.id.add_courses);

        buttonAdd = findViewById(R.id.btnAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String name = editTextAddName.getText().toString();
                String age = editTextAddAge.getText().toString();
                String college = spinnerAddCollege.getSelectedItem().toString();
                String major = autoCompleteTextViewAddMajor.getText().toString();
                String admissionDate = editTextAddAdmissionDate.getText().toString();
                List<String> courses = getCheckBoxInfo(checkbox);
                Student student = new Student(name, age, sex, college, major, admissionDate, courses);
                if (!check(student)) return;
                intent.putExtra("stu", student);
                setResult(1, intent);
                finish();
            }
        });


    }

    public boolean check(Student student) {
        String mandatory = student.mandatory();
        if (!mandatory.equals("null")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("警告！");
            builder.setMessage("请输入正确的" + mandatory + "！");
            builder.setPositiveButton("好", null);
            builder.show();
            return false;
        }
        return true;
    }

    private List<String> getCheckBoxInfo(LinearLayout checkbox) {
        List<String> courses = new ArrayList<>();
        int num = checkbox.getChildCount();
        for (int i = 0; i < num; i++) {
            CheckBox cb = (CheckBox) checkbox.getChildAt(i);
            if (cb.isChecked())
                courses.add(cb.getText().toString());
        }
        return courses;
    }
}
