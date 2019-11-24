package com.example.testpic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView dataView;
    private static String currentPhotoName = "";
    private static GridAdapter adapter;
    private int index;
    private String isCheck;
    private ArrayList<PicInfo> arrayList;
    private List<ImgInfo> imgList = new ArrayList<ImgInfo>();
    private HashMap<Integer, List<ImgInfo>> allMap = new HashMap<Integer, List<ImgInfo>>();
    private ModelDetailAdapter modelDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        dataView = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
        // TODO Auto-generated method stub
        arrayList = new ArrayList<PicInfo>();
        for (int i = 0; i < 10; i++) {
            PicInfo picInfo = new PicInfo();
            picInfo.setNum(i + 1 + "");
            picInfo.setIndex(-1);
            picInfo.setPicStr("");
            arrayList.add(picInfo);
        }
        modelDetailAdapter = new ModelDetailAdapter(MainActivity.this);
        dataView.setAdapter(modelDetailAdapter);
    }


    public class ModelDetailAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Zujian zujian;

        public ModelDetailAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        public final class Zujian {
            public MyListView noScrollgridview1;

        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private void updateSingleRow(ListView listView, int id) {

            if (listView != null) {
                int start = listView.getFirstVisiblePosition();
                for (int i = start, j = listView.getLastVisiblePosition(); i <= j; i++) {
                    System.err.println("--" + ((PicInfo) listView.getItemAtPosition(i)).getIndex());
//                    Toast.makeText(MainActivity.this, "--" + ((PicInfo) listView.getItemAtPosition(i)).getIndex(), 1).show();
//	            	Messages itemAtPosition = listView.getItemAtPosition(i);
                    if (id == ((PicInfo) listView.getItemAtPosition(i)).getIndex()) {
                        View view = listView.getChildAt(i - start);
                        getView(id, view, listView);
                        break;
                    }
                }
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                zujian = new Zujian();
                convertView = layoutInflater.inflate(R.layout.record_model_detail_activity, parent, false);
                zujian.noScrollgridview1 = (MyListView) convertView
                        .findViewById(R.id.noScrollgridview1);
                convertView.setTag(zujian);
            } else {
                zujian = (Zujian) convertView.getTag();
            }
            try {
                final PicInfo picInfo = arrayList.get(position);
                zujian.noScrollgridview1.setTag(picInfo);
                zujian.noScrollgridview1.clearFocus();
                zujian.noScrollgridview1.setSelector(new ColorDrawable(Color.TRANSPARENT));
                adapter = new GridAdapter(MainActivity.this);
                zujian.noScrollgridview1.setAdapter(adapter);
                zujian.noScrollgridview1.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
//						PicInfo picInfo=	(PicInfo) zujian.noScrollgridview1.getTag();
//						for (int i = 0; i < arrayList.size(); i++) {
//							arrayList.get(i).setIndex(-1);
//							arrayList.set(i, picInfo);
//						}
                        picInfo.setIndex(position);
                        arrayList.set(position, picInfo);
                        index = position;
                        isCheck = "Y";
//						imgList.clear();
                        photo();

                    }
                });
                if (picInfo.getIndex() == position) {
                    adapter.update(allMap.get(position));
                    zujian.noScrollgridview1.setAdapter(adapter);
//					adapter.notifyDataSetChanged();
//				}else{

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return convertView;
        }

    }


    public void photo() {

        try {
            FileUtils.initSdPath(this);

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            currentPhotoName = String.valueOf(System.currentTimeMillis());
//		AppContext.set(AppConfig.KEY_IMG_TIME, currentPhotoName);
            File file = new File(FileUtils.SDPATH, currentPhotoName + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(openCameraIntent,
                    111);
        } catch (Exception ex) {
//			AppException.getAppExceptionHandler(XunjianModelActivity.this).uncaughtException(null, ex);
            Log.e("photo", ex.toString());
        }
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        private ArrayList<String> imageList;
        private List<ImgInfo> imgsList = new ArrayList<ImgInfo>();

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update(List<ImgInfo> imgList) {
            this.imgsList = imgList;
//		adapter.notifyDataSetChanged();
//		loading();
        }


        public int getCount() {
            if (imgsList != null) {
                if (imgsList.size() == 9) {
                    return 9;
                } else {
                    return (imgsList.size() + 1);
                }
            } else {
                return 0;
            }
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(
                        R.layout.item_published_listview, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                holder.time = (TextView) convertView
                        .findViewById(R.id.time_photo);
                holder.address = (TextView) convertView
                        .findViewById(R.id.address_photo);
                holder.del_info = (Button) convertView
                        .findViewById(R.id.info_del);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == imgsList.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                // Bimp.tempSelectBitmap.clear();

                holder.time.setVisibility(View.GONE);
                holder.address.setVisibility(View.GONE);
                holder.del_info.setVisibility(View.GONE);
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {

                holder.time.setVisibility(View.VISIBLE);
                holder.address.setVisibility(View.VISIBLE);
//			if(isFinish){
//				holder.del_info.setVisibility(View.GONE);
//			}else{
//				holder.del_info.setVisibility(View.VISIBLE);
//			}
                holder.image.setImageBitmap((Bitmap) imgsList.get(position).getImg());
                holder.address.setText("--" + String.valueOf(imgsList.get(position).getAddress()) + "--");
                holder.time.setText("--" + String.valueOf(imgsList.get(position).getTime()));

                holder.del_info.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View paramView) {
                        // TODO Auto-generated method stub
                        try {
                            imgsList.remove(position);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
//						AppException.getAppExceptionHandler(XunjianModelActivity.this).uncaughtException(null, e);
                            e.printStackTrace();
                        }
                    }
                });
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public TextView time;
            public TextView address;
            public Button del_info;
        }

//	Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 1:
//				adapter.notifyDataSetChanged();
//				break;
//			}
//			super.handleMessage(msg);
//		}
//	};
//	public void loading() {
//		new Thread(new Runnable() {
//			public void run() {
//				while (true) {
//					if (Bimp.max == list.size()) {
//						Message message = new Message();
//						message.what = 1;
//						handler.sendMessage(message);
//						break;
//					} else {
//						Bimp.max += 1;
//						Message message = new Message();
//						message.what = 1;
//						handler.sendMessage(message);
//					}
//				}
//			}
//		}).start();
//	}
    }

    private static String getCurrTime(String pattern) {
        if (pattern == null) {
            pattern = "yyyyMMddHHmmss";
        }
        return (new SimpleDateFormat(pattern)).format(new Date());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_OK) {


                    String imgPath = FileUtils.SDPATH + currentPhotoName + ".jpg";
                    String imgMinPath = FileUtils.SDPATH + currentPhotoName + "_min.jpg";



                    String time = getCurrTime("yyyy-MM-dd HH:mm:ss");

                    try {
                        List<ImgInfo> imgList = new ArrayList<ImgInfo>();
                        Bitmap bitmap_ya = ImageTool.compressImageFromFile(imgPath, MainActivity.this);
                        Bitmap minBitmap = ThumbnailUtils.extractThumbnail(bitmap_ya, 80, 80);
                        if (minBitmap != null) {
                            FileUtils.saveImg(minBitmap, imgMinPath);
                        }
                        if (bitmap_ya != null) {
                            FileUtils.saveImg(bitmap_ya, imgPath);
                        }
                        if (allMap.containsKey(index)) {
                            imgList = allMap.get(index);
                        } else {
                            imgList.clear();
                        }
//				HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        ImgInfo imgInfo = new ImgInfo();
                        imgInfo.setTime(time);
                        imgInfo.setAddress("--");
//				hashMap.put("img", minBitmap);
//				hashMap.put("minPath", imgMinPath);
//				hashMap.put("imgPath", imgPath);
                        imgInfo.setImg(minBitmap);
                        imgList.add(imgInfo);
//				if(allMap.get(index).size()==0){
                        allMap.put(index, imgList);
//				}
                        String imgPath1 = "file://" + imgPath;
                        modelDetailAdapter.notifyDataSetChanged();
//				}
                    } catch (Exception ex) {
//				AppException.getAppExceptionHandler(XunjianModelActivity.this).uncaughtException(null, ex);
                        ex.printStackTrace();
                    }

                }

        }
    }
}
