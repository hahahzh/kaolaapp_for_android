package com.winwinapp.my;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.util.Utils;

public class MyIDAuthenActivity extends ActionBarActivity implements OnClickListener{

	ImageView mIDFront;
	ImageView mIDBack;
	ImageView mCert;
	LinearLayout mIDLL;
	LinearLayout mCertLL;
	TextView mIDText;
	TextView mCertText;
	ImageView mIDIndicator;
	ImageView mCertIndicator;
	TextView mName;
	TextView mIDNumber;
	String mIDFrontPath = "img_id_front.jpg";
	String mIDBackPath = "img_id_back.jpg";
	String mCertPath = "img_cert.jpg";
	int mPicWidth = 100;
	int mPicHeight = 100;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_id_authen);

		initActionBar();
		
		mIDFront = (ImageView)findViewById(R.id.id_authen_id_front);
		mIDBack = (ImageView)findViewById(R.id.id_authen_id_back);
		mCert = (ImageView)findViewById(R.id.id_authen_add_cert);
		mIDFront.setOnClickListener(this);
		mIDBack.setOnClickListener(this);
		mCert.setOnClickListener(this);
		
		mIDText = (TextView)findViewById(R.id.id_auth_id_txt);
		mCertText = (TextView)findViewById(R.id.id_auth_cert_txt);
		mIDText.setOnClickListener(this);
		mCertText.setOnClickListener(this);
		mIDLL = (LinearLayout)findViewById(R.id.id_authen_id_ll);
		mCertLL = (LinearLayout)findViewById(R.id.id_authen_cert_ll);
		
		mIDIndicator = (ImageView)findViewById(R.id.id_auth_id_indicator);
		mCertIndicator = (ImageView)findViewById(R.id.id_auth_cert_indicator);
		mName = (TextView)findViewById(R.id.id_auth_name);
		mIDNumber = (TextView)findViewById(R.id.id_auth_id_number);
		
		mIDText.setTextColor(0xFFFF6600);
		mCertText.setTextColor(0xFF000000);
		mCertLL.setVisibility(View.GONE);
		mCertIndicator.setVisibility(View.INVISIBLE);
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_add_photo, options);
        mPicWidth = options.outWidth;
        mPicHeight = options.outHeight;
        
        mIDFrontPath = Utils.getPrivateFilePath(this) + "/" + "img_id_front.jpg";
    	mIDBackPath = Utils.getPrivateFilePath(this) + "/" + "img_id_back.jpg";
    	mCertPath = Utils.getPrivateFilePath(this) + "/" + "img_cert.jpg";
	}
	
	public void onResume(){
		super.onResume();
		
		loadPic(mIDFrontPath,mIDFront);
		loadPic(mIDBackPath,mIDBack);
		loadPic(mCertPath,mCert);
	}
	
	public void loadPic(String path,ImageView imageView){
		File file = new File(path);
		if(file.exists() && file.length() > 0){
			Bitmap bm= BitmapFactory.decodeFile(path);
			Bitmap des = Bitmap.createBitmap(mPicWidth, mPicHeight, bm.getConfig());
			Canvas canvas = new Canvas(des);
			canvas.drawBitmap(bm, null, new Rect(0,0,des.getWidth(),des.getHeight()),null);
			imageView.setImageBitmap(des);
			bm.recycle();
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("身份认证");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF00FF00);
		txtView.setText("提交");
		setRightView(txtView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				//finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.id_authen_add_cert:
		case R.id.id_authen_id_back:
		case R.id.id_authen_id_front:
			startPicActivity(arg0.getId());
			break;
		case R.id.id_auth_cert_txt:
			mIDLL.setVisibility(View.GONE);
			mIDIndicator.setVisibility(View.INVISIBLE);
			mIDText.setTextColor(0xFF000000);
			
			mCertLL.setVisibility(View.VISIBLE);
			mCertIndicator.setVisibility(View.VISIBLE);
			mCertText.setTextColor(0xFFFF6600);
			break;
		case R.id.id_auth_id_txt:
			mCertLL.setVisibility(View.GONE);
			mCertIndicator.setVisibility(View.INVISIBLE);
			mCertText.setTextColor(0xFF000000);
			
			mIDLL.setVisibility(View.VISIBLE);
			mIDIndicator.setVisibility(View.VISIBLE);
			mIDText.setTextColor(0xFFFF6600);
			break;
		}
	}
	
	private void startPicActivity(int id){
		Intent intent = new Intent(MyIDAuthenActivity.this,MySelectPicActivity.class);
		switch(id){
		case R.id.id_authen_id_front:
			intent.putExtra("path",mIDFrontPath);
			break;
		case R.id.id_authen_id_back:
			intent.putExtra("path",mIDBackPath);
			break;
		case R.id.id_authen_add_cert:
			intent.putExtra("path",mCertPath);
			break;
		default:
			intent.putExtra("path","tmp.jpg");
		}
		
		this.startActivityForResult(intent, id);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		//Toast.makeText(this, "get picture,requestCode="+requestCode+",resultCode="+resultCode, Toast.LENGTH_LONG).show();
		if(resultCode == Activity.RESULT_OK){
			int source = data.getIntExtra("source", 0);
			String path = null;
			ImageView imageView = null;
			switch(requestCode){
			case R.id.id_authen_id_front:
				path =  mIDFrontPath;
				imageView = mIDFront; 
				break;
			case R.id.id_authen_id_back:
				path = mIDBackPath;
				imageView = mIDBack;
				break;
			case R.id.id_authen_add_cert:
				path = mCertPath;
				imageView = mCert;
				break;
			}
			if(source == 0){
				if(path != null && imageView != null){
					Bitmap bm = BitmapFactory.decodeFile(path);
					Bitmap des = Bitmap.createBitmap(mPicWidth, mPicHeight, bm.getConfig());
					Canvas canvas = new Canvas(des);
					canvas.drawBitmap(bm, null, new Rect(0,0,des.getWidth(),des.getHeight()),null);
					imageView.setImageBitmap(des);
					bm.recycle();
				}
			}else{
				if(path != null && imageView != null){
					Uri selectedImage = data.getData(); //获取系统返回的照片的Uri  
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };   
	                Cursor cursor =getContentResolver().query(selectedImage,   
	                       filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片  
	                cursor.moveToFirst();   
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	                String picturePath = cursor.getString(columnIndex);  //获取照片路径  
	                cursor.close();   
	                Bitmap bm= BitmapFactory.decodeFile(picturePath);
					Bitmap des = Bitmap.createBitmap(mPicWidth, mPicHeight, bm.getConfig());
					Canvas canvas = new Canvas(des);
					canvas.drawBitmap(bm, null, new Rect(0,0,des.getWidth(),des.getHeight()),null);
					imageView.setImageBitmap(des);
					
					Utils.bmpToFile(bm, path);
					bm.recycle();
				}
				
			}
		}
	}
	
}
