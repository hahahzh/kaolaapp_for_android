package com.winwinapp.koala;

import android.graphics.Bitmap;

public class MessageItem {

	String mName;
	String mIdentify;
	String mLastUpdateTime;
	int mMessageNum;
	int mAvataResId;
	String mSnippet;
	String type;//0,˽�ţ�1��������Ϣ��2��ϵͳ��Ϣ
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
