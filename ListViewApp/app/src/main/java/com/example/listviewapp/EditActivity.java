package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private EditText editTextEditName;
    private EditText editTextEditAge;
    private RadioGroup radioGroupSex;
    private RadioButton radioButtonMan;
    private RadioButton radioButtonWoman;
    private Spinner spinnerEditCollege;
    private AutoCompleteTextView autoCompleteTextViewEditMajor;
    private Button buttonEdit;
    private String sex = "男";
    private EditText editTextEditAdmissionDate;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private LinearLayout checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editTextEditName = findViewById(R.id.edit_name);
        editTextEditAge = findViewById(R.id.edit_age);
        radioGroupSex = findViewById(R.id.rg_editSex);
        radioButtonMan = findViewById(R.id.rb_editMan);
        radioButtonWoman = findViewById(R.id.rb_editWoman);
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
        spinnerEditCollege = findViewById(R.id.edit_college);
        autoCompleteTextViewEditMajor = findViewById(R.id.edit_major);
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
        autoCompleteTextViewEditMajor.setAdapter(adapter);
        //设置输入多少字符后提示，默认值为2，在此设为１
        autoCompleteTextViewEditMajor.setThreshold(1);
        // 获取对象
        editTextEditAdmissionDate = findViewById(R.id.edit_date);
        calendar = Calendar.getInstance();
        // 点击"日期"按钮布局 设置日期
        editTextEditAdmissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                editTextEditAdmissionDate.setText(new StringBuilder()
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

        checkbox = findViewById(R.id.edit_courses);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int position = Integer.parseInt(bundle.get("position").toString());
        final Student student = (Student) bundle.get("stu");
        // 默认选中
        if (student.getSex() == " 男 ")
            ((RadioButton) radioGroupSex.getChildAt(0)).setChecked(true);
        else
            ((RadioButton) radioGroupSex.getChildAt(1)).setChecked(true);
        editTextEditName.setText(student.getName());
        editTextEditAge.setText(student.getAge());
        editTextEditAdmissionDate.setText(student.getAdmissionDate());
        autoCompleteTextViewEditMajor.setText(student.getMajor());
        autoCompleteTextViewEditMajor.clearFocus();
        setSpinnerDefaultValue(spinnerEditCollege, student.getCollege());
        setCheckBoxInfo(checkbox, student.getCourses());
        buttonEdit = findViewById(R.id.btnEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = editTextEditName.getText().toString();
                String age = editTextEditAge.getText().toString();
                String college = spinnerEditCollege.getSelectedItem().toString();
                String major = autoCompleteTextViewEditMajor.getText().toString();
                String admissionDate = editTextEditAdmissionDate.getText().toString();
                List<String> courses = getCheckBoxInfo(checkbox);
                Student student = new Student(name, age, sex, college, major, admissionDate, courses);
                if (!check(student)) return;
                intent.putExtra("stu", student);
                intent.putExtra("position", position);
                setResult(0, intent);
                finish();
            }
        });


    }

    private void setCheckBoxInfo(LinearLayout checkbox, List<String> courses) {
        if (courses == null)
            return;
        int num = checkbox.getChildCount();
        for (int i = 0; i < num; i++) {
            CheckBox cb = (CheckBox) checkbox.getChildAt(i);
            for (String s : courses) {
                if (s.equals(cb.getText().toString()))
                    cb.setChecked(true);
            }
        }
    }

    /**
     * spinner 接收默认值的 Spinner
     * value 需要设置的默认值
     */
    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);
                break;
            }
        }
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
