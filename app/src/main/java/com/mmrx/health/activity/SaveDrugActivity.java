package com.mmrx.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.fragment.DrugFragment;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.MyToast;

import java.util.Date;

public class SaveDrugActivity extends BaseActivity implements OnClickListener{

	Button but_dismiss;
	EditText et_baozhiqi;
	EditText et_name;
	EditText et_month;
	EditText et_year;
	DbUtils dbUtils;

    //title bar 相关设置
    ImageButton mBackBut;
    TextView mTitleTv;
    Button but_more;

    String mFragmentTag;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_drug);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		dbUtils=DbUtils.create(this);
		but_dismiss = (Button) findViewById(R.id.sd_but_submit);
		et_baozhiqi = (EditText) findViewById(R.id.sd_et_baozhiqi);
		et_name = (EditText) findViewById(R.id.sd_et_name);
		et_month = (EditText) findViewById(R.id.sd_et_month);
		et_year = (EditText) findViewById(R.id.sd_et_year);

        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mBackBut.setOnClickListener(this);
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("药品录入");
        but_more = (Button)findViewById(R.id.title_bar_menu);
        but_more.setVisibility(View.GONE);

        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);
		
		but_dismiss.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = et_name.getText().toString();
				if ("".equals(name)) {
					MyToast.showShort(getApplicationContext(), "请输入完整信息");
					return;
				}
				
				String string = et_month.getText().toString();
				if ("".equals(string)) {
					MyToast.showShort(getApplicationContext(), "请输入完整信息");
					return;
				}
				int month = Integer.parseInt(string);
				
				
				String string1 = et_year.getText().toString();
				if ("".equals(string1)) {
					MyToast.showShort(getApplicationContext(), "请输入完整信息");
					return;
				}
				int year = Integer.parseInt(string1);
				
				
				String string2 = et_baozhiqi.getText().toString();
				if ("".equals(string2)) {
					MyToast.showShort(getApplicationContext(), "请输入完整信息");
					return;
				}
				int baozhiqi = Integer.parseInt(string2);

				Date date=new Date(year-1900, month-1, 1);
				
				try {
					dbUtils.save(new Drug(name, date.getTime(), baozhiqi));
					MyToast.showShort(getApplicationContext(), "录入成功");
					Intent intent=new Intent();
					intent.setAction(DrugFragment.ACTION);
					sendBroadcast(intent);
					finish();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
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
