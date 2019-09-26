package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_major;
    Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Student stu=(Student)bundle.get("stu");
        final int position=(int)bundle.get("position");
        setContentView(R.layout.activity_edit);
        et_name = findViewById(R.id.edit_name);
        et_major = findViewById(R.id.edit_major);
        et_name.setText(stu.getName());
        et_major.setText(stu.getMajor());
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Editable name=et_name.getText();
                Editable major=et_major.getText();
                intent.putExtra("stu",new Student(name.toString(),major.toString()));
                intent.putExtra("position",position);
                setResult(0,intent);
                finish();
            }
        });
    }
}
