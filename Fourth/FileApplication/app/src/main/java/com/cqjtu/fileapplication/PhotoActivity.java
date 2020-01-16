package com.cqju.fileapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener, innerItemClickListerner {
    private Button btn_photo;
    private ListView lv_1;
    private myAdapter adapter;
    private List<String>imagesname;
    private Map<String,Bitmap> images;
    String name;//图片路径
    private File file;//图片所在的文件夹
    String path;//图片所在文件夹的路径
    File imagefile;//图片文件
    private  Uri imageUri;//照相机关联的URI
    final private int CAMER=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        btn_photo=(Button)findViewById(R.id.btn_photo);
        lv_1=(ListView)findViewById(R.id.lv_1);
        imagesname=new ArrayList<String>();
        images=new HashMap<String, Bitmap>();
        btn_photo.setOnClickListener(this);
        //file=getFilesDir();//该文件夹不允许其他应用使用，因此不能作为照相机拍照后图片的保存路径
        path=getExternalFilesDir(null).getAbsolutePath()+File.separator+"mypictures"+File.separator;
        file=new File(path);
        if(!file.exists())
            file.mkdir();
        initial();
        adapter=new myAdapter(this,this);
        lv_1.setAdapter(adapter);
    }
    private void initial()
    {
        int i;
        if(file.list().length==0)
            return ;
        String [] list=file.list();
       for(i=0;i<list.length;i++)
       {
           imagesname.add(list[i]);
       }
    }

    private  Bitmap zoomBitmap2(String imagePath, int width, int height)
    {

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;}
        else {
            be = beHeight; }
        if (be <= 0) {
            be = 1; }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
    private Bitmap readImage(String content)
    {

        Bitmap pic=null;
        String filepath=path+content;
        pic=zoomBitmap2(filepath,250,250);
//        FileInputStream fis=null;
//        ByteArrayOutputStream bos=null;
//        try {
//           fis=new FileInputStream(filepath);
//           bos=new ByteArrayOutputStream();
//           byte[] buffer=new byte[1024];
//           int len=-1;
//           while((len=fis.read(buffer))!=-1)
//           {
//               bos.write(buffer,0,len);
//           }
//           byte[] bpic=bos.toByteArray();
//         if(bpic.length != 0)
//            pic= zoomBitmap(BitmapFactory.decodeByteArray(bpic,0,bpic.length),200,200);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//                try {
//                    bos.close();
//                    fis.close();
//                } catch (IOException e) {
//                    pic=null;
//                }
//        }
        return pic;
    }
    private Bitmap zoomBitmap(Bitmap bitmap,int width,int height)//图片缩放
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width /w);
        float scaleHeight = ((float) height /h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);
        return newbmp;
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.btn_photo:
                //na=imagens.size()+".jpg";
               DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                name=path+ df.format(new Date(System.currentTimeMillis())) +".jpg";
                imagefile=new File(name);
                try {
                    if(imagefile.exists())
                    {
                        imagefile.delete();
                    }
                    imagefile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri= FileProvider.getUriForFile(this, "com.cqju.fileapplication.PhotoActivity.fileprovider",imagefile);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMER);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case  CAMER:
                if(resultCode==RESULT_OK)
                {
                      imagesname.add(imagefile.getName());
                      adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void itemClick(View v) {
        switch (v.getId()) {
            case R.id.del:
            int position = (int) v.getTag();
            File file = new File(path, imagesname.get(position));
            file.delete();
            images.remove(imagesname.get(position));
            imagesname.remove(position);
            adapter.notifyDataSetChanged();
            break;
        }
    }
    class myAdapter extends BaseAdapter implements View.OnClickListener {
        private Context context;
        private LayoutInflater layoutInflater;
        private innerItemClickListerner listerner;

        public myAdapter(Context context, innerItemClickListerner listerner) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.listerner = listerner;
        }

        @Override
        public int getCount() {
            return imagesname.size();
        }

        @Override
        public Object getItem(int position) {
            return imagesname.get(position);
        }

        class viewHolder
        {
            private ImageButton btn_del;
            private ImageView pic;
            private TextView picna;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewHolder holder=new viewHolder();
            if(convertView==null) {
                convertView = layoutInflater.inflate(R.layout.pic_row, null);
                holder.pic = (ImageView) convertView.findViewById(R.id.pic);
                holder.picna = (TextView) convertView.findViewById(R.id.picna);
                holder.btn_del = (ImageButton) convertView.findViewById(R.id.del);
                convertView.setTag(holder);
            }else
            {
                holder=(viewHolder) convertView.getTag();
            }
            String na=imagesname.get(position);
            if(!images.containsKey(na))
            {
                Bitmap bitmap=readImage(na);
                if(bitmap!=null)
                {
                    images.put(na,bitmap);
                    holder.pic.setImageBitmap(bitmap);
                }
            }else {
                holder.pic.setImageBitmap(images.get(na));
            }
            holder.picna.setText(na);
            holder.btn_del.setOnClickListener(this);
            holder.btn_del.setTag(position);
            return convertView;
        }
        @Override
        public void onClick(View v) {
            listerner.itemClick(v);
        }
    }
}
