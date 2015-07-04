package com.winwinapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
}
