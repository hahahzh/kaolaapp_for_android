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
import android.widget.Toast;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.my.MyProjectActivity.MyProjectAdapter;
import com.winwinapp.my.MyProjectActivity.MyProjectItem;
import com.winwinapp.my.MyProjectActivity.OnItemChildClickListener;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class MyContractActivity extends ActionBarActivity implements OnClickListener,OnItemClickListener{

	ImageView mIDFront;
	ImageView mIDBack;
	ImageView mCert;
	private static final int INDEX_AREA = 1;
	private static final int INDEX_OTHERS = 2;
	private static final int CONTRACT_LIST = 3;
	ArrayList<MyContractItem> mArrayList = new ArrayList<MyContractItem>();
	ListView mListView;
	int i = 0;
	NetworkData.BidListData mData = NetworkData.getInstance().getNewBidListData();
	NetworkData.MyContractListBack mBack = NetworkData.getInstance().getNewMyContractListBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_AREA:
			case INDEX_OTHERS:
				
				break;
			case CONTRACT_LIST:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					updateListView();
				}else{
					Toast.makeText(MyContractActivity.this, "获取合同列表失败："+error, Toast.LENGTH_LONG).show();
				}
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
	
	public void updateListView(){
		mArrayList.clear();
		MyContractItem item;
		for(int i=0;i<mBack.items.size();i++){
			item = new MyContractItem();
			item.mIndex = i+1;
			item.bConfirm = "1".equals(mBack.items.get(i).is_confirm);
			item.title = mBack.items.get(i).title;
			item.name = mBack.items.get(i).real_name;
			mArrayList.add(item);
		}
		mListView.setAdapter(new MyContractAdapter(this));
		mListView.setOnItemClickListener(this);
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		new Thread(){
			public void run(){
				boolean success = false;
				success = HTTPPost.RequestMyContractList(mData, mBack);
				Message msg = Message.obtain();
				msg.what = CONTRACT_LIST;
				if(success){
					msg.obj = "OK";
				}else{
					msg.obj = mBack.error;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
		
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
				Intent intent;
				if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
					intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
					intent.putExtra("type", 0);//new contract
				}else{
					intent = new Intent(MyContractActivity.this,ContractSuperiorActivity.class);
					intent.putExtra("type", 0);//new contract
				}
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
		String title;
		String name;
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
			TextView mIndex,title,name;
			ImageView mConfirmImage;
			arg1 = mInflater.inflate(R.layout.layout_my_contract_item, null);
			mIndex = (TextView)arg1.findViewById(R.id.my_contract_item_index);
			title = (TextView)arg1.findViewById(R.id.my_contract_item_area);
			name = (TextView)arg1.findViewById(R.id.my_contract_item_user_name);
			mIndex.setText(""+mArrayList.get(arg0).mIndex);
			mConfirmImage = (ImageView)arg1.findViewById(R.id.my_contract_is_confirm);
			if(!mArrayList.get(arg0).bConfirm){
				mConfirmImage.setImageResource(R.drawable.my_contract_unconfirm);
			}
			title.setText(mArrayList.get(arg0).title);
			name.setText(mArrayList.get(arg0).name);
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
		Intent intent;
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
			intent.putExtra("type", 1);//view contract
			intent.putExtra("id", Integer.parseInt(mBack.items.get(arg2).c_id));
		}else{
			intent = new Intent(MyContractActivity.this,ContractSuperiorActivity.class);
			intent.putExtra("type", 1);//view contract
			intent.putExtra("id", Integer.parseInt(mBack.items.get(arg2).c_id));
		}
		startActivity(intent);
	}
}
