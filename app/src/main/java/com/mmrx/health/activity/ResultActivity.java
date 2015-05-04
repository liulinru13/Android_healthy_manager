package com.mmrx.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.SPutil;

public class ResultActivity extends BaseActivity implements View.OnClickListener{

	
	private TextView tv_1;
	private TextView tv_2;
	int result=0;
	private TextView tv_bmi;

    //title bar 相关设置
    ImageButton mBackBut;
    TextView mTitleTv;
    Button but_more;
    String mFragmentTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_1 = (TextView) findViewById(R.id.re_tv1);
		tv_2 = (TextView) findViewById(R.id.re_tv2);
		tv_bmi = (TextView) findViewById(R.id.re_tv_bmi);

        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);

        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mBackBut.setOnClickListener(this);
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("计算结果");
        but_more = (Button)findViewById(R.id.title_bar_menu);
        but_more.setVisibility(View.GONE);

		SPutil putil=new SPutil(this);
		float weight = putil.ReadWeight();
		float a=4.186f*24*weight;
		tv_1.setText(a+"");
		Intent intent = getIntent();
		int extra = intent.getIntExtra("result", 0);
		
		tv_2.setText(extra+"");
		tv_bmi.setText(putil.ReadBMI());
		
		
		
		
//		4.186千焦 * 24小时 * 50千克 = 5023.2千焦
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar_back:
                Intent intent = new Intent();
                intent.setClass(this,ManageActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,mFragmentTag);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
	

}
