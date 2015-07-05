package com.winwinapp.koala;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class WebActivity extends ActionBarActivity {

	public String mTitle = "广告页面";
	WebView mWebView;
	public String mUrl = "http://120.26.197.206/index.php";
	public final static String mDefaultUrl = "http://120.26.197.206/index.php";
	int mPage = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webpage);
		
		initActionBar();
		
		mPage = getIntent().getIntExtra("page", -1);
		Toast.makeText(this, "page "+ mPage+" is clicked", Toast.LENGTH_LONG).show();
		mUrl = getIntent().getStringExtra("url");
		if(mUrl == null){
			mUrl = mDefaultUrl;
		}
		initWebView();
	}
	

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle(mTitle);
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}
	
	public void initWebView(){
		mWebView = (WebView)findViewById(R.id.webView);
		mWebView.loadUrl(mUrl);
		mWebView.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
	}

	
}
