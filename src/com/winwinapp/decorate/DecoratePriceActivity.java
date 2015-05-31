package com.winwinapp.decorate;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class DecoratePriceActivity extends ActionBarActivity implements OnClickListener{

	Button mSavePrice;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_price);

		initActionBar();
		mSavePrice = (Button)findViewById(R.id.decorate_save_price);
		mSavePrice.setOnClickListener(this);
		
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("���۽��");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
		switch(arg0.getId()){
		case R.id.decorate_save_price:
			showDialog();
			break;
		}
	}
	
	protected void showDialog() {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("���ۼ�¼�ѱ���ɹ���"); 
		  builder.setTitle("��ʾ");  
		  builder.setNegativeButton("�ر�", new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialoginterface, int i) {
            	  dialoginterface.dismiss();
              }
			  });
		  builder.create().show();
	}
}
