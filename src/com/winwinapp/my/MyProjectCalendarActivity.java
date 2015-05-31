package com.winwinapp.my;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import com.winwinapp.koala.R;
import com.winwinapp.util.ActionBarView;

public class MyProjectCalendarActivity extends Activity  implements OnTabChangeListener{

	private String mTabTitle[] = {"标准工作流","庭院工作流","其他工作流"};
	private TabHost mTabHost;
	private ActionBarView mActionBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_decorate_tip_main);
		initActionBar();
		initTabHost();
		
	}

	public void initActionBar(){
		ActionBar actionBar = this.getActionBar();
		if(actionBar != null){
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			
			mActionBar = new ActionBarView(this);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.back);
			mActionBar.setLeftView(imageView);
			
			mActionBar.setOnLeftClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					finish();
				}
				
			});
			
			mActionBar.setTitle("装修宝典");
			
			
			actionBar.setCustomView(mActionBar,new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
	}
	
	public void initTabHost(){
		mTabHost = (TabHost) this.findViewById(R.id.tips_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[0]).setIndicator(mTabTitle[0]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[1]).setIndicator(mTabTitle[1]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[2]).setIndicator(mTabTitle[2]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.setOnTabChangedListener(this);
	}
	
	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO 自动生成的方法存根
		
	}
}

