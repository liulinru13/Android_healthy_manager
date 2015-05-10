package com.mmrx.health.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.util.SPutil;

import java.text.DecimalFormat;

@SuppressLint("NewApi")
public class HomeActivity extends BaseActivity {

	private TextView tv_bminum;
	private TextView tv_bmi;
	private float weight;
	private float readTall;
	boolean isRun=true;
	boolean f=true;
	String str_bmi="";
	private TextView tv_go;
	
	float alpha=0.0f;
	Handler handler=new Handler(){
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			tv_go.setAlpha(alpha);
			if (f) {
				alpha+=0.1f;
				if (alpha>=1) {
					f=false;
				}
			}else{
				alpha-=0.1f;
				if (alpha<=0) {
					f=true;
				}
			}
			
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		init();
		SPutil sPutil=new SPutil(this);
        //如果没有填写个人信息跳转到填写个人信息页面
        if(!sPutil.ReadLogin()){
            Intent intent = new Intent();
            intent.setClass(this,LogActivity.class);
            startActivity(intent);
        }

		weight = sPutil.ReadWeight();
		readTall = sPutil.ReadHeight();
		
		float we = weight;
		float tall = readTall;
		tall=tall/100;
		float bmi=we/(tall*tall);
		if (bmi<18.5) {
			str_bmi="过轻";
		}else if (bmi<24) {
			str_bmi="正常";
		
			
		}else if (bmi<27) {
			str_bmi="过重";
		}else if (bmi<30) {
			str_bmi="轻度肥胖";
		}else if (bmi<35) {
			str_bmi="中度肥胖";
		}else {
			str_bmi="重度肥胖";
		}
		
		DecimalFormat decimalFormat=new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(1);
		String format = decimalFormat.format(bmi);
		
		sPutil.WriteBMI(format);
		tv_bminum.setText(format);
		tv_bmi.setText(str_bmi);
		
		new Thread(){
			public void run() {
				while (isRun) {
					handler.sendEmptyMessage(0);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			};
		}.start();
//        new Thread(){
//            @Override
//            public void run() {
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_food,
//                        HomeActivity.this,10, false);
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_sleep,
//                        HomeActivity.this,15, false);
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_build,
//                        HomeActivity.this,2, false);
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_drug,
//                        HomeActivity.this,30, false);
//            }
//        }.start();
	}

	private void init() {
		// TODO Auto-generated method stub

		tv_bminum = (TextView) findViewById(R.id.bmi_tv);
		tv_bmi = (TextView) findViewById(R.id.bmi_tv2);
		tv_go = (TextView) findViewById(R.id.bmi_tv_go);
		tv_go.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(HomeActivity.this,ManageActivity.class);
				startActivity(it);
				
				isRun=false;
				finish();
			}
		});
		
		
	}

		

}
