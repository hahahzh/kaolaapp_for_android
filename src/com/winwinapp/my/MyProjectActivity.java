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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyProjectActivity extends ActionBarActivity {

	private static final int INDEX_BUTTON_SSET_DATE = 1;
	private static final int INDEX_OTHERS = 2;
	ArrayList<MyProjectItem> mArrayList = new ArrayList<MyProjectItem>();
	ListView mListView;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_BUTTON_SSET_DATE:
				int position = msg.arg1;
				Intent intent = new Intent(MyProjectActivity.this,MyProjectCalendarActivity.class);
				startActivity(intent);
				break;
			case INDEX_OTHERS:
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
		MyProjectItem item = new MyProjectItem();
		item.mIndex = 1;
		mArrayList.add(item);
		item = new MyProjectItem();
		item.mIndex = 2;
		mArrayList.add(item);
		mListView.setAdapter(new MyProjectAdapter(this));
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
			Button mBtn_set_date;
			arg1 = mInflater.inflate(R.layout.layout_my_project_item, null);
			mIndex = (TextView)arg1.findViewById(R.id.my_project_item_index);
			mIndex.setText(""+mArrayList.get(arg0).mIndex);
			mBtn_set_date = (Button)arg1.findViewById(R.id.my_project_item_btn_set_date);
			mBtn_set_date.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_SSET_DATE,arg0));
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
