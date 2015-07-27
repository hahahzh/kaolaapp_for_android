package com.winwinapp.koala;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.designer.ContactDesignerActivity;
import com.winwinapp.designer.DesignerActivity;
import com.winwinapp.koala.SlideListView.RemoveDirection;
import com.winwinapp.koala.SlideListView.RemoveListener;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

public class MessageListActivity extends ActionBarActivity implements RemoveListener{

	private static final int MESSAGE_ID_AVATRAR = 0;
	private static final int MESSAGE_ID_OTHERS = 1;
	private static final int MESSAGE_INVALIDATE_LIST = 2;
	private static final int MESSAGE_DELETE_PUBLIC_MSG = 3;
	private static final int MESSAGE_DELETE_PRIVATE_MSG = 4;
	private SlideListView mMessageListView;
	private ArrayList<MessageItem> mMessageItemArray = new ArrayList<MessageItem>();
	private LayoutInflater mInflater;
	MessageListAdapter mMessageAdapter;
	KoalaApplication mApp;
	public static NetworkData.PublicMessageListData mRequestPublicData;
	public static NetworkData.PublicMessageListBack mRequestPublicBack;
	
	public static NetworkData.PrivateMessageListData mRequestPrivateData;
	public static NetworkData.PrivateMessageListBack mRequestPrivateBack;
	
	Drawable mDefaultAvatar;
	
	private int type = 0;//0.public; 1, private
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			Intent intent;
			String error;
			switch(msg.what){
			case MESSAGE_ID_AVATRAR:
				//Toast.makeText(MessageListActivity.this, "avatar:"+(int)msg.arg1, Toast.LENGTH_SHORT).show();
				intent = new Intent(MessageListActivity.this,DesignerActivity.class);
				intent.putExtra("type", fragment_homepage.TYPE_DESIGNER);
				startActivity(intent);
				break;
			case MESSAGE_ID_OTHERS:
				intent = new Intent(MessageListActivity.this,KoalaChatActivity.class);
				int position = msg.arg1;
				intent.putExtra("type", type);//0,ϵͳ��Ϣ;1,˽��
				if(type == 0){
					intent.putExtra("msg_id", mRequestPublicBack.items.get(position).msg_id);
					intent.putExtra("is_exist", mRequestPublicBack.items.get(position).is_exist);
				}else{
					intent.putExtra("msg_id", mRequestPrivateBack.items.get(position).msg_id);
					intent.putExtra("topic_id", mRequestPrivateBack.items.get(position).topic_id);
					intent.putExtra("rec_id", mRequestPrivateBack.items.get(position).rec_id);
				}
				startActivity(intent);
				break;
			case MESSAGE_INVALIDATE_LIST:
				error = (String)msg.obj;
				if("OK".equals(error)){
					//Toast.makeText(MessageListActivity.this, "OK received", Toast.LENGTH_LONG).show();
					//mMessageListView.invalidate();
					mMessageAdapter.notifyDataSetChanged();
				}
				else{
					Toast.makeText(MessageListActivity.this, "��ȡ��Ϣ�б�ʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_DELETE_PUBLIC_MSG:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(MessageListActivity.this, "ɾ��������Ϣ�ɹ�", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(MessageListActivity.this, "ɾ��������Ϣʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_DELETE_PRIVATE_MSG:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(MessageListActivity.this, "ɾ��˽����Ϣ�ɹ�"+error, Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(MessageListActivity.this, "ɾ��˽����Ϣʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list_slide);
		
		type = getIntent().getIntExtra("type", 0);
		
		mApp = (KoalaApplication) this.getApplication();
		initActionBar();
		initMessageList();
		
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("��Ϣ�б�");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}
	
	public void initMessageList(){
		mMessageListView = (SlideListView)findViewById(R.id.slideCutListView);
		mMessageListView.setRemoveListener(this);
		mInflater = LayoutInflater.from(this);
		mMessageAdapter = new MessageListAdapter(this);
		mMessageListView.setAdapter(mMessageAdapter);
		
		if(type == 0){
			mRequestPublicData = NetworkData.getInstance().getNewPublicMessageListData();
			mRequestPublicData.sessid = mApp.getSession();
			mRequestPublicBack = NetworkData.getInstance().getNewPublicMessageListBack();
		}else{
			mRequestPrivateData = NetworkData.getInstance().getNewPrivateMessageListData();
			mRequestPrivateData.limit = 5;
			mRequestPrivateBack = NetworkData.getInstance().getNewPrivateMessageListBack();
		}
		
		if(!TextUtils.isEmpty(mApp.getSession())){
			new Thread(){
				public void run(){
					boolean success = false;
					Message msg = Message.obtain();
					msg.what = MESSAGE_INVALIDATE_LIST;
					if(type == 0){//public message
						success = HTTPPost.RequestPublicMsgList(mRequestPublicData, mRequestPublicBack);
						if(success){
							for(int i=0;i<mRequestPublicBack.total;i++){
								NetworkData.PublicMessageListItem backItem = mRequestPublicBack.items.get(i);
								MessageItem item = new MessageItem("�ų�","���ʦ","2015-03-19",2,R.drawable.avatar1,"����ľ���Ѿ��͵������칤������װ�������ĵȴ�");
								item.mName = "ϵͳ��Ϣ";
								item.mIdentify = fragment_homepage.getIdetifyStringFromID(Integer.parseInt(backItem.user_type));
								item.mLastUpdateTime = backItem.send_time;
								item.mSnippet = backItem.content;
								mMessageItemArray.add(item);
							}
							msg.obj = (Object)("OK");
						}else{
							msg.obj = mRequestPublicBack.error;
						}
					}else{//private message
						success = HTTPPost.RequestPrivateMsgList(mRequestPrivateData, mRequestPrivateBack);
						if(success){
							for(int i=0;i<mRequestPrivateBack.total;i++){
								NetworkData.PrivateMessageListItem backItem = mRequestPrivateBack.items.get(i);
								MessageItem item = new MessageItem("�ų�","���ʦ","2015-03-19",2,R.drawable.avatar1,"����ľ���Ѿ��͵������칤������װ�������ĵȴ�");
								item.mName = backItem.send_name;
								item.mIdentify = fragment_homepage.getIdetifyStringFromID(Integer.parseInt(backItem.user_type));
								item.mLastUpdateTime = backItem.send_time;
								item.mSnippet = backItem.content;
								item.mMessageNum = Integer.parseInt(backItem.reply_num);
								Bitmap bmp;
								try {
									bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+backItem.avatar).openStream());
									Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,mDefaultAvatar.getIntrinsicWidth(), mDefaultAvatar.getIntrinsicHeight(), true);
									item.avatar = thumbBmp;
									item.mAvataResId = -1;
								} catch (MalformedURLException e) {
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								} catch (IOException e) {
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								}
								mMessageItemArray.add(item);
							}
							msg.obj = (Object)("OK");
						}else{
							msg.obj = mRequestPrivateBack.error;
						}
					}
					mHandler.sendMessage(msg);
					/*
					MessageItem item = new MessageItem("�ų�","���ʦ","2015-03-19",2,R.drawable.avatar1,"����ľ���Ѿ��͵������칤������װ�������ĵȴ�");
					mMessageItemArray.add(item);
					item = new MessageItem("������","����","2015-02-23",6,R.drawable.avatar1,"����ľ���Ѿ��͵������칤������װ�������ĵȴ�");
					mMessageItemArray.add(item);
					item = new MessageItem("����","����","2015-01-23",3,R.drawable.avatar1,"���̽����һ������ѿء�");
					mMessageItemArray.add(item);
					item = new MessageItem("����","ҵ��","2015-01-23",0,R.drawable.avatar1,"���̽����һ������ѿء�");
					mMessageItemArray.add(item);*/
				};
			
			}.start();
			
			//mMessageListView.on
		}else{
			Toast.makeText(this, "���ȵ�¼�ٲ�ѯϵͳ��Ϣ", Toast.LENGTH_LONG).show();
		}
	}
	
	public class MessageListAdapter extends BaseAdapter{
		private Context mContext;
		
		public MessageListAdapter(Context context){
			mContext = context;
		}
		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			return mMessageItemArray.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
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
			
			if(mMessageItemArray.get(position).mAvataResId == -1){
				mHolder.mAvatarView.setImageBitmap(mMessageItemArray.get(position).avatar);
			}else{
				mHolder.mAvatarView.setImageResource(mMessageItemArray.get(position).mAvataResId);
			}
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
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			mHandler.sendMessage(msg);
		}
		
	}

	@Override
	public void removeItem(RemoveDirection direction, final int position) {
		// TODO �Զ����ɵķ������
		mMessageItemArray.remove(position);
		mMessageAdapter.notifyDataSetChanged();
		if(type == 0){//public 
			new Thread(){
				public void run(){
					NetworkData.DeletePublicMsgData data = NetworkData.getInstance().getNewDeletePublicMsgData();
					NetworkData.CommonBack back = NetworkData.getInstance().getCommonBack();
					data.id = Integer.parseInt(mRequestPublicBack.items.get(position).id);
					data.is_exist = Integer.parseInt(mRequestPublicBack.items.get(position).is_exist);
					data.rec_id = Integer.parseInt(mRequestPublicBack.items.get(position).rec_id);
					boolean success = HTTPPost.DeletePublicMsg(data, back);
					Message msg = Message.obtain();
					msg.what = MESSAGE_DELETE_PUBLIC_MSG;
					if(success){
						msg.obj = (Object)("OK");
					}else{
						msg.obj = back.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}else{
			new Thread(){
				public void run(){
					NetworkData.DeletePrivateMsgData data = NetworkData.getInstance().getNewDeletePrivateMsgData();
					NetworkData.CommonBack back = NetworkData.getInstance().getCommonBack();
					Integer iMsg =  Integer.valueOf(Integer.parseInt(mRequestPrivateBack.items.get(position).id));
					data.mIds.add(iMsg);
					boolean success = HTTPPost.DeletePrivateMsg(data, back);
					Message msg = Message.obtain();
					msg.what = MESSAGE_DELETE_PRIVATE_MSG;
					if(success){
						msg.obj = (Object)("OK");
					}else{
						msg.obj = back.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}
		//Toast.makeText(this, "delete item " + position, Toast.LENGTH_LONG).show();
	}
}
