package com.winwinapp.bids;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.designer.DesignerActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.koala.MessageListActivity.OnItemChildClickListener;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class BidsDetailsActivity extends ActionBarActivity implements OnClickListener{

	private static final int DETAIL_INVALID = 1;
	private static final int MESSAGE_BIDS = 2;
	private static final int MESSAGE_ABORT_BID = 3;
	private static final int MESSAGE_SELECT = 4;
	private static final int MESSAGE_LEAVE_MSG = 5;
	public String bid_id;
	NetworkData.BidDetailData mData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.BidListBack mBack = NetworkData.getInstance().getNewBidListBack();
	NetworkData.UserBidDetailBack mUserBack = NetworkData.getInstance().getNewUserBidDetailBack();
	
	NetworkData.BidAbortData mBidAbortData;
	NetworkData.BidVieData mBidData;
	NetworkData.CommonBack mCommonBack;
	LinearLayout mLL;
	TextView mName;
	TextView mLocation;
	TextView mPublicTime;
	TextView mArea;
	TextView mBudget;
	TextView mDecorate_style;
	TextView mHouse_type;
	TextView mSpaceType;
	TextView mDecorateWay;
	TextView mBidNumber;
	TextView mNeedDesigner;
	TextView mNeedLabor;
	TextView mNeedSuperior;
	TextView mRequirement;
	LinearLayout mBidLL;
	LinearLayout mBidMyLL;
	Button mAbortBid;
	Button mBid;
	TextView mTitleDesigner;
	TextView mTitleLabor;
	TextView mTitleSuperior;
	ListView mBidderList;
	MyAdapter mBidderAdapter;
	LayoutInflater mInflater;
	EditText mBidMsg;
	int mType = 0;//0:���꣬ 1���ҵľ��꣬���Ա༭
	int mCurrentTitle = 0;
	static int ForTest = 0;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			String error;
			int position;
			NetworkData.UserBidDetailBidders item = null;
			switch(msg.what){
			case DETAIL_INVALID:
				error = (String)msg.obj;
				if("OK".equals(error)){
					//mAdapter.notifyDataSetChanged();
					if(mType == 0){
						mName.setText(mBack.items.get(0).biotope_name);
						mLocation.setText(mBack.items.get(0).postion);
						mPublicTime.setText(mBack.items.get(0).publish_time);
						mArea.setText(mBack.items.get(0).area + "�O");
						mBudget.setText(mBack.items.get(0).budget);
						mDecorate_style.setText(mBack.items.get(0).design_style);
						mHouse_type.setText(mBack.items.get(0).house_type);
						mSpaceType.setText(mBack.items.get(0).space_type);
						mDecorateWay.setText(mBack.items.get(0).decorate_way);
						mBidNumber.setText(mBack.items.get(0).bid_number);
						if(!"1".equals(mBack.items.get(0).need_designer)){
							mNeedDesigner.setVisibility(View.GONE);
						}
						if(!"1".equals(mBack.items.get(0).need_foreman)){
							mNeedLabor.setVisibility(View.GONE);
						}
						if(!"1".equals(mBack.items.get(0).need_supervisor)){
							mNeedSuperior.setVisibility(View.GONE);
						}
						mRequirement.setText(mBack.items.get(0).requirement);
						mBidLL.setVisibility(View.VISIBLE);
						mBidMyLL.setVisibility(View.GONE);
					}else{
						mName.setText(mUserBack.bid_info.biotope_name);
						mLocation.setText(mUserBack.bid_info.postion);
						mPublicTime.setText(mUserBack.bid_info.publish_time);
						mArea.setText(mUserBack.bid_info.area + "�O");
						mBudget.setText(mUserBack.bid_info.budget);
						mDecorate_style.setText(mUserBack.bid_info.design_style);
						mHouse_type.setText(mUserBack.bid_info.house_type);
						mSpaceType.setText(mUserBack.bid_info.space_type);
						mDecorateWay.setText(mUserBack.bid_info.decorate_way);
						mBidNumber.setText(mUserBack.bid_info.bid_number);
						if(!"1".equals(mUserBack.bid_info.need_designer)){
							mNeedDesigner.setVisibility(View.GONE);
						}
						if(!"1".equals(mUserBack.bid_info.need_foreman)){
							mNeedLabor.setVisibility(View.GONE);
						}
						if(!"1".equals(mUserBack.bid_info.need_supervisor)){
							mNeedSuperior.setVisibility(View.GONE);
						}
						mRequirement.setText(mUserBack.bid_info.requirement);
						mBidLL.setVisibility(View.GONE);
						mBidMyLL.setVisibility(View.VISIBLE);
						mInflater = LayoutInflater.from(BidsDetailsActivity.this);
						mBidderAdapter = new MyAdapter();
						mBidderList.setAdapter(mBidderAdapter);
						
						mTitleDesigner.setText("���ʦ("+mUserBack.designers.size()+")");
						mTitleLabor.setText("����("+mUserBack.labors.size()+")");
						mTitleSuperior.setText("����("+mUserBack.superiors.size()+")");
					}
					mLL.setVisibility(View.VISIBLE);
				}else{
					Toast.makeText(BidsDetailsActivity.this, "��ȡ�б���Ϣ����"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_BIDS:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(BidsDetailsActivity.this, "Ͷ��ɹ���", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(BidsDetailsActivity.this, "Ͷ�귢������"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_ABORT_BID:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(BidsDetailsActivity.this, "��ֹ�б�ɹ���", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(BidsDetailsActivity.this, "��ֹ�б귢������"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_SELECT:
				position = msg.arg1;
				ImageView image = (ImageView) msg.obj;
				String checkedId = "";
				String selectType = "";
				if(mType == 1){
					if(mCurrentTitle == 0){
						item = mUserBack.designers.get(position);
						checkedId = mUserBack.des_uid;
						selectType = "���ʦ-";
					}else if(mCurrentTitle == 1){
						item = mUserBack.labors.get(position);
						checkedId = mUserBack.frm_uid;
						selectType = "����-";
					}else if(mCurrentTitle == 2){
						item = mUserBack.superiors.get(position);
						checkedId = mUserBack.sup_uid;
						selectType = "����-";
					}
					
					if( (item != null) && !checkedId.equals(item.uid)){
						String displayMsg = "ѡ��\""+selectType+item.username+"\"Ϊ��������";
						showDialog(image,displayMsg);
					}
				}
				
				
				break;
			case MESSAGE_LEAVE_MSG:
				position = msg.arg1;
				if(mType == 1){
					if(mCurrentTitle == 0){
						item = mUserBack.designers.get(position);
						checkedId = mUserBack.des_uid;
					}else if(mCurrentTitle == 1){
						item = mUserBack.labors.get(position);
						checkedId = mUserBack.frm_uid;
					}else if(mCurrentTitle == 2){
						item = mUserBack.superiors.get(position);
						checkedId = mUserBack.sup_uid;
					}
					
					Intent intent = new Intent(BidsDetailsActivity.this,KoalaChatActivity.class);
					intent.putExtra("type", 1);
					intent.putExtra("msg_id", "54");
					intent.putExtra("topic_id", "21");
					intent.putExtra("rec_id", "5");
					startActivity(intent);
				}
				break;
			}
		}
	};
	
	protected void showDialog(final ImageView image,String msg) {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage(msg); 
		  builder.setTitle("��ʾ");  
		  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
          	  dialoginterface.dismiss();
            }
			  });
		  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
            	image.setImageResource(R.drawable.reg2_check_c);
          	  	dialoginterface.dismiss();
            }
			  });
		  builder.create().show();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bids_details);
		
		mType = getIntent().getIntExtra("type", 0);

		initActionBar();
		
		bid_id = getIntent().getStringExtra("bid_id");
		if(bid_id != null){
			new Thread(){
				public void run(){
					mData.bid_id = Integer.parseInt(bid_id);
					boolean success;
					if(mType == 0){
						success = HTTPPost.RequestBidDetail(mData, mBack);
					}else{
						success = HTTPPost.RequestUserBidDetail(mData, mUserBack);
					}
					Message msg = Message.obtain();
					msg.what = DETAIL_INVALID;
					if(success){
						msg.obj = "OK";
					}else{
						if(mType == 0){
							msg.obj = mBack.error;
						}else{
							msg.obj = mUserBack.error;
						}
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}
		
		mLL = (LinearLayout)findViewById(R.id.bid_detail_ll);
		mName = (TextView)findViewById(R.id.bid_detail_biotope_name);
		mLocation = (TextView)findViewById(R.id.bid_detail_position);
		mPublicTime = (TextView)findViewById(R.id.bid_detail_publish_time);
		mArea = (TextView)findViewById(R.id.bid_detail_area);
		mBudget = (TextView)findViewById(R.id.bid_detail_budget);
		mDecorate_style = (TextView)findViewById(R.id.bid_detail_decorate_style);
		mHouse_type = (TextView)findViewById(R.id.bid_detail_house_type);
		mSpaceType = (TextView)findViewById(R.id.bid_detail_space_type);
		mDecorateWay = (TextView)findViewById(R.id.bid_detail_decorate_way);
		mBidNumber = (TextView)findViewById(R.id.bid_detail_bid_number);
		mNeedDesigner = (TextView)findViewById(R.id.bid_detail_designer);
		mNeedLabor = (TextView)findViewById(R.id.bid_detail_labor);
		mNeedSuperior = (TextView)findViewById(R.id.bid_detail_superior);
		mRequirement = (TextView)findViewById(R.id.bid_detail_requirement);
		mAbortBid = (Button)findViewById(R.id.bid_detail_abort_bid);
		mAbortBid.setOnClickListener(this);
		mBid = (Button)findViewById(R.id.bid_detail_bid);
		mBid.setOnClickListener(this);
		mTitleDesigner = (TextView)findViewById(R.id.bid_detail_title_designer);
		mTitleDesigner.setOnClickListener(this);
		mTitleLabor = (TextView)findViewById(R.id.bid_detail_title_labor);
		mTitleLabor.setOnClickListener(this);
		mTitleSuperior = (TextView)findViewById(R.id.bid_detail_title_superior);
		mTitleSuperior.setOnClickListener(this);
		mBidderList = (ListView)findViewById(R.id.bid_detail_list_bidder);
		mBidMsg = (EditText)findViewById(R.id.bid_details_bid_msg);
		
		mBidLL = (LinearLayout)findViewById(R.id.bid_detail_ll_bid);
		mBidMyLL = (LinearLayout)findViewById(R.id.bid_detail_ll_mybid);
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("�б�����");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
		
		imageView = new ImageView(this);
		if(mType == 0){
			imageView.setImageResource(R.drawable.avatar1);
		}else{
			imageView.setImageResource(R.drawable.edit);
			this.setOnRightClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO �Զ����ɵķ������
					//finish();
					Intent intent = new Intent(BidsDetailsActivity.this,BidsPublishBids.class);
					intent.putExtra("type", 1);
					intent.putExtra("bid_id", mUserBack.bid_info.bid_id);
					//intent.putExtra("bid_id", "17");//need to delete, only for test
					startActivity(intent);	
				}
				
			});
		}
		setRightView(imageView);	
	}
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			int cnt = 0;
			if(mType == 1){
				if(mCurrentTitle == 0){
					cnt = mUserBack.designers.size();
				}else if(mCurrentTitle == 1){
					cnt = mUserBack.labors.size();
				}else if(mCurrentTitle == 2){
					cnt = mUserBack.superiors.size();
				}
			}
			return cnt;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO �Զ����ɵķ������
			convertView = mInflater.inflate(R.layout.layout_bids_detail_bidder_item, null);
			NetworkData.UserBidDetailBidders item = null ;//= mUserBack.designers.get(position);
			ImageView imageCheck = (ImageView)convertView.findViewById(R.id.bid_detail_bidder_check);
			TextView txtMsg = (TextView)convertView.findViewById(R.id.bid_detail_bidder_leave_message);
			ImageView imageMsg = (ImageView)convertView.findViewById(R.id.bid_detail_bidder_message);
			TextView name = (TextView)convertView.findViewById(R.id.bid_detail_bidder_avatar_name);
			TextView BidTime = (TextView)convertView.findViewById(R.id.bid_detail_bidder_bid_time);
			TextView  rank = (TextView)convertView.findViewById(R.id.bid_detail_bidder_rank);
			TextView special = (TextView)convertView.findViewById(R.id.bid_detail_bidder_special);
			TextView SelfIntroduce = (TextView)convertView.findViewById(R.id.bid_detail_bidder_self_introduce);
			String checkedId = "";
			if(mType == 1){
				if(mCurrentTitle == 0){
					item = mUserBack.designers.get(position);
					checkedId = mUserBack.des_uid;
				}else if(mCurrentTitle == 1){
					item = mUserBack.labors.get(position);
					checkedId = mUserBack.frm_uid;
				}else if(mCurrentTitle == 2){
					item = mUserBack.superiors.get(position);
					checkedId = mUserBack.sup_uid;
				}
				
				if(checkedId.equals(item.uid)){
					imageCheck.setImageResource(R.drawable.reg2_check_c);
				}
				imageCheck.setOnClickListener(new OnItemChildClickListener(MESSAGE_SELECT,position,imageCheck));
				OnItemChildClickListener msgLis = new OnItemChildClickListener(MESSAGE_LEAVE_MSG,position,null);
				imageMsg.setOnClickListener(msgLis);
				txtMsg.setOnClickListener(msgLis);
				name.setText(item.username);
				BidTime.setText(item.datetime);
				rank.setText("רҵ��"+item.rate_avg+"    ����"+item.attud_avg);
				SelfIntroduce.setText("�Լ����ܣ�\n"+item.msg);
			}
			
			return convertView;
		}
		
	}

	public class OnItemChildClickListener implements View.OnClickListener{
		private int mClickIndex;
		private int mPosition;
		private ImageView mImage;
		
		public OnItemChildClickListener(int clickIndex, int position,ImageView image){
			mClickIndex = clickIndex;
			mPosition = position;
			mImage = image;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			msg.obj = mImage;
			mHandler.sendMessage(msg);
		}
		
	}
	
	public class AbortBidThread extends Thread{
		public void run(){
			mBidAbortData = NetworkData.getInstance().getNewBidAbortData();
			mCommonBack = NetworkData.getInstance().getCommonBack();
			mBidAbortData.bid = Integer.parseInt(bid_id);
			boolean success;
			Message msg = Message.obtain();
			msg.what = BidsDetailsActivity.MESSAGE_ABORT_BID;
			success = HTTPPost.RequestBidAbort(mBidAbortData,mCommonBack);
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mCommonBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class BidThread extends Thread{
		public void run(){
			mBidData = NetworkData.getInstance().getNewBidVieData();
			mCommonBack = NetworkData.getInstance().getCommonBack();
			mBidData.bidid = Integer.parseInt(bid_id);
			mBidData.bidmsg = mBidMsg.getText().toString();
			boolean success;
			Message msg = Message.obtain();
			msg.what = BidsDetailsActivity.MESSAGE_BIDS;
			success = HTTPPost.RequestBid(mBidData,mCommonBack);
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mCommonBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
		switch(arg0.getId()){
		case R.id.bid_detail_abort_bid:
			if(!KoalaApplication.isUserLogin()){
				Toast.makeText(this, "���ȵ�¼!", Toast.LENGTH_SHORT).show();
			}else{
				if(KoalaApplication.mUserType != fragment_homepage.TYPE_OWER){
					Toast.makeText(this, "ֻ��ҵ��������ֹͶ��", Toast.LENGTH_SHORT).show();
				}else{
					new AbortBidThread().start();
				}
			}
			break;
		case R.id.bid_detail_bid:
			if(!KoalaApplication.isUserLogin()){
				Toast.makeText(this, "���ȵ�¼!", Toast.LENGTH_SHORT).show();
			}else{
				if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
					Toast.makeText(this, "ҵ������Ͷ��", Toast.LENGTH_SHORT).show();
				}else{
					new BidThread().start();
				}
			}
			break;
		case R.id.bid_detail_title_designer:
			mTitleDesigner.setBackgroundResource(R.drawable.layout_border_darkgray);
			mTitleDesigner.setTextColor(0xFFFFFFFF);
			mTitleLabor.setBackgroundResource(R.drawable.layout_border);
			mTitleLabor.setTextColor(0xFF000000);
			mTitleSuperior.setBackgroundResource(R.drawable.layout_border);
			mTitleSuperior.setTextColor(0xFF000000);
			mCurrentTitle = 0;
			mBidderAdapter.notifyDataSetChanged();
			break;
		case R.id.bid_detail_title_labor:
			mTitleLabor.setBackgroundResource(R.drawable.layout_border_darkgray);
			mTitleLabor.setTextColor(0xFFFFFFFF);
			mTitleDesigner.setBackgroundResource(R.drawable.layout_border);
			mTitleDesigner.setTextColor(0xFF000000);
			mTitleSuperior.setBackgroundResource(R.drawable.layout_border);
			mTitleSuperior.setTextColor(0xFF000000);
			mCurrentTitle = 1;
			mBidderAdapter.notifyDataSetChanged();
			break;
		case R.id.bid_detail_title_superior:
			mTitleSuperior.setBackgroundResource(R.drawable.layout_border_darkgray);
			mTitleSuperior.setTextColor(0xFFFFFFFF);
			mTitleLabor.setBackgroundResource(R.drawable.layout_border);
			mTitleLabor.setTextColor(0xFF000000);
			mTitleDesigner.setBackgroundResource(R.drawable.layout_border);
			mTitleDesigner.setTextColor(0xFF000000);
			mCurrentTitle = 2;
			mBidderAdapter.notifyDataSetChanged();
			break;
		}
	}
}
