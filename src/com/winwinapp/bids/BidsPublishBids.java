package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.AddSubLL;

public class BidsPublishBids extends ActionBarActivity implements OnClickListener{

	public static final int REFRESH = 1;
	public static final int REFRESH_EDIT = 2;
	private static final int REFRESH_EDIT_POST = 3;
	public static final int ACTIVITY_RESQUEST_CODE_DISTINCT = 1;
	public static final int ACTIVITY_RESQUEST_CODE_HOUSE_TYPE = 2;
	public static final int ACTIVITY_RESQUEST_CODE_HOUSE_AREA = 3;
	public static final int ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY = 4;
	public static final int ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_TYPE = 5;
	public static final int ACTIVITY_RESQUEST_CODE_HOUSE_BUDGET = 6;
	public static final int ACTIVITY_RESQUEST_CODE_EXPERIENCE = 7;
	TextView mPosition;
	TextView mbiotope_name;
	TextView mArea;
	TextView mHouseType;
	AddSubLL mHall;
	AddSubLL mRoom;
	AddSubLL mKitchen;
	AddSubLL mToilet;
	AddSubLL mBalcony;
	TextView mDecorateWay;
	TextView mDecorateStyle;
	TextView mBudget;
	EditText mRequirement;
	TextView mDesigner;
	boolean mSelDesigner = false;
	TextView mLabor;
	boolean mSelLabor = true;
	TextView mSuperior;
	boolean mSelSuperior = true;
	Button mPublish;
	
	NetworkData.BidPostEditData mPostEditData = NetworkData.getInstance().getNewBidPostEditData();
	NetworkData.BidPublishData mData = NetworkData.getInstance().getNewBidPublishData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	NetworkData.BidDetailData mEditData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.UserBidEditBack mEditBack = NetworkData.getInstance().getNewUserBidEditBack();
	int mType = 0;//0,发布招标;1,编辑招标
	LinearLayout mLL;
	boolean bRefreshEdit = false;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			String error;
			switch(msg.what){
			case REFRESH:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Intent intent = new Intent(BidsPublishBids.this,BidsDetailsActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(BidsPublishBids.this, "发布招标消息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case REFRESH_EDIT:
				String error1 = (String)msg.obj;
				if("OK".equals(error1)){
					bRefreshEdit = true;
					refreshContentOfEdit();
				}else{
					Toast.makeText(BidsPublishBids.this, "获取编辑招标信息失败："+error1, Toast.LENGTH_LONG).show();
				}
				
				break;
			case REFRESH_EDIT_POST:
				error = (String)msg.obj;
				if("OK".equals(error)){
					finish();
					Toast.makeText(BidsPublishBids.this, "更新招标成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(BidsPublishBids.this, "更新招标失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bids_publish);
		mType = getIntent().getIntExtra("type", 0);
		if(mType == 1){
			mEditData.bid_id = Integer.parseInt(getIntent().getStringExtra("bid_id"));
		}
		
		initActionBar();
		
		initView();
		
		if(mType == 1){
			new Thread(){
				public void run(){
					boolean success = HTTPPost.RequestUserBidEdit(mEditData, mEditBack);
					Message msg = Message.obtain();
					msg.what = REFRESH_EDIT;
					if(success){
						msg.obj = "OK";
					}else{
						msg.obj = mBack.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}
	}

	public void initView(){
		mLL = (LinearLayout)findViewById(R.id.bid_publish_ll);
		mPosition = (TextView)findViewById(R.id.bid_publish_position);
		mbiotope_name = (TextView)findViewById(R.id.bid_publish_biotope_name);
		mArea = (TextView)findViewById(R.id.bid_publish_area);
		mArea.setOnClickListener(this);
		mHouseType = (TextView)findViewById(R.id.bid_publish_house_type);
		mHouseType.setOnClickListener(this);
		mHall = (AddSubLL)findViewById(R.id.bid_publish_hall);	
		mRoom = (AddSubLL)findViewById(R.id.bid_publish_room);
		mKitchen = (AddSubLL)findViewById(R.id.bid_publish_kitchen);
		mToilet = (AddSubLL)findViewById(R.id.bid_publish_toilet);
		mBalcony = (AddSubLL)findViewById(R.id.bid_publish_balcony);
		mDecorateWay = (TextView)findViewById(R.id.bid_publish_decorate_way);
		mDecorateWay.setOnClickListener(this);
		mDecorateStyle = (TextView)findViewById(R.id.bid_publish_decorate_style);
		mDecorateStyle.setOnClickListener(this);
		mBudget = (TextView)findViewById(R.id.bid_publish_budget);
		mBudget.setOnClickListener(this);
		mRequirement = (EditText)findViewById(R.id.bid_publish_requirement);
		mDesigner = (TextView)findViewById(R.id.bid_publish_designer);
		mDesigner.setOnClickListener(this);
		mLabor = (TextView)findViewById(R.id.bid_publish_labor);
		mLabor.setOnClickListener(this);
		mSuperior = (TextView)findViewById(R.id.bid_publish_superior);
		mSuperior.setOnClickListener(this);
		mPublish = (Button)findViewById(R.id.bid_publish_publish);
		
		
		//mPosition.setEnabled(false);
		mPosition.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					Intent intent = new Intent(BidsPublishBids.this,BidsDistinctActivity.class);
					startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_DISTINCT);
				}
				
		});
		
		if(mType == 1){
			mPublish.setText("提交修改");
			mPublish.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					postEdit();
				}
				
			});
			mLL.setVisibility(View.GONE);
		}else{
			mPublish.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					publish();
				}
				
			});
		}
	}
	
	public void postEdit(){
		if(mType == 1){
			if(!bRefreshEdit){
				Toast.makeText(BidsPublishBids.this, "不能获取招标信息", Toast.LENGTH_LONG).show();
				return;
			}
			String area = mArea.getText().toString();
			mPostEditData.bid = Integer.parseInt(mEditBack.items.get(0).bid_id);
			area = area.replace("㎡", "");
			mPostEditData.area = Integer.parseInt(area);
			
			mPostEditData.budget = BudgetToIndex(mBudget.getText().toString());
			mPostEditData.decorate_way = DecorateWayToIndex(mDecorateWay.getText().toString());
			mPostEditData.design_style = DesignStyleToIndex(mDecorateStyle.getText().toString());
			//mData.city
			mPostEditData.biotope_name = mbiotope_name.getText().toString();
			mPostEditData.house_type = HouseTypeToIndex(mHouseType.getText().toString());
			mPostEditData.hall = mHall.getCurrentNumber();
			mPostEditData.room = mRoom.getCurrentNumber();
			mPostEditData.kitchen = mKitchen.getCurrentNumber();
			mPostEditData.toilet = mToilet.getCurrentNumber();
			mPostEditData.balcony = mBalcony.getCurrentNumber();
			mPostEditData.need_designer = mSelDesigner?1:0;
			mPostEditData.need_forman = mSelLabor?1:0;
			mPostEditData.need_supervisor = mSelSuperior?1:0;
			new Thread(){
				public void run(){
					boolean success = HTTPPost.PostUserBidEdit(mPostEditData,mBack);
					Message msg = Message.obtain();
					msg.what = REFRESH_EDIT_POST;
					if(success){
						msg.obj = "OK";
					}else{
						msg.obj = mBack.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}
	}
	
	public void refreshContentOfEdit(){
		mPosition.setText(mEditBack.items.get(0).postion);
		mbiotope_name.setText(mEditBack.items.get(0).biotope_name);
		mArea.setText(mEditBack.items.get(0).area+"㎡");
		mHouseType.setText(mEditBack.constHouseType.get(Integer.parseInt(mEditBack.items.get(0).house_type)));
		mDecorateWay.setText(mEditBack.constDecorateWay.get(Integer.parseInt(mEditBack.items.get(0).decorate_way)));
		mDecorateStyle.setText(mEditBack.constDesignerStyle.get(Integer.parseInt(mEditBack.items.get(0).design_style)));
		mBudget.setText(mEditBack.constBudget.get(Integer.parseInt(mEditBack.items.get(0).budget)));
		mRequirement.setText(mEditBack.items.get(0).requirement);
		mSelLabor = "1".equals(mEditBack.items.get(0).need_foreman);
		mLabor.setCompoundDrawablesWithIntrinsicBounds(mSelLabor? R.drawable.reg2_check_c:R.drawable.reg2_check, 0, 0, 0);
		mLabor.setTextColor(mSelLabor?getResources().getColor(R.color.green):getResources().getColor(R.color.black));
		mSelSuperior = "1".equals(mEditBack.items.get(0).need_supervisor);
		mSuperior.setCompoundDrawablesWithIntrinsicBounds(mSelSuperior? R.drawable.reg2_check_c:R.drawable.reg2_check, 0, 0, 0);
		mSuperior.setTextColor(mSelSuperior?getResources().getColor(R.color.green):getResources().getColor(R.color.black));
		mSelDesigner = "1".equals(mEditBack.items.get(0).need_designer);
		mDesigner.setCompoundDrawablesWithIntrinsicBounds(mSelDesigner? R.drawable.reg2_check_c:R.drawable.reg2_check, 0, 0, 0);
		mDesigner.setTextColor(mSelDesigner?getResources().getColor(R.color.green):getResources().getColor(R.color.black));
		mHall.setCurrentNumber("1");	
		mRoom.setCurrentNumber("1");
		mKitchen.setCurrentNumber("1");
		mToilet.setCurrentNumber("1");
		mBalcony.setCurrentNumber("1");
		mLL.setVisibility(View.VISIBLE);
	}
	
	public void publish(){
		String area = mArea.getText().toString();
		area = area.replace("㎡", "");
		mData.area = Integer.parseInt(area);
		
		mData.budget = BudgetToIndex(mBudget.getText().toString());
		mData.decorate_way = DecorateWayToIndex(mDecorateWay.getText().toString());
		mData.design_style = DesignStyleToIndex(mDecorateStyle.getText().toString());
		//mData.city
		mData.biotope_name = mbiotope_name.getText().toString();
		mData.house_type = HouseTypeToIndex(mHouseType.getText().toString());
		mData.hall = mHall.getCurrentNumber();
		mData.room = mRoom.getCurrentNumber();
		mData.kitchen = mKitchen.getCurrentNumber();
		mData.toilet = mToilet.getCurrentNumber();
		mData.balcony = mBalcony.getCurrentNumber();
		mData.need_designer = mSelDesigner?1:0;
		mData.need_forman = mSelLabor?1:0;
		mData.need_supervisor = mSelSuperior?1:0;
		
		mData.req = mRequirement.getText().toString();
		
		new PublishThread().start();
	}
	
	String[] mBudgetArray = {"2万以下","2-4万","4-6万","6-8万","8-10万","10-15万","15-20万","20-50万","50万以上"};
	String[] mDecorateWayArray = {"半包","全包","清包"};
	String[] mHouseTypeArray = {"普通住宅","别墅","商铺"};
	String [] mDesignStyle = {"欧美","古典","现代"};
	private int BudgetToIndex(String budget){
		int index = 0;
		for(String str:mBudgetArray){
			if(str.equals(budget)){
				return index;
			}
		}
		return 0;
	}
	
	private int HouseTypeToIndex(String house){
		int index = 0;
		for(String str:mHouseTypeArray){
			if(str.equals(house)){
				return index;
			}
		}
		return 0;
	}
	
	private String DecorateWayToIndex(String dec){
		int index = 0;
		for(String str:mDecorateWayArray){
			if(str.equals(dec)){
				return index+"";
			}
		}
		return "0";
	}
	
	private String DesignStyleToIndex(String style){
		int index = 0;
		for(String str:mDesignStyle){
			if(str.equals(style)){
				return index+"";
			}
		}
		return "0";
	}
	
	public class PublishThread extends Thread{
		public void run(){
			boolean success = HTTPPost.PublishBids(mData, mBack);
			Message msg = Message.obtain();
			msg.what = REFRESH;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView textView = new TextView(this);
		if(mType == 1){
			setTitle("招标编辑");
			textView.setText("完成");
			this.setRightView(textView);
			this.setOnRightClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					postEdit();
				}
				
			});
		}else{
			setTitle("发起招标");
			textView.setText("发布");
			this.setRightView(textView);
			this.setOnRightClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					publish();
				}
				
			});
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case ACTIVITY_RESQUEST_CODE_DISTINCT:
			String province = data.getStringExtra("province");
			String city = data.getStringExtra("city");
			String country = data.getStringExtra("country");
			mPosition.setText(province+" "+city+" "+ country);
			break;
		case ACTIVITY_RESQUEST_CODE_HOUSE_TYPE:
			mHouseType.setText(data.getStringExtra("data"));
			break;
		case ACTIVITY_RESQUEST_CODE_HOUSE_AREA:
			mArea.setText(data.getStringExtra("data")+"㎡");
			break;
		case ACTIVITY_RESQUEST_CODE_HOUSE_BUDGET:
			mBudget.setText(data.getStringExtra("data"));
			break;
		case ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_TYPE:
			mDecorateStyle.setText(data.getStringExtra("data"));
			break;
		case ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY:
			mDecorateWay.setText(data.getStringExtra("data"));
			break;
		}
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(view.getId()){
		case R.id.bid_publish_house_type:
			intent = new Intent(BidsPublishBids.this,BidsPopupActivity.class);
			intent.putExtra("type", ACTIVITY_RESQUEST_CODE_HOUSE_TYPE);
			startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_HOUSE_TYPE);
			break;
		case R.id.bid_publish_area:
			intent = new Intent(BidsPublishBids.this,BidsPopupActivity.class);
			intent.putExtra("type", ACTIVITY_RESQUEST_CODE_HOUSE_AREA);
			startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_HOUSE_AREA);
			break;
		case R.id.bid_publish_budget:
			intent = new Intent(BidsPublishBids.this,BidsPopupActivity.class);
			intent.putExtra("type", ACTIVITY_RESQUEST_CODE_HOUSE_BUDGET);
			startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_HOUSE_BUDGET);
			break;
		case R.id.bid_publish_decorate_style:
			intent = new Intent(BidsPublishBids.this,BidsPopupActivity.class);
			intent.putExtra("type", ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_TYPE);
			startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_TYPE);
			break;
		case R.id.bid_publish_decorate_way:
			intent = new Intent(BidsPublishBids.this,BidsPopupActivity.class);
			intent.putExtra("type", ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY);
			startActivityForResult(intent, ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY);
			break;
		case R.id.bid_publish_designer:
			if(mSelDesigner){
				mSelDesigner = false;
				mDesigner.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check, 0, 0, 0);
				mDesigner.setTextColor(getResources().getColor(R.color.black));
			}else{
				mSelDesigner = true;
				mDesigner.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check_c, 0, 0, 0);
				mDesigner.setTextColor(getResources().getColor(R.color.green));
			}
			break;
		case R.id.bid_publish_labor:
			if(mSelLabor){
				mSelLabor = false;
				mLabor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check, 0, 0, 0);
				mLabor.setTextColor(getResources().getColor(R.color.black));
			}else{
				mSelLabor = true;
				mLabor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check_c, 0, 0, 0);
				mLabor.setTextColor(getResources().getColor(R.color.green));
			}
			break;
		case R.id.bid_publish_superior:
			if(mSelSuperior){
				mSelSuperior = false;
				mSuperior.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check, 0, 0, 0);
				mSuperior.setTextColor(getResources().getColor(R.color.black));
			}else{
				mSelSuperior = true;
				mSuperior.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reg2_check_c, 0, 0, 0);
				mSuperior.setTextColor(getResources().getColor(R.color.green));
			}
			break;
		}
	}
}
