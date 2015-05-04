package com.mmrx.health.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.adapter.BuildingAdapter;
import com.mmrx.health.bean.BodyBuildingBean;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar_back:

                break;
            case R.id.title_bar_add:

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
