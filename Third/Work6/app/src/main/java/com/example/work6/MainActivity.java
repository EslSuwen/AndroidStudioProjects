package com.example.work6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.SeekableByteChannel;


    public class MainActivity extends Activity  {
        private int minWidth = 80;
        private ImageView imageView;
        private TextView textView1, textView2;
        private SeekBar seekBar1, seekBar2;
        private Button btnDownload;
        private Matrix matrix = new Matrix();
        public static final int DOWNLOAD_CODE = 10001;
        public static final int DOWNLOAD_FAIL = 300;
        public static final int CONNECT_TIMEOUT = 2000;

        private Handler handler;

        private String path="https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg";

        private int fileLength;

        private Bitmap mBitmap;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            imageView = (ImageView) findViewById(R.id.imageview);
            seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
            seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
            textView1 = (TextView) findViewById(R.id.textview1);
            textView2 = (TextView) findViewById(R.id.textview2);
            btnDownload=findViewById(R.id.btn_download);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new GetPictThread(handler,path)).start();
                }
            });

            seekBar1.setOnSeekBarChangeListener(new seekBar1ChangeListenner());
            seekBar2.setOnSeekBarChangeListener(new seekBar2ChangeListenner());

            // 一个结构描述的一般信息显示，比如大小、密度、和字体缩放
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            seekBar1.setMax(displayMetrics.widthPixels - minWidth);

            //接收子线程的消息
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case DOWNLOAD_CODE:
                            /*
                             * 更新UI
                             * 提取消息中的bitmap,并设置ImageView
                             * */
                            Bitmap bitmap=(Bitmap) msg.obj;
                            if (bitmap!=null){
                                imageView.setImageBitmap(bitmap);//disPlay image
                            }
                            break;
                        case DOWNLOAD_FAIL:
                            Toast.makeText(MainActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };

        }

        //图形放大
        class seekBar1ChangeListenner implements SeekBar.OnSeekBarChangeListener {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                int newWidth = progress + minWidth;
                int newHeight = (int) (newWidth * 3 / 4);//按照原图片的比例缩放
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        newWidth, newHeight));
                textView1.setText("图像宽度：" + newWidth + " 图像高度：" + newHeight);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

        }

        //图形旋转
        class seekBar2ChangeListenner implements OnSeekBarChangeListener{

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                //Bitmap bitmap = ((BitmapDrawable) (getResources().getDrawable(R.drawable.p))).getBitmap();
                Bitmap bitmap=mBitmap;
                matrix.setRotate(progress);//设置翻转的角度
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() , bitmap.getHeight(), matrix,true);
                imageView.setImageBitmap(bitmap);
                textView2.setText(progress+"度");

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        }

        //自定义GetPictThread类实现Runnable类
        public class GetPictThread implements Runnable{
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
                    URL url=new URL(path);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //设置超时时间
                    connection.setConnectTimeout(30*1000);
                    //发起连接
                    connection.connect();

                    //获取状态码
                    int requestCode=connection.getResponseCode();
                    System.out.println(requestCode);

                    if (requestCode==HttpURLConnection.HTTP_OK){
                        /*
                         * 1.获得文件长度
                         * 2.通过缓冲输入流
                         * 3.将输入流转换成字节数组
                         * 4.将字节数组转换成位图
                         * */
                        fileLength=connection.getContentLength();

                        InputStream is=new BufferedInputStream(connection.getInputStream());

                        //获取到字节数组
                        byte[] arr=streamToArr(is);

                        //将字节数组转换成位图
                        mBitmap= BitmapFactory.decodeByteArray(arr,0,arr.length);

                        /*
                         * 下载完成后将消息发送出去
                         * 通知主线程，更新UI
                         * */
                        Message message=Message.obtain();
                        message.what=DOWNLOAD_CODE;
                        message.obj=mBitmap;
                        handler.sendMessage(message);

                    }else {
                        Log.e("TAG", "run:error "+requestCode);
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                    handler.sendEmptyMessage(DOWNLOAD_FAIL);
                }catch (IOException e){
                    e.printStackTrace();
                    handler.sendEmptyMessage(DOWNLOAD_FAIL);
                }
            }
        }

        //将输入流转换成字节数组
        public byte[] streamToArr(InputStream inputStream){

            try {
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                byte[] buffer=new byte[1024];
                int len;

                while ((len=inputStream.read(buffer))!=-1){
                    baos.write(buffer,0,len);
                }

                //关闭输出流
                baos.close();
                //关闭输入流
                inputStream.close();
                //返回字节数组
                return baos.toByteArray();
            }catch (IOException e){
                e.printStackTrace();
                //若失败，则返回空
                return null;
            }
        }

    }
