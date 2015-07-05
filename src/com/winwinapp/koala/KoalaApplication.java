package com.winwinapp.koala;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.network.NetworkData;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class KoalaApplication extends Application {

	private static String PREF_NAME = "koala";
	private String USER_NAME = "username";
	private String SESSION_NAME = "session_name";
	private static String LOCATION_CITY = "location_city";
	private String USER_ID = "id";
	public static NetworkData.LoginBack loginData = NetworkData.getInstance().getNewLoginBack();
	public static int mUserType = -1;
	private static boolean mIsLogIn = false;
	public static String mLocationCity = "上海";
	public KoalaApplication() {
		super();
		// TODO 自动生成的构造函数存根
		//init();
	}

	public void init(){
		SharedPreferences pre = this.getSharedPreferences(PREF_NAME, Context.MODE_WORLD_READABLE);
		loginData.sessid = pre.getString(SESSION_NAME, null);
		loginData.username = pre.getString(USER_NAME, null);
		loginData.id = pre.getString(USER_ID, null);
		mLocationCity = pre.getString(LOCATION_CITY, "上海");
		
		if( (!TextUtils.isEmpty(loginData.sessid)) && (!TextUtils.isEmpty(loginData.username))){
			mIsLogIn = true;
			setUserType();
		}
	}
	
	public void setUserType(){
		if("10".equals(loginData.id)){
			mUserType = fragment_homepage.TYPE_OWER;
		}else if("31".equals(loginData.id)){
			mUserType = fragment_homepage.TYPE_DESIGNER;
		}else if("32".equals(loginData.id)){
			mUserType = fragment_homepage.TYPE_SUPRIOR;
		}else if("33".equals(loginData.id)){
			mUserType = fragment_homepage.TYPE_LABOR;
		}else{
			mUserType = -1;
		}
	}
	
	public int getUserType(){
		return mUserType;
	}
	
	public String getSession(){
		return loginData.sessid;
	}
	
	public String getUsername(){
		return loginData.username;
	}
	
	public static boolean isUserLogin(){
		return mIsLogIn;
	}
	
	public void saveLocationCity(String cityName){
		SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_WORLD_WRITEABLE).edit();
		editor.putString(LOCATION_CITY,cityName);
		editor.commit();
		mLocationCity = cityName;
	}
	
	public void saveSession(NetworkData.LoginBack login){
		SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_WORLD_WRITEABLE).edit();
		editor.putString(SESSION_NAME,login.sessid);
		editor.putString(USER_NAME, login.username);
		editor.putString(USER_ID, login.id);
		editor.commit();
		loginData.sessid = login.sessid;
		loginData.username = login.username;
		loginData.id = login.id;
		mIsLogIn = true;
		setUserType();
	}
	
	public void clearLoginInformation(){
		SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_WORLD_WRITEABLE).edit();
		editor.remove(SESSION_NAME);
		editor.remove(USER_NAME);
		editor.remove(USER_ID);
		editor.commit();
		loginData.sessid = null;
		loginData.username = null;
		loginData.id = null;
		mIsLogIn = false;
		mUserType = -1;
	}
	
	public boolean loadSavedDecorateData(PreEvaluateActivity.DecorateSaveData data){
		SharedPreferences pre = this.getSharedPreferences(PREF_NAME, Context.MODE_WORLD_READABLE);
		data.area = pre.getInt("decorate_area", -100);
		if(data.area == -100){
			return false;
		}
		data.hall = pre.getInt("decorate_hall", -100);
		data.room = pre.getInt("decorate_room", -100);
		data.kitchen = pre.getInt("decorate_kitchen", -100);
		data.toilet = pre.getInt("decorate_toilet", -100);
		data.balcony = pre.getInt("decorate_balcony", -100);
		data.RichOrPoor = pre.getInt("decorate_RichOrPoor", -1);
		
		return true;
	}
	
	public boolean SaveDecorateData(PreEvaluateActivity.DecorateSaveData data){
		SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_WORLD_WRITEABLE).edit();
		
		editor.putInt("decorate_area", data.area);
		editor.putInt("decorate_hall", data.hall);
		editor.putInt("decorate_room", data.room);
		editor.putInt("decorate_kitchen", data.kitchen);
		editor.putInt("decorate_toilet", data.toilet);
		editor.putInt("decorate_balcony", data.balcony);
		editor.putInt("decorate_RichOrPoor", data.RichOrPoor);
		editor.commit();
		
		return true;
	}
	
}
