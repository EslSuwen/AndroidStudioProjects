package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;

public class AddActivity extends AppCompatActivity {
    EditText et_name;
    Spinner sp_major;
    EditText et_age;
    Spinner sp_sex;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        et_name = findViewById(R.id.edit_name);
        sp_major = findViewById(R.id.edit_major);
        et_age = findViewById(R.id.edit_age);
        et_age.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        sp_sex = findViewById(R.id.edit_sex);
        btn_add = findViewById(R.id.btn_edit);
        View.OnClickListener listener_add = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                int age = 0;
                String name = et_name.getText().toString();
                String major = sp_major.getSelectedItem().toString();
                if (isNumeric(et_age.getText().toString()))
                    age = Integer.parseInt(et_age.getText().toString());
                String sex = sp_sex.getSelectedItem().toString();
                if (!mandatory(name, major, age, sex)) return;
                Student stu = new Student(name, major, age, sex);
                intent.putExtra("stu", stu);
                setResult(1, intent);
                finish();
            }
        };
        btn_add.setOnClickListener(listener_add);

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
