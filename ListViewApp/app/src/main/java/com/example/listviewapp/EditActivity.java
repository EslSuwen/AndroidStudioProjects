package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class EditActivity extends AppCompatActivity {
    EditText et_name;
    Spinner sp_major;
    EditText et_age;
    Spinner sp_sex;
    Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Student stu = (Student) bundle.get("stu");
        final int position = (int) bundle.get("position");
        setContentView(R.layout.activity_edit);
        et_name = findViewById(R.id.edit_name);
        sp_major = findViewById(R.id.edit_major);
        et_age = findViewById(R.id.edit_age);
        et_age.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        sp_sex = findViewById(R.id.edit_sex);
        et_name.setText(stu.getName());
        setSpinnerDefaultValue(sp_major, stu.getMajor());
        et_age.setText(String.valueOf(stu.getAge()));
        setSpinnerDefaultValue(sp_sex, stu.getSex());
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                int age=0;
                String name = et_name.getText().toString();
                String major = sp_major.getSelectedItem().toString();
                if (isNumeric(et_age.getText().toString()))
                    age = Integer.parseInt(et_age.getText().toString());
                String sex = sp_sex.getSelectedItem().toString();
                if(!mandatory(name,major,age,sex)) return;
                Student stu = new Student(name, major, age, sex);
                intent.putExtra("stu", stu);
                intent.putExtra("position",position);
                setResult(0, intent);
                finish();
            }
        });
    }

    /**
     * spinner 接收默认值的Spinner
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
    public boolean mandatory(String name, String major, int age, String sex) {
        if (name.equals(null) || name == ""|| name.length()<2) {
            Toast.makeText(this, "请输入正确的姓名！", LENGTH_LONG).show();
            return false;
        }
        if (major.equals("请选择专业")) {
            Toast.makeText(this, "专业是必选项！", LENGTH_LONG).show();
            return false;
        }
        if (age < 10 || age > 100) {
            Toast.makeText(this, "请输入正确的年龄！", LENGTH_LONG).show();
            return false;
        }
        if (sex.equals("请选择性别")) {
            Toast.makeText(this, "性别是必选项！", LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public static boolean isNumeric(String str) {
        if(str.length()<2)
            return false;
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

