package com.example.searchstudent;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class Edit extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener {

    Button bt_add;
    // Button bt_send;
    EditText et_name;
    AutoCompleteTextView et_major;
    EditText et_age;
    RadioGroup rg_sex;
    LinearLayout check;
    Spinner academyspinner;
    EditText et_date;
    private Calendar cal;
    private int y, m, d;
    RadioButton rg_nan;
    RadioButton rg_nv;
    DBop dBop = new DBop();
    StuInfo altStu = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = getIntent().getExtras();

        dBop.test(this);
        bt_add = (Button) findViewById(R.id.bt_add);
        // bt_send = (Button) findViewById(R.id.bt_send);
        academyspinner = (Spinner) findViewById(R.id.academy);
        et_date = (EditText) findViewById(R.id.et_date);
        rg_nan = (RadioButton) findViewById(R.id.rg_nan);
        rg_nv = (RadioButton) findViewById(R.id.rg_nv);

        et_name = (EditText) findViewById(R.id.et_name);
        et_major = (AutoCompleteTextView) findViewById(R.id.et_major);
        et_age = (EditText) findViewById(R.id.et_age);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        // check = (LinearLayout) findViewById(R.id.check);


        bt_add.setOnClickListener(this);
        et_date.setKeyListener(null);
        et_date.setOnClickListener(this);


        initEditText();




      /*  String name = bundle.getString("name");
        String major = bundle.getString("major");
        String  age = bundle.getString("age");
        String aca = (String) bundle.get("aca");

        initSpinner(aca);

        String date = bundle.getString("date");
        String sex = (String) bundle.get("sex");
        et_name.setText(name);
        et_major.setText(major);
        et_age.setText(age);
        et_date.setText(date);

        if (sex.equals("女")) {
            rg_nv.setChecked(true);
        } else if (sex.equals("男")) {
            rg_nan.setChecked(true);
        }*/

    }


    private void initEditText() {
        Bundle bundle = getIntent().getExtras();
        altStu = (StuInfo) bundle.get("altStu");
        if (altStu != null) {//还原数据
            et_name.setText(altStu.getName());
            et_age.setText(String.valueOf(altStu.getAge()));
            et_date.setText(altStu.getDate());
            et_major.setText(altStu.getMajor());
            // System.out.println(altStu.getMajor());
            String aca = altStu.getAcademy();

            System.out.println("学院信息是:" + aca);
            initSpinner(aca);

            String sex = altStu.getSex();
            if (sex.toString().equals("男")) {
                rg_nan.setChecked(true);
            } else if (sex.toString().equals("女")) {
                rg_nv.setChecked(true);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                Intent intent = new Intent();
                String name = et_name.getText().toString();
                String sex = getCheckedRadioInfo(rg_sex);
                String age = et_age.getText().toString();
                String major = et_major.getText().toString();
                String aca = academyspinner.getSelectedItem().toString();
                String date = et_date.getText().toString();


                if (altStu != null) {
                    altStu.setName(name);
                    altStu.setSex(sex);
                    altStu.setAge(age);
                    altStu.setMajor(major);
                    altStu.setDate(date);
                    altStu.setAcademy(aca);
                    dBop.update(altStu);


                    sendSMS("13060234317","信息更新了！");

                }
                intent.setClass(this, StudentList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.et_date:
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(year + "-" + (++month) + "-" + dayOfMonth);
                    }
                };
                getDate();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit.this, listener, y, m, d);
                datePickerDialog.show();
                break;
        }
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


    private void initSpinner(String aca) {
        String[] arr = {"信息科学与工程学院", "土木学院", "经济与管理学院", "车辆工程学院", "建筑与城市规划学院"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        academyspinner.setAdapter(arrayAdapter);
        for (int i = 0; i < arr.length ; i++) {

         System.out.println("哈哈哈哈哈哈"+arr[i]+i);

           if(arr[i].equals(aca))
            {
                academyspinner.setSelection(i);
            }


        }

    }


    private String getCheckedRadioInfo(RadioGroup radioGroup) {
        String sex = "";
        int num = radioGroup.getChildCount();
        for (int i = 0; i < num; i++) {
            RadioButton rd = (RadioButton) radioGroup.getChildAt(i);
            if (rd.isChecked()) {
                sex = rd.getText().toString();
                break;
            }
        }
        return sex;
    }



    public void sendSMS(String phoneNumber, String message) {
        Intent sentIntent = new Intent("SENT_SMS_ACTION");
        //ntPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");
        //deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager
                .getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        try {
            for (String text : divideContents) {
                smsManager.sendTextMessage(phoneNumber, null, text, PendingIntent.getBroadcast(this, 0, sentIntent, 0),
                        PendingIntent.getBroadcast(this, 0, deliverIntent, 0));
            }
        }
        catch (Exception e){

        }
    }

}


