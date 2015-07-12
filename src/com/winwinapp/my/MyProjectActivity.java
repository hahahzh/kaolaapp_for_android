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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsDetailsActivity;
import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class MyProjectActivity extends ActionBarActivity implements OnItemClickListener{

	private static final int INDEX_BUTTON_SSET_DATE = 1;
	private static final int INDEX_OTHERS = 2;
	private static final int MESSAGE_REQUEST_LIST = 3;
	ArrayList<MyProjectItem> mArrayList = new ArrayList<MyProjectItem>();
	ListView mListView;
	NetworkData.BidListData mData = NetworkData.getInstance().getNewBidListData();
	NetworkData.BidListBack mBack = NetworkData.getInstance().getNewBidListBack();
	MyProjectAdapter mAdapter;
	
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			Intent intent;
			int position;
			switch(msg.what){
			case INDEX_BUTTON_SSET_DATE:
				position = msg.arg1;
				intent = new Intent(MyProjectActivity.this,MyProjectCalendarActivity.class);
				startActivity(intent);
				break;
			case INDEX_OTHERS:
				position = msg.arg1;
				intent = new Intent(MyProjectActivity.this,BidsDetailsActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("bid_id", mBack.items.get(position).bid_id);
				startActivity(intent);
				break;
			case MESSAGE_REQUEST_LIST:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(MyProjectActivity.this, "获取装修列表失败："+error, Toast.LENGTH_LONG).show();
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
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		/*MyProjectItem item = new MyProjectItem();
		item.mIndex = 1;
		mArrayList.add(item);
		item = new MyProjectItem();
		item.mIndex = 2;
		mArrayList.add(item);
		mListView.setAdapter(new MyProjectAdapter(this));*/
		mAdapter = new MyProjectAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		new Thread(){
			public void run(){
				boolean success = false;
				success = HTTPPost.RequestDecorateList(mData, mBack);
				Message msg = Message.obtain();
				msg.what = MESSAGE_REQUEST_LIST;
				if(success){
					msg.obj = "OK";
				}else{
					msg.obj = mBack.error;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(MyProjectActivity.this,BidsDetailsActivity.class);
		intent.putExtra("type", 1);
		intent.putExtra("bid_id", mBack.items.get(position).bid_id);
		startActivity(intent);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的项目");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	public class MyProjectItem{
		int mIndex;
		
	}
	
	public class MyProjectAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		public MyProjectAdapter(Context context){
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
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			NetworkData.BidListItem item = mBack.items.get(position);
			TextView mIndex,mBioName,mName,mArea,mBudget;
			Button mBtn_set_date;
			arg1 = mInflater.inflate(R.layout.layout_my_project_item, null);
			mIndex = (TextView)arg1.findViewById(R.id.my_project_item_index);
			mIndex.setText(""+position);
			mBtn_set_date = (Button)arg1.findViewById(R.id.my_project_item_btn_set_date);
			mBtn_set_date.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_SSET_DATE,position));
			mBioName = (TextView)arg1.findViewById(R.id.my_project_biotope_name);
			mBioName.setText(item.biotope_name);
			mBioName.setOnClickListener(new OnItemChildClickListener(INDEX_OTHERS,position));
			
			mName = (TextView)arg1.findViewById(R.id.my_project_user_name);
			mName.setText(item.name);
			
			mArea = (TextView)arg1.findViewById(R.id.my_project_area);
			mArea.setText(item.area+"㎡");
			mArea.setOnClickListener(new OnItemChildClickListener(INDEX_OTHERS,position));
			
			mBudget = (TextView)arg1.findViewById(R.id.my_project_budget);
			mBudget.setText(item.budget);
			mBudget.setOnClickListener(new OnItemChildClickListener(INDEX_OTHERS,position));
			
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
}
