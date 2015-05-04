package com.mmrx.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.adapter.SetAdapter;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    String mFragmentTag;

    //title bar 相关设置
    ImageButton mBackBut;
    TextView mTitleTv;
    Button but_more;

	ListView list;
	List<Drug> d_list;
	private SetAdapter setAdapter;
	DbUtils dbUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		init();
	}

    private void init(){
        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mBackBut.setOnClickListener(this);
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("服药提醒");
        but_more = (Button)findViewById(R.id.title_bar_menu);
        but_more.setVisibility(View.GONE);
        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);

        list = (ListView) findViewById(R.id.set_list);

        dbUtils=DbUtils.create(getApplicationContext());
        try {
            d_list=dbUtils.findAll(Drug.class);
            if (d_list==null) {
                d_list=new ArrayList<Drug>();
            }
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setAdapter = new SetAdapter(d_list, getApplicationContext());
        list.setAdapter(setAdapter);
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
