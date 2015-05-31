package com.winwinapp.koala;

import com.winwinapp.util.ActionBarView;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

public class ActionBarActivity extends Activity {

	ActionBarView mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = this.getActionBar();
		if(actionBar != null){
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			mActionBar = new ActionBarView(this);
		}
		
		actionBar.setCustomView(mActionBar,new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}

	public void setLeftView(View view){
		mActionBar.setLeftView(view);
	}
	
	public void setRightView(View view){
		mActionBar.setRightView(view);
	}
	
	public void setTitle(String title){
		mActionBar.setTitle(title);
	}
	
	public void setOnLeftClickListener(OnClickListener l){
		mActionBar.setOnLeftClickListener(l);
	}
	
	public void setOnRightClickListener(OnClickListener l){
		mActionBar.setOnRightClickListener(l);
	}
}
