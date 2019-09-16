package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private EditText etPhone;
    private ImageButton IB_contacts;
    private ImageButton IB_messages;
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String phone = etPhone.getText().toString();
            if (phone != null && phone.trim().length() > 0) {
                //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + phone.trim()));
                startActivity(intent);//调用上面这个intent实现拨号
            } else return;
        }
    };


    private ImageButton IB_StartCamera;
    private ImageView IV_photo;

    private static final String TAG = "main";
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = findViewById(R.id.etPhone);
        IB_contacts = findViewById(R.id.IB_contacts);
        IB_messages = findViewById(R.id.IB_messages);
        IB_StartCamera = findViewById(R.id.IB_camera);
        IV_photo = findViewById(R.id.IV_photo);
        IV_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                /* 指定相机拍摄照片保存地址 */
                intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                // 根据文件地址创建文件
                File file = new File(FILE_PATH);
                if (file.exists()) {
                    file.delete();
                }
                // 把文件地址转换成Uri格式
                Uri uri = Uri.fromFile(file);
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
                // 不指定相机拍摄照片保存地址

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
        IB_messages.setOnClickListener(listener);


    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            etPhone.setText("1234567890");
//            return;
//        }
//        String info = (String) data.getExtras().get("number");
//        etPhone.setText(info);
//    }


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
            makeText(this, "电话号码不能为空", LENGTH_LONG).show();
        }
    }

    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = null;
            /* 指定相机拍摄照片保存地址 */
            intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            // 根据文件地址创建文件
            File file = new File(FILE_PATH);
            if (file.exists()) {
                file.delete();
            }
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(file);
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 0);
            // 不指定相机拍摄照片保存地址
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode=" + resultCode);

        if (requestCode == 0) {
            File file = new File(FILE_PATH);
            Uri uri = Uri.fromFile(file);
            IV_photo.setImageURI(uri);
        } else if (requestCode == 1) {
            Log.i(TAG, "默认content地址：" + data.getData());
            IV_photo.setImageURI(data.getData());
        }
    }
};
