package com.winwinapp.designer;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;

public class ContactDesignerActivity extends ActionBarActivity implements TabHost.OnTabChangeListener{

	TabHost mTabHost;
	ListView mListView;
	ArrayList<DesignerListItem> mArrayList = new ArrayList<DesignerListItem>();
	LayoutInflater mInflater;
	DesignerListAdapter mAdapter = new DesignerListAdapter();
	String mTitle = null;
	int type;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contact_designer);

		getTitleFromIntent(this.getIntent());
		initActionBar();
		initTabHost(this.getIntent());
		initListView(this.getIntent());
	}
	
	public void getTitleFromIntent(Intent intent){
		int type = intent.getIntExtra("type", fragment_homepage.TYPE_DESIGNER);
		switch(type){
		case fragment_homepage.TYPE_DESIGNER:
			mTitle = "联系设计师";
			break;
		case fragment_homepage.TYPE_LABOR:
			mTitle = "寻找好工长";
			break;
		case fragment_homepage.TYPE_SUPRIOR:
			mTitle = "监理来帮忙";
			break;
		}
		setTitle(mTitle);
	}
	
	public void initTabHost(Intent intent){
		mTabHost = (TabHost) this.findViewById(R.id.contact_designer_tabhost);
		mTabHost.setup();
		
		LayoutInflater lf = LayoutInflater.from(this);
		View view = lf.inflate(R.layout.layout_contact_tab_item, null);
		TextView text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("专业水平");
		mTabHost.addTab(mTabHost.newTabSpec("专业水平").setIndicator(view).setContent(R.id.contact_designer_list));
		view = lf.inflate(R.layout.layout_contact_tab_item, null);
		text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("服务态度");
		mTabHost.addTab(mTabHost.newTabSpec("服务态度").setIndicator(view).setContent(R.id.contact_designer_list));
		view = lf.inflate(R.layout.layout_contact_tab_item, null);
		text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("案例数");
		mTabHost.addTab(mTabHost.newTabSpec("案例数").setIndicator(view).setContent(R.id.contact_designer_list));
		
		type = intent.getIntExtra("type", fragment_homepage.TYPE_DESIGNER);
		switch(type){
		case fragment_homepage.TYPE_DESIGNER:
			view = lf.inflate(R.layout.layout_contact_tab_item, null);
			text = (TextView) view.findViewById(R.id.contact_tab_title);
			text.setText("收费标准");
			mTabHost.addTab(mTabHost.newTabSpec("收费标准").setIndicator(view).setContent(R.id.contact_designer_list));
			break;
		case fragment_homepage.TYPE_LABOR:
		case fragment_homepage.TYPE_SUPRIOR:
			view = lf.inflate(R.layout.layout_contact_tab_item, null);
			text = (TextView) view.findViewById(R.id.contact_tab_title);
			text.setText("从业年限");
			mTabHost.addTab(mTabHost.newTabSpec("从业年限").setIndicator(view).setContent(R.id.contact_designer_list));
			break;
		}
		
		mTabHost.setOnTabChangedListener(this);
		
		onTabChanged("全部");
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle(mTitle);
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}

	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}
	
	public void initListView(Intent intent){
		mInflater = LayoutInflater.from(this);
		DesignerListItem item = new DesignerListItem();
		mArrayList.add(item);
		item = new DesignerListItem();
		item.mIsHeart = true;
		mArrayList.add(item);
		mListView = (ListView) this.findViewById(R.id.contact_designer_list);
		mListView.setAdapter(mAdapter);
		type = intent.getIntExtra("type", fragment_homepage.TYPE_DESIGNER);
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(type){
				case fragment_homepage.TYPE_DESIGNER:
					// TODO 自动生成的方法存根
					Intent intent = new Intent(ContactDesignerActivity.this,DesignerActivity.class);
					startActivity(intent);
					break;
				case fragment_homepage.TYPE_LABOR:
					Intent intentLabor = new Intent(ContactDesignerActivity.this,LaborActivity.class);
					startActivity(intentLabor);
					break;
				case fragment_homepage.TYPE_SUPRIOR:
					Intent intentSuprior = new Intent(ContactDesignerActivity.this,SupriorActivity.class);
					startActivity(intentSuprior);
					break;
				}
			}
			
		});
	}
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO 自动生成的方法存根
		refreshTab();
		mListView = (ListView) mTabHost.getCurrentView().findViewById(R.id.contact_designer_list);
		mListView.setAdapter(mAdapter);
	}
	
	public void refreshTab(){
		int current = mTabHost.getCurrentTab();
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.contact_tab_title);
            //ImageView img = (ImageView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_img);
            
            if(i == current){
            	tv.setTextColor(getResources().getColor(R.color.green));//设置字体的颜色
            	//img.setBackgroundColor(getResources().getColor(R.color.green));
            	//mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.yellow));
            }else{
            	tv.setTextColor(Color.GRAY);//设置字体的颜色；
            	//img.setBackgroundColor(getResources().getColor(R.color.graylight));
            }
                //获取tabs图片；
        }
		
		switch(current){
		case 0:
			//mAdapter.setArrayList(mArrayList);
			mAdapter.notifyDataSetChanged();
			break;
		case 1:
			//mAdapter.setArrayList(mArrayProject);
			mAdapter.notifyDataSetChanged();
			break;
		case 2:
			//mAdapter.setArrayList(mArraySoft);
			mAdapter.notifyDataSetChanged();
			break;
		case 3:
			//mAdapter.setArrayList(mArrayDesign);
			mAdapter.notifyDataSetChanged();
			break;
		case 4:
			//mAdapter.setArrayList(mArrayMateria);
			mAdapter.notifyDataSetChanged();
			break;
		case 5:
			//mAdapter.setArrayList(mArrayWindWater);
			mAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	public class DesignerListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mArrayList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			arg1 = mInflater.inflate(R.layout.layout_contact_designer_item, null);
			if(mArrayList.get(arg0).mIsHeart){
				ImageView image = (ImageView)arg1.findViewById(R.id.contact_designer_heart);
				image.setImageResource(R.drawable.heart_yes);
			}
			
			return arg1;
		}
		
	}
	
	public class DesignerListItem{
		String mName = "钟晓晓";
		int mCases = 8;
		float mSkills = 9.0f;
		float mService = 9.0f;
		int mPrice = 100;
		boolean mIsHeart = false;
		int mSupport = 50;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO 自动生成的方法存根
		super.onNewIntent(intent);
	}
}
