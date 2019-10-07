package com.example.work1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Framelayout extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btn_1;
    private ImageButton btn_2;
    private ImageView im_paly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }
     public void onClick(View view){
        im_paly=findViewById(R.id.im_play);
        if(view.getId()==R.id.btn_1);
         {
             im_paly.setVisibility(View.VISIBLE);
             btn_2.setVisibility(View.VISIBLE);
         }
         if(view.getId()==R.id.btn_2){
            im_paly.setVisibility(View.INVISIBLE);
            btn_2.setVisibility(View.INVISIBLE);
         }
     }
}
