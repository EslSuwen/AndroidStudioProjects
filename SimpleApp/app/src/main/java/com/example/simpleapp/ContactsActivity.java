package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactsActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        setContentView(R.layout.activity_contacts);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv1:
                intent.putExtra("number","10000");
                setResult(0, intent);
                finish();
                break;
            case R.id.tv2:
                intent.putExtra("number","10010");
                setResult(0, intent);
                finish();
                break;
            case R.id.tv3:
                intent.putExtra("number","10086");
                setResult(0, intent);
                finish();
                break;
        }

    }
}
