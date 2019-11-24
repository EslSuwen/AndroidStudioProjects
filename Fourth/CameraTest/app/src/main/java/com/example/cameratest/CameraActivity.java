package com.example.cameratest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cameratest.adapter.HorizontalListViewAdapter;
import com.example.cameratest.views.HorizontalListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2018/7/2.
 */

public class CameraActivity extends Activity {

    private static SurfaceView surfaceView;               //图像实时窗口
    private Button btn_takePhoto;                         //拍照按钮
    private SurfaceHolder surfaceHolder;                  //定义访问surfaceView的接口

    private static final int CAMERA_NOTEXIST = -1;        //无摄像头标记
    private static final int FRONT = 1;                   //前置摄像头标记
    private static final int BACK = 2;                    //后置摄像头标记
    private int currentCameraType = CAMERA_NOTEXIST;      //当前打开摄像头标记
    private int currentCameraIndex = -1;                  //当前摄像头下标
    private boolean mPreviewRunning = false;              //预览是否启动

    private Camera mCamera = null;                        //Camera对象

    View olderSelectView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(R.layout.activity_main);
        //设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        surfaceView = findViewById(R.id.camera_surfaceView);
        btn_takePhoto = findViewById(R.id.btn_takePhoto);

        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(new CameraSurfaceCallBack());
        btn_takePhoto.setOnClickListener(new BtnTakePhotoListener());

        initUI();
    }

    private void initUI(){
        final HorizontalListView hListView;
        final HorizontalListViewAdapter hListViewAdapter;
        hListView = (HorizontalListView)findViewById(R.id.horizon_listview);
        //previewImg = (ImageView)findViewById(R.id.image_preview);
        String[] titles = {"1", "2", "3", "4", "5", "6", "7", "8"};

        hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),titles);
        hListView.setAdapter(hListViewAdapter);

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
              if(olderSelectView == null){
                  olderSelectView = view;
              }else{
                  olderSelectView.setSelected(false);
                  olderSelectView = null;
              }
              olderSelectView = view;
              view.setSelected(true);
                //previewImg.setImageResource(ids[position]);
                hListViewAdapter.setSelectIndex(position);
                hListViewAdapter.notifyDataSetChanged();

                handler.obtainMessage(position).sendToTarget();

            }
        });

    }

    /**
     * 查找摄像头
     *
     * @param camera_facing 按要求查找，镜头是前还是后
     * @return -1表示找不到
     */
    private int findBackOrFrontCamera(int camera_facing) {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == camera_facing) {
                return camIdx;
            }
        }
        return -1;
    }

    /**
     * 按照type的类型打开相应的摄像头
     *
     * @param type 标志当前打开前还是后的摄像头
     * @return 返回当前打开摄像机的对象
     */
    private Camera openCamera(int type) {
        int frontIndex = -1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();

        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontIndex = cameraIndex;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backIndex = cameraIndex;
            }
        }

        currentCameraType = type;
        if (type == FRONT && frontIndex != -1) {
            currentCameraIndex = frontIndex;
            return Camera.open(frontIndex);
        } else if (type == BACK && backIndex != -1) {
            currentCameraIndex = backIndex;
            return Camera.open(backIndex);
        }
        return null;
    }

    /**
     * 初始化摄像头
     * @param holder
     */
    private void initCamera(SurfaceHolder holder){
        Log.e("TAG","initCamera");
        if (mPreviewRunning)
            mCamera.stopPreview();

        Camera.Parameters parameters;
        try{
            //获取预览的各种分辨率
            parameters = mCamera.getParameters();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        //这里我设为480*800的尺寸
        parameters.setPreviewSize(480,800);
        // 设置照片格式
        parameters.setPictureFormat(PixelFormat.JPEG);
        //设置图片预览的格式
        parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
        setCameraDisplayOrientation(this,currentCameraIndex,mCamera);
        try{
            mCamera.setPreviewDisplay(holder);
        }catch(Exception e){
            if(mCamera != null){
                mCamera.release();
                mCamera = null;
            }
            e.printStackTrace();
        }
        mCamera.startPreview();
        mPreviewRunning = true;
        handler.obtainMessage(99).sendToTarget();
    }

    /**
     * 设置旋转角度
     * @param activity
     * @param cameraId
     * @param camera
     */
    private void setCameraDisplayOrientation(Activity activity,int cameraId,Camera camera){
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch(rotation){
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        }else{
            result = (info.orientation - degrees +360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     * 实现拍照功能
     */
    public void takePhoto(){
        Camera.Parameters parameters;
        try{
            parameters = mCamera.getParameters();
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        //获取摄像头支持的各种分辨率,因为摄像头数组不确定是按降序还是升序，这里的逻辑有时不是很好找得到相应的尺寸
        //可先确定是按升还是降序排列，再进对对比吧，我这里拢统地找了个，是个不精确的...
        List<Camera.Size> list = parameters.getSupportedPictureSizes();
        int size = 0;
        for (int i =0 ;i < list.size() - 1;i++){
            if (list.get(i).width >= 480){
                //完美匹配
                size = i;
                break;
            }
            else{
                //找不到就找个最接近的吧
                size = i;
            }
        }
        //设置照片分辨率，注意要在摄像头支持的范围内选择
        parameters.setPictureSize(list.get(size).width,list.get(size).height);
        //设置照相机参数
        mCamera.setParameters(parameters);

        //使用takePicture()方法完成拍照
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            //自动聚焦完成后拍照
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success && camera != null){
                    mCamera.takePicture(new ShutterCallback(), null, new Camera.PictureCallback() {
                        //拍照回调接口
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            savePhoto(data);
                            //停止预览
                            mCamera.stopPreview();
                            //重启预览
                            mCamera.startPreview();
                        }
                    });
                }
            }
        });
    }

   /* *//**
     * 快门回调接口，如果不想拍照声音，直接将new ShutterCallback()修改为null即可
     */
    private class ShutterCallback implements Camera.ShutterCallback {
        @Override
        public void onShutter() {
            MediaPlayer mPlayer = new MediaPlayer();
            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shutter);
            try{
                mPlayer.prepare();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            mPlayer.start();
        }
    }

    /**
     * 将拍照保存下来
     * @param data
     */
    public void savePhoto(byte[] data){
        FileOutputStream fos = null;
        String timeStamp =new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        //保存路径+图片名字
        String imagePath = setPicSaveFile() + "/" + timeStamp + ".png";
        try{
            fos = new FileOutputStream(imagePath);
            fos.write(data);
            //清空缓冲区数据
            fos.flush();
            //关闭
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(this,"拍照成功!",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置照片的路径，具体路径可自定义
     * @return
     */
    private String setPicSaveFile(){
        //创建保存的路径
        File storageDir = getOwnCacheDirectory(this,"MyCamera/photos");
        //返回自定义的路径
        return storageDir.getPath();
    }

    private File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        //判断SD卡正常挂载并且拥有根限的时候创建文件
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) &&
                hasExternalStoragePermission(context)){
            appCacheDir = new File(Environment.getExternalStorageDirectory(),cacheDir);
        }
        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()){
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * 检查是否有权限
     * @param context
     * @return
     */
    private boolean hasExternalStoragePermission(Context context) {
        int permission = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        //PERMISSION_GRANTED=0
        return permission == 0;
    }


    /**
     * 拍照按钮事件回调
     */
    public class BtnTakePhotoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("TAG","拍照按钮事件回调");
            takePhoto();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setCameraWonderful(Camera.Parameters.EFFECT_NONE);
                    break;
                case 1:
                    setCameraWonderful(Camera.Parameters.EFFECT_MONO);
                    break;
                case 2:
                    setCameraWonderful(Camera.Parameters.EFFECT_NEGATIVE);
                    break;
                case 3:
                    setCameraWonderful(Camera.Parameters.EFFECT_SOLARIZE);
                    break;
                case 4:
                    setCameraWonderful(Camera.Parameters.EFFECT_SEPIA);
                    break;
                case 5:
                    setCameraWonderful(Camera.Parameters.EFFECT_POSTERIZE);
                    break;
                case 6:
                    setCameraWonderful(Camera.Parameters.EFFECT_WHITEBOARD);
                    break;
                case 7:
                    setCameraWonderful(Camera.Parameters.EFFECT_BLACKBOARD);
                    break;
                case 8:
                    setCameraWonderful(Camera.Parameters.EFFECT_AQUA);
                    break;
                case 99:
                    initUI();
                    break;
            }
        }
    };

    private void setCameraWonderful(String effect){
        try{
            if (mPreviewRunning) {
                mCamera.stopPreview();
            }
            Camera.Parameters parameters;
            parameters = mCamera.getParameters();

            parameters.setColorEffect(effect);

            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * surfaceView实例回调
     */
    public class CameraSurfaceCallBack implements SurfaceHolder.Callback {

       /* public CameraSurfaceCallBack(Context context) {
            super(context);
        }*/

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.e("TAG","------surfaceCreated------");
            try {
                //这里我优先找后置摄像头,找不到再找前面的
                int cameraIndex = findBackOrFrontCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
                if (cameraIndex == -1) {
                    cameraIndex = findBackOrFrontCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    if (cameraIndex == -1) {
                        Log.e("TAG", "No Camera!");
                        currentCameraType = CAMERA_NOTEXIST;
                        currentCameraIndex = -1;
                        return;
                    } else {
                        currentCameraType = FRONT;
                    }
                } else {
                    currentCameraType = BACK;
                }

                //找到想要的摄像头后，就打开
                if (mCamera == null) {
                    mCamera = openCamera(currentCameraType);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.e("TAG","------surfaceChanged------");
            initCamera(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
