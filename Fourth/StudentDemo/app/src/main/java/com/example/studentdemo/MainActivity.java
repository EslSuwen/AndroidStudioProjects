package com.example.studentdemo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    Button bt_add;
    Button bt_send;
    EditText et_name;
    AutoCompleteTextView et_major;
    EditText et_age;
    RadioGroup rg_sex;
    LinearLayout check;
    Spinner academyspinner;
    EditText et_date;
    private Calendar cal;
    private int y, m, d;
    DBop dbop = new DBop();
    ArrayList<StuInfo> studentlist = new ArrayList<StuInfo>();
    MySQLiteAccess mySQLiteAccess = new MySQLiteAccess(MainActivity.this, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt_add = (Button) findViewById(R.id.bt_add);
        bt_send = (Button) findViewById(R.id.bt_send);
        academyspinner = (Spinner) findViewById(R.id.academy);
        et_date = (EditText) findViewById(R.id.et_date);


        et_name = (EditText) findViewById(R.id.et_name);
        et_major = (AutoCompleteTextView) findViewById(R.id.et_major);
        et_age = (EditText) findViewById(R.id.et_age);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        // check = (LinearLayout) findViewById(R.id.check);


        String[] arr = {"物联网工程", "计算机科学", "车辆工程", "物流管理"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.au_textview, arr);
        et_major.setAdapter(arrayAdapter);


        bt_add.setOnClickListener(this);
        bt_send.setOnClickListener(this);
        et_date.setKeyListener(null);
        et_date.setOnClickListener(this);

        dbop.test(this);
        initSpinner();
        // academyspinner.setOnItemSelectedListener(this);
        //  Bundle bundle1 = getIntent().getExtras();


    }


    private boolean isDigital(String num)  //正则表达式判断输入是否为数字
    {
        return num.matches("[0-9]{1,}");
    }

    private String getRadioInfo(RadioGroup radioGroup) {
        String info = "";
        int num = radioGroup.getChildCount();
        for (int i = 0; i < num; i++) {
            RadioButton rd = (RadioButton) radioGroup.getChildAt(i);
            if (rd.isChecked()) {
                info = rd.getText().toString();
                break;
            }
        }
        return info;

    }

    private String getCheckBoxInfo(LinearLayout CheckBoxgroup) {
        String info = "";
        int num = CheckBoxgroup.getChildCount();
        for (int i = 0; i < num; i++) {
            CheckBox cb = (CheckBox) CheckBoxgroup.getChildAt(i);
            if (cb.isChecked()) {
                info += cb.getText().toString() + "\n";
            }
        }
        return info;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_add:

                String aca = academyspinner.getSelectedItem().toString();
                String name = et_name.getText().toString();
                String major = et_major.getText().toString();
                String age = et_age.getText().toString();
                String sex = getRadioInfo(rg_sex);
//                String kecheng = getCheckBoxInfo(check).toString();
                String date = et_date.getText().toString();
                CharSequence a = et_age.getText();
                if (TextUtils.isEmpty(a) | TextUtils.isEmpty((et_major.getText())) | TextUtils.isEmpty(et_name.getText())) {
                    Toast.makeText(MainActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else if (aca == "选择学院") {
                    Toast.makeText(MainActivity.this, "请选择正确的学院", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isDigital(a.toString())) {
                    et_age.setError("请输入数字");
                } else {
                    // StuInfo stu = new StuInfo(name, major, age, sex, kecheng, aca, date);
                    StuInfo stu = new StuInfo(name, major, age, sex, aca, date);
                    studentlist.add(stu);
                    //StuInfo s=new StuInfo(name,sex,age);
                    StuInfo s = new StuInfo(name, sex, age, aca, major, date);
                    dbop.insert(s);
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.bt_send:
                if (studentlist.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请先添加信息", Toast.LENGTH_SHORT).show();

                    return;
                } else {
                    intent.putExtra("students", (Serializable) studentlist);
                    intent.setClass(MainActivity.this, StudentList.class);
                    startActivity(intent);
                }
                break;
            case R.id.et_date:
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(year + "-" + (++month) + "-" + dayOfMonth);
                    }
                };
                getDate();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, listener, y, m, d);
                datePickerDialog.show();
                break;

        }

    }


    private void initSpinner() {
        String[] arr = {"信息科学与工程学院", "土木学院", "经济与管理学院", "车辆工程学院", "建筑与城市规划学院", "选择学院"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        academyspinner.setAdapter(arrayAdapter);
        academyspinner.setSelection(arr.length - 1, true);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    }

    private void getDate() {
        cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
    }


  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        List<StuInfo> students;
        Bundle bundle = data.getExtras();
        String name=bundle.getString("name");
        String major=bundle.getString("major");
        String age=bundle.getString("age");


        // CharSequence a=et_age.getText();
        // String aca=academyspinner.getSelectedItem().toString();
        // academyspinner.setSelection();
        et_name.setText(name);
        et_major.setText(major);
        et_age.setText(age);

        String[] arr = {"信息科学与工程学院", "土木学院", "经济与管理学院", "车辆工程学院", "建筑与城市规划学院", "选择学院"};
        for (int i = arr.length; i > 0; i--) {
            if (arr[i] == bundle.getString("aca"))
                academyspinner.setSelection(i);
        }

    }
      /*  rg_sex.
        String name = et_name.getText().toString();
        String major = et_major.getText().toString();
        String age=et_age.getText().toString();
        String sex=getRadioInfo(rg_sex);
        String kecheng=getCheckBoxInfo(check).toString();
        CharSequence a=et_age.getText(); */


    private void test() {
        MySQLiteAccess mySQLiteAccess = new MySQLiteAccess(this, 1);
        mySQLiteAccess.getReadableDatabase();

    }


}
//@Override
  /* public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner s=(Spinner) parent;
//        Map<String ,Object> a=(Map<String,Object>)s.getItemAtPosition(position);
        List<String>a=(List<String>)s.getItemAtPosition(position);
        setTitle((CharSequence)a.get(""));
    }*/

//  @Override
//  public void onNothingSelected(AdapterView<?> parent) {


//   }
