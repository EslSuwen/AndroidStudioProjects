package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etPhone;
    private ImageButton IB_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = findViewById(R.id.etPhone);
        IB_contacts = findViewById(R.id.IB_contacts);
        IB_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            etPhone.setText("1234567890");
            return;
        }
        String info = (String) data.getExtras().get("number");
            etPhone.setText(info);
    }


    // ACTION_CALL方式拨打电话(直接拨打)
    public void onClickActionCall(View v) {
        //这里的Intent.ACTION_CALL实际就是一个特定的字符串，
        //ACTION_CALL = "android.intent.action.CALL"，
        //告诉系统我要直接拨号了。
        call(Intent.ACTION_CALL);
    }

    // ACTION_DIAL方式拨打电话(打开拨号界面)
    public void onClickActionDial(View v) {
        //同理，这里的Intent.ACTION_DIAL也是一个特定的字符串
        //ACTION_DIAL = "android.intent.action.DIAL"
        //告诉系统我要打开拨号界面，并把要拨的号显示在拨号界面上，由用户决定是否要拨打。
        call(Intent.ACTION_DIAL);
    }

    private void call(String action) {
        String phone = etPhone.getText().toString();
        if (phone != null && phone.trim().length() > 0) {
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phone.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        } else {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }
    }
}
