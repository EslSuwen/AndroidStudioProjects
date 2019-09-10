package com.example.studentinfo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Destroyable;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    List<StuInfo> stus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tv = findViewById(R.id.tvInfo);
        setSupportActionBar(toolbar);
        stus = new ArrayList<StuInfo>();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data==null)
            return;
        Bundle bundle=data.getExtras();
        StuInfo stuInfo = new StuInfo();
        String info = "";

        stuInfo = (StuInfo) bundle.get("stu");
        stus.add(stuInfo);

        for (StuInfo s : stus) {
            info += s.getName() + "--" + s.getMajor() + "\n";
        }

        tv.setText(info);
    }

//    public void freshInfo() {
//        Bundle bundle = getIntent().getExtras();
//
//        if (bundle.get("stu") == null)
//            return;
//        StuInfo stuInfo = new StuInfo();
//        String info = "";
//
//        stuInfo = (StuInfo) bundle.get("stu");
//        stus.add(stuInfo);
//
//        for (StuInfo s : stus) {
//            info += s.getName() + "--" + s.getMajor() + "\n";
//        }
//
//        tv.setText(info);
//    }

}
