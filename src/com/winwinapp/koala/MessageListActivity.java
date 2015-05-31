package com.winwinapp.koala;

import java.util.ArrayList;

import com.winwinapp.chat.KoalaChatActivity;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessageListActivity extends ActionBarActivity {

	private static final int MESSAGE_ID_AVATRAR = 0;
	private static final int MESSAGE_ID_OTHERS = 1;
	private ListView mMessageListView;
	private ArrayList<MessageItem> mMessageItemArray = new ArrayList<MessageItem>();
	private LayoutInflater mInflater;
	MessageListAdapter mMessageAdapter;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case MESSAGE_ID_AVATRAR:
				Toast.makeText(MessageListActivity.this, "avatar:"+(int)msg.arg1, Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_ID_OTHERS:
				Intent intent = new Intent(MessageListActivity.this,KoalaChatActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_message_list);
		
		initActionBar();
		initMessageList();
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("消息列表");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	public void initMessageList(){
		mMessageListView = (ListView)findViewById(R.id.listView_message);
		MessageItem item = new MessageItem("张澈","设计师","2015-03-19",2,R.drawable.avatar1,"今天木板已经送到，明天工人来安装，请耐心等待");
		mMessageItemArray.add(item);
		item = new MessageItem("刘雨鑫","工长","2015-02-23",6,R.drawable.avatar1,"今天木板已经送到，明天工人来安装，请耐心等待");
		mMessageItemArray.add(item);
		item = new MessageItem("陈韵","监理","2015-01-23",3,R.drawable.avatar1,"工程进度我会有所把控。");
		mMessageItemArray.add(item);
		item = new MessageItem("陈韵","业主","2015-01-23",0,R.drawable.avatar1,"工程进度我会有所把控。");
		mMessageItemArray.add(item);
		
		mInflater = LayoutInflater.from(this);
		mMessageAdapter = new MessageListAdapter(this);
		mMessageListView.setAdapter(mMessageAdapter);
	}
	
	public class MessageListAdapter extends BaseAdapter{
		private Context mContext;
		
		public MessageListAdapter(Context context){
			mContext = context;
		}
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mMessageItemArray.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			MessageListViewHolder mHolder;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.layout_message_item, null);
				mHolder = new MessageListViewHolder();
				mHolder.mAvatarView = (ImageView)convertView.findViewById(R.id.message_list_avatar);
				mHolder.mUserNameText = (TextView)convertView.findViewById(R.id.message_list_name);
				mHolder.mUserIdentifyText = (TextView)convertView.findViewById(R.id.message_list_identity);
				mHolder.mMessageDateText = (TextView)convertView.findViewById(R.id.message_list_date);
				mHolder.mMessageNum = (TextView)convertView.findViewById(R.id.message_list_message_num);
				mHolder.mMessageSnippetText = (TextView)convertView.findViewById(R.id.message_list_message_content);
				mHolder.mFirstLayout = (LinearLayout)convertView.findViewById(R.id.message_list_first);
				mHolder.mSecondLayout = (RelativeLayout)convertView.findViewById(R.id.message_list_second);
				convertView.setTag(mHolder);
			}else{
				mHolder = (MessageListViewHolder)convertView.getTag();
			}
			
			mHolder.mAvatarView.setImageResource(mMessageItemArray.get(position).mAvataResId);
			mHolder.mUserNameText.setText(mMessageItemArray.get(position).mName);
			mHolder.mMessageDateText.setText(mMessageItemArray.get(position).mLastUpdateTime);
			if(mMessageItemArray.get(position).mMessageNum <= 0){
				mHolder.mMessageNum.setVisibility(View.INVISIBLE);
			}else{
				mHolder.mMessageNum.setVisibility(View.VISIBLE);
				mHolder.mMessageNum.setText(""+mMessageItemArray.get(position).mMessageNum);
			}
			mHolder.mMessageSnippetText.setText(mMessageItemArray.get(position).mSnippet);
			mHolder.mUserIdentifyText.setText(mMessageItemArray.get(position).mIdentify);
			
			mHolder.mAvatarView.setOnClickListener(new OnItemChildClickListener(MESSAGE_ID_AVATRAR,position));
			mHolder.mFirstLayout.setOnClickListener(new OnItemChildClickListener(MESSAGE_ID_OTHERS,position));
			mHolder.mSecondLayout.setOnClickListener(new OnItemChildClickListener(MESSAGE_ID_OTHERS,position));
			mHolder.mMessageSnippetText.setOnClickListener(new OnItemChildClickListener(MESSAGE_ID_OTHERS,position));
			return convertView;
		}
		
	}
	
	public final class MessageListViewHolder{
		ImageView mAvatarView;
		TextView mUserNameText;
		TextView mUserIdentifyText;
		TextView mMessageDateText;
		TextView mMessageNum;
		TextView mMessageSnippetText;
		LinearLayout mFirstLayout;
		RelativeLayout mSecondLayout;
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
