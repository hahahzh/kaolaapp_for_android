package com.winwinapp.designer;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class DesignerProjectItem {

	String mAreaName;
	String mArea;
	String mSkills;
	String mService;
	ArrayList<comment> mComments = new ArrayList<comment>();
	comment mComment = new comment();
	Drawable mAvatar;
	public String date;
	
	
	public class comment{
		Drawable mAvatar;
		String mCommenterName;
		String mComments;
	}
}
