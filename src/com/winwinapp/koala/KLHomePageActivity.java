package com.winwinapp.koala;

import com.winwinapp.my.MyActivity;
import com.winwinapp.selectcity.SelectCityActivity;
import com.winwinapp.util.ActionBarView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.login.SettingPageActivity;
import com.winwinapp.my.MyProjectActivity;

public class KLHomePageActivity extends FragmentActivity {

	private String mTabContent[];
	private FragmentTabHost mTabHost;
	@SuppressWarnings("rawtypes")
	private Class mFragmentArray[] = {fragment_homepage.class, fragment_bid.class, fragment_project.class, MyActivity.class};
	private int mImageResource[] = {R.drawable.item_homepage,R.drawable.item_bid,R.drawable.item_project,R.drawable.item_my};
	
	private ActionBarView mActionBar;
	private String mCurrentCity = "�Ϻ�";
	private KoalaApplication mApplication;
	private int mSwitchPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme); 
		initActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tabhost_home);
		getSwitchPageFromIntent(this.getIntent());
		mApplication = (KoalaApplication) this.getApplication();
		mApplication.init();
		initView();
	}

	public void onStart(){
		super.onStart();
	}
	
	public void getSwitchPageFromIntent(Intent intent){
		mSwitchPage = intent.getIntExtra("page", 0 );
		if(mTabHost != null){
			if( (mSwitchPage >= 0) && (mSwitchPage < mTabHost.getTabWidget().getTabCount())){
				mTabHost.setCurrentTab(mSwitchPage);
			}
		}
	}
	
	private void initActionBar(){
		ActionBar actionBar = this.getActionBar();
		if(actionBar != null){
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			
			mActionBar = new ActionBarView(this);
			setActionBarMain();
			
			actionBar.setCustomView(mActionBar,new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			actionBar.show();
			
		}
	}
	
	public void setActionBarMain(){
		mCurrentCity = mApplication.mLocationCity;
		TextView textView = new TextView(this);
		Drawable drawable = this.getResources().getDrawable(R.drawable.location);
		textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		textView.setText(mCurrentCity);
		textView.setTextColor(this.getResources().getColor(R.color.orange));
		mActionBar.setLeftView(null);
		mActionBar.setLeftView(textView);
		
		mActionBar.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(KLHomePageActivity.this,SelectCityActivity.class);
				startActivity(intent);
			}
			
		});
		
		mActionBar.setTitle("����С��");
		
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.message);
		mActionBar.setRightView(null);
		mActionBar.setRightView(imageView);
		
		mActionBar.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				if(mApplication.isUserLogin()){
					Intent intent = new Intent(KLHomePageActivity.this,MessageListActivity.class);
					intent.putExtra("type", 0);
					startActivity(intent);
				}else{
					Intent intent = new Intent(KLHomePageActivity.this,LoginPageActivity.class);
					startActivity(intent);
				}
			}
			
		});
	}
	
	public void setActionBarBids(){
		//ImageView imageView = new ImageView(this);
		//imageView.setImageResource(R.drawable.back);
		//mActionBar.setLeftView(imageView);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER || 
				KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR ||
				KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
			mActionBar.setTitle("�ҵľ���");
		}else{
			mActionBar.setTitle("�ҵ��б�");
		}
		mActionBar.setLeftView(null);
		mActionBar.setRightView(null);
	}
	
	public void setActionBarProjects(){
		mActionBar.setTitle("�ҵ���Ŀ");
		mActionBar.setLeftView(null);
		mActionBar.setRightView(null);
	}
	
	public void setActionBarMy(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		mActionBar.setLeftView(null);
		mActionBar.setLeftView(imageView);
		
		mActionBar.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				mTabHost.setCurrentTab(0);
			}
			
		});
		
		mActionBar.setTitle("�ҵ�");
		
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.engineering);
		mActionBar.setRightView(null);
		mActionBar.setRightView(imageView);
		
		mActionBar.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
			
				Intent intent = new Intent(KLHomePageActivity.this,SettingPageActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.klhome_page, menu);
		return false;
	}
	
	private void initView(){
		//LayoutInflater inflater = LayoutInflater.from(this);
		
		mTabContent = this.getResources().getStringArray(R.array.tabhost_tab_string);
		
		mTabHost = (FragmentTabHost)findViewById(R.id.homepage_tabhost);
		mTabHost.setup(this,this.getSupportFragmentManager(),R.id.common_fragment);
		mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
		
		int count = Math.min(mFragmentArray.length, mTabContent.length);
		count = Math.min(count, mImageResource.length);
		for(int i = 0; i< count; i++){
			TabSpec tabSpec = mTabHost.newTabSpec(mTabContent[i]).setIndicator(getTabItemView(i));
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(0x888888);
		}
		mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				if(!KoalaApplication.isUserLogin()){
					Intent intent = new Intent(KLHomePageActivity.this, LoginPageActivity.class);
					startActivity(intent);
				}else{
					mSwitchPage = 3;
					mTabHost.setCurrentTab(mSwitchPage);
				}
			}
			
		});
		
		mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				if(!KoalaApplication.isUserLogin()){
					Intent intent = new Intent(KLHomePageActivity.this, LoginPageActivity.class);
					startActivity(intent);
				}else{
					mSwitchPage = 1;
					mTabHost.setCurrentTab(mSwitchPage);
				}
			}
			
		});
		
		mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				if(!KoalaApplication.isUserLogin()){
					Intent intent = new Intent(KLHomePageActivity.this, LoginPageActivity.class);
					startActivity(intent);
				}else{
					mSwitchPage = 2;
					mTabHost.setCurrentTab(mSwitchPage);
				}
			}
			
		});
		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String arg0) {
				// TODO �Զ����ɵķ������
				if(arg0 == null)
					return;
				if(arg0.equals(mTabContent[3])){
					setActionBarMy();
				}else if(arg0.equals(mTabContent[0])){
					setActionBarMain();
				}else if(arg0.equals(mTabContent[1])){
					setActionBarBids();
					/*Bundle bundle = new Bundle();
					Intent intent = new Intent(KLHomePageActivity.this, BidsListActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);*/
					
				}else if(arg0.equals(mTabContent[2])){
					setActionBarProjects();
					/*Bundle bundle = new Bundle();
					Intent intent = new Intent(KLHomePageActivity.this, MyProjectActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);*/
				}
			}
			
		});
		
		if( (mSwitchPage >= 0) && (mSwitchPage < count)){
			mTabHost.setCurrentTab(mSwitchPage);
		}
	}
	
	private View getTabItemView(int index){
		View view = LayoutInflater.from(this).inflate(R.layout.layout_grid_item_common, null);
		
		ImageView imageView = (ImageView)view.findViewById(R.id.item_image);
		TextView textView = (TextView)view.findViewById(R.id.item_text);
		
		imageView.setImageResource(mImageResource[index]);
		if(index == 1){
			if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER || 
					KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR ||
					KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
				textView.setText(mTabContent[index]);
			}else{
				textView.setText("�б�");
			}
		}else{
			textView.setText(mTabContent[index]);
		}	
		
		
		return view;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO �Զ����ɵķ������
		super.onNewIntent(intent);
		this.setIntent(intent);
		View view = mTabHost.getTabWidget().getChildAt(1);
		TextView textView = (TextView) view.findViewById(R.id.item_text);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER || 
				KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR ||
				KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
			textView.setText(mTabContent[1]);
		}else{
			textView.setText("�б�");
		}
		getSwitchPageFromIntent(intent);
	}

	@Override
	protected void onResume() {
		// TODO �Զ����ɵķ������
		super.onResume();
		if(mTabHost.getCurrentTab() == 0){
			setActionBarMain();
		}
	}

}
