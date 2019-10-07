package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST = 8888;

    private EditText etPhone;
    private ImageButton IB_contacts;
    private ImageButton IB_messages;
    private ImageButton IB_call;
    private ImageButton IB_StartCamera;
    private ImageButton IB_map;
    private ImageView IV_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = findViewById(R.id.etPhone);
        IV_photo = findViewById(R.id.IV_photo);
        IB_call = findViewById(R.id.IB_call);
        IB_contacts = findViewById(R.id.IB_contacts);
        IB_messages = findViewById(R.id.IB_messages);
        IB_map = findViewById(R.id.IB_map);
        IB_StartCamera = findViewById(R.id.IB_camera);
        IB_StartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        IB_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        IB_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString();
                if (phone != null && phone.trim().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + phone.trim()));
                    startActivity(intent);
                } else return;
            }
        });
        IB_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据地名打开地图应用显示，字符串要记得编码！！
                String encodedName = Uri.encode("");
                Uri locationUri = Uri.parse("geo:0,0?q=" + encodedName);
                //根据经纬度打开地图显示，?z=11表示缩放级别，范围为1-23
//        Uri locationUri = Uri.parse("geo:26.5789070770,106.7170012064?z=11");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Intent chooser = Intent.createChooser(intent, "请选择地图软件");
                intent.setData(locationUri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            IV_photo.setImageBitmap(photo);
            return;
        }
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

    private void call(String action) {
        String phone = etPhone.getText().toString();
        if (phone != null && phone.trim().length() > 0) {
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phone.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        } else {
            makeText(this, "电话号码不能为空", LENGTH_LONG).show();
        }
    }
};



