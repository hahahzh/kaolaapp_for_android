package com.winwinapp.koala;

import java.util.ArrayList;

import com.winwinapp.calendar.SetDepositActivity;
import com.winwinapp.my.MyProjectActivity;
import com.winwinapp.my.MyProjectCalendarActivity;
import com.winwinapp.my.MyProjectActivity.MyProjectAdapter;
import com.winwinapp.my.MyProjectActivity.MyProjectItem;
import com.winwinapp.my.MyProjectActivity.OnItemChildClickListener;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class fragment_project extends Fragment implements OnClickListener{

	private Activity mActivity;
	private static final int INDEX_BUTTON_SET_DATE = 1;
	private static final int INDEX_OTHERS = 2;
	private static final int INDEX_BUTTON_FINISH = 3;
	private static final int MESSAGE_REQUEST_LIST = 4;
	ListView mListView;
	MyProjectAdapter mAdapter;
	NetworkData.BidListData mData = NetworkData.getInstance().getNewBidListData();
	NetworkData.BidListBack mBack = NetworkData.getInstance().getNewBidListBack();
	int mUserType;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			int position;
			Intent intent;
			switch(msg.what){
			case INDEX_BUTTON_SET_DATE:
				position = msg.arg1;
				intent = new Intent(mActivity , MyProjectCalendarActivity.class);
				startActivity(intent);
				break;
			case INDEX_OTHERS:
				break;
			case INDEX_BUTTON_FINISH:
				showDialog(msg.arg1);
				break;
			case MESSAGE_REQUEST_LIST:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(mActivity, "获取装修列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	protected void showDialog(final int position) {
		  AlertDialog.Builder builder = new Builder(mActivity);
		  if(mUserType == fragment_homepage.TYPE_OWER){
			  builder.setMessage("确定装修已完成？"); 
		  }else{
			  builder.setMessage("确定向业主发出竣工请求？"); 
		  }
		  builder.setTitle("竣工提示");  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
			  public void onClick(DialogInterface dialoginterface, int i) {
		      	  		dialoginterface.dismiss();
		        	}
			  });
		  builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			  public void onClick(DialogInterface dialoginterface, int i) {
//					  	Intent intent = new Intent(mActivity , SetDepositActivity.class);
//					  	intent.putExtra("bid", mBack.items.get(position).bid_id);
//						startActivity(intent);
		        	}
			  });
		  builder.create().show();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_list,null);
		
		mActivity = this.getActivity();
		
		mListView = (ListView)view.findViewById(R.id.list_common);
//		MyProjectItem item = new MyProjectItem();
//		mArrayList.clear();
//		item.mIndex = 1;
//		mArrayList.add(item);
//		item = new MyProjectItem();
//		item.mIndex = 2;
//		mArrayList.add(item);
//		mListView.setAdapter(new MyProjectAdapter(mActivity));
		
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		
		mBack.total = 0;
		mBack.items.clear();
		mAdapter = new MyProjectAdapter(mActivity);
		mListView.setAdapter(mAdapter);
		mUserType = KoalaApplication.mUserType;
		//mListView.setOnItemClickListener(this);
		if(mUserType == -1){
			return;
		}
		new Thread(){
			public void run(){
				boolean success = false;
				mData.limit = 6;
				if( mUserType == fragment_homepage.TYPE_OWER){
					success = HTTPPost.RequestDecorateList(mData, mBack);
				}else {
					success = HTTPPost.RequestOtherDecorateList(mData, mBack);
				}
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
			Button mBtn_finish;
			arg1 = mInflater.inflate(R.layout.layout_my_project_item, null);
			mIndex = (TextView)arg1.findViewById(R.id.my_project_item_index);
			mIndex.setText(""+(position+1));
			mBtn_set_date = (Button)arg1.findViewById(R.id.my_project_item_btn_set_date);
			mBtn_set_date.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_SET_DATE,position));
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
			mBtn_finish = (Button)arg1.findViewById(R.id.my_project_item_btn_finish);
			mBtn_finish.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_FINISH,position));
			
			
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
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		
	}

}
