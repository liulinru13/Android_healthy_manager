package com.mmrx.health.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;

public class AlarmActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
//        init();
		new AlertDialog.Builder(AlarmActivity.this).
        setTitle("闹钟").
        setMessage("时间到了！").
        setPositiveButton("知道了", new DialogInterface.OnClickListener(){//���ð�ť
            public void onClick(DialogInterface dialog, int which) {
                AlarmActivity.this.finish();
            }
        }).create().show();
	}

//    private void init(){
//        LinearLayout linear = (LinearLayout)findViewById(R.id.layout);
//        List<AbsMetroNode> list = new ArrayList<AbsMetroNode>();
//
//        for(int i=0;i<6;i++)
//            list.add(new BodyBuildingBean());
//
//        for(int i=0;i<3;i++) {
//            BodyBuildingBean bbb = new BodyBuildingBean("text",i+"",R.drawable.icon_basketball100,3, MetroConstant.MetroStyle.VERTICAL,20,14,R.color.red,false);
//            list.add(bbb);
//        }
//        list.add(new BodyBuildingBean("text","22222",R.drawable.icon_basketball100,2, MetroConstant.MetroStyle.VERTICAL,20,14,R.color.red,false));
//        list.add(new BodyBuildingBean("text","3333",R.drawable.icon_basketball100,2, MetroConstant.MetroStyle.HORIZONTAL,20,14,R.color.red,false));
//
//        MetroAdapter ma = new MetroAdapter(list,(Context)(this));
//        for(LinearLayout ll:ma.getLayoutList()){
//            linear.addView(ll);
//        }
//        ma.invalidateMv();
//    }

	

}
