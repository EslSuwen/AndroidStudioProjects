package com.example.studentdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;


import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class StudentAdapter extends BaseAdapter implements View.OnClickListener{
    private List<StuInfo> stuDates;
    private Context stuContext;
    private InnerItemOnClickListener innerItemOnClickListener;
    DBop dbOperate=new DBop();
    public StudentAdapter(Context context,List list) {
        this.stuDates=list;
        this.stuContext=context;
        dbOperate.test(context);
    }

    @Override
    public int getCount() {
        return stuDates.size();
    }

    @Override
    public Object getItem(int position) {
        return stuDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View stuView=View.inflate(stuContext,R.layout.list,null);

       TextView tv_id=stuView.findViewById(R.id.li_id);
       TextView tv_name=stuView.findViewById(R.id.li_name);
        TextView tv_academy=stuView.findViewById(R.id.li_academy);
        ImageView iv_edit=stuView.findViewById(R.id.li_edit);
        ImageView iv_delete=stuView.findViewById(R.id.li_delete);

       final StuInfo student=(StuInfo) stuDates.get(position);

        tv_id.setText(String.valueOf(student.getId()));
        tv_name.setText(student.getName());
        tv_academy.setText(student.getAcademy());

        iv_edit.setImageResource(R.drawable.edit);
        iv_delete.setImageResource(R.drawable.delete);

        //增加监听

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context=v.getContext();
                Intent intent=new Intent();
                intent.setClass(context,Edit.class);
                intent.putExtra("altStu",student);
                ((Activity)context).startActivity(intent);
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOperate.delete(student.getId());
                stuDates.remove(student);
                StudentAdapter.this.notifyDataSetChanged();
            }
        });

        //给删除和编辑设置标志
        iv_edit.setTag(position);
        iv_delete.setTag(position);
        return stuView;
    }


    //创建内部控件监听接口
    interface  InnerItemOnClickListener{
        abstract  void itemClick(View view);
    }

    public void setOnInnerOnClickListenner(InnerItemOnClickListener listener)
    {
        this.innerItemOnClickListener=listener;
    }
    @Override
    public void onClick(View v) {
        innerItemOnClickListener.itemClick(v);
    }


}
