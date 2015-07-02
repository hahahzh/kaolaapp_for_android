package com.winwinap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getName();
	//name of database
	private static final String DATABASE_NAME = "kaolaxj.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME_DECORATE_TIPS = "decorateTips";
	
	public static final String FIELD_ID = BaseColumns._ID;
	public static final String DOC_ID = "doc_id";
	public static final String CAT_ID = "cat_id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String AUTHOR = "author";
	public static final String KEYWORDS = "keywords";
	public static final String IS_OPEN = "is_open";
	public static final String ADD_TIME = "add_time";
	public static final String SCAN_NUM = "scan_num";
	
	public static final Uri CONTENT_URI_DECORATE_TIPS = Uri.parse("content://com.winwinapp.database.DatabaseProvider/decorateTips");
	public static final String CONTENT_AUTHORITY = "com.winwinapp.database.DatabaseProvider";
	
	public DatabaseHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO 自动生成的方法存根
		try
		{
			String sql = "Create table " + TABLE_NAME_DECORATE_TIPS + "(" + FIELD_ID + " integer primary key autoincrement," + 
					DOC_ID + " text," + CAT_ID + " text not null," + TITLE + " text not null," + 
					CONTENT + " text," + AUTHOR + " text not null," + KEYWORDS + " text not null," + 
					IS_OPEN + " text," + ADD_TIME + " text not null," + SCAN_NUM + " float not null)";
			arg0.execSQL(sql);
			
			/*
			sql = "Create table " + TABLE_NAME_CELL + "(" + FIELD_ID + " integer primary key autoincrement," + 
					FIELD_TYPE + " short not null," + FIELD_MCC + " short not null," + FIELD_MNC + " short not null," + 
					FIELD_LAC + " short not null," + FIELD_CI + " short not null," + FIELD_RSSI + " short not null," + 
					FIELD_GPSLAT + " float not null," + FIELD_GPSLON + " float not null," + FIELD_GPSUNCERT + " short not null," + 
					FIELD_SOURCE + " short not null," + FIELD_TIME + " long not null," + FIELD_FLAG + " short not null);";
			arg0.execSQL(sql);*/
		}catch(Exception e)
		{
			Log.e(TAG, "create database table error\n" + e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO 自动生成的方法存根
		arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DECORATE_TIPS);
		//arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WIFI);
		
		onCreate(arg0);
	}

}
