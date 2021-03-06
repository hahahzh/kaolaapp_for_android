package com.winwinapp.decorateTips;

import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.ActionBarView;
import com.winwinapp.util.RefreshableListView;

@SuppressWarnings("deprecation")
public class DecorateTipsActivity extends Activity  implements OnTabChangeListener,OnItemClickListener{

	public static final int URL_INVALIDATE = 1;
	private TabHost mTabHost;
	LayoutInflater mInflater;
	private ActionBarView mActionBar;
	RefreshableListView mRefreshListView;
	private ListView mListView;

	private int mType = 0;//0: request all bid list; 1: request user bid list(only for owner)
	NetworkData.DecorateTipsData mData = NetworkData.getInstance().getDecorateTipsData();
	NetworkData.DecorateTipsBack mBack0 = NetworkData.getInstance().getDecorateTipsBack();
	NetworkData.DecorateTipsBack mBack1 = NetworkData.getInstance().getDecorateTipsBack();
	NetworkData.DecorateTipsBack mBack2 = NetworkData.getInstance().getDecorateTipsBack();
	NetworkData.DecorateTipsBack mBack3 = NetworkData.getInstance().getDecorateTipsBack();
	NetworkData.DecorateTipsBack mBack4 = NetworkData.getInstance().getDecorateTipsBack();
	NetworkData.DecorateTipsBack mBack5 = NetworkData.getInstance().getDecorateTipsBack();
	ArrayList<SubDecorateTips> mList0 = new ArrayList<SubDecorateTips>();
	ArrayList<SubDecorateTips> mList1 = new ArrayList<SubDecorateTips>();
	ArrayList<SubDecorateTips> mList2 = new ArrayList<SubDecorateTips>();
	ArrayList<SubDecorateTips> mList3 = new ArrayList<SubDecorateTips>();
	ArrayList<SubDecorateTips> mList4 = new ArrayList<SubDecorateTips>();
	ArrayList<SubDecorateTips> mList5 = new ArrayList<SubDecorateTips>();
	DecorateTipsAdapter mAdapter = new DecorateTipsAdapter();
	
	class MyThread extends Thread {  
		public void run(){
			int current = mTabHost.getCurrentTab();
			boolean success = false;
			
			switch(current){
			case 0:
				success = HTTPPost.RequestDecorateTipList(mData, mBack0);
				break;
			case 1:
				success = HTTPPost.RequestDecorateTipList(mData, mBack1);
				break;
			case 2:
				success = HTTPPost.RequestDecorateTipList(mData, mBack2);
				break;
			case 3:
				success = HTTPPost.RequestDecorateTipList(mData, mBack3);
				break;
			case 4:
				success = HTTPPost.RequestDecorateTipList(mData, mBack4);
				break;
			case 5:
				success = HTTPPost.RequestDecorateTipList(mData, mBack5);
				break;
			}
			Message msg = Message.obtain();
			msg.what = URL_INVALIDATE;
			if(success){
				msg.obj = "OK";
				switch(current){
				case 0:
					mList0.clear();
					mList0.addAll(mBack0.items.subDecorate);
					break;
				case 1:
					mList1.clear();
					mList1.addAll(mBack1.items.subDecorate);
					break;
				case 2:
					mList2.clear();
					mList2.addAll(mBack2.items.subDecorate);
					break;
				case 3:
					mList3.clear();
					mList3.addAll(mBack3.items.subDecorate);
					break;
				case 4:
					mList4.clear();
					mList4.addAll(mBack4.items.subDecorate);
					break;
				case 5:
					mList5.clear();
					mList5.addAll(mBack5.items.subDecorate);
					break;
				}
			}else{
				
				switch(current){
				case 0:
					msg.obj = mBack0.error;
					break;
				case 1:
					msg.obj = mBack1.error;
					break;
				case 2:
					msg.obj = mBack2.error;
					break;
				case 3:
					msg.obj = mBack3.error;
					break;
				case 4:
					msg.obj = mBack4.error;
					break;
				case 5:
					msg.obj = mBack5.error;
					break;
				}
			}
			mHandler.sendMessage(msg);
		}
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;ta
			switch(msg.what){
			case URL_INVALIDATE:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(DecorateTipsActivity.this, "获取装修宝典列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_decorate_tip_main);
		
		mRefreshListView = (RefreshableListView)findViewById(R.id.decorate_tips_refreshable_list_view);
		mRefreshListView.setOnRefreshListener(new com.winwinapp.util.RefreshableListView.PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);//refresh item
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mRefreshListView.finishRefreshing();
			}
		}, 1);
		initActionBar();
		mInflater = LayoutInflater.from(this);
		mListView = (ListView) this.findViewById(R.id.decorate_tips_list);
		initTabHost();
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(this);
		new MyThread().start();
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
		LayoutInflater lf = LayoutInflater.from(this);
		View view = lf.inflate(R.layout.layout_tips_tab, null);
		TextView text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("全部");
		mTabHost.addTab(mTabHost.newTabSpec("全部").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("施工");
		mTabHost.addTab(mTabHost.newTabSpec("施工").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("软装");
		mTabHost.addTab(mTabHost.newTabSpec("软装").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("设计");
		mTabHost.addTab(mTabHost.newTabSpec("设计").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("材料");
		mTabHost.addTab(mTabHost.newTabSpec("材料").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("风水");
		mTabHost.addTab(mTabHost.newTabSpec("风水").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		
		mTabHost.setOnTabChangedListener(this);
		
		onTabChanged("全部");
	}
	
	public void refreshTab(){
		int current = mTabHost.getCurrentTab();
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_title);
            ImageView img = (ImageView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_img);
            
            if(i == current){
            	tv.setTextColor(getResources().getColor(R.color.green));//设置字体的颜色
            	img.setBackgroundColor(getResources().getColor(R.color.green));
            	//mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.yellow));
            }else{
            	tv.setTextColor(Color.GRAY);//设置字体的颜色；
            	img.setBackgroundColor(getResources().getColor(R.color.graylight));
            }
                //获取tabs图片；
        }
		
		switch(current){
		case 0:
			if(mList0.size() == 0){
				mData.cid = 0;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		case 1:
			if(mList1.size() == 0){
				mData.cid = 9;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		case 2:
			if(mList2.size() == 0){
				mData.cid = 10;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		case 3:
			if(mList3.size() == 0){
				mData.cid = 11;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		case 4:
			if(mList4.size() == 0){
				mData.cid = 12;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		case 5:
			if(mList5.size() == 0){
				mData.cid = 13;
				mData.page = 1;
				mData.limit = 10;
				new MyThread().start();
			}
			mAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
		
	}
	

	@Override
	public void onTabChanged(String arg0) {
		// TODO 自动生成的方法存根
		refreshTab();
//		new MyThread().start();
		//mListView = (ListView) mTabHost.getCurrentView().findViewById(R.id.decorate_tips_list);
		//mListView.setAdapter(new DecorateTipsAdapter(this,mArrayList));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// TODO 自动生成的方法存根
		Intent intent = new Intent(DecorateTipsActivity.this,DecorateTipsDetailActivity.class);
		intent.putExtra("type", mType);
		int current = mTabHost.getCurrentTab();
		
		switch(current){
		case 0:
			intent.putExtra("doc_id", mList0.get(position).doc_id);
			break;
		case 1:
			intent.putExtra("doc_id", mList1.get(position).doc_id);
			break;
		case 2:
			intent.putExtra("doc_id", mList2.get(position).doc_id);
			break;
		case 3:
			intent.putExtra("doc_id", mList3.get(position).doc_id);
			break;
		case 4:
			intent.putExtra("doc_id", mList4.get(position).doc_id);
			break;
		case 5:
			intent.putExtra("doc_id", mList5.get(position).doc_id);
			break;
		}
		
		startActivity(intent);
	}
	
	public class DecorateTipsAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			int current = mTabHost.getCurrentTab();
			
			switch(current){
			case 0:
				return mBack0.items.subDecorate.size();
			case 1:
				return mBack1.items.subDecorate.size();
			case 2:
				return mBack2.items.subDecorate.size();
			case 3:
				return mBack3.items.subDecorate.size();
			case 4:
				return mBack4.items.subDecorate.size();
			case 5:
				return mBack5.items.subDecorate.size();
			}
			return 0;
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			convertView = mInflater.inflate(R.layout.layout_tips_items, null);
			SubDecorateTips item = null;
			int current = mTabHost.getCurrentTab();
			
			switch(current){
			case 0:
				item = mList0.get(position);
				break;
			case 1:
				item = mList1.get(position);
				break;
			case 2:
				item = mList2.get(position);
				break;
			case 3:
				item = mList3.get(position);
				break;
			case 4:
				item = mList4.get(position);
				break;
			case 5:
				item = mList5.get(position);
				break;
			}
			
			tipsViewHolder mHolder;
			
			mHolder = new tipsViewHolder();
			mHolder.mtitleText = (TextView)convertView.findViewById(R.id.tips_item_title);
			mHolder.mtitleText.setText(item.title);
			mHolder.mContentText = (TextView)convertView.findViewById(R.id.tips_item_content);
			mHolder.mContentText.setText(item.content);
			mHolder.mDateText = (TextView)convertView.findViewById(R.id.tips_item_date);
			java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Date time = new Date(Long.parseLong(item.add_time)*1000);
			mHolder.mDateText.setText(format.format(time));
			mHolder.mPreviewImage = (ImageView)convertView.findViewById(R.id.tips_item_preview);
			mHolder.mViewedText = (TextView)convertView.findViewById(R.id.tips_item_viewed);
			mHolder.mViewedText.setText(item.scan_num);
			convertView.setTag(mHolder);
			
			mHolder = (tipsViewHolder) convertView.getTag();
//			
//			mHolder.mtitleText.setText(mArrayList.get(position).mTitle);
//			mHolder.mContentText.setText(mArrayList.get(position).content);
//			mHolder.mDateText.setText(mArrayList.get(position).mDate);
//			mHolder.mViewedText.setText(mArrayList.get(position).mViewed);
//			mHolder.mPreviewImage.setImageDrawable(mArrayList.get(position).mImage);
//			mHolder.type = mArrayList.get(position).type;
//			
			return convertView;
		}
		
		public class tipsViewHolder{
			TextView mtitleText;
			TextView mDateText;
			TextView mViewedText;
			ImageView mPreviewImage;
			TextView mContentText;
			int type;
		}

	}
}
