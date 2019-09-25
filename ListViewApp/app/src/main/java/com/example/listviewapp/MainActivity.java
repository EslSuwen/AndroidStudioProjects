package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, StuAdapater.InnerItemOnClickListener {
    private ListView studentList;
    private StuAdapater stuAdapater;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentList = (ListView) findViewById(R.id.studentList);
/*
构造一组学生的集合
*/
        students = new ArrayList<>();
        Student s1 = new Student("李四", "物联网工程");
        Student s2 = new Student("王五", "物联网工程");
        Student s3 = new Student("张三", "计算机科学与技术");
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
                Toast.makeText(this, "编辑" + position, Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                students.remove(position);//删除集合
                stuAdapater.notifyDataSetChanged();//适配器更新数据
                Toast.makeText(this, "删除" + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}