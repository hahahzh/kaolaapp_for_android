package com.winwinapp.about;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.login.ResetPWD1Activity;

public class AboutActivity extends ActionBarActivity {

	private static final int UPDATA_CLIENT = 1;
	private static final int DOWN_ERROR = 2;
	private static final int GET_UNDATAINFO_ERROR = 3;
	private Context context = AboutActivity.this;
	private View l1;
	private View l2;
	private View l3;
	private static String ServerUrl = "http://";//address of app server
	private LinearLayout layoutProcess;
	int versionCode = 1;
	String versionName = "1.0";
	UpdataInfo info;
	TextView mVersionNameTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		
		initActionBar();
		
		l1 = (View)findViewById(R.id.about_l1);
		l2 = (LinearLayout)findViewById(R.id.about_l2);
		l3 = (LinearLayout)findViewById(R.id.about_l3);
		mVersionNameTxt = (TextView)findViewById(R.id.about_version_name);
		
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionCode = info.versionCode;
			versionName = info.versionName;
			mVersionNameTxt.setText("-----V"+versionName+"-----");
		} catch (NameNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FuncDescActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		l2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLatestVersionDialog();
			}
		});
		
		l3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FeedbackActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("����");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}
	
	private void showLatestVersionDialog(){
		AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("�汾����");  
	    builer.setMessage("�Ѿ������°汾");  
	    //����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ   
	    builer.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	            //finish(); 
	        }
    
	    }); 
	    AlertDialog dialog = builer.create();  
	    dialog.show();
	}
	/* 
	 *  
	 * �����Ի���֪ͨ�û����³���  
	 *  
	 * �����Ի���Ĳ��裺 
	 *  1.����alertDialog��builder.   
	 *  2.Ҫ��builder��������, �Ի��������,��ʽ,��ť 
	 *  3.ͨ��builder ����һ���Ի��� 
	 *  4.�Ի���show()����   
	 */  
	protected void showUpdataDialog() {  
	    AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("�汾����");  
	    builer.setMessage(info.getDescription());  
	    //����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ   
	    builer.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	            downLoadApk();  
	        }
    
	    });  
	    //����ȡ����ťʱ���е�¼  
	    builer.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            // TODO Auto-generated method stub  
	            //LoginMain();
	        	//finish();
	        }  
	    });  
	    AlertDialog dialog = builer.create();  
	    dialog.show();
	}  
	  
	/* 
	 * �ӷ�����������APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //�������Ի���  
	    pd = new  ProgressDialog(this);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("�������ظ���");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = getFileFromServer(info.getUrl(), pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //�������������Ի���  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//��װapk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //ִ�ж���  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //ִ�е���������  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    startActivity(intent);  
	}  
	
	/* 
	 * �ӷ�������ȡxml���������бȶ԰汾��  
	 */  
	public class CheckVersionTask implements Runnable{  
	  
	    public void run() {  
	        try {  
	            //����Դ�ļ���ȡ������ ��ַ   
	            String path = ServerUrl;  
	            //��װ��url�Ķ���   
	            URL url = new URL(path);  
	            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	            conn.setConnectTimeout(5000);  
	            InputStream is =conn.getInputStream();   
	            info =  getUpdataInfo(is);  
	              
	            if(info.getVersion().equals(versionName)){   
	                //LoginMain();  //�汾��ͬ
	            }else{  
	                //Log.i(TAG,"�汾�Ų�ͬ ,��ʾ�û����� ");  
	                Message msg = new Message();  
	                msg.what = UPDATA_CLIENT;  
	                handler.sendMessage(msg);  
	            }  
	        } catch (Exception e) {  
	            // ������   
	            Message msg = new Message();  
	            msg.what = GET_UNDATAINFO_ERROR;  
	            handler.sendMessage(msg);  
	            e.printStackTrace();  
	        }   
	    }  
	}  
	  
	Handler handler = new Handler(){  
	      
	    @Override  
	    public void handleMessage(Message msg) {  
	        // TODO Auto-generated method stub  
	        super.handleMessage(msg);  
	        switch (msg.what) {  
	        case UPDATA_CLIENT:  
	            //�Ի���֪ͨ�û���������   
	            showUpdataDialog();  
	            break;  
	        case GET_UNDATAINFO_ERROR:  
	            //��������ʱ   
	            Toast.makeText(getApplicationContext(), "��ȡ������������Ϣʧ��", 1).show();  
	            //LoginMain();  
	            break;    
	        case DOWN_ERROR:  
	            //����apkʧ��  
	            Toast.makeText(getApplicationContext(), "�����°汾ʧ��", 1).show();  
	            //LoginMain();  
	            break;    
	        }  
	    }  
	};  
	
	/* 
	 * ��pull�������������������ص�xml�ļ� (xml��װ�˰汾��) 
	 */  
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//���ý���������Դ   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//ʵ��  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //��ȡ�汾��  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //��ȡҪ������APK�ļ�  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //��ȡ���ļ�����Ϣ  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}  
	
	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{  
	    //�����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //��ȡ���ļ��Ĵ�С   
	        pd.setMax(conn.getContentLength());  
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");  
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //��ȡ��ǰ������  
	            pd.setProgress(total);  
	        }  
	        fos.close();  
	        bis.close();  
	        is.close();  
	        return file;  
	    }  
	    else{  
	        return null;  
	    }  
	}  
	
}
