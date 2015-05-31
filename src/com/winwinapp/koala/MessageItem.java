package com.winwinapp.koala;

public class MessageItem {

	String mName;
	String mIdentify;
	String mLastUpdateTime;
	int mMessageNum;
	int mAvataResId;
	String mSnippet;
	
	public MessageItem(String name,String identify,String time,int num, int avatarId,String snippet){
		mName = name;
		mIdentify = identify;
		mLastUpdateTime = time;
		mMessageNum = num;
		mAvataResId = avatarId;
		mSnippet = snippet;
	}
}
