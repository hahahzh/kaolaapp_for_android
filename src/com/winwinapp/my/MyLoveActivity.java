package com.winwinapp.my;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.winwinapp.bids.BidsDetailsActivity;
import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyLoveActivity extends ActionBarActivity {

	private static final int INDEX_BUTTON_MESSAGE = 1;
	private static final int INDEX_BUTTON_DELETE = 2;
	private static final int INDEX_BUTTON_OTHER = 3;
	ArrayList<MyLoveItem> mArrayList = new ArrayList<MyLoveItem>();
	ListView mListView;
	MyLoveAdapter mAdapter;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			int position;
			switch(msg.what){
			case INDEX_BUTTON_MESSAGE:
				Intent intent = new Intent(MyLoveActivity.this,KoalaChatActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("msg_id", "54");
				intent.putExtra("topic_id", "21");
				intent.putExtra("rec_id", "5");
				startActivity(intent);
				break;
			case INDEX_BUTTON_DELETE:
				position = msg.arg1;
				if(position >= 0 && position <= mArrayList.size()-1){
					showSelfDefineDialog(position);
				}
				break;
			case INDEX_BUTTON_OTHER:
				break;
			}
		}
	};
	
	public void showSelfDefineDialog(final int position) {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("确定删除收藏？"); 
		  builder.setTitle("提示");  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialoginterface, int i) {
        	  dialoginterface.dismiss();
          }
			  });
		  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialoginterface, int i) {
        	  	mArrayList.remove(position);
				mAdapter.notifyDataSetChanged();
        	  	dialoginterface.dismiss();
          		}
			  });
		  builder.create().show();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		MyLoveItem item = new MyLoveItem();
		item.mIndex = 1;
		item.mSpecial = "9年经验";
		item.name = "钟晓晓";
		item.attitude_score = "9.0";
		item.profess_score = "9.0";
		item.cases = "8";
		item.job = "监理";
		mArrayList.add(item);
		item = new MyLoveItem();
		item.mIndex = 2;
		item.mSpecial = "￥100/m2";
		item.name = "钟晓晓";
		item.attitude_score = "9.0";
		item.profess_score = "9.0";
		item.cases = "8";
		item.job = "设计师";
		mArrayList.add(item);
		
		item = new MyLoveItem();
		item.mIndex = 2;
		item.mSpecial = "9年经验";
		item.name = "钟晓晓";
		item.attitude_score = "9.0";
		item.profess_score = "9.0";
		item.cases = "8";
		item.job = "工长";
		mArrayList.add(item);
		mAdapter = new MyLoveAdapter(this);
		mListView.setAdapter(mAdapter);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的收藏");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	public class MyLoveItem{
		int mIndex;
		String mSpecial;
		String job;
		String name;
		String profess_score;
		String attitude_score;
		String cases;
	}
	
	public class MyLoveAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		public MyLoveAdapter(Context context){
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
			ImageView message;
			ImageView delete;
			TextView mSpecial,name,job,cases,score;
			MyLoveItem item = mArrayList.get(arg0);
			arg1 = mInflater.inflate(R.layout.layout_my_love_item, null);
			message = (ImageView)arg1.findViewById(R.id.my_love_item_message);
			message.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_MESSAGE,arg0));
			
			delete = (ImageView)arg1.findViewById(R.id.my_love_item_delete);
			delete.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_DELETE,arg0));
			
			mSpecial = (TextView)arg1.findViewById(R.id.my_love_item_special);
			mSpecial.setText(item.mSpecial);
			
			name = (TextView)arg1.findViewById(R.id.my_love_item_name);
			name.setText(item.name);
			job = (TextView)arg1.findViewById(R.id.my_love_item_job);
			job.setText(item.job);
			cases = (TextView)arg1.findViewById(R.id.my_love_item_cases);
			cases.setText("案例数："+item.cases);
			score = (TextView)arg1.findViewById(R.id.my_love_item_score);
			score.setText("专业："+item.profess_score+"     服务："+item.attitude_score);
			
			
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
