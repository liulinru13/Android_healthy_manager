package com.mmrx.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.adapter.BuildingAdapter;
import com.mmrx.health.bean.BodyBuildingBean;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.L;

import java.util.ArrayList;
import java.util.List;

public class BodyBuildingSettingActivity extends BaseActivity implements View.OnClickListener{

    ImageButton mBackBut;
    Button mAddBut;

    SwipeListView slv;
    BuildingAdapter mAdapter;

    DbUtils dbUtils;
    List<BodyBuildingBean> mlist;

//    String mFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_building_setting);
        init();
    }

    private void init(){
        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mAddBut = (Button)findViewById(R.id.title_bar_add);
        slv = (SwipeListView)findViewById(R.id.build_swipe_list);
        slv.setSwipeListViewListener(new SwipeListViewListener());
        dbUtils = DbUtils.create(this);
        mBackBut.setOnClickListener(this);
        mAddBut.setOnClickListener(this);

//        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);

        try {
            mlist = dbUtils.findAll(BodyBuildingBean.class);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mlist == null) {
            mlist = new ArrayList<BodyBuildingBean>();
        }
        mAdapter = new BuildingAdapter(mlist,getApplicationContext(),slv);
        slv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新数据源
        try {
            mlist = dbUtils.findAll(BodyBuildingBean.class);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.title_bar_back:
                intent.setClass(this,ManageActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,Constant.FRAGMENT_TAG_BUILD);
                startActivity(intent);
                break;
            case R.id.title_bar_add:
                if(mlist.size()<7){
                    intent.setClass(this,BodyBuildingSettingDetailActivity.class);
    //                -1表示新建
                    intent.putExtra(Constant.BODY_BUILDING_BEAN_ID,-1);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this, "健身计划超出最大数量(7项),请删除原有计划后再添加.", Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }

    class SwipeListViewListener extends BaseSwipeListViewListener {

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
//            Toast.makeText(getApplicationContext(), m_list.get(position).toString(), Toast.LENGTH_SHORT).show();
            //获取bean的id，传入到具体的健身计划设置页面
            Intent intent = new Intent(BodyBuildingSettingActivity.this,BodyBuildingSettingDetailActivity.class);
            intent.putExtra(Constant.BODY_BUILDING_BEAN_ID,mlist.get(position).getId());
            startActivity(intent);
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                L.i("SwipeListViewListener-onDismiss");
                mlist.remove(position);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
