package com.example.work7s;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;


import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;

import android.widget.ViewSwitcher.ViewFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;


public class MainActivity extends Activity {
    //声明并初始化一个保存要显示图像id的数组
    private int[] imageId = new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    private int index = 0;//当前显示图像的索引
    private ImageSwitcher imageSwitcher;//声明一个图像切换器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        imageSwitcher.setImageResource(imageId[index]);//显示默认的图片

        Button up = findViewById(R.id.button1);
        Button down = findViewById(R.id.button2);
        up.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (index > 0) {
                    index--;//图片索引后退一个
                } else {
                    index = imageId.length - 1;//图片达到最前面一张之后，循环至最后一张
                }
                imageSwitcher.setImageResource(imageId[index]);//显示当前图片
            }
        });
        down.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (index < imageId.length - 1) {
                    index++;//图片索引前进一个
                } else {
                    index = 0;//图片达到最后面一张之后，循环至第一张
                }
                imageSwitcher.setImageResource(imageId[index]);//显示当前图片
            }
        });
    }
}