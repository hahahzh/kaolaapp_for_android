package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BindCardTypeActivity extends ActionBarActivity {
	
	RadioGroup mType;
	RadioGroup mBank;
	String mStrType = "���";
	String mStrBank = "��������";
	RadioButton mICBC;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card_type);

		initActionBar();
		mType = (RadioGroup)findViewById(R.id.card_type_type);
		mBank = (RadioGroup)findViewById(R.id.card_type_bank);
		mICBC = (RadioButton)findViewById(R.id.card_type_radio3);
		
		mType.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

			@Override 
			public void onCheckedChanged(RadioGroup arg0, int arg1) { 
					// TODO Auto-generated method stub 
					//��ȡ������ѡ�����ID 
					int radioButtonId = arg0.getCheckedRadioButtonId(); 
					//����ID��ȡRadioButton��ʵ�� 
					RadioButton rb = (RadioButton)BindCardTypeActivity.this.findViewById(radioButtonId); 
					//�����ı����ݣ��Է���ѡ���� 
					mStrType = rb.getText().toString(); 
				} 
			
			}); 
		
		mICBC.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				int radioButtonId = mBank.getCheckedRadioButtonId(); 
				//����ID��ȡRadioButton��ʵ�� 
				RadioButton rb = (RadioButton)BindCardTypeActivity.this.findViewById(radioButtonId); 
				//�����ı����ݣ��Է���ѡ���� 
				mStrBank = rb.getText().toString();
				
				Intent data = new Intent();
				data.putExtra("type", mStrType);
				data.putExtra("bank", mStrBank);
				BindCardTypeActivity.this.setResult(RESULT_OK, data);
				finish();
			}
			
		});
		
		mBank.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

			@Override 
			public void onCheckedChanged(RadioGroup arg0, int arg1) { 
					// TODO Auto-generated method stub 
					//��ȡ������ѡ�����ID 
					int radioButtonId = arg0.getCheckedRadioButtonId(); 
					//����ID��ȡRadioButton��ʵ�� 
					RadioButton rb = (RadioButton)BindCardTypeActivity.this.findViewById(radioButtonId); 
					//�����ı����ݣ��Է���ѡ���� 
					mStrBank = rb.getText().toString();
					
					Intent data = new Intent();
					data.putExtra("type", mStrType);
					data.putExtra("bank", mStrBank);
					BindCardTypeActivity.this.setResult(RESULT_OK, data);
					finish();
				} 
			
			}); 
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("ѡ�����п�����");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				//Intent data = new Intent();
				//data.putExtra("type", mStrType);
				//data.putExtra("bank", mStrBank);
				//BindCardTypeActivity.this.setResult(RESULT_OK, data);
				finish();
			}
			
		});
	}

}
