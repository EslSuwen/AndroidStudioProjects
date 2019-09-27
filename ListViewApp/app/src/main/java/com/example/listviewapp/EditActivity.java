package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class EditActivity extends AppCompatActivity {
    EditText et_name;
    Spinner sp_college;
    EditText et_age;
    Button btn_edit;
    RadioGroup mRadioGroupSex;
    RadioButton mRadioButtonMan;
    RadioButton mRadioButtonWoman;
    AutoCompleteTextView auto_major;
    String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String[] major_array = {"物联网工程",
                "计算机科学与技术",
                "材料科学与工程",
                "环境科学与工程",
                "化学化工与生物工程"};
        auto_major = findViewById(R.id.edit_major);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                major_array);
        //初始化autoCompleteTextView
        auto_major.setAdapter(adapter);
        //设置输入多少字符后提示，默认值为2，在此设为１
        auto_major.setThreshold(1);

        mRadioGroupSex = findViewById(R.id.rg_sex);
        mRadioButtonMan = findViewById(R.id.rb_man);
        mRadioButtonWoman = findViewById(R.id.rb_woman);
        mRadioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                if (checkedId == mRadioButtonMan.getId()) {
                    sex = "男";
                } else if (checkedId == mRadioButtonWoman.getId()) {
                    sex = "女";
                } else {
                    sex = "男";
                }
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Student stu = (Student) bundle.get("stu");
        auto_major.setText(stu.getMajor());

        final int position = (int) bundle.get("position");
        //默认选中
        if (stu.getSex() == "男")
            ((RadioButton) mRadioGroupSex.getChildAt(0)).setChecked(true);
        else
            ((RadioButton) mRadioGroupSex.getChildAt(1)).setChecked(true);

        et_name = findViewById(R.id.edit_name);
        sp_college = findViewById(R.id.edit_college);
        et_age = findViewById(R.id.edit_age);
        et_age.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        et_name.setText(stu.getName());
        setSpinnerDefaultValue(sp_college, stu.getCollege());
        et_age.setText(String.valueOf(stu.getAge()));
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                int age = 0;
                String name = et_name.getText().toString();
                String college = sp_college.getSelectedItem().toString();
                String major=auto_major.getText().toString();
                if (isNumeric(et_age.getText().toString()))
                    age = Integer.parseInt(et_age.getText().toString());
                if (!mandatory(name, major, age)) return;
                Student stu = new Student(name,college,null,major,age,sex);
                intent.putExtra("stu", stu);
                intent.putExtra("position", position);
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

    public boolean mandatory(String name, String major, int age) {
        if (name.equals(null) || name == "" || name.length() < 2) {
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
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str.length() < 2)
            return false;
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

