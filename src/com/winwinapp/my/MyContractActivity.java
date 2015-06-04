package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.my.MyProjectActivity.MyProjectAdapter;
import com.winwinapp.my.MyProjectActivity.MyProjectItem;
import com.winwinapp.my.MyProjectActivity.OnItemChildClickListener;

public class MyContractActivity extends ActionBarActivity implements OnClickListener,OnItemClickListener{

	ImageView mIDFront;
	ImageView mIDBack;
	ImageView mCert;
	private static final int INDEX_AREA = 1;
	private static final int INDEX_OTHERS = 2;
	ArrayList<MyContractItem> mArrayList = new ArrayList<MyContractItem>();
	ListView mListView;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_AREA:
			case INDEX_OTHERS:
				//int position = msg.arg1;
				//Intent intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
				//startActivity(intent);
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
		
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		MyContractItem item = new MyContractItem();
		item.mIndex = 1;
		item.bConfirm = true;
		mArrayList.add(item);
		item = new MyContractItem();
		item.mIndex = 2;
		item.bConfirm = false;
		mArrayList.add(item);
		mListView.setAdapter(new MyContractAdapter(this));
		//mListView.setOnItemClickListener(this);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的合同");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF00FF00);
		txtView.setText("发起合同");
		setRightView(txtView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		}
	}
	
	public class MyContractItem{
		int mIndex;
		boolean bConfirm;
	}
	
	public class MyContractAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		public MyContractAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
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
			TextView mIndex;
			ImageView mConfirmImage;
			arg1 = mInflater.inflate(R.layout.layout_my_contract_item, null);
			mIndex = (TextView)arg1.findViewById(R.id.my_contract_item_index);
			mIndex.setText(""+mArrayList.get(arg0).mIndex);
			mConfirmImage = (ImageView)arg1.findViewById(R.id.my_contract_is_confirm);
			if(!mArrayList.get(arg0).bConfirm){
				mConfirmImage.setImageResource(R.drawable.my_contract_unconfirm);
			}
			mConfirmImage.setOnClickListener(new OnItemChildClickListener(INDEX_AREA,arg0));
			return arg1;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
		startActivity(intent);
	}
}
