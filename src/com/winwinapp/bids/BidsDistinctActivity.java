package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BidsDistinctActivity extends ActionBarActivity {

	Button mInviteBids;
	Spinner mProvince;
	Spinner mCity;
	Spinner mCountry;
	private ArrayAdapter<String> provinceAdapter;
	private ArrayAdapter<String> cityAdapter ; 
	private ArrayAdapter<String> countryAdapter; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_distinct_select);

		initActionBar();
		mInviteBids = (Button)findViewById(R.id.distict_invite_bids);
		mInviteBids.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(BidsDistinctActivity.this,BidsPublishBids.class);
				startActivity(intent);
			}
			
		});
		
		mProvince = (Spinner)findViewById(R.id.distinct_select_province);
		mCity = (Spinner)findViewById(R.id.distinct_select_city);
		mCountry = (Spinner)findViewById(R.id.distinct_select_country);

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
}
