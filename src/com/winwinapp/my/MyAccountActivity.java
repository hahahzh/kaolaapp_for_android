package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyAccountActivity extends ActionBarActivity {

	ArrayList<MyAccountItem> mArrayList = new ArrayList<MyAccountItem>();
	ArrayList<MyAccountMoneyItem> mArrayMoneyList = new ArrayList<MyAccountMoneyItem>();
	ListView mListView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
		
	}
	
	
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		MyAccountItem item = new MyAccountItem();
		item.mName = "����С��";
		item.isFinish = false;
		mArrayList.add(item);
		item = new MyAccountItem();
		item.mName = "����С��";
		item.isFinish = true;
		mArrayList.add(item);
		mListView.setAdapter(new MyAccountAdapter(this));
		//mListView.setOnItemClickListener(this);
		
		MyAccountMoneyItem item1 = new MyAccountMoneyItem();
		item1.mDate = "2015.01.01";
		item1.mMoneyFrom = "����С��";
		item1.mMoneyTo = "��ɺ";
		item1.mExtra = "����Ԥ�յ�һ�ʹ��̿�";
		item1.mMoney = "��20000";
		mArrayMoneyList.add(item1);
		item1 = new MyAccountMoneyItem();
		item1.mMoneyTo = "��ɺ";
		item1.mDate = "2015.01.01";
		item1.mMoneyFrom = "�����˻�";
		item1.mExtra = "��3000��Ϊ�ʱ�����2016.05.01ת�������ʻ�";
		item1.mMoney = "��17000";
		mArrayMoneyList.add(item1);
		item1 = new MyAccountMoneyItem();
		item1.mMoneyTo = "��ɺ";
		item1.mDate = "2015.01.01";
		item1.mMoneyFrom = "�����˻�";
		item1.mExtra = "�黹�ʱ���";
		item1.mMoney = "��3000";
		mArrayMoneyList.add(item1);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("�ҵ�֧��");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
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
			// TODO �Զ����ɵķ������
			return mArrayList.size();
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
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
			View view = mInflater.inflate(R.layout.layout_my_account_item, null);
			TextView name = (TextView) view.findViewById(R.id.my_account_item_name);
			name.setText(mArrayList.get(arg0).mName);
			ImageView img = (ImageView)view.findViewById(R.id.my_account_status);
			if(mArrayList.get(arg0).isFinish){
				img.setImageResource(R.drawable.my_account_finished);
			}
			ListView listView = (ListView)view.findViewById(R.id.my_account_list_money);
			listView.setAdapter(new MyAccountMoneyAdapter(mContext));
			return view;
		}
		
	}
	
	public class MyAccountMoneyAdapter extends BaseAdapter{

		Context mContext;
		LayoutInflater mInflater;
		public MyAccountMoneyAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
		
		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			return mArrayMoneyList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO �Զ����ɵķ������
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO �Զ����ɵķ������
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
			View view = mInflater.inflate(R.layout.layout_my_account_money_item, null);
			TextView mDate = (TextView) view.findViewById(R.id.my_account_money_date);
			mDate.setText(mArrayMoneyList.get(arg0).mDate);
			TextView mFrom = (TextView) view.findViewById(R.id.my_account_money_from);
			mFrom.setText(mArrayMoneyList.get(arg0).mMoneyFrom);
			TextView mTo = (TextView) view.findViewById(R.id.my_account_money_to);
			mTo.setText(mArrayMoneyList.get(arg0).mMoneyTo);
			TextView mMoney = (TextView) view.findViewById(R.id.my_account_money_money);
			mMoney.setText(mArrayMoneyList.get(arg0).mMoney);
			TextView mExtra = (TextView) view.findViewById(R.id.my_account_money_extra);
			mExtra.setText(mArrayMoneyList.get(arg0).mExtra);
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
