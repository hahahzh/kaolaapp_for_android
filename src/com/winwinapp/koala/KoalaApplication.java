package com.winwinapp.koala;

import com.winwinapp.network.NetworkData;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class KoalaApplication extends Application {

	private String PREF_NAME = "koala";
	private String USER_NAME = "username";
	private String SESSION_NAME = "session_name";
	private String USER_ID = "id";
	public static NetworkData.LoginBack loginData = NetworkData.getInstance().getNewLoginBack();
	public static int mUserType = -1;
	private boolean mIsLogIn = false;
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
	
}
