package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    Button button;
    EditText etName;
    EditText etMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        button = findViewById(R.id.bt);
        etName = findViewById(R.id.etName);
        etMajor = findViewById(R.id.etMajor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                StuInfo stu = new StuInfo();
                Editable name = etName.getText();
                Editable major = etMajor.getText();
                stu.setName(name.toString());
                stu.setMajor(name.toString());
                intent.putExtra("stu", stu);
                setResult(0, intent);
                finish();
            }
        });
    }


}
