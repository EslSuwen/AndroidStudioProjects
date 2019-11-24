package com.example.testpic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.text.TextPaint;
import android.widget.Toast;

public class ImageTool {

	static URL fileUrl = null;
	public static final int ALL_SAVE_FLAG = 0x1F;
	  /**
     * 根据指定的图像路径和大小来获取缩略图
    * 此方法有两点好处�?
    *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度�?
    *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图�??
    *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
    *        用这个工具生成的图像不会被拉伸�??
    * @param imagePath 图像的路�?
    * @param width 指定输出图像的宽�?
    * @param height 指定输出图像的高�?
    * @return 生成的缩略图
    */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放�?
       int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
       bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }    


	
	
	/**
	 * @param urlpath
	 * @return Bitmap
	 * 根据图片url获取图片对象
	 */
	public static Bitmap getBitMBitmap(String urlpath) {
		Bitmap map = null;
		try {
			URL url = new URL(urlpath);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream in;
			in = conn.getInputStream();
			map = BitmapFactory.decodeStream(in);
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
//	图片按比例大小压缩方法（根据路径获取图片并压缩）�?
	public static  Bitmap getimage(String srcPath, Activity activity) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//�?始读入图片，此时把options.inJustDecodeBounds 设回true�?
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;//默认为ARGB_8888
		newOpts.inPurgeable = true;//
		newOpts.inInputShareable = true;//
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，�?以高和宽我们设置�?
		float hh = 800f;//这里设置高度�?800f
		float ww = 480f;//这里设置宽度�?480f
		//缩放比�?�由于是固定比例缩放，只用高或�?�宽其中�?个数据进行计算即�?
		int be = 1;//be=1表示不缩�?
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩�?
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩�?
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?
		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Toast.makeText(context, text, duration)
			e.printStackTrace();
		}
//		Toast.makeText(activity, "图片缩放�?"+bitmap, 1).show();
		return compressImage(bitmap);//压缩好比例大小后再进行质量压�?
	}
	
	public static int computeSampleSize(BitmapFactory.Options options,


	        int minSideLength, int maxNumOfPixels) {


	    int initialSize = computeInitialSampleSize(options, minSideLength,


	            maxNumOfPixels);

	 

	    int roundedSize;


	    if (initialSize <= 8) {


	        roundedSize = 1;


	        while (roundedSize < initialSize) {


	            roundedSize <<= 1;


	        }


	    } else {


	        roundedSize = (initialSize + 7) / 8 * 8;


	    }

	 

	    return roundedSize;

	}

	

	 

	private static int computeInitialSampleSize(BitmapFactory.Options options,


	        int minSideLength, int maxNumOfPixels) {


	    double w = options.outWidth;


	    double h = options.outHeight;

	 

	    int lowerBound = (maxNumOfPixels == -1) ? 1 :


	            (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));


	    int upperBound = (minSideLength == -1) ? 128 :


	            (int) Math.min(Math.floor(w / minSideLength),


	            Math.floor(h / minSideLength));

	 

	    if (upperBound < lowerBound) {


	        // return the larger one when there is no overlapping zone.


	        return lowerBound;


	    }

	 

	    if ((maxNumOfPixels == -1) &&


	            (minSideLength == -1)) {


	        return 1;


	    } else if (minSideLength == -1) {


	        return lowerBound;


	    } else {


	        return upperBound;


	    }

	}   

 
//	第三：图片按比例大小压缩方法（根据Bitmap图片压缩）：
	public static  Bitmap comp(Bitmap image, Activity activity) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出	
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos�?
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//�?始读入图片，此时把options.inJustDecodeBounds 设回true�?
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，�?以高和宽我们设置�?
		float hh = 900f;//这里设置高度�?800f
		float ww = 600f;//这里设置宽度�?480f
		//缩放比�?�由于是固定比例缩放，只用高或�?�宽其中�?个数据进行计算即�?
		int  be = 1;//be=1表示不缩�?
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩�?
//			be = (int) (newOpts.outWidth / ww);
			be=(Float.valueOf(newOpts.outWidth / ww)).intValue();
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩�?
//			be = (int) (newOpts.outHeight / hh);
			be=(Float.valueOf(newOpts.outHeight / hh)).intValue();
		}
		if (be <= 0)
			be = 1;
		
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?
		isBm = new ByteArrayInputStream(baos.toByteArray());
		image.recycle();
		System.gc();
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		Toast.makeText(activity, "图片缩放�?"+bitmap, Toast.LENGTH_LONG).show();
		return compressImage(bitmap);//压缩好比例大小后再进行质量压�?
	}
	
	//质量压缩
	public static  Bitmap compressImage(Bitmap image) {
		Bitmap bitmap = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(StrKit.notNull(image)){
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这�?100表示不压缩，把压缩后的数据存放到baos�?
		int options = 90;
		while ( baos.toByteArray().length / 1024>100&&options>0) {	//循环判断如果压缩后图片是否大�?100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos�?
			options -= 10;//每次都减�?10
		}
		image.recycle();
		 System.gc() ; 
		 try {
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream�?
	
			bitmap = BitmapFactory.decodeStream(isBm, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
		}
		else{
			return null;
		}
	}
	
	


	public static Bitmap scaleBitmap(String src, int max) {
		// 获取图片的高和宽
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这一个设置使 BitmapFactory.decodeFile获得的图片是空的,但是会将图片信息写到options�?
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(src, options);
		// 计算比例 为了提高精度,本来是要640 这里缩为64
		max = max / 10;
		int be = options.outWidth / max;
		if (be % 10 != 0) {
			be += 10;
			be = be / 10;
		}
		if (be <= 0)
			be = 1;
		options.inSampleSize = 2;
		// 设置可以获取数据
		options.inJustDecodeBounds = false;
		// 获取图片
		return BitmapFactory.decodeFile(src, options);
	}

	/**
	 * 图片压缩
	 * 
	 * @param srcPath
	 * @param activity 
	 * @return
	 */
	public static Bitmap compressImageFromFile(String srcPath, Activity activity) {
//		File imgFile = new File(srcPath);
//		try {
//			if(!imgFile.exists()){
//				imgFile.createNewFile();
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读�?,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;//默认为ARGB_8888
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0){
			be = 1;}

		newOpts.inPreferredConfig = null;// 该模式是默认�?,可不�?
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时�?�图片自动被回收
		newOpts.inSampleSize = be;// 设置采样�?

		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//			BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, newOpts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(activity, "oom异常", 1);
			e.printStackTrace();
		}
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return compressImage(bitmap);
	}

	// 加水�? 也可以加文字
	public static Bitmap watermarkBitmap(Activity activity, Bitmap src,
			Bitmap watermark, String title, String title2, String time) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = 0;
		int wh = 0;
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建�?个新的和SRC长度宽度�?样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// �? 0�?0坐标�?始画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {
			ww = watermark.getWidth();
			wh = watermark.getHeight();
			paint.setAlpha(60);
			// 横竖�?

			if (h > w) {
				// 竖屏
				cv.drawBitmap(watermark, 10, (h - wh) / 9*8, paint);// 在src的右下角画入水印

			} else {
				// 横屏
				cv.drawBitmap(watermark, 10, (h - wh) / 4*3 , paint);// 在src的左下角画入水印
			}
		}
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		TextPaint textPaint = new TextPaint();
		 textPaint.setColor(Color.WHITE);
		textPaint.setTypeface(font);
		// textPaint.setTextSize(1200.0f);
		textPaint.setTextSize(30);
		textPaint.setAlpha(50);

		// 加入文字
		if (title != null) {
				cv.drawText(title, 10, h - 70, textPaint);
		}
		if (title2 != null) {
				cv.drawText(title2, 10, h - 40, textPaint);
			}
		
		if (time != null) {
				cv.drawText(time, 10, h - 10, textPaint);
		}
		
		
		cv.save();// 保存
		cv.restore();// 存储
		return newb;
	}
	
	
	// 加水�? 也可以加文字
		public static Bitmap watermarkBitmap(Activity activity, Bitmap src,
				Bitmap watermark, String imageType,String title, String title2, String time) {
			if (src == null) {
				return null;
			}
			int w = src.getWidth();
			int h = src.getHeight();
			int ww = 0;
			int wh = 0;
			Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建�?个新的和SRC长度宽度�?样的位图
			Canvas cv = new Canvas(newb);
			cv.drawBitmap(src, 0, 0, null);// �? 0�?0坐标�?始画入src
			Paint paint = new Paint();
			// 加入图片
			if (watermark != null) {
				ww = watermark.getWidth();
				wh = watermark.getHeight();
				paint.setAlpha(50);
				// 横竖�?

				if (h > w) {
					// 竖屏
					cv.drawBitmap(watermark, 10, (h - wh) / 7*6, paint);// 在src的右下角画入水印

				} else {
					// 横屏
					cv.drawBitmap(watermark, 10, (h - wh) / 2, paint);// 在src的左下角画入水印
				}
			}
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			 textPaint.setColor(Color.WHITE);
			textPaint.setTypeface(font);
			// textPaint.setTextSize(1200.0f);
			textPaint.setTextSize(60);
			textPaint.setAlpha(50);

			// 加入文字
			
			if (imageType != null) {
				cv.drawText(imageType, 10, h - 200, textPaint);
		}
			if (title != null) {
					cv.drawText(title, 10, h - 140, textPaint);
			}
			if (title2 != null) {
					cv.drawText(title2, 10, h - 80, textPaint);
				}
			
			if (time != null) {
					cv.drawText(time, 10, h - 20, textPaint);
			}
			
			
			cv.save();// 保存
			cv.restore();// 存储
			return newb;
		}
		
		
		public static Bitmap getbitMap(String srcPath){
			
			BitmapFactory.Options bfOptions=new BitmapFactory.Options(); 

            bfOptions.inDither=false;                     

            bfOptions.inPurgeable=true;               

            bfOptions.inTempStorage=new byte[12 * 1024];  

           // bfOptions.inJustDecodeBounds = true; 

            File file = new File(srcPath); 

            FileInputStream fs=null; 

            try { 

               fs = new FileInputStream(file); 

           } catch (FileNotFoundException e) { 

               e.printStackTrace(); 

           } 

            Bitmap bmp = null; 

            if(fs != null) 

               try { 

                   bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions); 

               } catch (IOException e) { 

                   e.printStackTrace(); 

               }finally{  

                   if(fs!=null) { 

                       try { 

                           fs.close(); 

                       } catch (IOException e) { 

                           e.printStackTrace(); 

                       } 

                   } 

               } 
            return  compressImage(bmp);
		}
		
		/**
	     * 从网络中获取图片，以流的形式返回
	     * @return
	     */
	    public static InputStream getImageViewInputStream(String url1) throws IOException {
	        InputStream inputStream = null;
	        URL url = new URL(url1);                    //服务器地�?
	        if (url != null) {
	            //打开连接
	            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
	            httpURLConnection.setConnectTimeout(3000);//设置网络连接超时的时间为3�?
	            httpURLConnection.setRequestMethod("GET");        //设置请求方法为GET
	            httpURLConnection.setDoInput(true);                //打开输入�?
	            int responseCode = httpURLConnection.getResponseCode();    // 获取服务器响应�??
	            if (responseCode == HttpURLConnection.HTTP_OK) {        //正常连接
	                inputStream = httpURLConnection.getInputStream();        //获取输入�?
	            }
	        }
	        return inputStream;
	    }

}
