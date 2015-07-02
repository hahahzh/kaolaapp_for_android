package com.winwinap.database;

import android.content.Context;
import android.database.Cursor;

public class DatabaseManager {
	Context m_context;
	DatabaseManager m_databaseManager;
	
	private DatabaseManager(Context context){
		m_context = context;
	}
	
	public DatabaseManager getInstance(Context context){
		if(m_databaseManager == null){
			m_databaseManager = new DatabaseManager(context);
		}
		return m_databaseManager;
	}
	
	public boolean insertDecoreateTips(){
		boolean success = false;
		return success;
	}
	
	public boolean requestDecorateTips(){
		Cursor cur;
		String where = null;;	
    	
    	//where = DatabaseHelper.FIELD_ + "=" +  + " and " + .FIELD_ + "=" + ;
    	cur = m_context.getContentResolver().query(DatabaseHelper.CONTENT_URI_DECORATE_TIPS,null,where,null,null);
    	if(cur.moveToFirst())
    	{
    		cur.close();
        	return true;
    	}
    	
    	cur.close();
		return false;
	}
}
