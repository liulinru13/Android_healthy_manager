package com.mmrx.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.bean.User;
import com.mmrx.health.util.MyToast;
import com.mmrx.health.util.SPutil;


public class LogActivity extends BaseActivity {

	@ViewInject(R.id.log_but_dismiss)
	Button but_dismiss;
	@ViewInject(R.id.log_et_name)
	EditText et_name;
	@ViewInject(R.id.log_et_age)
	EditText et_age;
	@ViewInject(R.id.log_rg)
	RadioGroup group;
	@ViewInject(R.id.log_et_tall)
	EditText et_tall;
	@ViewInject(R.id.log_et_weight)
	EditText et_weight;
	
	
	String gender="男";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		ViewUtils.inject(this);
		but_dismiss.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
				
			}
		});
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int checkedRadioButtonId = arg0.getCheckedRadioButtonId();
				if (checkedRadioButtonId==R.id.log_rb_female) {
					gender="女";
				}
				if (checkedRadioButtonId==R.id.log_rb_male) {
					gender="男";
				}
				
			}
		});
		
	}
	
	public void dismiss(){
		String name = et_name.getText().toString();
		if ("".equals(name)) {
			MyToast.showShort(getApplicationContext(), "请输入您的名字");
			return;
		}
		
		String str_tall = et_tall.getText().toString();
		if ("".equals(str_tall)) {
			MyToast.showShort(getApplicationContext(), "请输入您的身高");
			return;
		}
		int tall = Integer.parseInt(str_tall);
		
		String str_weight = et_weight.getText().toString();
		
		if ("".equals(str_weight)) {
			MyToast.showShort(getApplicationContext(), "请输入您的体重");
			return;
		}
		int weight = Integer.parseInt(str_weight);
		
		
		String str_age = et_age.getText().toString();
		
		if ("".equals(str_age)) {
			MyToast.showShort(getApplicationContext(), "请输入您的年龄");
			return;
		}
		int age = Integer.parseInt(str_age);
		
		
		
//		String gender = et_gender.getText().toString();
//		if ("".equals(gender)) {
//			T.showShort(getApplicationContext(), "����������Ա�");
//			return;
//		}
//		
		User u=new User(name, age, gender, tall, weight);
		SPutil putil=new SPutil(getApplicationContext());
        putil.WriteLogin(true);
		putil.WriteUser(u);
		Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
		startActivity(intent);
	}
	

	

}
