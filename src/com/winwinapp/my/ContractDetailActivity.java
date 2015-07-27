package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class ContractDetailActivity extends ActionBarActivity implements OnItemClickListener,OnClickListener{
	
	private final static int MESSAGE_AGREE_BACK = 1;
	int mCId = 0;
	GridView mGridView;
	ArrayList<String> mArrayList = new ArrayList<String>();
	MyContractDetailAdapter mAdapter;
	int mScreenWidth;
	Bitmap mScaleOut;
	Bitmap mTxtBg;
	Button mAgree;
	Button mReject;
	NetworkData.ContractAgreeRejectData mData = NetworkData.getInstance().getNewContractAgreeRejectData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	LinearLayout mLL;
	Button mModify;
	boolean bConfirm;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case MESSAGE_AGREE_BACK:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					ContractDetailActivity.this.finish();
				}else{
					Toast.makeText(ContractDetailActivity.this, "发送确认消息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid);

		mCId = getIntent().getIntExtra("id", 0);
		bConfirm = getIntent().getBooleanExtra("confirm", true);
		initActionBar();
		
		mGridView = (GridView)findViewById(R.id.grid_view_common);
		mArrayList.add("1");
		mArrayList.add("2");
		mArrayList.add("3");
		mAdapter = new MyContractDetailAdapter(this);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
		
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mScaleOut = BitmapFactory.decodeResource(getResources(), R.drawable.scale_out);
		mTxtBg = BitmapFactory.decodeResource(getResources(), R.drawable.my_project_greeen_bg);
		
		mAgree = (Button)findViewById(R.id.contract_detail_agree);
		mReject = (Button)findViewById(R.id.contract_detail_reject);
		
		mLL = (LinearLayout)findViewById(R.id.contract_detail_ok_reject_ll);
		mModify = (Button)findViewById(R.id.contract_detail_modify);
		if(bConfirm){
			mLL.setVisibility(View.GONE);
			mModify.setVisibility(View.GONE);
		}else{
			if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
				mModify.setVisibility(View.GONE);
				mAgree.setOnClickListener(this);
				mReject.setOnClickListener(this);
			}else{
				mLL.setVisibility(View.GONE);
				mModify.setOnClickListener(this);
			}
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("合同详情");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}
	
	public class MyContractDetailAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		public MyContractDetailAdapter(Context context){
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
			TextView title;
			ImageView image;
			arg1 = mInflater.inflate(R.layout.layout_grid_item_common, null);
			title = (TextView)arg1.findViewById(R.id.item_text);
			image = (ImageView)arg1.findViewById(R.id.item_image);
			title.setVisibility(View.GONE);
			Bitmap bm = getDrawable(arg0);
			image.setImageBitmap(bm);
			
			return arg1;
		}
		
	}
	
	public Bitmap getDrawable(int index){
		final BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bm = null;
		String text = "";
		if(index == 0){
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.contract_detail_1, options);
			text = "第1页";
		}else if(index == 1){
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.contract_detail_2, options);
			text = "第2页";
		}else if(index == 2){
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.contract_detail_3, options);
			text = "第3页";
		}
        int mPicWidth = options.outWidth;
        int mPicHeight = options.outHeight;
        int textPadding = 5;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        Rect txtBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), txtBounds);
        int destWidth = (mScreenWidth - 30)/2;
        int destHeight = mPicHeight * destWidth / mPicWidth;
        Bitmap des = Bitmap.createBitmap(destWidth, destHeight, bm.getConfig());
		Canvas canvas = new Canvas(des);
		Paint paint1 = new Paint();
		ColorMatrix cMatrix = new ColorMatrix();  
        cMatrix.set(new float[] { 1, 0, 0, 0, -90, 0, 1,  
                0, 0, -90,// 改变亮度  
                0, 0, 1, 0, -90, 0, 0, 0, 1, 0 });
        paint1.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        canvas.drawBitmap(bm, null, new Rect(0,0,des.getWidth(),des.getHeight()),paint1);
        canvas.drawBitmap(mScaleOut, null, new Rect(des.getWidth()-mScaleOut.getWidth(),des.getHeight()-mScaleOut.getHeight(),des.getWidth(),des.getHeight()),null);
        canvas.drawBitmap(mTxtBg, null,new Rect(10,0,10+txtBounds.width()+textPadding*2,txtBounds.height()+textPadding*2),null);
        canvas.drawText(text, 10+textPadding,txtBounds.height()/2+textPadding*2, paint);
        
        return des;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(this,ContractBigPicActivity.class);
		intent.putExtra("page", arg2);
		intent.putExtra("type", 3+arg2);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(view.getId()){
		case R.id.contract_detail_agree:
			new SendAgreeThread().start();
			break;
		case R.id.contract_detail_reject:
			intent = new Intent(this,ContractRejectActivity.class);
			intent.putExtra("id", mCId);
			startActivity(intent);
			break;
		case R.id.contract_detail_modify:
			if(KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR ){
				intent = new Intent(ContractDetailActivity.this,ContractLaborActivity.class);
				intent.putExtra("type", 1);//view contract
				intent.putExtra("id", mCId);
				startActivity(intent);
			}else if(KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
				intent = new Intent(ContractDetailActivity.this,ContractSuperiorActivity.class);
				intent.putExtra("type", 1);//view contract
				intent.putExtra("id", mCId);
				startActivity(intent);
			}else if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER){
				intent = new Intent(ContractDetailActivity.this,ContractDesignerActivity.class);
				intent.putExtra("type", 1);//view contract
				intent.putExtra("id", mCId);
				startActivity(intent);
			}
			
			break;
		}
	}
	
	public class SendAgreeThread extends Thread{
		public void run(){
			mData.c_id = mCId;
			boolean success = HTTPPost.SendMyContractReject(mData, mBack);
			Message msg = Message.obtain();
			msg.what = MESSAGE_AGREE_BACK;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
}
