package com.winwinapp.decorate;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.util.AddSubLL;
import com.winwinapp.util.OnAddSubChangeListener;

public class DecoratePriceActivity extends ActionBarActivity implements OnClickListener,OnAddSubChangeListener{

	Button mSavePrice;
	int mPrice1 = 800,mPrice2 = 600,mPrice3 = 100,mPrice4 = 100,mPrice5 = 1000,mPrice6 = 1000,mPrice7 = 500,mPrice8 = 100,mPrice9 = 2000,
			mPrice10 = 2000,mPrice11 = 200,mPrice12 = 800,mPrice13 = 200,mPrice14 = 200,mPrice15 = 300,mPrice16 = 600,mPrice17 = 500,mPrice18 = 500;
	AddSubLL mNumb1,mNumb2,mNumb3,mNumb4,mNumb5,mNumb6,mNumb7,mNumb8,mNumb9,mNumb10,mNumb11,mNumb12,mNumb13,mNumb14,mNumb15,mNumb16,mNumb17,mNumb18;
	TextView mTotal1,mTotal2,mTotal3,mTotal4,mTotal5,mTotal6,mTotal7,mTotal8,mTotal9,mTotal10,mTotal11,mTotal12,mTotal13,mTotal14,mTotal15,mTotal16,mTotal17,mTotal18;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_price);

		initActionBar();
		mSavePrice = (Button)findViewById(R.id.decorate_save_price);
		mSavePrice.setOnClickListener(this);
		mNumb1 = (AddSubLL)findViewById(R.id.decorate_num1);
		mNumb1.setCurrentNumber(2+"");
		mNumb1.registerListener(this);
		mTotal1 = (TextView)findViewById(R.id.decorate_price_total1);
		
		mNumb2 = (AddSubLL)findViewById(R.id.decorate_num2);
		mNumb2.registerListener(this);
		mNumb2.setCurrentNumber(2+"");
		mTotal2 = (TextView)findViewById(R.id.decorate_price_total2);
		
		mNumb3 = (AddSubLL)findViewById(R.id.decorate_num3);
		mNumb3.registerListener(this);
		mNumb3.setCurrentNumber(1+"");
		mTotal3 = (TextView)findViewById(R.id.decorate_price_total3);
		
		mNumb4 = (AddSubLL)findViewById(R.id.decorate_num4);
		mNumb4.registerListener(this);
		mNumb4.setCurrentNumber(50+"");
		mTotal4 = (TextView)findViewById(R.id.decorate_price_total4);
		
		mNumb5 = (AddSubLL)findViewById(R.id.decorate_num5);
		mNumb5.registerListener(this);
		mNumb5.setCurrentNumber(1+"");
		mTotal5 = (TextView)findViewById(R.id.decorate_price_total5);
		
		mNumb6 = (AddSubLL)findViewById(R.id.decorate_num6);
		mNumb6.registerListener(this);
		mNumb6.setCurrentNumber(1+"");
		mTotal6 = (TextView)findViewById(R.id.decorate_price_total6);
		
		mNumb7 = (AddSubLL)findViewById(R.id.decorate_num7);
		mNumb7.registerListener(this);
		mNumb7.setCurrentNumber(1+"");
		mTotal7 = (TextView)findViewById(R.id.decorate_price_total7);
		
		mNumb8 = (AddSubLL)findViewById(R.id.decorate_num8);
		mNumb8.registerListener(this);
		mNumb8.setCurrentNumber(10+"");
		mTotal8 = (TextView)findViewById(R.id.decorate_price_total8);
		
		mNumb9 = (AddSubLL)findViewById(R.id.decorate_num9);
		mNumb9.registerListener(this);
		mNumb9.setCurrentNumber(1+"");
		mTotal9 = (TextView)findViewById(R.id.decorate_price_total9);
		
		mNumb10 = (AddSubLL)findViewById(R.id.decorate_num10);
		mNumb10.registerListener(this);
		mNumb10.setCurrentNumber(1+"");
		mTotal10 = (TextView)findViewById(R.id.decorate_price_total10);
		
		mNumb11 = (AddSubLL)findViewById(R.id.decorate_num11);
		mNumb11.registerListener(this);
		mNumb11.setCurrentNumber(50+"");
		mTotal11 = (TextView)findViewById(R.id.decorate_price_total11);
		
		mNumb12 = (AddSubLL)findViewById(R.id.decorate_num12);
		mNumb12.registerListener(this);
		mNumb12.setCurrentNumber(1+"");
		mTotal12 = (TextView)findViewById(R.id.decorate_price_total12);
		
		mNumb13 = (AddSubLL)findViewById(R.id.decorate_num13);
		mNumb13.registerListener(this);
		mNumb13.setCurrentNumber(3+"");
		mTotal13 = (TextView)findViewById(R.id.decorate_price_total13);
		
		mNumb14 = (AddSubLL)findViewById(R.id.decorate_num14);
		mNumb14.registerListener(this);
		mNumb14.setCurrentNumber(50+"");
		mTotal14 = (TextView)findViewById(R.id.decorate_price_total14);
		
		mNumb15 = (AddSubLL)findViewById(R.id.decorate_num15);
		mNumb15.registerListener(this);
		mNumb15.setCurrentNumber(20+"");
		mTotal15 = (TextView)findViewById(R.id.decorate_price_total15);
		
		mNumb16 = (AddSubLL)findViewById(R.id.decorate_num16);
		mNumb16.registerListener(this);
		mNumb16.setCurrentNumber(1+"");
		mTotal16 = (TextView)findViewById(R.id.decorate_price_total16);
		
		mNumb17 = (AddSubLL)findViewById(R.id.decorate_num17);
		mNumb17.registerListener(this);
		mNumb17.setCurrentNumber(1+"");
		mTotal17 = (TextView)findViewById(R.id.decorate_price_total17);
		
		mNumb18 = (AddSubLL)findViewById(R.id.decorate_num18);
		mNumb18.registerListener(this);
		mNumb18.setCurrentNumber(1+"");
		mTotal18 = (TextView)findViewById(R.id.decorate_price_total18);
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("报价结果");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.decorate_save_price:
			showDialog();
			break;
		}
	}
	
	protected void showDialog() {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("报价记录已保存成功！"); 
		  builder.setTitle("提示");  
		  builder.setNegativeButton("关闭", new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialoginterface, int i) {
            	  dialoginterface.dismiss();
              }
			  });
		  builder.create().show();
	}

	@Override
	public void onAddSubChange(int id) {
		// TODO 自动生成的方法存根
		switch(id){
		case R.id.decorate_num1:
			mTotal1.setText(mNumb1.getCurrentNumber()*mPrice1+"");
			break;
		case R.id.decorate_num2:
			mTotal2.setText(mNumb2.getCurrentNumber()*mPrice2+"");
			break;
		case R.id.decorate_num3:
			mTotal3.setText(mNumb3.getCurrentNumber()*mPrice3+"");
			break;
		case R.id.decorate_num4:
			mTotal4.setText(mNumb4.getCurrentNumber()*mPrice4+"");
			break;
		case R.id.decorate_num5:
			mTotal5.setText(mNumb5.getCurrentNumber()*mPrice5+"");
			break;
		case R.id.decorate_num6:
			mTotal6.setText(mNumb6.getCurrentNumber()*mPrice6+"");
			break;
		case R.id.decorate_num7:
			mTotal7.setText(mNumb7.getCurrentNumber()*mPrice7+"");
			break;
		case R.id.decorate_num8:
			mTotal8.setText(mNumb8.getCurrentNumber()*mPrice8+"");
			break;
		case R.id.decorate_num9:
			mTotal9.setText(mNumb9.getCurrentNumber()*mPrice9+"");
			break;
		case R.id.decorate_num10:
			mTotal10.setText(mNumb10.getCurrentNumber()*mPrice10+"");
			break;
		case R.id.decorate_num11:
			mTotal11.setText(mNumb11.getCurrentNumber()*mPrice11+"");
			break;
		case R.id.decorate_num12:
			mTotal12.setText(mNumb12.getCurrentNumber()*mPrice12+"");
			break;
		case R.id.decorate_num13:
			mTotal13.setText(mNumb13.getCurrentNumber()*mPrice13+"");
			break;
		case R.id.decorate_num14:
			mTotal14.setText(mNumb14.getCurrentNumber()*mPrice14+"");
			break;
		case R.id.decorate_num15:
			mTotal15.setText(mNumb15.getCurrentNumber()*mPrice15+"");
			break;
		case R.id.decorate_num16:
			mTotal16.setText(mNumb16.getCurrentNumber()*mPrice16+"");
			break;
		case R.id.decorate_num17:
			mTotal17.setText(mNumb17.getCurrentNumber()*mPrice17+"");
			break;
		case R.id.decorate_num18:
			mTotal18.setText(mNumb18.getCurrentNumber()*mPrice18+"");
			break;
			
		}
	}
}
