package com.example.piclistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /**文件夹下所有图片的bitmap*/
    private List<Bitmap> listpath;
    /**文件夹下图片的真实路径*/
    private String scanpath;
    /**显示图片的适配器*/
    private Photodaapter adapter;
    /**所有图片的名字*/
    public String[] allFiles;
    /**想要查找的文件夹*/
    private File folder;
    ListView picList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listpath = new ArrayList<>();
        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/OnePieceSD/FloorPage/");//图片地址
        /**将文件夹下所有文件名存入数组*/
        allFiles = folder.list();
        if (allFiles.length<1){
            Toast.makeText(MainActivity.this,"暂无图片", Toast.LENGTH_LONG).show();
        }else {
            /**遍历数组*/
            for (int i = 0; i < allFiles.length; i++) {
                scanpath = folder + "/" + allFiles[i];
                Log.i("Warning", "initData: "+scanpath);
                /**将文件转为bitmap如果为空则不是图片文件*/
                Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
                if(bitmap!=null) {
                    listpath.add(bitmap);
                }
            }
            /** 图片写入适配器*/
            adapter = new Photodaapter(listpath, this);
           picList.setAdapter(adapter);
        }
    }

    private void init(){

    }






    public class Photodaapter extends BaseAdapter {
        private List<Bitmap> mlist;
        private LayoutInflater minflater;

        public Photodaapter(List<Bitmap> list, Context context) {
            super();
            this.mlist = list;
            this.minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            VIewHolder viewholder;
            if (convertView == null) {
                viewholder = new VIewHolder();
                convertView = minflater.inflate(R.layout.terr_img_item, null);
                viewholder.img_item = (ImageView) convertView.findViewById(R.id.img_item);
                convertView.setTag(viewholder);
            } else {
                viewholder = (VIewHolder) convertView.getTag();
            }
            viewholder.img_item.setImageBitmap(mlist.get(position));
            return convertView;
        }
        class VIewHolder {
            ImageView img_item;
        }
    }

}
