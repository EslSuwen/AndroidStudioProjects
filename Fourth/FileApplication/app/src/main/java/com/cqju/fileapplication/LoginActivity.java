package com.cqju.fileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_user;
    private  EditText edt_pwd;
    private CheckBox cb_rm;
    private Button btn_login;
    private ImageButton ib_lsit;
    private List<String> userlist;
    private SharedPreferences usersp;
    private SharedPreferences pwdsp;
    private PopupWindow pop;
    private View layout;
    private MyAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_user=(EditText)findViewById(R.id.user);
        edt_pwd=(EditText)findViewById(R.id.pwd);
        btn_login=(Button)findViewById(R.id.btn_login);
        ib_lsit=(ImageButton)findViewById(R.id.btn_list);
        cb_rm=(CheckBox)findViewById(R.id.cb_rm);
        layout=findViewById(R.id.layout);
        usersp=getSharedPreferences("user",MODE_PRIVATE);
        pwdsp=getSharedPreferences("pwd",MODE_PRIVATE);
        getuserList();
        btn_login.setOnClickListener(this);
        ib_lsit.setOnClickListener(this);
        edt_user.setOnClickListener(this);
        adapter=new MyAdapter(LoginActivity.this);
        listView=new ListView(LoginActivity.this);
        listView.setAdapter(adapter);
    }
    private void getuserList()
    {
        int i;
      userlist=new ArrayList<String>();
        Map<String,?> umap=usersp.getAll();
        for(i=0;i<umap.size();i++)
        {
            userlist.add(umap.get(i+"").toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                if(cb_rm.isChecked())
                {
                    SharedPreferences.Editor uediton=usersp.edit();
                    SharedPreferences.Editor pwdediton=pwdsp.edit();
                    String key=edt_user.getText().toString();
                    String value=edt_pwd.getText().toString();
                    if(!userlist.contains(key)) {
                        userlist.add(key);
                        pwdediton.putString(key, value);
                        pwdediton.commit();
                        int pos = userlist.size() - 1;
                        uediton.putString(pos + "", key);
                        uediton.commit();
                        adapter.notifyDataSetChanged();
                    }

                    edt_pwd.setText("");
                    edt_user.setText("");
                    edt_user.requestFocus();
                }else
                {
                    edt_pwd.setText("");
                    edt_user.setText("");
                    edt_user.requestFocus();
                }
                break;
            case R.id.btn_list:
                if(userlist.size()==0)
                    return;
                if(pop==null)
                {
                    pop=new PopupWindow(listView,layout.getWidth(),5*layout.getHeight());
                    pop.showAsDropDown(layout);
                }else{
                    if(pop.isShowing())
                    {
                        pop.dismiss();
                    }else
                    {
                        pop.showAsDropDown(edt_user);
                    }
                }
                break;
            case R.id.user:
                if(pop!=null &&pop.isShowing())
                    pop.dismiss();
        }
    }

    class MyAdapter extends BaseAdapter
    {
        private Context context;
        private LayoutInflater layoutInflater;
        private TextView tv_1;
        private ImageButton ib_close;
        public MyAdapter(Context context)
        {
            this.context=context;
            layoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return userlist.size();
        }

        @Override
        public Object getItem(int position) {
            return userlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

                convertView=layoutInflater.inflate(R.layout.list_row,null);
                ib_close=(ImageButton) convertView.findViewById(R.id.close_row);
                tv_1=(TextView)convertView.findViewById(R.id.text_row);
                final String editContent = userlist.get(position);
                tv_1.setText(userlist.get(position).toString());
                tv_1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        edt_user.setText(editContent);
                        edt_pwd.setText(pwdsp.getString(editContent,null));
                        pop.dismiss();
                        return false;
                    }
                });
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor pwdediton=pwdsp.edit();
                        SharedPreferences.Editor ueditor=usersp.edit();
                        userlist.remove(position);
                        notifyDataSetChanged();
                        pwdediton.remove(editContent);
                        ueditor.remove(position+"");
                        pwdediton.commit();
                        ueditor.commit();
                        if(userlist.size()==0)
                            pop.dismiss();
                    }
                });
            return convertView;
        }
    }

}
