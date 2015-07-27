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

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyPayActivity extends ActionBarActivity implements OnClickListener{

	private static final int INDEX_BUTTON_PAY = 1;
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
	String mOnPayTips = "�׸�=��ͬ�ܶ�  ���ٷֱ�(���50%,����20%,����50%)";
	String mPayedTips = "�׸��������Ϊ�����Ʊ��ܣ�����������֧�����տ��ˡ�";
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_BUTTON_PAY:
				int position = msg.arg1;
				Toast.makeText(MyPayActivity.this, "pos="+position+",��ʱ����֧��֧������", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_pay);

		initActionBar();
		initListView();
		initView();
		
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
		MyPayItem item = new MyPayItem();
		item.mBioName = "����С��";
		item.mName = "�³�";
		item.mJob = "���ʦ";
		item.mTotalAcount = "��10000";
		item.date = "  2015-03-19";
		item.firstPay = "��5000";
		mArrayListOnPay.add(item);
		mArrayListPayed.add(item);
		item = new MyPayItem();
		item.mBioName = "����С��";
		item.mName = "�³�";
		item.mJob = "����";
		item.mTotalAcount = "��10000";
		item.date = "  2015-03-19";
		item.firstPay = "��2000";
		mArrayListOnPay.add(item);
		mArrayListPayed.add(item);
		item = new MyPayItem();
		item.mBioName = "����С��";
		item.mName = "�³�";
		item.mJob = "����";
		item.mTotalAcount = "��10000";
		item.date = "  2015-03-19";
		item.firstPay = "��2000";
		mArrayListOnPay.add(item);
		mArrayListPayed.add(item);
		mAdapter = new MyPayAdapter(this);
		mListView.setAdapter(mAdapter);
		//mListView.setOnItemClickListener(this);
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("�ҵ��˻�");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
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
			// TODO �Զ����ɵķ������
			if(mCurrentState == 0){
				return mArrayListOnPay.size();
			}else{
				return mArrayListPayed.size();
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO �Զ����ɵķ������
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO �Զ����ɵķ������
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
			View view = mInflater.inflate(R.layout.layout_my_pay_item, null);
			TextView bio = (TextView) view.findViewById(R.id.my_pay_item_bioname);
			TextView name = (TextView) view.findViewById(R.id.my_pay_item_name);
			TextView job = (TextView) view.findViewById(R.id.my_pay_item_job);
			TextView account = (TextView) view.findViewById(R.id.my_pay_item_account);
			TextView date = (TextView) view.findViewById(R.id.my_pay_item_date);
			TextView firstPay = (TextView) view.findViewById(R.id.my_pay_item_first_pay);
			Button pay = (Button) view.findViewById(R.id.my_pay_item_pay);
			MyPayItem item;
			if(mCurrentState == 0){
				item = mArrayListOnPay.get(position);
				pay.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_PAY,position));
			}else{
				firstPay.setTextColor(Color.BLACK);
				pay.setVisibility(View.GONE);
				item = mArrayListPayed.get(position);
			}
			name.setText(item.mName);
			bio.setText(item.mBioName);
			job.setText(item.mJob);
			account.setText(item.mTotalAcount);
			date.setText(item.date);
			firstPay.setText(item.firstPay);
			
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
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			mHandler.sendMessage(msg);
		}
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
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
