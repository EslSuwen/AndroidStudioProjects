package com.example.list1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    ListView students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        students = findViewById(R.id.studentslist);
        List<Map<String, Object>> stuMap = new ArrayList<Map<String, Object>>();
        String[] names = {"Luna", "Bob", "Jack"};
        String[] majors = {"FL", "CS", "ED"};
        int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("icon", images[i]);
            m.put("name", names[i]);
            m.put("major", majors[i]);
            stuMap.add(m);
        }
        String[] key = {"icon", "name", "major"};
        int[] value={R.id.userIcon,R.id.userName,R.id.major};
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,stuMap,R.layout.list_view,key,value);
        students.setAdapter(simpleAdapter);


    }
}
