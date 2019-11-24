package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etPass;
    private CheckBox cbIsRememberPass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
         sharedPreferences=getSharedPreferences("remenberpass", Context.MODE_PRIVATE);
         boolean isRemenber=sharedPreferences.getBoolean("remenberpass",false);
         if(isRemenber){
             String name=sharedPreferences.getString("name","");
             String pass=sharedPreferences.getString("pass","");
             etName.setText(name);
             etPass.setText(pass);
             cbIsRememberPass.setChecked(true);
         }
    }
    private void initViews() {
        etName=(EditText) findViewById(R.id.et_Name);
        etPass=(EditText)findViewById(R.id.et_Pass);
        cbIsRememberPass=(CheckBox) findViewById(R.id.cbIsRememberPass);
    }
    public void login(View view){
         String name=etName.getText().toString();
         String pass=etPass.getText().toString();
         if("123".equals(name)&&"123".equals(pass)){
             SharedPreferences.Editor editor=sharedPreferences.edit();
             if(cbIsRememberPass.isChecked()){
                 editor.putBoolean("remenberpass",true);
                 editor.putString("name",name);
                 editor.putString("pass",pass);
             }else {
                 editor.clear();
             }
             editor.commit();
             Intent intent=new Intent(this,login_successful.class);
             startActivity(intent);
             finish();
         }else {
             Toast.makeText(this,"账号或密码有误",Toast.LENGTH_LONG).show();
         }
    }
}
