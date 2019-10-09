package com.example.work7s;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;


import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;

import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnClickListener {
    //声明并初始化一个保存要显示图像id的数组
    private int[] imageId = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
    private int index = 0;//当前显示图像的索引
    private ImageSwitcher imageSwitcher;//声明一个图像切换器对象

    List<Drawable> images = new ArrayList<>();
    public static final int DOWNLOAD_CODE = 10001;
    public static final int DOWNLOAD_FAIL = 300;
    public static final int CONNECT_TIMEOUT = 2000;
    private Button btn_download;

    private Handler handler;

    private String path = "http://img4.imgtn.bdimg.com/it/u=3109771133,3648418599&fm=15&gp=0.jpg";
    private String[] paths = {"http://img0.imgtn.bdimg.com/it/u=3418669363,500882015&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3109771133,3648418599&fm=15&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3515329134,82162239&fm=15&gp=0.jpg"
    };

    private int fileLength;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialView();

        //接收子线程的消息
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DOWNLOAD_CODE:
                        /*
                         * 更新UI
                         * 提取消息中的bitmap,并设置ImageView
                         * */
                        Bitmap bitmap = (Bitmap) msg.obj;
                        if (bitmap != null) {
//                            image_download.setImageBitmap(bitmap);//disPlay image
                            Resources resources = MainActivity.this.getResources();
                            BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
                            images.add(drawable);
                            imageSwitcher.setImageDrawable(drawable);
                            Toast.makeText(MainActivity.this, "下载成功" + images.size(), Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case DOWNLOAD_FAIL:
                        Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    //初始化控件
    public void initialView() {
        btn_download = findViewById(R.id.btn_download);
        imageSwitcher = findViewById(R.id.imageSwitcher1);//获取图像切换器
        //设置动画效果
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));//设置淡入动画
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));//设置淡出动画
        imageSwitcher.setFactory(new ViewFactory() {//设置View工厂

            @Override
            public View makeView() {
                ImageView imageView = null;
                imageView = new ImageView(MainActivity.this);//实例化一个ImageView类的对象
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);//设置保持纵横比居中缩放图像
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
//        imageSwitcher.setImageResource(imageId[index]);//显示默认的图片
//        imageSwitcher.setImageResource(R.drawable.pic1);
        Button up = findViewById(R.id.button1);
        Button down = findViewById(R.id.button2);
    }

    //按钮点击
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                if (images.size() == 3) {
                    Toast.makeText(MainActivity.this, "下载失败,图片已下载完成!",Toast.LENGTH_SHORT).show();
                    break;
                }
                //开启线程
                new Thread(new GetPictThread(handler, paths[images.size()])).start();
                break;
            case R.id.button1:
                if (images.size()==0) {
                    Toast.makeText(MainActivity.this, "请先下载图片!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (index > 0) {
                    index--;//图片索引后退一个
                } else {
                    index = images.size() - 1;//图片达到最前面一张之后，循环至最后一张
                }
//                imageSwitcher.setImageResource(imageId[index]);//显示当前图片
                imageSwitcher.setImageDrawable(images.get(index));
                break;
            case R.id.button2:
                if (images.size()==0) {
                    Toast.makeText(MainActivity.this, "请先下载图片!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (index < images.size() - 1) {
                    index++;//图片索引前进一个
                } else {
                    index = 0;//图片达到最后面一张之后，循环至第一张
                }
//                imageSwitcher.setImageResource(imageId[index]);//显示当前图片
                imageSwitcher.setImageDrawable(images.get(index));
                break;
        }
    }

    //自定义GetPictThread类实现Runnable类
    public class GetPictThread implements Runnable {
        //定义handler和path
        public Handler handler;
        public String path;

        //带参构造
        public GetPictThread(Handler handler, String path) {
            this.handler = handler;
            this.path = path;
        }

        //在run方法中实现图片下载
        @Override
        public void run() {
            //通过Get方法请求获取网络图片
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //设置请求方式
                connection.setRequestMethod("GET");
                //设置超时时间
                connection.setConnectTimeout(30 * 1000);
                //发起连接
                connection.connect();

                //获取状态码
                int requestCode = connection.getResponseCode();
                System.out.println(requestCode);

                if (requestCode == HttpURLConnection.HTTP_OK) {
                    /*
                     * 1.获得文件长度
                     * 2.通过缓冲输入流
                     * 3.将输入流转换成字节数组
                     * 4.将字节数组转换成位图
                     * */
                    fileLength = connection.getContentLength();

                    InputStream is = new BufferedInputStream(connection.getInputStream());

                    //获取到字节数组
                    byte[] arr = streamToArr(is);

                    //将字节数组转换成位图
                    mBitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

                    /*
                     * 下载完成后将消息发送出去
                     * 通知主线程，更新UI
                     * */
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_CODE;
                    message.obj = mBitmap;
                    handler.sendMessage(message);

                } else {
                    Log.e("TAG", "run:error " + requestCode);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(DOWNLOAD_FAIL);
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(DOWNLOAD_FAIL);
            }
        }
    }

    //将输入流转换成字节数组
    public byte[] streamToArr(InputStream inputStream) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            //关闭输出流
            baos.close();
            //关闭输入流
            inputStream.close();
            //返回字节数组
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            //若失败，则返回空
            return null;
        }
    }
}
