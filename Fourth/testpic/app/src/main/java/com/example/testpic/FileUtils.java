package com.example.testpic;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

public class FileUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/DCIM/xxx/";
	
	public static String SDPATH1 = Environment.getExternalStorageDirectory()
			+ "/xxx/txt/";
	
	
	/**
	 * 鍒濆鍖栧浘鐗囦綅缃?
	 * @param activity 
	 */
	public static void initSdPath(Activity activity){
		try{
			File file = new File(SDPATH);
			if(!file.exists()){
				file.mkdirs();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 淇濆瓨鍥剧墖
	 * @param bm
	 * @param filePath
	 */
	public static void saveImg(Bitmap bm, String filePath) {
		try {
			
			File f = new File(filePath); 
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 寰楀埌鏈湴鎴栬?呯綉缁滀笂鐨刡itmap url - 缃戠粶鎴栬?呮湰鍦板浘鐗囩殑缁濆璺緞,姣斿:
	 * 
	 * A.缃戠粶璺緞: url="http://blog.foreverlove.us/girl2.png" ;
	 * 
	 * B.鏈湴璺緞:url="file://mnt/sdcard/photo/image.png";
	 * 
	 * C.鏀寔鐨勫浘鐗囨牸寮? ,png, jpg,bmp,gif绛夌瓑
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap GetLocalOrNetBitmap(String url)
	{
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try
		{
			in = new BufferedInputStream(new URL(url).openStream(), 1024);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 1024);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(); 
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}
	
	
	/**
	 * 鑾峰彇鏈湴鍥剧墖鐨勭缉鐣ュ浘
	 * @param imgPath 鍥剧墖璺緞
	 * @return
	 */
	public static Bitmap getThumbnailImage(Activity activity, String imgPath){
		try{
			//棣栧厛鍙栧緱灞忓箷瀵硅薄   
			Display display = activity.getWindowManager().getDefaultDisplay();   
			//鑾峰彇灞忓箷鐨勫鍜岄珮   
			DisplayMetrics metrics = new DisplayMetrics();
			
			display.getMetrics(metrics);
			int dw = metrics.widthPixels;
			int dh = metrics.heightPixels;
			
			File file = new File(imgPath);
			if(!file.exists()){
				file.createNewFile();
			}
			
			Uri imageUri = Uri.fromFile(file);
			
			/**  
			* 涓轰簡璁＄畻缂╂斁鐨勬瘮渚嬶紝鎴戜滑闇?瑕佽幏鍙栨暣涓浘鐗囩殑灏哄锛岃?屼笉鏄浘鐗?  
			* BitmapFactory.Options绫讳腑鏈変竴涓竷灏斿瀷鍙橀噺inJustDecodeBounds锛屽皢鍏惰缃负true  
			* 杩欐牱锛屾垜浠幏鍙栧埌鐨勫氨鏄浘鐗囩殑灏哄锛岃?屼笉鐢ㄥ姞杞藉浘鐗囦簡銆?  
			* 褰撴垜浠缃繖涓?肩殑鏃跺?欙紝鎴戜滑鎺ョ潃灏卞彲浠ヤ粠BitmapFactory.Options鐨刼utWidth鍜宱utHeight涓幏鍙栧埌鍊?  
			*/   
		BitmapFactory.Options op = new BitmapFactory.Options();   
		//op.inSampleSize = 8;   
		op.inJustDecodeBounds = true;   
		//Bitmap pic = BitmapFactory.decodeFile(imageFilePath, op);//璋冪敤杩欎釜鏂规硶浠ュ悗锛宱p涓殑outWidth鍜宱utHeight灏辨湁鍊间簡   
		//鐢变簬浣跨敤浜哅ediaStore瀛樺偍锛岃繖閲屾牴鎹甎RI鑾峰彇杈撳叆娴佺殑褰㈠紡   
		op.inPreferredConfig = Bitmap.Config.RGB_565;//榛樿涓篈RGB_8888

		Bitmap pic = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri), null, op);   
		op.inJustDecodeBounds = false;   
		//娉ㄦ剰杩欓噷锛屼竴瀹氳璁剧疆涓篺alse锛屽洜涓轰笂闈㈡垜浠皢鍏惰缃负true鏉ヨ幏鍙栧浘鐗囧昂瀵镐簡   
		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); //璁＄畻瀹藉害姣斾緥   
		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); //璁＄畻楂樺害姣斾緥   
		Log.v("Width Ratio:", wRatio + "");   
		Log.v("Height Ratio:", hRatio + "");   
		/**  
		* 鎺ヤ笅鏉ワ紝鎴戜滑灏遍渶瑕佸垽鏂槸鍚﹂渶瑕佺缉鏀句互鍙婂埌搴曞瀹借繕鏄珮杩涜缂╂斁銆?  
		* 濡傛灉楂樺拰瀹戒笉鏄叏閮借秴鍑轰簡灞忓箷锛岄偅涔堟棤闇?缂╂斁銆?  
		* 濡傛灉楂樺拰瀹介兘瓒呭嚭浜嗗睆骞曞ぇ灏忥紝鍒欏浣曢?夋嫨缂╂斁鍛€??  
		* 杩欓渶瑕佸垽鏂瓀Ratio鍜宧Ratio鐨勫ぇ灏?  
		* 澶х殑涓?涓皢琚缉鏀撅紝鍥犱负缂╂斁澶х殑鏃讹紝灏忕殑搴旇鑷姩杩涜鍚屾瘮鐜囩缉鏀俱??  
		* 缂╂斁浣跨敤鐨勮繕鏄痠nSampleSize鍙橀噺  
		*/   
		if (wRatio > 1 && hRatio > 1) {   
		if (wRatio > hRatio) {   
		op.inSampleSize = wRatio;   
		} else {   
		op.inSampleSize = hRatio;   
		}   
		}   
		pic = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri), null, op);   
		return pic;
		//imageView.setImageBitmap(pic);   
		} catch (Exception e) {   
		e.printStackTrace();   
		}   
		
		return null;

	}
	
//	public static long getFileLength(){
//		   File file = FileUtils.getSaveFile("itower", "error.log");
//	    if (f.exists() && f.isFile()){  
//	        logger.info(f.length());  
//	    }else{  
//	        logger.info("file doesn't exist or is not a file");  
//	    }  
//	}

}
