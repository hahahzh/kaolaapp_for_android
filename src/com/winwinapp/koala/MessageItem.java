package com.winwinapp.koala;

import android.graphics.Bitmap;

public class MessageItem {

	String mName;
	String mIdentify;
	String mLastUpdateTime;
	int mMessageNum;
	int mAvataResId;
	String mSnippet;
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
