package com.winwinapp.decorate;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.util.AddSubLL;

public class PreEvaluateActivity extends ActionBarActivity {

	Button mGeneratePrice;
	EditText mArea;
	AddSubLL mHall;
	AddSubLL mRoom;
	AddSubLL mKitchen;
	AddSubLL mToilet;
	AddSubLL mBalcony;
	RadioButton mRich;
	RadioButton mPoor;
	KoalaApplication mApp;
	DecorateSaveData mSavedData = new DecorateSaveData();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_pre_evaluate);

		initActionBar();
		initView();
		mApp = (KoalaApplication) this.getApplication();
		loadSavedData();
	}

	public void loadSavedData(){
		if(mApp.loadSavedDecorateData(mSavedData)){
			mArea.setText(mSavedData.area+"");
			mHall.setCurrentNumber(mSavedData.hall+"");
			mRoom.setCurrentNumber(mSavedData.room+"");
			mKitchen.setCurrentNumber(mSavedData.kitchen+"");
			mToilet.setCurrentNumber(mSavedData.toilet+"");
			mBalcony.setCurrentNumber(mSavedData.balcony+"");
			if(mSavedData.RichOrPoor == 1){
				mRich.setChecked(true);
			}else if(mSavedData.RichOrPoor == 2){
				mPoor.setChecked(true);
			}
		}
	}
	
	public void initView(){
		mArea = (EditText)findViewById(R.id.decorate_area);
		mHall = (AddSubLL)findViewById(R.id.decorate_pre_hall);
		mRoom = (AddSubLL)findViewById(R.id.decorate_pre_room);
		mKitchen = (AddSubLL)findViewById(R.id.decorate_pre_kitchen);
		mToilet = (AddSubLL)findViewById(R.id.decorate_pre_toilet);
		mBalcony = (AddSubLL)findViewById(R.id.decorate_pre_balcony);
		mRich = (RadioButton)findViewById(R.id.decorate_radio_rich);
		mPoor = (RadioButton)findViewById(R.id.decorate_radio_poor);
		
		mGeneratePrice = (Button)findViewById(R.id.decorate_generate_price);
		mGeneratePrice.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(PreEvaluateActivity.this,DecoratePriceActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("装修预算");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.save);
		setRightView(imageView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				mSavedData.area = Integer.parseInt(mArea.getText().toString());
				mSavedData.hall = mHall.getCurrentNumber();
				mSavedData.room = mRoom.getCurrentNumber();
				mSavedData.kitchen = mKitchen.getCurrentNumber();
				mSavedData.toilet = mToilet.getCurrentNumber();
				mSavedData.balcony = mBalcony.getCurrentNumber();
				if(mRich.isChecked()){
					mSavedData.RichOrPoor = 1;
				}else if(mPoor.isChecked()){
					mSavedData.RichOrPoor = 2;
				}else{
					mSavedData.RichOrPoor = 0;
				}
				mApp.SaveDecorateData(mSavedData);
				showDialog();
			}
			
		});
	}
	
	protected void showDialog() {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("装修预算已保存成功！"); 
		  builder.setTitle("提示");  
		  builder.setNegativeButton("关闭", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
          	  dialoginterface.dismiss();
            }
			  });
		  builder.create().show();
	}
	
	public class DecorateSaveData{
		public int area;
		public int hall;
		public int room;
		public int kitchen;
		public int toilet;
		public int balcony;
		public int RichOrPoor;
	}
	
}
