package com.mmrx.health.fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmrx.health.R;
import com.mmrx.health.activity.AlarmActivity;
import com.mmrx.health.util.BitmapCache;
import com.mmrx.health.util.MyToast;
import com.mmrx.health.util.SPutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepFrament extends Fragment {

    private TextView mName;
	private TextView tv_age;
	private TextView tv_time;
	private TextView tv_alarm;

	int time=0;
	AlarmManager alarmManager=null;
	int alarmtime=0;
    private boolean f=true;
    private float alpha=0.0f;
    private boolean isRun = true;
    private MyThread mThread;
    Handler handler=new Handler(){
        @SuppressLint("NewApi")
        public void handleMessage(android.os.Message msg) {
            tv_alarm.setAlpha(alpha);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_sleep, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initview();
		SPutil putil=new SPutil(getActivity());
		int age = putil.ReadAge();
		tv_age.setText(age+"");
		if (age==-1) {
			MyToast.showShort(getActivity(), "没有您的年龄信息");
			
		}else if(age<18){
			time=9;
			alarmtime=20;
		}else if (age<59) {
			time=7;
			alarmtime=21;
		}else{
			time=5;
			alarmtime=22;
		}
		tv_time.setText(time+"小时");
		boolean readAlarm = putil.ReadAlarm();
		if (readAlarm) {
			
		}
		
		super.onActivityCreated(savedInstanceState);
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		tv_age = (TextView) getView().findViewById(R.id.sleep_tv_age);
		tv_time = (TextView) getView().findViewById(R.id.sleep_tv_time);
		tv_alarm = (TextView) getView().findViewById(R.id.sleep_tv_alarm);
        mName = (TextView)getView().findViewById(R.id.sleep_fragment_name);
        SPutil sp = new SPutil(getActivity());
        mName.setText(sp.ReadName());
		tv_alarm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("是否开启当天的早睡提醒");
				builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						MyToast.showShort(getActivity(), "设置成功");
						launchAlert();
					}

					
				});
				builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
				builder.show();
			}
		});
        RelativeLayout back = (RelativeLayout)getView().findViewById(R.id.fragment_sleep_layou);
        back.setBackground(new BitmapDrawable(getResources(),
                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_sleep,
                        getActivity(),15,false)));

	}

    @Override
    public void onResume() {
        super.onResume();
        isRun = true;
        mThread = new MyThread();
        mThread.start();
    }

    private void launchAlert() {
		// TODO Auto-generated method stub
	
		Intent intent=new Intent(getActivity(),AlarmActivity.class);
//		PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent, 0); 
		PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, 0); 
		alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
		System.out.println(System.currentTimeMillis()+"----!");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, alarmtime);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date a=new Date(calendar.getTimeInMillis());
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd hh-mm-ss");
		String format = dateFormat.format(a);
		System.out.println(format+"----format");
		
		
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , 24*60*60*1000, pi);
	}

    @Override
    public void onPause() {
        super.onPause();
        isRun = false;
    }

    class MyThread extends Thread{
        @Override
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
        }
    }
}
