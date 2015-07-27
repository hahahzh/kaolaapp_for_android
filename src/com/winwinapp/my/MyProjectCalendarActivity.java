package com.winwinapp.my;

import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.util.ActionBarView;
import com.winwinapp.util.Utils;

public class MyProjectCalendarActivity extends Activity  implements OnTabChangeListener,OnClickListener{
	private static final int REFRESH_SPINNER = 1;
	private String mTabTitle[] = {"标准工作流","庭院工作流","其他工作流"};
	private TabHost mTabHost;
	private ActionBarView mActionBar;
	LinearLayout mSwitchLL;
	TextView mTipsBtn;
	TextView mAppendBtn;
	TextView mTipsContent;
	LinearLayout mAppendContentLL;
	boolean mCurrentState = true;
	TextView spinner_calendar_second,spinner_calendar_third;
	TextView spinner_calendar_first;
	FrameLayout mFrame;
	Drawable mDrawCheck;
	Drawable mDrawUncheck;
	TextView textView1,textView2,textView3;
	boolean bSpinnerShowing = false;
	int mCurrentIndex = -1;
	LinearLayout mSpinnerLL = null;
	int firstSelect = -1;
	int secondSelect = -1;
	int thirdSelect = -1;
	Bitmap bitmap1=null,bitmap2=null,bitmap3=null,bitmap4=null;
	TextView mTextModify;
	TextView mShigong;
	TextView mShuidian;
	TextView mQiangzhuan;
	TextView mYouqi;
	boolean bModifyMode = false;
	Bitmap xpng;
	TextView mAddAttach;
	Button mSubmit;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case REFRESH_SPINNER:
				int index = mCurrentIndex;
				int subIndex = msg.arg1;
				TextView txt = null;
				switch(index){
				case 0:
					txt = spinner_calendar_first;
					txt.setBackgroundResource(R.drawable.spinner_r);
					firstSelect = subIndex;
					break;
				case 1:
					txt = spinner_calendar_second;
					txt.setBackgroundResource(R.drawable.spinner_b);
					secondSelect = subIndex;
					break;
				case 2:
					txt = spinner_calendar_third;
					txt.setBackgroundResource(R.drawable.spinner_o);
					thirdSelect = subIndex;
					break;
				}
				if(txt != null && subIndex >= 0 && subIndex <= 2){
					txt.setCompoundDrawablesWithIntrinsicBounds(mDrawCheck, null, null, null);
				}
				if(bSpinnerShowing){
					mFrame.removeView(mSpinnerLL);
					bSpinnerShowing = false;
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_tip_main);
		initActionBar();
		initView();
		initTabHost();
		mFrame = (FrameLayout)findViewById(android.R.id.tabcontent);
		mDrawCheck = getResources().getDrawable(R.drawable.calendar_check_c);
		mDrawUncheck = getResources().getDrawable(R.drawable.calendar_check);
	}

	public void initView(){
		mSwitchLL = (LinearLayout)findViewById(R.id.project_tips_append_ll);
		mSwitchLL.setBackgroundResource(R.drawable.icon_button_reverse_bg);
		
		mTipsBtn = (TextView)findViewById(R.id.project_calendar_tips);
		mAppendBtn = (TextView)findViewById(R.id.project_calendar_append);
		mTipsContent = (TextView)findViewById(R.id.project_calendar_tips_txt);
		mAppendContentLL = (LinearLayout)findViewById(R.id.project_calendar_append_ll);
		
		spinner_calendar_first = (TextView)findViewById(R.id.spinner_calendar_first);
		spinner_calendar_second = (TextView)findViewById(R.id.spinner_calendar_second);
		spinner_calendar_third = (TextView)findViewById(R.id.spinner_calendar_third);
		
		spinner_calendar_first.setOnClickListener(this);
		spinner_calendar_second.setOnClickListener(this);
		spinner_calendar_third.setOnClickListener(this);
		
		mTipsBtn.setOnClickListener(this);
		mAppendBtn.setOnClickListener(this);
		mCurrentState = true;
		
		mTextModify = (TextView)findViewById(R.id.calendar_modify);
		mShigong = (TextView)findViewById(R.id.calendar_shigongjiaodi);
		mShuidian = (TextView)findViewById(R.id.calendar_shuidiangaizao);
		mQiangzhuan = (TextView)findViewById(R.id.calendar_qiangzhuan);
		mYouqi = (TextView)findViewById(R.id.calendar_youqituliao);
		bModifyMode = false;
		mTextModify.setOnClickListener(this);
		mShigong.setOnClickListener(this);
		mShuidian.setOnClickListener(this);
		mQiangzhuan.setOnClickListener(this);
		mYouqi.setOnClickListener(this);
		
		mSubmit = (Button)findViewById(R.id.project_calendar_submit);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			mSubmit.setText("确定");
		}else{
			mSubmit.setText("提交");
		}
		mSubmit.setOnClickListener(this);
		
		mAddAttach = (TextView)findViewById(R.id.project_calendar_add_attachment);
		mAddAttach.setOnClickListener(this);
		xpng = BitmapFactory.decodeResource(getResources(), R.drawable.x);
	}
	
	public void showPopupText(int index){
		if(mSpinnerLL == null){
			int start[] = new int[2];
			mSpinnerLL = new LinearLayout(this);
			spinner_calendar_first.getLocationOnScreen(start);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			params.topMargin = spinner_calendar_first.getMeasuredHeight();
			params.leftMargin = start[0];
			mSpinnerLL.setLayoutParams(params);
			mSpinnerLL.setOrientation(LinearLayout.VERTICAL);
			
			LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(new LayoutParams(spinner_calendar_first.getMeasuredWidth(),LayoutParams.WRAP_CONTENT));
			txtParams.bottomMargin = 1;
			
			textView1 = new TextView(this);
			textView1.setText("水电改造");
			textView1.setTextSize(18);
			textView1.setGravity(Gravity.CENTER);
			textView1.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView1.setBackgroundResource(R.drawable.spinner);
			textView1.setLayoutParams(txtParams);
			mSpinnerLL.addView(textView1);
			textView1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					Message msg = Message.obtain();
					msg.what = REFRESH_SPINNER;
					msg.arg1 = 0;
					mHandler.sendMessage(msg);
				}
				
			});
			
			textView2 = new TextView(this);
			textView2.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView2.setText("更换门窗");
			textView2.setTextSize(18);
			textView2.setLayoutParams(txtParams);
			textView2.setGravity(Gravity.CENTER);
			textView2.setBackgroundResource(R.drawable.spinner);
			textView2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					Message msg = Message.obtain();
					msg.what = REFRESH_SPINNER;
					msg.arg1 = 1;
					mHandler.sendMessage(msg);
				}
				
			});
			mSpinnerLL.addView(textView2);
			
			textView3 = new TextView(this);
			textView3.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView3.setText("水电验收");
			textView3.setTextSize(18);
			textView3.setLayoutParams(txtParams);
			textView3.setGravity(Gravity.CENTER);
			textView3.setBackgroundResource(R.drawable.spinner);
			textView3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					Message msg = Message.obtain();
					msg.what = REFRESH_SPINNER;
					msg.arg1 = 2;
					mHandler.sendMessage(msg);
				}
				
			});
			mSpinnerLL.addView(textView3);
			
			mSpinnerLL.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		}
		if(bSpinnerShowing){
			mFrame.removeView(mSpinnerLL);
			bSpinnerShowing = false;
			mCurrentIndex = -1;
		}else{
			int start[] = new int[2];
			View top;
			int id = -1;
			TextView view;
			int subSelect;
			if(index == 0){
				top = spinner_calendar_first;
				id = R.drawable.spinner_r;
				subSelect = firstSelect;
			}else if(index == 1){
				top = spinner_calendar_second;
				id = R.drawable.spinner_b;
				subSelect = secondSelect;
			}else{
				top = spinner_calendar_third;
				id = R.drawable.spinner_o;
				subSelect = thirdSelect;
			}
			textView1.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView1.setBackgroundResource(R.drawable.spinner);
			textView2.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView2.setBackgroundResource(R.drawable.spinner);
			textView3.setCompoundDrawablesWithIntrinsicBounds(mDrawUncheck, null, null, null);
			textView3.setBackgroundResource(R.drawable.spinner);
			if(subSelect == 0){
				textView1.setCompoundDrawablesWithIntrinsicBounds(mDrawCheck, null, null, null);
				textView1.setBackgroundResource(id);
			}else if(subSelect == 1){
				textView2.setCompoundDrawablesWithIntrinsicBounds(mDrawCheck, null, null, null);
				textView2.setBackgroundResource(id);
			}else if(subSelect == 2){
				textView3.setCompoundDrawablesWithIntrinsicBounds(mDrawCheck, null, null, null);
				textView3.setBackgroundResource(id);
			}
			top.getLocationOnScreen(start);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			params.topMargin = spinner_calendar_first.getMeasuredHeight();
			params.leftMargin = start[0];
			mSpinnerLL.setLayoutParams(params);
			mFrame.addView(mSpinnerLL);
			bSpinnerShowing = true;
			mCurrentIndex = index;
		}
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch(view.getId()){
		case R.id.project_calendar_tips:
			if(!mCurrentState){
				mCurrentState = true;
				mSwitchLL.setBackgroundResource(R.drawable.icon_button_reverse_bg);
				mTipsContent.setVisibility(View.VISIBLE);
				mAppendContentLL.setVisibility(View.GONE);
			}
			//showMenu();
			break;
		case R.id.project_calendar_append:
			if(mCurrentState){
				mCurrentState = false;
				mSwitchLL.setBackgroundResource(R.drawable.icon_button_bg);
				mTipsContent.setVisibility(View.GONE);
				mAppendContentLL.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.spinner_calendar_first:
			showPopupText(0);
			break;
		case R.id.spinner_calendar_second:
			showPopupText(1);
			break;
		case R.id.spinner_calendar_third:
			showPopupText(2);
			break;
		case R.id.calendar_modify:
			if(bModifyMode){
				bModifyMode = false;
				mTextModify.setText("修改");
				enterModifyMode(false);
			}else{
				bModifyMode = true;
				mTextModify.setText("完成");
				enterModifyMode(true);
			}
			break;
		case R.id.calendar_qiangzhuan:
			if(bModifyMode){
				mQiangzhuan.setVisibility(View.GONE);
			}
			break;
		case R.id.calendar_shigongjiaodi:
			if(bModifyMode){
				mShigong.setVisibility(View.GONE);
			}
			break;
		case R.id.calendar_shuidiangaizao:
			if(bModifyMode){
				mShuidian.setVisibility(View.GONE);
			}
			break;
		case R.id.calendar_youqituliao:
			if(bModifyMode){
				mYouqi.setVisibility(View.GONE);
			}
			break;
		case R.id.project_calendar_add_attachment:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
            intent.addCategory(Intent.CATEGORY_OPENABLE);  
            intent.setType("image/*");  
            //intent.putExtra("crop", "false");  
            //intent.putExtra("aspectX",1);  
            //intent.putExtra("aspectY",1);  
            //intent.putExtra("outputX", 80);  
            //intent.putExtra("outputY", 80);  
            intent.putExtra("return-data",true); 
            startActivityForResult(intent, 0); 
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			Uri selectedImage = data.getData(); //获取系统返回的照片的Uri 
			String[] filePathColumn = { MediaStore.Images.Media.DATA };   
            Cursor cursor =getContentResolver().query(selectedImage,   
                   filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片  
            cursor.moveToFirst();   
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
            String picturePath = cursor.getString(columnIndex);  //获取照片路径  
            File file = new File(picturePath);
            TextView txtView = new TextView(this);
            txtView.setText(file.getName());
            LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			txtParams.leftMargin = Utils.dp2px(this, 40);
			txtView.setLayoutParams(txtParams);
			mAppendContentLL.addView(txtView);
		}
	}
	
	private Bitmap drawableToBitmap(Drawable drawable){
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity()!=PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
		Bitmap bm = Bitmap.createBitmap(w, h, config);
		Canvas c = new Canvas(bm);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(c);
		return bm;
	}
	
	public void enterModifyMode(boolean bl){
		if(bl){
			if(bitmap1 == null){
				Bitmap bm = drawableToBitmap(mShigong.getBackground());
				Toast.makeText(this, "w="+bm.getWidth()+",h="+bm.getHeight()+",xw="+xpng.getWidth()+",xh="+xpng.getHeight(), Toast.LENGTH_LONG).show();
				bitmap1 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight()+xpng.getHeight()/2, bm.getConfig());
				Canvas c = new Canvas(bitmap1);
				c.drawBitmap(bm, null, new Rect(0,xpng.getHeight()/2,bm.getWidth(),bm.getHeight()+xpng.getHeight()/2),null);
				c.drawBitmap(xpng, null, new Rect(bm.getWidth()-xpng.getWidth(),0,bm.getWidth(),xpng.getHeight()),null);
				//c.drawBitmap(xpng, bitmap1.getWidth()-xpng.getWidth(), 0, null);
				//c.drawBitmap(bm, 0, bm.getHeight()/2, null);
			}
			if(bitmap2 == null){
				Bitmap bm = drawableToBitmap(mShuidian.getBackground());
				bitmap2 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight()+xpng.getHeight()/2, bm.getConfig());
				Canvas c = new Canvas(bitmap2);
				c.drawBitmap(bm, null, new Rect(0,xpng.getHeight()/2,bm.getWidth(),bm.getHeight()+xpng.getHeight()/2),null);
				c.drawBitmap(xpng, null, new Rect(bm.getWidth()-xpng.getWidth(),0,bm.getWidth(),xpng.getHeight()),null);
			}
			if(bitmap3 == null){
				Bitmap bm = drawableToBitmap(mQiangzhuan.getBackground());
				bitmap3 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight()+xpng.getHeight()/2, bm.getConfig());
				Canvas c = new Canvas(bitmap3);
				c.drawBitmap(bm, null, new Rect(0,xpng.getHeight()/2,bm.getWidth(),bm.getHeight()+xpng.getHeight()/2),null);
				c.drawBitmap(xpng, null, new Rect(bm.getWidth()-xpng.getWidth(),0,bm.getWidth(),xpng.getHeight()),null);
			}
			if(bitmap4 == null){
				Bitmap bm =  drawableToBitmap(mYouqi.getBackground());
				bitmap4 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight()+xpng.getHeight()/2, bm.getConfig());
				Canvas c = new Canvas(bitmap4);
				c.drawBitmap(bm, null, new Rect(0,xpng.getHeight()/2,bm.getWidth(),bm.getHeight()+xpng.getHeight()/2),null);
				c.drawBitmap(xpng, null, new Rect(bm.getWidth()-xpng.getWidth(),0,bm.getWidth(),xpng.getHeight()),null);
			}
			mShigong.setBackground(new BitmapDrawable(this.getResources(),bitmap1));
			mShuidian.setBackground(new BitmapDrawable(this.getResources(),bitmap2));
			mQiangzhuan.setBackground(new BitmapDrawable(this.getResources(),bitmap3));
			mYouqi.setBackground(new BitmapDrawable(this.getResources(),bitmap4));
		}else{
			mShigong.setBackgroundResource(R.drawable.rec_red);
			mShuidian.setBackgroundResource(R.drawable.rec_blue);
			mQiangzhuan.setBackgroundResource(R.drawable.rec_orange);
			mYouqi.setBackgroundResource(R.drawable.rec_green);
		}
	}
//	public void showMenu(){
//		AlertDialog.Builder build = new AlertDialog.Builder(MyProjectCalendarActivity.this).setTitle(null).setItems(mTabTitle,new DialogInterface.OnClickListener(){  
//		      public void onClick(DialogInterface dialog, int which){  
//		       Toast.makeText(MyProjectCalendarActivity.this, "您已经选择了: " + which + ":" + mTabTitle[which],Toast.LENGTH_LONG).show();  
//		       dialog.dismiss();  
//		      }  
//		   });
//		build.show();
//	}
//	public void onClick(Spinner spinner) {
//		// TODO 自动生成的方法存根
//		switch(spinner.getId()){
//		case R.id.spinner_calendar_first:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//			break;
//		case R.id.spinner_calendar_second:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//			break;
//		case R.id.spinner_calendar_third:
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner_r);
//			break;
//		}
//	}
	
	public void initActionBar(){
		ActionBar actionBar = this.getActionBar();
		if(actionBar != null){
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			
			mActionBar = new ActionBarView(this);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.back);
			mActionBar.setLeftView(imageView);
			
			mActionBar.setOnLeftClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					finish();
				}
				
			});
			
			mActionBar.setTitle("项目日历");
			
			TextView textView = new TextView(this);
			textView.setText("保存");
			textView.setTextColor(Color.GREEN);
			mActionBar.setRightView(textView);
			mActionBar.setOnRightClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					//finish();
				}
				
			});
			actionBar.setCustomView(mActionBar,new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
	}
	
	public void initTabHost(){
		mTabHost = (TabHost) this.findViewById(R.id.tips_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[0]).setIndicator(mTabTitle[0]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[1]).setIndicator(mTabTitle[1]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(mTabTitle[2]).setIndicator(mTabTitle[2]).setContent(R.id.tab_content_my_project_calendar));
		mTabHost.setOnTabChangedListener(this);
	}
	
	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}

	@Override
	public void onTabChanged(String arg0) {
		if(arg0 == null)
			return;
//		if(arg0.equals(mTabTitle[0])){
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//		}else if(arg0.equals(mTabTitle[1])){
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner_r);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner);
//		}else if(arg0.equals(mTabTitle[2])){
//			spinner_calendar_first.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_second.setBackgroundResource(R.drawable.spinner);
//			spinner_calendar_third.setBackgroundResource(R.drawable.spinner_r);
//		}
		updateTab(mTabHost);
		
	}

	private void updateTab(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); 
            tv.setTextSize(15);
            if (tabHost.getCurrentTab() == i) {//选中    
                tv.setTextColor(this.getResources().getColorStateList(R.color.green)); 
            } else {//不选中    
                tv.setTextColor(this.getResources().getColorStateList(R.color.gray)); 
            } 
        } 
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(bSpinnerShowing){
				mFrame.removeView(mSpinnerLL);
				bSpinnerShowing = false;
			}
			break;
		}
		return super.onTouchEvent(event);
	} 
	
}

