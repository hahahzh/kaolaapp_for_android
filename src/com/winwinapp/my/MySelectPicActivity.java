package com.winwinapp.my;

import java.io.File;
import java.io.IOException;

import com.winwinapp.koala.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MySelectPicActivity extends Activity implements OnClickListener{

	private static final int FLAG_RESULT_CODE_TAKE_PHOTO = 0;
	private static final int FLAG_RESULT_CODE_PICTURES = 1;
	private Button btn_take_photo, btn_pick_photo, btn_cancel;  
    private LinearLayout layout;
    String path = "temp.jpg";
    int source = 0;
      
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.layout_popup_take_photo_window);  
        btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);  
        btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);  
        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);  
          
        layout=(LinearLayout)findViewById(R.id.pop_layout);
        path = getIntent().getStringExtra("path");
          
        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity  
        layout.setOnClickListener(new OnClickListener() {  
              
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                //Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",   
                  //      Toast.LENGTH_SHORT).show();   
            }  
        });  
        //添加按钮监听  
        btn_cancel.setOnClickListener(this);  
        btn_pick_photo.setOnClickListener(this);  
        btn_take_photo.setOnClickListener(this);  
    }  
      
    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity  
    @Override  
    public boolean onTouchEvent(MotionEvent event){  
        //finish();  
        return true;  
    }  
  
    public void onClick(View v) { 
    	Intent intent;
        switch (v.getId()) {  
        case R.id.btn_take_photo:
        	intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File newFile = new File(path);
			if(!newFile.exists())
				try {
					newFile.createNewFile();
					newFile.setReadable(true, false);
					newFile.setWritable(true, false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			source = 0;
			this.startActivityForResult(intent, FLAG_RESULT_CODE_TAKE_PHOTO);
            break;  
        case R.id.btn_pick_photo:
        	intent = new Intent(Intent.ACTION_GET_CONTENT);  
            intent.addCategory(Intent.CATEGORY_OPENABLE);  
            intent.setType("image/*");  
            //intent.putExtra("crop", "false");  
            //intent.putExtra("aspectX",1);  
            //intent.putExtra("aspectY",1);  
            //intent.putExtra("outputX", 80);  
            //intent.putExtra("outputY", 80);  
            intent.putExtra("return-data",true); 
            source = 1;
            startActivityForResult(intent, FLAG_RESULT_CODE_PICTURES); 
            break;  
        case R.id.btn_cancel:                 
            break;  
        default:  
            break;  
        } 
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			if(data == null){
				data = new Intent();
			}
			data.putExtra("source", source);
		}
		this.setResult(resultCode, data);
		finish();
//		if(Activity.RESULT_OK == resultCode)
//		{
//			ExifInterface exif = null;
//			try {
//				exif = new ExifInterface(path);
//				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
//				int ori = 0;
//				switch(orientation)
//				{
//				case ExifInterface.ORIENTATION_ROTATE_90:
//					ori = 90;
//					break;
//				case ExifInterface.ORIENTATION_ROTATE_180:
//					ori = 180;
//					break;
//				case ExifInterface.ORIENTATION_ROTATE_270:
//					ori = 270;
//					break;
//				default:
//					ori = 0;
//					break;
//				}
//				Log.i(TAG,"exif,orientation="+orientation+",ori="+ori);
//					
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			Log.i(TAG,"begin to decode");
//			Bitmap bm = BitmapFactory.decodeFile(path);
//			Matrix m = new Matrix();
//			m.setRotate(0, bm.getWidth()*0.5f, bm.getHeight()*0.5f);
//			Bitmap b = Bitmap.createBitmap(bm, 0,0, bm.getWidth()/2, bm.getHeight()/2, m, true);
//			Log.i(TAG,"end to decode");
//			if(b != bm)
//			{
//				bm.recycle();
//				bm = b;
//				Log.i(TAG, "b != bm");
//			}else
//			{
//				Log.i(TAG,"b = bm");
//			}
//			//mImageView.setImageBitmap(bm);
//			
//		}
//		if(requestCode == FLAG_RESULT_CODE_PHOTO)
//		{
//			Log.i(TAG, "Get result");
//		}
	}  
    
}
