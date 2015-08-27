package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.Utils;

public class MyAccountActivity extends ActionBarActivity {

	ArrayList<MyAccountItem> mArrayList = new ArrayList<MyAccountItem>();
	ArrayList<MyAccountMoneyItem> mArrayMoneyList = new ArrayList<MyAccountMoneyItem>();
	ListView mListView;
	
	MyAccountAdapter mAdapter;
	NetworkData.GetPayListBack mBack = NetworkData.getInstance().getNewGetPayListBack();
	NetworkData.GetPayListData mData = NetworkData.getInstance().getNewGetPayList();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(MyAccountActivity.this, "获取支付列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public class GetPayListThread extends Thread{
		public void run(){
			boolean success = false;
			mData.limit = 5;
			mData.page = 0;
			Message msg = Message.obtain();
			msg.what = 1;
			mBack.items.clear();
			success = HTTPPost.GetPayList(mData, mBack);
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
		new GetPayListThread().start();
	}
	
	
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
//		MyAccountItem item = new MyAccountItem();
//		item.mName = "杨浦小区";
//		item.isFinish = false;
//		mArrayList.add(item);
//		item = new MyAccountItem();
//		item.mName = "复旦小区";
//		item.isFinish = true;
//		mArrayList.add(item);
		mAdapter = new MyAccountAdapter(this);
		mListView.setAdapter(mAdapter);
		
//		MyAccountMoneyItem item1 = new MyAccountMoneyItem();
//		item1.mDate = "2015.01.01";
//		item1.mMoneyFrom = "卡拉小匠";
//		item1.mMoneyTo = "张珊";
//		item1.mExtra = "考拉预收第一笔工程款";
//		item1.mMoney = "￥20000";
//		mArrayMoneyList.add(item1);
//		item1 = new MyAccountMoneyItem();
//		item1.mMoneyTo = "张珊";
//		item1.mDate = "2015.01.01";
//		item1.mMoneyFrom = "您的账户";
//		item1.mExtra = "￥3000作为质保金将于2016.05.01转入您的帐户";
//		item1.mMoney = "￥17000";
//		mArrayMoneyList.add(item1);
//		item1 = new MyAccountMoneyItem();
//		item1.mMoneyTo = "张珊";
//		item1.mDate = "2015.01.01";
//		item1.mMoneyFrom = "您的账户";
//		item1.mExtra = "归还质保金";
//		item1.mMoney = "￥3000";
//		mArrayMoneyList.add(item1);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的支付");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}


	public class MyAccountItem{
		String mName;
		boolean isFinish;
	}
	
	public class MyAccountAdapter extends BaseAdapter{

		Context mContext;
		LayoutInflater mInflater;
		public MyAccountAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
		
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
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			View view = mInflater.inflate(R.layout.layout_my_account_item, null);
			TextView name = (TextView) view.findViewById(R.id.my_account_item_name);
			TextView total = (TextView) view.findViewById(R.id.my_account_total);
//			name.setText(mArrayList.get(arg0).mName);
			ImageView img = (ImageView)view.findViewById(R.id.my_account_status);
//			if(mArrayList.get(arg0).isFinish){
//				img.setImageResource(R.drawable.my_account_finished);
//			}
			if("1".equals((mBack.items.get(arg0).ret))){
				img.setImageResource(R.drawable.my_account_finished);
			}
			total.setText(mBack.items.get(arg0).amount+"");
			ListView listView = (ListView)view.findViewById(R.id.my_account_list_money);
			listView.setAdapter(new MyAccountMoneyAdapter(mContext,mBack.items.get(arg0)));
			return view;
		}
		
	}
	
	public class MyAccountMoneyAdapter extends BaseAdapter{

		Context mContext;
		LayoutInflater mInflater;
		NetworkData.PayListItem mItem;
		public MyAccountMoneyAdapter(Context context,NetworkData.PayListItem item){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mItem = item;
		}
		
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return 1;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO 自动生成的方法存根
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			View view = mInflater.inflate(R.layout.layout_my_account_money_item, null);
			TextView mDate = (TextView) view.findViewById(R.id.my_account_money_date);
			//mDate.setText(mArrayMoneyList.get(arg0).mDate);
			mDate.setText(Utils.utcToData(mItem.operate_time));
			TextView mFrom = (TextView) view.findViewById(R.id.my_account_money_from);
			//mFrom.setText(mArrayMoneyList.get(arg0).mMoneyFrom);
			mFrom.setText(mItem.username);
			TextView mTo = (TextView) view.findViewById(R.id.my_account_money_to);
			//mTo.setText(mArrayMoneyList.get(arg0).mMoneyTo);
			TextView mMoney = (TextView) view.findViewById(R.id.my_account_money_money);
			//mMoney.setText(mArrayMoneyList.get(arg0).mMoney);
			mMoney.setText(mItem.amount);
			TextView mExtra = (TextView) view.findViewById(R.id.my_account_money_extra);
			//mExtra.setText(mArrayMoneyList.get(arg0).mExtra);
			mExtra.setText(mItem.bill_info);
			return view;
		}
		
	}
	
	public class MyAccountMoneyItem{
		String mDate;
		String mMoney;
		String mMoneyTo;
		String mMoneyFrom;
		String mExtra;
	}

}
