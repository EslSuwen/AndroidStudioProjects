package com.example.listviewapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StuAdapater extends BaseAdapter implements View.OnClickListener {
    private List stuDates;///Aadapter 的数据源，
    private Context stuContext;//一个上下文
    private InnerItemOnClickListener innerItemOnClickListener;//自定义的内部点击监听接口

    //构造方法
    public StuAdapater(Context context, List list) {
        this.stuDates = list;
        this.stuContext = context;
    }

    /**
     * 获得列表项 item 的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return stuDates.size();
    }

    /**
     * 根据列表中 item 的位置获（i)获得 item 的值
     *
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return stuDates.get(i);
    }

    /**
     * 根据 item 的位置 i 获得 item 的 id
     *
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//创建一个 view 对象（item），对象的样式采用自定义的 layout 样式
        View stuView = View.inflate(stuContext, R.layout.list_view, null);
///获得 item 的每个子元素
        TextView tv_name = stuView.findViewById(R.id.name);
        TextView tv_major = stuView.findViewById(R.id.major);
        ImageView iv_edit = stuView.findViewById(R.id.edit);
        ImageView iv_delete = stuView.findViewById(R.id.delete);
//将数据源的一项数据和 item 的子元素绑定
        Student student = (Student) stuDates.get(i);
        tv_name.setText(student.getName());
        tv_major.setText(student.getMajor());
        iv_edit.setImageResource(R.drawable.edit);
        iv_delete.setImageResource(R.drawable.delete);
//给 iv_edit 增加一个监听
        iv_delete.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
//给删除和编辑的 imageView 设置一个标志，用 item 的位置 i 来定位，类似一个 id
        iv_delete.setTag(i);
        iv_edit.setTag(i);
        return stuView;
    }

    ///创建一个内部控件的监听接口
    interface InnerItemOnClickListener {
        abstract void itemClick(View view);
    }

    ///adapter 的内部控件的监听事件设置
    public void setOnInnerItemOnClickListener(InnerItemOnClickListener listener) {
//实现此接口的类的对象传入
        this.innerItemOnClickListener = listener;
    }

    //OnClick()方法，在此方法里面会调用子类重写的 itemClick()方法，
// 而方法里的参数 view 指的就是点击的 view。
// 所以我们只要在重写的 itemClick()方法里实现点击后的逻辑即可
    @Override
    public void onClick(View view) {
        innerItemOnClickListener.itemClick(view);
    }
}