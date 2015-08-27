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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	LinearLayout mContractPreviewLL;
	ImageView mDesigner;
	ImageView mLabor;
	ImageView mSuperior;
	int mUserType;
	
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
		setContentView(R.layout.layout_contract_list);

		initActionBar();
		initListView();
		initView();
	}
	
	public void initView(){
		String text = "设计师合同";
		//mText.setBackgroundResource(R.drawable.my_project_greeen_bg);
		mContractPreviewLL = (LinearLayout)findViewById(R.id.contract_list_preview_ll);
		mUserType = KoalaApplication.mUserType;
		if(mUserType == fragment_homepage.TYPE_OWER){
			mDesigner = (ImageView)findViewById(R.id.contract_list_designer);
			mLabor = (ImageView)findViewById(R.id.contract_list_labor);
			mSuperior = (ImageView)findViewById(R.id.contract_list_superior);
			DisplayMetrics dm = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int ScreenWidth = dm.widthPixels;
			final BitmapFactory.Options options = new BitmapFactory.Options();
	        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.contract_preview, options);
	        int mPicWidth = options.outWidth;
	        int mPicHeight = options.outHeight;
	        Bitmap txtBg = BitmapFactory.decodeResource(getResources(), R.drawable.my_project_greeen_bg);
	        Bitmap scaleOut = BitmapFactory.decodeResource(getResources(), R.drawable.scale_out);
	        int textPadding = 5;
	        int destWidth = (ScreenWidth - 40)/3;
	        int destHeight = mPicHeight * destWidth / mPicWidth;
	        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        paint.setColor(Color.WHITE);
	        paint.setTextSize(20);
	        Rect txtBounds = new Rect();
	        paint.getTextBounds(text, 0, text.length(), txtBounds);
	        
	        Bitmap des = Bitmap.createBitmap(destWidth, destHeight+txtBounds.height() + textPadding*2, bm.getConfig());
			Canvas canvas = new Canvas(des);
			Paint paint1 = new Paint();
			ColorMatrix cMatrix = new ColorMatrix();  
	        cMatrix.set(new float[] { 1, 0, 0, 0, -90, 0, 1,  
	                0, 0, -90,// 改变亮度  
	                0, 0, 1, 0, -90, 0, 0, 0, 1, 0 });
	        paint1.setColorFilter(new ColorMatrixColorFilter(cMatrix));
			canvas.drawBitmap(bm, null, new Rect(0,txtBounds.height()+textPadding,des.getWidth(),des.getHeight()),paint1);
			canvas.drawBitmap(txtBg, null,new Rect((destWidth-txtBounds.width())/2-textPadding,0,(destWidth+txtBounds.width())/2+textPadding,txtBounds.height()+textPadding*2),null);
			canvas.drawBitmap(scaleOut, null, new Rect(des.getWidth()-scaleOut.getWidth(),des.getHeight()-scaleOut.getHeight(),des.getWidth(),des.getHeight()),null);
			
			Bitmap des1 = Bitmap.createBitmap(des);
			Canvas canvas1 = new Canvas(des1);
			canvas1.drawText(text, (destWidth-txtBounds.width())/2,txtBounds.height()+textPadding, paint);
			mDesigner.setImageBitmap(des1);
			Bitmap des2 = Bitmap.createBitmap(des);
			Canvas canvas2 = new Canvas(des2);
			text = "工长合同";
			canvas2.drawText(text, (destWidth-txtBounds.width()*4/5)/2,txtBounds.height()+textPadding, paint);
			mLabor.setImageBitmap(des2);
			Bitmap des3 = Bitmap.createBitmap(des);
			Canvas canvas3 = new Canvas(des3);
			text = "监理合同";
			canvas3.drawText(text, (destWidth-txtBounds.width()*4/5)/2,txtBounds.height()+textPadding, paint);
			mSuperior.setImageBitmap(des3);
			
			mDesigner.setOnClickListener(this);
			mLabor.setOnClickListener(this);
			mSuperior.setOnClickListener(this);
		}else{
			mContractPreviewLL.setVisibility(View.GONE);
		}
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
		mListView = (ListView)this.findViewById(R.id.contract_list_listview);
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
		
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			return;
		}
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF00FF00);
		txtView.setText("发起合同");
		setRightView(txtView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent;
				if(KoalaApplication.mUserType == fragment_homepage.TYPE_LABOR){
					intent = new Intent(MyContractActivity.this,ContractLaborActivity.class);
					intent.putExtra("type", 0);//new contract
					startActivity(intent);
				}else if(KoalaApplication.mUserType == fragment_homepage.TYPE_SUPRIOR){
					intent = new Intent(MyContractActivity.this,ContractSuperiorActivity.class);
					intent.putExtra("type", 0);//new contract
					startActivity(intent);
				}else if(KoalaApplication.mUserType == fragment_homepage.TYPE_DESIGNER){
					intent = new Intent(MyContractActivity.this,ContractDesignerActivity.class);
					intent.putExtra("type", 0);//new contract
					startActivity(intent);
				}
				
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(arg0.getId()){
		case R.id.contract_list_designer:
			intent = new Intent(MyContractActivity.this,ContractBigPicActivity.class);
			intent.putExtra("page", 5);
			intent.putExtra("type", 0);
			startActivity(intent);
			break;
		case R.id.contract_list_labor:
			intent = new Intent(MyContractActivity.this,ContractBigPicActivity.class);
			intent.putExtra("page", 5);
			intent.putExtra("type", 1);
			startActivity(intent);
			break;
		case R.id.contract_list_superior:
			intent = new Intent(MyContractActivity.this,ContractBigPicActivity.class);
			intent.putExtra("page", 5);
			intent.putExtra("type", 2);
			startActivity(intent);
			break;
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
		boolean bConfirmed = mArrayList.get(arg2).bConfirm;
		intent = new Intent(MyContractActivity.this,ContractDetailActivity.class);
		intent.putExtra("id", Integer.parseInt(mBack.items.get(arg2).c_id));
		intent.putExtra("confirm", bConfirmed);
		startActivity(intent);
	}
}
