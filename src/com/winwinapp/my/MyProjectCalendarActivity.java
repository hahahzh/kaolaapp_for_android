package com.winwinapp.my;

import android.app.ActionBar;
import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.winwinapp.koala.R;
import com.winwinapp.util.ActionBarView;

public class MyProjectCalendarActivity extends Activity  implements OnTabChangeListener,OnClickListener{
	private String mTabTitle[] = {"标准工作流","庭院工作流","其他工作流"};
	private TabHost mTabHost;
	private ActionBarView mActionBar;
	LinearLayout mSwitchLL;
	TextView mTipsBtn;
	TextView mAppendBtn;
	TextView mTipsContent;
	LinearLayout mAppendContentLL;
	boolean mCurrentState = true;
	Spinner spinner_calendar_first,spinner_calendar_second,spinner_calendar_third;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_tip_main);
		initActionBar();
		initView();
		initTabHost();
		
	}

	public void initView(){
		mSwitchLL = (LinearLayout)findViewById(R.id.project_tips_append_ll);
		mSwitchLL.setBackgroundResource(R.drawable.icon_button_reverse_bg);
		
		mTipsBtn = (TextView)findViewById(R.id.project_calendar_tips);
		mAppendBtn = (TextView)findViewById(R.id.project_calendar_append);
		mTipsContent = (TextView)findViewById(R.id.project_calendar_tips_txt);
		mAppendContentLL = (LinearLayout)findViewById(R.id.project_calendar_append_ll);
		
		spinner_calendar_first = (Spinner)findViewById(R.id.spinner_calendar_first);
		spinner_calendar_second = (Spinner)findViewById(R.id.spinner_calendar_second);
		spinner_calendar_third = (Spinner)findViewById(R.id.spinner_calendar_third);
		
//		spinner_calendar_first.setOnClickListener(this);
//		spinner_calendar_second.setOnClickListener(this);
//		spinner_calendar_third.setOnClickListener(this);
		
		mTipsBtn.setOnClickListener(this);
		mAppendBtn.setOnClickListener(this);
		mCurrentState = true;
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch(view.getId()){
		case R.id.project_calendar_tips:
			if(!mCurrentState){
				mCurrentState = true;
				mSwitchLL.setBackgroundResource(R.drawable.icon_button_reverse_bg);
				mTipsContent.setVisibility(View.VISIBLE);
				mAppendContentLL.setVisibility(View.GONE);
			}
			break;
		case R.id.project_calendar_append:
			if(mCurrentState){
				mCurrentState = false;
				mSwitchLL.setBackgroundResource(R.drawable.icon_button_bg);
				mTipsContent.setVisibility(View.GONE);
				mAppendContentLL.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
	
//	public void onClick(Spinner spinner) {
//		// TODO 自动生成的方法存根
//		switch(spinner.getId()){
//		case R.id.spinner_calendar_first:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//			break;
//		case R.id.spinner_calendar_second:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//			break;
//		case R.id.spinner_calendar_third:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner_r);
//			break;
//		}
//	}
	
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
			
			mActionBar.setTitle("项目日历");
			
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
		if(arg0 == null)
			return;
		if(arg0.equals(mTabTitle[0])){
			spinner_calendar_first.setBackgroundResource(R.drawable.spinner_r);
			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
		}else if(arg0.equals(mTabTitle[1])){
			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
			spinner_calendar_second.setBackgroundResource(R.drawable.spinner_r);
			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
		}else if(arg0.equals(mTabTitle[2])){
			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
			spinner_calendar_third.setBackgroundResource(R.drawable.spinner_r);
		}
		updateTab(mTabHost);
		
	}

	private void updateTab(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); 
            tv.setTextSize(15);
            if (tabHost.getCurrentTab() == i) {//选中    
                tv.setTextColor(this.getResources().getColorStateList(R.color.green)); 
            } else {//不选中    
                tv.setTextColor(this.getResources().getColorStateList(R.color.gray)); 
            } 
        } 
    } 
	
}

