package com.winwinapp.my;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.winwinapp.koala.R;
import com.winwinapp.util.ActionBarView;

public class MyProjectCalendarActivity extends Activity  implements OnTabChangeListener,OnClickListener{

	private String mTabTitle[] = {"��׼������","ͥԺ������","����������"};
	private TabHost mTabHost;
	private ActionBarView mActionBar;
	LinearLayout mSwitchLL;
	TextView mTipsBtn;
	TextView mAppendBtn;
	TextView mTipsContent;
	LinearLayout mAppendContentLL;
	boolean mCurrentState = true;
	
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
		
		mTipsBtn.setOnClickListener(this);
		mAppendBtn.setOnClickListener(this);
		mCurrentState = true;
	}
	
	@Override
	public void onClick(View view) {
		// TODO �Զ����ɵķ������
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
					// TODO �Զ����ɵķ������
					finish();
				}
				
			});
			
			mActionBar.setTitle("��Ŀ����");
			
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
		// TODO �Զ����ɵķ������
		
	}

}

