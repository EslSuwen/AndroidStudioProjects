package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, StuAdapater.InnerItemOnClickListener {
    private ListView studentList;
    private StuAdapater stuAdapater;
    private List<Student> students;
    private ImageView iv_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        studentList =findViewById(R.id.studentList);
        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        initSpinner();
/*
构造一组学生的集合
*/
        students = new ArrayList<>();
        Student s1 = new Student("李四","21","男","信息科学与工程学院","计算机科学与技术","2017-9-1",null);
        Student s2 = new Student("王五","21","男","信息科学与工程学院", "物联网工程","2017-9-1",null);
        Student s3 = new Student("张三","21","男","信息科学与工程学院", "计算机科学与技术","2017-9-1",null);
        students.add(s1);
        students.add(s2);
        students.add(s3);
        stuAdapater = new StuAdapater(this, students);
///为配置器设置一个自定义的 item 内部控件的点击监听
        stuAdapater.setOnInnerItemOnClickListener(this);
///listview 设置适配器
        studentList.setAdapter(stuAdapater);
///LISTVIEW 设置选项监听
        studentList.setOnItemClickListener(this);
    }

    private void initSpinner() {
//        String[] sex_arr = {"男", "女"};
//        String[] major_arr = {"物联网工程", "计算机科学与技术", "材料科学与工程", "环境科学与工程", "化学化工与生物工程"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sex_arr);
//        spinner_sex.setAdapter(arrayAdapter);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, major_arr);
//        spinner_sexner_major.setAdapter(arrayAdapter);

    }

    ///列表的每个 item 的点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ListView list = (ListView) adapterView;
        Student s = (Student) list.getItemAtPosition(i);
        Toast.makeText(this, s.getName(), Toast.LENGTH_SHORT).show();
    }

    ///内部控件的点击事件方法
    @Override
    public void itemClick(View view) {
        int position = (int) view.getTag();
        switch (view.getId()) {
            case R.id.edit:
                Intent intent = new Intent();
                Student stu = students.get(position);
                intent.putExtra("stu", stu);
                intent.putExtra("position", position);
                intent.setClass(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 0);
//                Student stu=new Student("TBD","TBD");
//                students.set(position,stu);
//                stuAdapater.notifyDataSetChanged();//适配器更新数据
//                Toast.makeText(this, "已编辑" + position, Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                students.remove(position);//删除集合
                stuAdapater.notifyDataSetChanged();//适配器更新数据
                Toast.makeText(this, "已删除" + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (resultCode) {
            case 0:
                Bundle bundle = data.getExtras();
                Student stu = (Student) bundle.get("stu");
                int position = (int) bundle.get("position");
                students.set(position, stu);
                stuAdapater.notifyDataSetChanged();//适配器更新数据
                Toast.makeText(this, "已编辑" + position, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Bundle bundle1 = data.getExtras();
                Student stu1 = (Student) bundle1.get("stu");
                students.add(stu1);
                stuAdapater.notifyDataSetChanged();
                break;
        }

    }


}
