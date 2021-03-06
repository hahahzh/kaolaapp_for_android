package com.winwinapp.koala;

import java.util.ArrayList;

import com.winwinapp.bids.BidsDetailsActivity;
import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.bids.BidsListActivity.MyAdapter;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class fragment_bid extends Fragment implements OnItemClickListener{

	private Activity mActivity;
	public static final int URL_INVALIDATE = 1;
	ListView mListView;
	ArrayList<String> mArrayList = new ArrayList<String>();
	NetworkData.BidListData mData = NetworkData.getInstance().getNewBidListData();
	NetworkData.BidListBack mBack = NetworkData.getInstance().getNewBidListBack();
	LayoutInflater mInflater;
	MyAdapter mAdapter = new MyAdapter();
	private int mType = 0;//0: request all bid list; 1: request user bid list(only for owner)
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case URL_INVALIDATE:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(mActivity, "获取招标列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void setType(int type){
		mType = type;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_list, null);
		
		mActivity = this.getActivity();
		mInflater = LayoutInflater.from(mActivity);
		mListView = (ListView)view.findViewById(R.id.list_common);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(this);
		
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			mType = 1;
		}else{
			mType = 0;
		}
		//initView(inflater,view);
		
		return view;
	}

	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mBack.items.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			convertView = mInflater.inflate(R.layout.layout_bids_item, null);
			NetworkData.BidListItem item = mBack.items.get(position);
			TextView index = (TextView)convertView.findViewById(R.id.bid_item_index);
			index.setText(""+(position+1));
			
			TextView biotope_name = (TextView)convertView.findViewById(R.id.bid_item_biotope_name);
			biotope_name.setText(item.biotope_name);
			TextView name = (TextView)convertView.findViewById(R.id.bid_item_name);
			name.setText(item.name);
			TextView area = (TextView)convertView.findViewById(R.id.bid_item_area);
			area.setText(item.area+"㎡");
			TextView budget = (TextView)convertView.findViewById(R.id.bid_item_budget);
			budget.setText(item.budget);
			TextView space_type = (TextView)convertView.findViewById(R.id.bid_item_space_type);
			space_type.setText(item.space_type);
			
			TextView labor = (TextView)convertView.findViewById(R.id.bid_item_labor);
			if("1".equals(item.need_foreman)){
				labor.setVisibility(View.VISIBLE);
			}
			TextView designer = (TextView)convertView.findViewById(R.id.bid_item_designer);
			if("1".equals(item.need_designer)){
				designer.setVisibility(View.VISIBLE);
			}
			TextView superior = (TextView)convertView.findViewById(R.id.bid_item_superior);
			if("1".equals(item.need_supervisor)){
				superior.setVisibility(View.VISIBLE);
			}
			
			TextView bid_number = (TextView)convertView.findViewById(R.id.bid_item_bid_number);
			bid_number.setText(item.bid_number+"人");
			
			
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(mActivity,BidsDetailsActivity.class);
		intent.putExtra("type", mType);
		intent.putExtra("bid_id", mBack.items.get(position).bid_id);
		startActivity(intent);
	}

	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		mBack.items.clear();
		new Thread(){
			public void run(){
				boolean success = false;
				if(mType == 0){				
					success = HTTPPost.RequestBidList(mData, mBack);
				}else{
					success = HTTPPost.RequestUserBidList(mData, mBack);
				}
				Message msg = Message.obtain();
				msg.what = URL_INVALIDATE;
				if(success){
					msg.obj = "OK";
				}else{
					msg.obj = mBack.error;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
}
