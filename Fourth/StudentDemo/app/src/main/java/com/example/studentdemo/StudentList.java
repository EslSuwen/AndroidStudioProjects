package com.example.studentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class StudentList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, StudentAdapter.InnerItemOnClickListener {

    private Button sl_add;
    private Button sl_search;
    private EditText search_id;
    DBop dbop = new DBop();

    //  private List<Student> students;
    List<Map<String, Object>> stuMap = new ArrayList<Map<String, Object>>();
    List<String> names = new ArrayList<String>();
    // List<String> majors = new ArrayList<String>();
    List<String> academy = new ArrayList<String>();
    ListView studentlist;
    private List<StuInfo> list = new ArrayList<StuInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        studentlist = (ListView) findViewById(R.id.studentlist);
        sl_add = (Button) findViewById(R.id.sl_add);

        sl_search = (Button) findViewById(R.id.sl_search);
        search_id=(EditText)findViewById(R.id.search_id);


        //  Bundle bundle = getIntent().getExtras();

        //    students = (ArrayList<StuInfo>) bundle.get("students");
        //   stuAdapater = new StudentAdapter(this, students);
        //   stuAdapater.setOnInnerOnClickListenner(this);
        //   studentlist.setAdapter(stuAdapater);

        sl_add.setOnClickListener(this);
        sl_search.setOnClickListener(this);

        studentlist.setOnItemClickListener(this);


        dbop.test(this);
        list = dbop.queryAll();
        if (!list.isEmpty()) {
            showList(list);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.sl_add:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.sl_search:
                String search = search_id.getText().toString();
                if (search.isEmpty()) return;
                    intent.setClass(this, searchActivity.class);
                    intent.putExtra("searchKey", search);
                    startActivity(intent);

                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ListView list = (ListView) parent;
        StuInfo s = (StuInfo) list.getItemAtPosition(position);
        String info = (String) s.getName() + "\n\n" + s.getAcademy();
        Toast.makeText(StudentList.this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClick(View view) {

        int position = (int) view.getTag();
        Intent intent = new Intent();
        switch (view.getId()) {
          /*  case R.id.li_edit:
                Toast.makeText(this, "编辑" + students.get(position).getName(), Toast.LENGTH_SHORT).show();
                intent.putExtra("students", (Serializable) students);
                String name = students.get(position).getName();
                String sex = students.get(position).getSex();
                String age = students.get(position).getAge();
                String aca = students.get(position).getAcademy();
                String major = students.get(position).getMajor();
                String date = students.get(position).getDate();
                intent.putExtra("name", name);
                intent.putExtra("sex", sex);
                intent.putExtra("age", age);
                intent.putExtra("aca", aca);
                intent.putExtra("major", major);
                intent.putExtra("date", date);

                intent.setClass(StudentList.this, Edit.class);
                startActivity(intent);
                students.remove(position);
                stuAdapater.notifyDataSetChanged();
                //setResult(0, intent);
                // finish();
                break;
            case R.id.li_delete:
                Toast.makeText(this, "删除" + students.get(position).getName(), Toast.LENGTH_SHORT).show();
                students.remove(position);//删除集合
                stuAdapater.notifyDataSetChanged();//适配器更新数据
                break;
            case R.id.sl_add:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;*/


        }
    }


    private void showList(List<StuInfo> stus) {
        StudentAdapter studentAdapter = new StudentAdapter(this, stus);
        studentlist.setAdapter(studentAdapter);
    }






}
