package com.winwinapp.decorate;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.AddSubLL;
import com.winwinapp.util.OnAddSubChangeListener;

public class DecoratePriceActivity extends ActionBarActivity implements OnClickListener,OnAddSubChangeListener{

	Button mSavePrice;
	TextView mPrice1,mPrice2,mPrice3,mPrice4,mPrice5 ,mPrice6 ,mPrice7 ,mPrice8 ,mPrice9,
			mPrice10,mPrice11,mPrice12,mPrice13,mPrice14,mPrice15 ,mPrice16 ,mPrice17,mPrice18;
	AddSubLL mNumb1,mNumb2,mNumb3,mNumb4,mNumb5,mNumb6,mNumb7,mNumb8,mNumb9,mNumb10,mNumb11,mNumb12,mNumb13,mNumb14,mNumb15,mNumb16,mNumb17,mNumb18;
	TextView mTotal1,mTotal2,mTotal3,mTotal4,mTotal5,mTotal6,mTotal7,mTotal8,mTotal9,mTotal10,mTotal11,mTotal12,mTotal13,mTotal14,mTotal15,mTotal16,mTotal17,mTotal18;
	
	TextView mBasePrice,mAccessPrice,mWorkerPrice,mTotalPrice;
	NetworkData.GeneratePriceData mData = NetworkData.getInstance().getNewGeneratePriceData();
	NetworkData.GeneratePriceBack mBack = NetworkData.getInstance().getNewGeneratePriceBack();
	int area;
	int ting;
	int room;
	int chu;
	int wei;
	int yangtai;
	String dec_way;
	LinearLayout mLL;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case 1:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					refresh();
				}else{
					Toast.makeText(DecoratePriceActivity.this, "获取报价结果失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void refresh(){
	
		mPrice1.setText(mBack.chug_price+"");
		mNumb1.setCurrentNumber(mBack.chug_num+"");
		mTotal1.setText(mBack.chug_num*mBack.chug_price+"");
		
		mPrice2.setText(mBack.youyj_price+"");
		mNumb2.setCurrentNumber(mBack.youyj_num+"");
		mTotal2.setText(mBack.youyj_num*mBack.youyj_price+"");
		
		mPrice3.setText(mBack.jingsq_price+"");
		mNumb3.setCurrentNumber(mBack.jingsq_num+"");
		mTotal3.setText(mBack.jingsq_num*mBack.jingsq_price+"");
		
		mPrice4.setText(mBack.diz_price+"");
		mNumb4.setCurrentNumber(mBack.diz_num+"");
		mTotal4.setText(mBack.diz_num*mBack.diz_price+"");
		
		mPrice5.setText(mBack.qiangz_price+"");
		mNumb5.setCurrentNumber(mBack.qiangz_num+"");
		mTotal5.setText(mBack.qiangz_num*mBack.qiangz_price+"");
		
		mPrice6.setText(mBack.shuic_price+"");
		mNumb6.setCurrentNumber(mBack.shuic_num+"");
		mTotal6.setText(mBack.shuic_num*mBack.shuic_price+"");
		
		mPrice7.setText(mBack.resq_price+"");
		mNumb7.setCurrentNumber(mBack.resq_num+"");
		mTotal7.setText(mBack.resq_num*mBack.resq_price+"");
		
		mPrice8.setText(mBack.jicdd_price+"");
		mNumb8.setCurrentNumber(mBack.jicdd_num+"");
		mTotal8.setText(mBack.jicdd_num*mBack.jicdd_price+"");
		
		mPrice9.setText(mBack.zuobq_price+"");
		mNumb9.setCurrentNumber(mBack.zuobq_num+"");
		mTotal9.setText(mBack.zuobq_num*mBack.zuobq_price+"");
		
		mPrice10.setText(mBack.yusg_price+"");
		mNumb10.setCurrentNumber(mBack.yusg_num+"");
		mTotal10.setText(mBack.yusg_num*mBack.yusg_price+"");
		
		mPrice11.setText(mBack.lingyf_price+"");
		mNumb11.setCurrentNumber(mBack.lingyf_num+"");
		mTotal11.setText(mBack.lingyf_num*mBack.lingyf_price+"");
		
		mPrice12.setText(mBack.lingylt_price+"");
		mNumb12.setCurrentNumber(mBack.lingylt_num+"");
		mTotal12.setText(mBack.lingylt_num*mBack.lingylt_price+"");
		
		mPrice13.setText(mBack.fangdm_price+"");
		mNumb13.setCurrentNumber(mBack.fangdm_num+"");
		mTotal13.setText(mBack.fangdm_num*mBack.fangdm_price+"");
		
		mPrice14.setText(mBack.dib_price+"");
		mNumb14.setCurrentNumber(mBack.dib_price+"");
		mTotal14.setText(mBack.dib_num*mBack.dib_price+"");
		
		mPrice15.setText(mBack.tul_price+"");
		mNumb15.setCurrentNumber(mBack.tul_num+"");
		mTotal15.setText(mBack.tul_num*mBack.tul_price+"");
		
		mPrice16.setText(mBack.mum_price+"");
		mNumb16.setCurrentNumber(mBack.mum_num+"");
		mTotal16.setText(mBack.mum_num*mBack.mum_price+"");
		
		mPrice17.setText(mBack.yangtc_price+"");
		mNumb17.setCurrentNumber(mBack.yangtc_num+"");
		mTotal17.setText(mBack.yangtc_num*mBack.yangtc_price+"");
		
		mPrice18.setText(mBack.kaig_price+"");
		mNumb18.setCurrentNumber(mBack.kaig_num+"");
		mTotal18.setText(mBack.kaig_num*mBack.kaig_price+"");
		
		mBasePrice.setText(mBack.base_price+"");
		mAccessPrice.setText(mBack.access_price+"");
		mWorkerPrice.setText(mBack.worker_price+"");
		mTotalPrice.setText(mBack.final_price+"");
		
		mLL.setVisibility(View.VISIBLE);
	}
	
	public class GeneratePriceThread extends Thread{
		public void run(){
			boolean success = false;
			mData.area = area;
			mData.chu = chu;
			mData.dec_way = dec_way;
			mData.room = room;
			mData.ting = ting;
			mData.wei = wei;
			mData.yangtai = yangtai;
			success = HTTPPost.GeneratePrice(mData, mBack);
			Message msg = Message.obtain();
			msg.what = 1;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_price);

		mLL = (LinearLayout)findViewById(R.id.decorate_price_ll);
		mLL.setVisibility(View.GONE);
		initActionBar();
		Intent intent = getIntent();
		area = intent.getIntExtra("area", 100);
		ting = intent.getIntExtra("ting", 1);
		room = intent.getIntExtra("room", 1);
		chu = intent.getIntExtra("chu", 1);
		wei = intent.getIntExtra("wei", 1);
		yangtai = intent.getIntExtra("yangtai", 1);
		dec_way = intent.getStringExtra("dec_way");
		mSavePrice = (Button)findViewById(R.id.decorate_save_price);
		mSavePrice.setOnClickListener(this);
		
		mBasePrice = (TextView)findViewById(R.id.price_base);
		mAccessPrice = (TextView)findViewById(R.id.price_access);
		mWorkerPrice = (TextView)findViewById(R.id.price_worker);
		mTotalPrice = (TextView)findViewById(R.id.price_total);
		
		mPrice1 = (TextView)findViewById(R.id.decorate_price1);
		mNumb1 = (AddSubLL)findViewById(R.id.decorate_num1);
		mNumb1.setCurrentNumber(2+"");
		mNumb1.registerListener(this);
		mTotal1 = (TextView)findViewById(R.id.decorate_price_total1);
		
		mPrice2 = (TextView)findViewById(R.id.decorate_price2);
		mNumb2 = (AddSubLL)findViewById(R.id.decorate_num2);
		mNumb2.registerListener(this);
		mNumb2.setCurrentNumber(2+"");
		mTotal2 = (TextView)findViewById(R.id.decorate_price_total2);
		
		mPrice3 = (TextView)findViewById(R.id.decorate_price3);
		mNumb3 = (AddSubLL)findViewById(R.id.decorate_num3);
		mNumb3.registerListener(this);
		mNumb3.setCurrentNumber(1+"");
		mTotal3 = (TextView)findViewById(R.id.decorate_price_total3);
		
		mPrice4 = (TextView)findViewById(R.id.decorate_price4);
		mNumb4 = (AddSubLL)findViewById(R.id.decorate_num4);
		mNumb4.registerListener(this);
		mNumb4.setCurrentNumber(50+"");
		mTotal4 = (TextView)findViewById(R.id.decorate_price_total4);
		
		mPrice5 = (TextView)findViewById(R.id.decorate_price5);
		mNumb5 = (AddSubLL)findViewById(R.id.decorate_num5);
		mNumb5.registerListener(this);
		mNumb5.setCurrentNumber(1+"");
		mTotal5 = (TextView)findViewById(R.id.decorate_price_total5);
		
		mPrice6 = (TextView)findViewById(R.id.decorate_price6);
		mNumb6 = (AddSubLL)findViewById(R.id.decorate_num6);
		mNumb6.registerListener(this);
		mNumb6.setCurrentNumber(1+"");
		mTotal6 = (TextView)findViewById(R.id.decorate_price_total6);
		
		mPrice7 = (TextView)findViewById(R.id.decorate_price7);
		mNumb7 = (AddSubLL)findViewById(R.id.decorate_num7);
		mNumb7.registerListener(this);
		mNumb7.setCurrentNumber(1+"");
		mTotal7 = (TextView)findViewById(R.id.decorate_price_total7);
		
		mPrice8 = (TextView)findViewById(R.id.decorate_price8);
		mNumb8 = (AddSubLL)findViewById(R.id.decorate_num8);
		mNumb8.registerListener(this);
		mNumb8.setCurrentNumber(10+"");
		mTotal8 = (TextView)findViewById(R.id.decorate_price_total8);
		
		mPrice9 = (TextView)findViewById(R.id.decorate_price9);
		mNumb9 = (AddSubLL)findViewById(R.id.decorate_num9);
		mNumb9.registerListener(this);
		mNumb9.setCurrentNumber(1+"");
		mTotal9 = (TextView)findViewById(R.id.decorate_price_total9);
		
		mPrice10 = (TextView)findViewById(R.id.decorate_price10);
		mNumb10 = (AddSubLL)findViewById(R.id.decorate_num10);
		mNumb10.registerListener(this);
		mNumb10.setCurrentNumber(1+"");
		mTotal10 = (TextView)findViewById(R.id.decorate_price_total10);
		
		mPrice11 = (TextView)findViewById(R.id.decorate_price11);
		mNumb11 = (AddSubLL)findViewById(R.id.decorate_num11);
		mNumb11.registerListener(this);
		mNumb11.setCurrentNumber(50+"");
		mTotal11 = (TextView)findViewById(R.id.decorate_price_total11);
		
		mPrice12 = (TextView)findViewById(R.id.decorate_price12);
		mNumb12 = (AddSubLL)findViewById(R.id.decorate_num12);
		mNumb12.registerListener(this);
		mNumb12.setCurrentNumber(1+"");
		mTotal12 = (TextView)findViewById(R.id.decorate_price_total12);
		
		mPrice13 = (TextView)findViewById(R.id.decorate_price13);
		mNumb13 = (AddSubLL)findViewById(R.id.decorate_num13);
		mNumb13.registerListener(this);
		mNumb13.setCurrentNumber(3+"");
		mTotal13 = (TextView)findViewById(R.id.decorate_price_total13);
		
		mPrice14 = (TextView)findViewById(R.id.decorate_price14);
		mNumb14 = (AddSubLL)findViewById(R.id.decorate_num14);
		mNumb14.registerListener(this);
		mNumb14.setCurrentNumber(50+"");
		mTotal14 = (TextView)findViewById(R.id.decorate_price_total14);
		
		mPrice15 = (TextView)findViewById(R.id.decorate_price15);
		mNumb15 = (AddSubLL)findViewById(R.id.decorate_num15);
		mNumb15.registerListener(this);
		mNumb15.setCurrentNumber(20+"");
		mTotal15 = (TextView)findViewById(R.id.decorate_price_total15);
		
		mPrice16 = (TextView)findViewById(R.id.decorate_price16);
		mNumb16 = (AddSubLL)findViewById(R.id.decorate_num16);
		mNumb16.registerListener(this);
		mNumb16.setCurrentNumber(1+"");
		mTotal16 = (TextView)findViewById(R.id.decorate_price_total16);
		
		mPrice17 = (TextView)findViewById(R.id.decorate_price17);
		mNumb17 = (AddSubLL)findViewById(R.id.decorate_num17);
		mNumb17.registerListener(this);
		mNumb17.setCurrentNumber(1+"");
		mTotal17 = (TextView)findViewById(R.id.decorate_price_total17);
		
		mPrice18 = (TextView)findViewById(R.id.decorate_price18);
		mNumb18 = (AddSubLL)findViewById(R.id.decorate_num18);
		mNumb18.registerListener(this);
		mNumb18.setCurrentNumber(1+"");
		mTotal18 = (TextView)findViewById(R.id.decorate_price_total18);
		
		new GeneratePriceThread().start();
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
			mTotal1.setText(mNumb1.getCurrentNumber()*Integer.parseInt(mPrice1.getText().toString())+"");
			break;
		case R.id.decorate_num2:
			mTotal2.setText(mNumb2.getCurrentNumber()*Integer.parseInt(mPrice2.getText().toString())+"");
			break;
		case R.id.decorate_num3:
			mTotal3.setText(mNumb3.getCurrentNumber()*Integer.parseInt(mPrice3.getText().toString())+"");
			break;
		case R.id.decorate_num4:
			mTotal4.setText(mNumb4.getCurrentNumber()*Integer.parseInt(mPrice4.getText().toString())+"");
			break;
		case R.id.decorate_num5:
			mTotal5.setText(mNumb5.getCurrentNumber()*Integer.parseInt(mPrice5.getText().toString())+"");
			break;
		case R.id.decorate_num6:
			mTotal6.setText(mNumb6.getCurrentNumber()*Integer.parseInt(mPrice6.getText().toString())+"");
			break;
		case R.id.decorate_num7:
			mTotal7.setText(mNumb7.getCurrentNumber()*Integer.parseInt(mPrice7.getText().toString())+"");
			break;
		case R.id.decorate_num8:
			mTotal8.setText(mNumb8.getCurrentNumber()*Integer.parseInt(mPrice8.getText().toString())+"");
			break;
		case R.id.decorate_num9:
			mTotal9.setText(mNumb9.getCurrentNumber()*Integer.parseInt(mPrice9.getText().toString())+"");
			break;
		case R.id.decorate_num10:
			mTotal10.setText(mNumb10.getCurrentNumber()*Integer.parseInt(mPrice10.getText().toString())+"");
			break;
		case R.id.decorate_num11:
			mTotal11.setText(mNumb11.getCurrentNumber()*Integer.parseInt(mPrice11.getText().toString())+"");
			break;
		case R.id.decorate_num12:
			mTotal12.setText(mNumb12.getCurrentNumber()*Integer.parseInt(mPrice12.getText().toString())+"");
			break;
		case R.id.decorate_num13:
			mTotal13.setText(mNumb13.getCurrentNumber()*Integer.parseInt(mPrice13.getText().toString())+"");
			break;
		case R.id.decorate_num14:
			mTotal14.setText(mNumb14.getCurrentNumber()*Integer.parseInt(mPrice14.getText().toString())+"");
			break;
		case R.id.decorate_num15:
			mTotal15.setText(mNumb15.getCurrentNumber()*Integer.parseInt(mPrice15.getText().toString())+"");
			break;
		case R.id.decorate_num16:
			mTotal16.setText(mNumb16.getCurrentNumber()*Integer.parseInt(mPrice16.getText().toString())+"");
			break;
		case R.id.decorate_num17:
			mTotal17.setText(mNumb17.getCurrentNumber()*Integer.parseInt(mPrice17.getText().toString())+"");
			break;
		case R.id.decorate_num18:
			mTotal18.setText(mNumb18.getCurrentNumber()*Integer.parseInt(mPrice18.getText().toString())+"");
			break;
			
		}
	}
}
