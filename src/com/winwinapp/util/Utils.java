package com.winwinapp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

public class Utils {
	public final String TAG = Utils.class.getName();
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            while( (halfHeight / inSampleSize) > reqHeight && (halfWidth/inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        Log.i("Utils","size="+options.inSampleSize+",w="+options.outWidth+",h="+options.outHeight);
        return BitmapFactory.decodeResource(res, resId,options);
    }
    
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        
        int reqHeight = options.outHeight;
        
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        Log.i("Utils","size="+options.inSampleSize+",w="+options.outWidth+",h="+options.outHeight);
        return BitmapFactory.decodeResource(res, resId,options);
    }
    
    public static boolean isStringEmpty(String str){
    	if( (str != null) && (str.length() > 0) ){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isEmail(String str){
    	boolean is = false;
    	String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        is = matcher.matches();
    	return is;
    }
    
    public static boolean isMobilePhone(String str){
    	boolean is = false;
    	String check = "^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        is = matcher.matches();
    	return is;
    }
    
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
    
    public static boolean bmpToFile(final Bitmap bmp, final String path){
    	FileOutputStream output;
    	boolean bl = true;
		try {
			output = new FileOutputStream(path);
			bmp.compress(CompressFormat.PNG, 100, output);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			bl = false;
		}catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			bl = false;
		}
    	
    	return bl;
    }
    
    public static String getPrivateFilePath(Context context){
    	File file = context.getFilesDir();
    	return file.getAbsolutePath();
    }
    
    public static float sp2px(Context context,float sp){
    	final float scale = context.getResources().getDisplayMetrics().density;
    	return sp*scale;
    }
    
    public static int dp2px(Context context,int dp){
    	final float scale = context.getResources().getDisplayMetrics().density;
    	return (int)(dp*scale+0.5f);
    }
    
    public static String utcToData(String utc){
    	long tm = 0;
    	try{
    		tm = Long.parseLong(utc);
    	}catch(Exception e){
    		return "1970-01-01";
    	}
    	SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	Date time = new Date(tm);
    	return foo.format(time);
    }
    
}
