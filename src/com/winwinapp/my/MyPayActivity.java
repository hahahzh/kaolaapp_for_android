package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.designer.DesignerActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.Utils;

public class MyPayActivity extends ActionBarActivity implements OnClickListener{

	private static final int INDEX_BUTTON_PAY = 1;
	private static final int MESSAGE_PAY_BACK = 2;
	ArrayList<MyPayItem> mArrayListOnPay = new ArrayList<MyPayItem>();
	ArrayList<MyPayItem> mArrayListPayed = new ArrayList<MyPayItem>();
	ListView mListView;
	TextView mOnPayTxt;
	TextView mPayedTxt;
	ImageView mOnPayIndicator;
	ImageView mPayedIndicator;
	int mCurrentState = 0;
	MyPayAdapter mAdapter;
	TextView mTipsText;
	String mOnPayTips = "首付=合同总额  ×百分比(设计50%,工长20%,监理50%)";
	String mPayedTips = "首付款，考拉会为您妥善保管，竣工后按流程支付给收款人。";
	NetworkData.GetPayListBack mBack = NetworkData.getInstance().getNewGetPayListBack();
	NetworkData.GetPayListData mData = NetworkData.getInstance().getNewGetPayList();
	ArrayList<NetworkData.PayListItem> mOnPay = new ArrayList<NetworkData.PayListItem>();
	ArrayList<NetworkData.PayListItem> mPayed = new ArrayList<NetworkData.PayListItem>();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_BUTTON_PAY:
				int position = msg.arg1;
				Toast.makeText(MyPayActivity.this, "pos="+position+",暂时还不支持支付功能", Toast.LENGTH_LONG).show();
				break;
			case 2:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
					//Toast.makeText(MyPayActivity.this, "获取支付列表成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(MyPayActivity.this, "获取支付列表失败："+error, Toast.LENGTH_LONG).show();
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
			msg.what = MESSAGE_PAY_BACK;
			mBack.items.clear();
			success = HTTPPost.GetPayList(mData, mBack);
			if(success){
				msg.obj = "OK";
				mOnPay.clear();
				mPayed.clear();
				if(mBack.items.size() > 0){
					for(NetworkData.PayListItem item:mBack.items){
						if("1".equals(item.ret)){
							mPayed.add(item);
						}else{
							mOnPay.add(item);
						}
					}
				}
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_pay);

		initActionBar();
		initListView();
		initView();
		new GetPayListThread().start();
		
	}
	
	public void initView(){
		mOnPayTxt = (TextView)findViewById(R.id.my_onpay_txt);
		mPayedTxt = (TextView)findViewById(R.id.my_payed_txt);
		mOnPayTxt.setOnClickListener(this);
		mPayedTxt.setOnClickListener(this);
		mOnPayIndicator = (ImageView)findViewById(R.id.id_auth_id_indicator);
		mPayedIndicator = (ImageView)findViewById(R.id.id_auth_cert_indicator);
		mOnPayIndicator.setOnClickListener(this);
		mPayedIndicator.setOnClickListener(this);
		mPayedIndicator.setVisibility(View.INVISIBLE);
		mPayedTxt.setTextColor(0xFF000000);
		mOnPayTxt.setTextColor(0xFFFF6600);
		
		mTipsText = (TextView)findViewById(R.id.my_pay_tips);
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.my_pay_list_view);
//		MyPayItem item = new MyPayItem();
//		item.mBioName = "杨浦小区";
//		item.mName = "章成";
//		item.mJob = "设计师";
//		item.mTotalAcount = "￥10000";
//		item.date = "  2015-03-19";
//		item.firstPay = "￥5000";
//		mArrayListOnPay.add(item);
//		mArrayListPayed.add(item);
//		item = new MyPayItem();
//		item.mBioName = "杨浦小区";
//		item.mName = "章成";
//		item.mJob = "工长";
//		item.mTotalAcount = "￥10000";
//		item.date = "  2015-03-19";
//		item.firstPay = "￥2000";
//		mArrayListOnPay.add(item);
//		mArrayListPayed.add(item);
//		item = new MyPayItem();
//		item.mBioName = "杨浦小区";
//		item.mName = "章成";
//		item.mJob = "监理";
//		item.mTotalAcount = "￥10000";
//		item.date = "  2015-03-19";
//		item.firstPay = "￥2000";
//		mArrayListOnPay.add(item);
//		mArrayListPayed.add(item);
		mAdapter = new MyPayAdapter(this);
		mListView.setAdapter(mAdapter);
		//mListView.setOnItemClickListener(this);
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的账户");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}


	public class MyPayItem{
		String mName;
		String mBioName;
		String mJob;
		String mTotalAcount;
		String date;
		String firstPay;
	}
	
	public class MyPayAdapter extends BaseAdapter{

		Context mContext;
		LayoutInflater mInflater;
		public MyPayAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
		
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			if(mCurrentState == 0){//待支付
				return mOnPay.size();
			}else{//已支付
				return mPayed.size();
			}
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
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			View view = mInflater.inflate(R.layout.layout_my_pay_item, null);
			TextView bio = (TextView) view.findViewById(R.id.my_pay_item_bioname);
			TextView name = (TextView) view.findViewById(R.id.my_pay_item_name);
			TextView job = (TextView) view.findViewById(R.id.my_pay_item_job);
			TextView account = (TextView) view.findViewById(R.id.my_pay_item_account);
			TextView date = (TextView) view.findViewById(R.id.my_pay_item_date);
			TextView firstPay = (TextView) view.findViewById(R.id.my_pay_item_first_pay);
			Button pay = (Button) view.findViewById(R.id.my_pay_item_pay);
			NetworkData.PayListItem item = mBack.items.get(position);
			//MyPayItem item;
			if(mCurrentState == 0){
				item =mOnPay.get(position);
				pay.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_PAY,position));
			}else{
				firstPay.setTextColor(Color.BLACK);
				pay.setVisibility(View.GONE);
				item = mPayed.get(position);
			}
			name.setText(item.username);
			String[] arr = item.bill_info.split(" ");
			if(arr != null && arr.length >= 3){
				bio.setText(arr[1]);
			}
			
			//job.setText(item.mJob);
			account.setText(item.amount);
			date.setText(Utils.utcToData(item.operate_time));
			//firstPay.setText(item.firstPay);
			
			return view;
		}
		
	}
	
	public class OnItemChildClickListener implements View.OnClickListener{
		private int mClickIndex;
		private int mPosition;
		
		public OnItemChildClickListener(int clickIndex, int position){
			mClickIndex = clickIndex;
			mPosition = position;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO 自动生成的方法存根
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			mHandler.sendMessage(msg);
		}
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.my_payed_txt:
			mOnPayIndicator.setVisibility(View.INVISIBLE);
			mOnPayTxt.setTextColor(0xFF000000);
			mPayedIndicator.setVisibility(View.VISIBLE);
			mPayedTxt.setTextColor(0xFFFF6600);
			mCurrentState = 1;
			mAdapter.notifyDataSetChanged();
			mTipsText.setText(mPayedTips);
			break;
		case R.id.my_onpay_txt:
			mPayedIndicator.setVisibility(View.INVISIBLE);
			mPayedTxt.setTextColor(0xFF000000);
			mOnPayIndicator.setVisibility(View.VISIBLE);
			mOnPayTxt.setTextColor(0xFFFF6600);
			mCurrentState = 0;
			mAdapter.notifyDataSetChanged();
			mTipsText.setText(mOnPayTips);
			break;
		}
	}
}
