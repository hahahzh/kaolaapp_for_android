package com.winwinap.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DatabaseProvider extends ContentProvider{

	private SQLiteDatabase m_sqlDB;
	private DatabaseHelper m_databaseHelper;
	private static final UriMatcher sUriMatcher;
	private static final int decorateTips = 1;
	private static final int decorateTips_ID = 2;
	
	static{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(DatabaseHelper.CONTENT_AUTHORITY, "decorateTips", decorateTips);
		sUriMatcher.addURI(DatabaseHelper.CONTENT_AUTHORITY, "decorateTips/#", decorateTips_ID);	
	}
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = m_databaseHelper.getWritableDatabase();
		int count = 0;
		switch(sUriMatcher.match(uri))
		{
		case decorateTips:
			count = db.delete(DatabaseHelper.TABLE_NAME_DECORATE_TIPS, where, whereArgs);
			break;
		case decorateTips_ID:
			String cellId = uri.getPathSegments().get(1);
			count = db.delete(DatabaseHelper.TABLE_NAME_DECORATE_TIPS,DatabaseHelper.FIELD_ID + "=" + cellId + (!TextUtils.isEmpty(where)?" AND (" + where + ")" : ""), whereArgs);
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO 自动生成的方法存根
		m_sqlDB = m_databaseHelper.getWritableDatabase();
		long rowId = 0;
		switch(sUriMatcher.match(uri))
		{
		case decorateTips:
		case decorateTips_ID:
			rowId = m_sqlDB.insert(DatabaseHelper.TABLE_NAME_DECORATE_TIPS,null,values);
			break;
		}
		if(rowId > 0)
		{
			Uri rowUri = ContentUris.appendId(uri.buildUpon(),rowId).build();
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
		//return null;
	}

	@Override
	public boolean onCreate() {
		// TODO 自动生成的方法存根
		m_databaseHelper = new DatabaseHelper(getContext());
		return (m_databaseHelper == null)? false:true;
		//return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO 自动生成的方法存根
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = m_databaseHelper.getReadableDatabase();
		switch(sUriMatcher.match(uri))
		{
		case decorateTips:
		case decorateTips_ID:
			qb.setTables(DatabaseHelper.TABLE_NAME_DECORATE_TIPS);
			break;
		}Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
		//return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = m_databaseHelper.getWritableDatabase();
		int count = 0;
		switch(sUriMatcher.match(uri))
		{
		case decorateTips:
			count = db.update(DatabaseHelper.TABLE_NAME_DECORATE_TIPS,values, where, whereArgs);
			break;
		case decorateTips_ID:
			String id = uri.getPathSegments().get(1);
			count = db.update(DatabaseHelper.TABLE_NAME_DECORATE_TIPS,values, DatabaseHelper.FIELD_ID + "=" + id + (!TextUtils.isEmpty(where)?" AND (" + where + ")" : ""), whereArgs);
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
		//return 0;
	}

}
