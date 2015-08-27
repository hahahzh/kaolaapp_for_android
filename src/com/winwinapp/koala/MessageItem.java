package com.winwinapp.koala;

import android.graphics.Bitmap;

public class MessageItem {

	String mName;
	String mIdentify;
	String mLastUpdateTime;
	int mMessageNum;
	int mAvataResId;
	String mSnippet;
	String type;//0,私信；1，公共消息；2，系统消息
	public Bitmap avatar;
	
	public MessageItem(String name,String identify,String time,int num, int avatarId,String snippet){
		mName = name;
		mIdentify = identify;
		mLastUpdateTime = time;
		mMessageNum = num;
		mAvataResId = avatarId;
		mSnippet = snippet;
	}
}
