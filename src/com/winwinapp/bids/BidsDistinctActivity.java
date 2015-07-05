package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class BidsDistinctActivity extends ActionBarActivity {

	//Button mInviteBids;
	private static final int REFRESH_SPINNER = 1;
	Spinner mProvince;
	Spinner mCity;
	Spinner mCountry;
	private ArrayAdapter<String> provinceAdapter;
	private ArrayAdapter<String> cityAdapter ; 
	private ArrayAdapter<String> countryAdapter;
	NetworkData.RegionsData mData = NetworkData.getInstance().getNewRegionsData();
	NetworkData.RegionsBack mProvinceBack = NetworkData.getInstance().getNewRegionsBack();
	NetworkData.RegionsBack mCityBack = NetworkData.getInstance().getNewRegionsBack();
	NetworkData.RegionsBack mCountryBack = NetworkData.getInstance().getNewRegionsBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case REFRESH_SPINNER:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					switch(mData.type){
					case 1:
						provinceAdapter.clear();
						for(int i=0;i<mProvinceBack.regions.size();i++){
							provinceAdapter.add(mProvinceBack.regions.get(i).regions_name);
						}
						provinceAdapter.notifyDataSetChanged();
						mProvince.setSelection(0);
						new RegionThread(2,Integer.parseInt(mProvinceBack.regions.get(0).regions_id)).start();
						break;
					case 2:
						cityAdapter.clear();
						for(int i=0;i<mCityBack.regions.size();i++){
							cityAdapter.add(mCityBack.regions.get(i).regions_name);
						}
						cityAdapter.notifyDataSetChanged();
						mCity.setSelection(0);
						new RegionThread(3,Integer.parseInt(mCityBack.regions.get(0).regions_id)).start();
						break;
					case 3:
						countryAdapter.clear();
						for(int i=0;i<mCountryBack.regions.size();i++){
							countryAdapter.add(mCountryBack.regions.get(i).regions_name);
						}
						countryAdapter.notifyDataSetChanged();
						mCountry.setSelection(0);
						break;
					}
				}else{
					Toast.makeText(BidsDistinctActivity.this, "获取城市列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_distinct_select);

		initActionBar();
		/*mInviteBids = (Button)findViewById(R.id.distict_invite_bids);
		mInviteBids.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(BidsDistinctActivity.this,BidsPublishBids.class);
				startActivity(intent);
			}
			
		});*/
		
		mProvince = (Spinner)findViewById(R.id.distinct_select_province);
		mCity = (Spinner)findViewById(R.id.distinct_select_city);
		mCountry = (Spinner)findViewById(R.id.distinct_select_country);
		mProvince.setBackground(null);
		mCity.setBackground(null);
		mCountry.setBackground(null);

		provinceAdapter = new ArrayAdapter<String>(this,R.layout.layout_spinner_textview);
		cityAdapter = new ArrayAdapter<String>(this,R.layout.layout_spinner_textview);
		countryAdapter = new ArrayAdapter<String>(this,R.layout.layout_spinner_textview);
		provinceAdapter.add("上海");
		cityAdapter.add("上海");
		countryAdapter.add("闸北区");
		
		mProvince.setAdapter(provinceAdapter);
		mCity.setAdapter(cityAdapter);
		mCountry.setAdapter(countryAdapter);
		
		mProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
        {  
            @Override  
            public void onItemSelected(AdapterView<?> arg0, View arg1,  
                    int arg2, long arg3)  
            {  
                // TODO Auto-generated method stub
            	if(arg2 >= 0 && arg2 < mProvinceBack.regions.size()){
            		new RegionThread(2,Integer.parseInt(mProvinceBack.regions.get(arg2).regions_id)).start();
            	}
            }  
            @Override  
            public void onNothingSelected(AdapterView<?> arg0)  
            {  
                // TODO Auto-generated method stub  
                  
            }  
              
        });  
		
		mCity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
        {  
            @Override  
            public void onItemSelected(AdapterView<?> arg0, View arg1,  
                    int arg2, long arg3)  
            {  
                // TODO Auto-generated method stub 
            	if(arg2 >= 0 && arg2 < mCityBack.regions.size()){
            		new RegionThread(3,Integer.parseInt(mCityBack.regions.get(arg2).regions_id)).start();
            	}
            	
            }  
            @Override  
            public void onNothingSelected(AdapterView<?> arg0)  
            {  
                // TODO Auto-generated method stub  
                  
            }  
              
        });
		
		mCountry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
        {  
            @Override  
            public void onItemSelected(AdapterView<?> arg0, View arg1,  
                    int arg2, long arg3)  
            {  
                // TODO Auto-generated method stub      
            }  
            @Override  
            public void onNothingSelected(AdapterView<?> arg0)  
            {  
                // TODO Auto-generated method stub  
                  
            }  
              
        });
		
		new RegionThread(1,1).start();
	}
	
	public class RegionThread extends Thread{
		int mType = 0;
		int mParent;
		public RegionThread(int type,int parent){
			mType = type;
			mParent = parent;
		}
		
		public void run(){
			boolean success = false;
			NetworkData.RegionsBack back = null;
			switch(mType){
			case 1:
				back = mProvinceBack;
				break;
			case 2:
				back = mCityBack;
				break;
			case 3:
				back = mCountryBack;
				break;
			default:
				back = NetworkData.getInstance().getNewRegionsBack();
				break;
			}
			mData.type = mType;
			mData.parent = mParent;
			back.regions.clear();
			success = HTTPPost.GetRegions(mData, back);
			Message msg = Message.obtain();
			msg.what = REFRESH_SPINNER;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = back.error;
			}
			mHandler.sendMessage(msg);
		}
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("地区");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}

	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		Intent intent = new Intent();
		intent.putExtra("province", provinceAdapter.getItem(mProvince.getSelectedItemPosition()));
		intent.putExtra("city", cityAdapter.getItem(mCity.getSelectedItemPosition()));
		intent.putExtra("country", countryAdapter.getItem(mCountry.getSelectedItemPosition()));
		this.setResult(BidsPublishBids.ACTIVITY_RESQUEST_CODE_DISTINCT, intent);
		
		super.onBackPressed();
		//Intent intent = new Intent(BidsDistinctActivity.this,BidsPublishBids.class);
		
	}
}
