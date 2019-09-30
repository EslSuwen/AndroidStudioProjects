package com.example.work6;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.nio.channels.SeekableByteChannel;


    public class MainActivity extends Activity  {
        private int minWidth = 80;
        private ImageView imageView;
        private TextView textView1, textView2;
        private SeekBar seekBar1, seekBar2;
        private Matrix matrix = new Matrix();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            imageView = (ImageView) findViewById(R.id.imageview);
            seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
            seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
            textView1 = (TextView) findViewById(R.id.textview1);
            textView2 = (TextView) findViewById(R.id.textview2);

            seekBar1.setOnSeekBarChangeListener(new seekBar1ChangeListenner());
            seekBar2.setOnSeekBarChangeListener(new seekBar2ChangeListenner());

            // 一个结构描述的一般信息显示，比如大小、密度、和字体缩放
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            seekBar1.setMax(displayMetrics.widthPixels - minWidth);
        }

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

        class seekBar2ChangeListenner implements OnSeekBarChangeListener{

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                Bitmap bitmap = ((BitmapDrawable) (getResources().getDrawable(R.drawable.p))).getBitmap();
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

    }
