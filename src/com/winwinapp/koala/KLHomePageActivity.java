package com.winwinapp.koala;

import com.winwinapp.my.MyActivity;
import com.winwinapp.selectcity.SelectCityActivity;
import com.winwinapp.util.ActionBarView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.winwinapp.network.HTTPGet;

public class KLHomePageActivity extends FragmentActivity implements LocationListener{

	private String mTabContent[];
	private FragmentTabHost mTabHost;
	@SuppressWarnings("rawtypes")
	private Class mFragmentArray[] = {fragment_homepage.class, fragment_bid.class, fragment_project.class, MyActivity.class};
	private int mImageResource[] = {R.drawable.item_homepage,R.drawable.item_bid,R.drawable.item_project,R.drawable.item_my};
	
	private ActionBarView mActionBar;
	private String mCurrentCity = "上海";
	private KoalaApplication mApplication;
	private int mSwitchPage = 0;
	private LocationManager mLocationManager;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				if(mTabHost.getCurrentTab() == 0){
					setActionBarMain();
				}
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tabhost_home);
		getSwitchPageFromIntent(this.getIntent());
		mApplication = (KoalaApplication) this.getApplication();
		mApplication.init();
		initView();
		initLocationService();
		initActionBar();
	}

	public void initLocationService(){
		mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		Location locGps = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location locNlp = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		if(locGps != null){
			new GetCurrentCityThread(locGps.getLatitude(),locGps.getLongitude()).start();
		}else if(locNlp != null){
			new GetCurrentCityThread(locNlp.getLatitude(),locNlp.getLongitude()).start();
		}else{
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
		}
	}
	
	public class GetCurrentCityThread extends Thread{
		double mLat = 0.0f;
		double mLon = 0.0f;
		public GetCurrentCityThread(double lat,double lon){
			mLat = lat;
			mLon = lon;
		}
		public void run(){
			String city = HTTPGet.RequestCurrentCity(31.0f, 120.1f);
			if(city != null){
				mApplication.saveLocationCity(city);
				mHandler.sendEmptyMessage(1);
			}
		}
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
				// TODO 自动生成的方法存根
				Intent intent = new Intent(KLHomePageActivity.this,SelectCityActivity.class);
				startActivity(intent);
			}
			
		});
		
		mActionBar.setTitle("考拉小匠");
		
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.message);
		mActionBar.setRightView(null);
		mActionBar.setRightView(imageView);
		
		mActionBar.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				if(mApplication.isUserLogin()){
					Intent intent = new Intent(KLHomePageActivity.this,MessageListActivity.class);
					intent.putExtra("type", 1);
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
			mActionBar.setTitle("我的竞标");
		}else{
			mActionBar.setTitle("我的招标");
		}
		mActionBar.setLeftView(null);
		mActionBar.setRightView(null);
	}
	
	public void setActionBarProjects(){
		mActionBar.setTitle("我的项目");
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
				// TODO 自动生成的方法存根
				mTabHost.setCurrentTab(0);
			}
			
		});
		
		mActionBar.setTitle("我的");
		
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.engineering);
		mActionBar.setRightView(null);
		mActionBar.setRightView(imageView);
		
		mActionBar.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
			
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
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
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
				textView.setText("招标");
			}
		}else{
			textView.setText(mTabContent[index]);
		}	
		
		
		return view;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO 自动生成的方法存根
		super.onNewIntent(intent);
		this.setIntent(intent);
		View view = mTabHost.getTabWidget().getChildAt(1);
		TextView textView = (TextView) view.findViewById(R.id.item_text);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER || 
				KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR ||
				KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
			textView.setText(mTabContent[1]);
		}else{
			textView.setText("招标");
		}
		getSwitchPageFromIntent(intent);
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		if(mTabHost.getCurrentTab() == 0){
			setActionBarMain();
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO 自动生成的方法存根
		if(arg0 != null){
			new GetCurrentCityThread(arg0.getLatitude(),arg0.getLongitude()).start();
			mLocationManager.removeUpdates(this);
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO 自动生成的方法存根
		
	}

}
