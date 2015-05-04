package com.mmrx.health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mmrx.health.activity.HomeActivity;
import com.mmrx.health.activity.LogActivity;
import com.mmrx.health.util.SPutil;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SPutil putil=new SPutil(this);
        //从xml中读取存储姓名信息，如果没有存储，则跳转到填写基本信息页面
		String name = putil.ReadName();
		if (name.equals("无")) {
			
			Intent intent=new Intent(this,LogActivity.class);
			startActivity(intent);
			
		}else{
			Intent intent=new Intent(this,HomeActivity.class);
			startActivity(intent);
			
		}
		
		finish();
		
		
		
	}
		


}
