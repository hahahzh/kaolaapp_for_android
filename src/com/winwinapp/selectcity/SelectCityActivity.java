package com.winwinapp.selectcity;

import java.util.ArrayList;
import java.util.List;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.my.MyProjectActivity;
import com.winwinapp.my.MyProjectCalendarActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCityActivity extends ActionBarActivity implements TextWatcher {

	private Context context_ = SelectCityActivity.this;
	private static final int REFRESH = 1;
	private CityListViewImpl listview;
	private Object searchLock = new Object();
	boolean inSearchMode = false;
	public final static String TAG = SelectCityActivity.class.getName();
	List<CityItemInterface> contactList;
	List<CityItemInterface> filterList;
	TextView mLocationCity;
	private static KoalaApplication mApp;

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REFRESH:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					
				}else{
					
				}
				setListView();
				break;
			}
		}
	};
	
	public void setListView(){
		contactList = CityData.getSampleContactList();

		CityAdapter adapter = new CityAdapter(this,R.layout.layout_city_item, contactList);

		
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView parent, View v, int position,
					long id)
			{
				List<CityItemInterface> searchList = inSearchMode ? filterList
						: contactList;

				/*Toast.makeText(context_,
						searchList.get(position).getDisplayInfo(),
						Toast.LENGTH_SHORT).show();*/
				mLocationCity.setText(searchList.get(position).getDisplayInfo());
				mApp.saveLocationCity(mLocationCity.getText().toString());
			}
		});
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_city_list);
		
		mApp = (KoalaApplication) this.getApplication();
		initActionBar();
		mLocationCity = (TextView)findViewById(R.id.location_city_name);

		filterList = new ArrayList<CityItemInterface>();
		listview = (CityListViewImpl) this.findViewById(R.id.listview);

		//searchBox = (EditText) findViewById(R.id.input_search_query);
		//searchBox.addTextChangedListener(this);
	}

	@Override
	public void onStart(){
		super.onStart();
		setListView();
		/*
		new Thread(){
			public void run(){
				boolean success;
				
				Message msg = Message.obtain();
				msg.what = REFRESH;
				if(success){
					msg.obj = "OK";
				}else{
					msg.obj = ;
				}
				mHandler.sendMessage(msg);
			}
		}.start();*/
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.cancel);
		setLeftView(imageView);
		setTitle("选择城市");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	@Override
	public void afterTextChanged(Editable s)
	{
		/*searchString = searchBox.getText().toString().trim().toUpperCase();

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
		{
			try
			{
				curSearchTask.cancel(true);
			} catch (Exception e)
			{
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); */
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode)
			{
				// get all the items matching this
				for (CityItemInterface item : contactList)
				{
					CityItem contact = (CityItem) item;

					boolean isPinyin = contact.getFullName().toUpperCase().indexOf(keyword) > -1;
					boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

					if (isPinyin || isChinese)
					{
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result)
		{

			synchronized (searchLock)
			{

				if (inSearchMode)
				{

					CityAdapter adapter = new CityAdapter(context_,R.layout.layout_city_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					CityAdapter adapter = new CityAdapter(context_,R.layout.layout_city_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

}
