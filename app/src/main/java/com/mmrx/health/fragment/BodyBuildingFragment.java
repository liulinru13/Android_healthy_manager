package com.mmrx.health.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.R;
import com.mmrx.health.activity.BodyBuildingSettingActivity;
import com.mmrx.health.bean.BodyBuildingBean;
import com.mmrx.health.util.Constant;

import java.util.ArrayList;
import java.util.List;

import mmrx.com.metrolayout.AbsMetroNode;
import mmrx.com.metrolayout.MetroAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BodyBuildingFragment extends Fragment {

    View mInflater;
    LinearLayout mLinearLayout;
    List<AbsMetroNode> mlist;
    MetroAdapter mMetroAdapter;
    DbUtils mDb;
    TextView adf;
    //刷新线程
    ReflashMetroLayoutThread mInitThread;

    Handler mHandler = new Handler(){
        @SuppressLint("NewApi")
        public void handleMessage(android.os.Message msg) {
            if(msg.what == Constant.MESSAGE_UPDATE_BODY_FRAGMENT){
                for(LinearLayout ll:mMetroAdapter.getLayoutList()){
                    mLinearLayout.addView(ll);
                }
            }

        };
    };

    public BodyBuildingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflater = inflater.inflate(R.layout.fragment_body_building, container, false);
//        Intent intent = new Intent(this.getActivity(), AlarmActivity.class);
//        startActivity(intent);
        init();
        return mInflater;
    }
    private void init(){
        mLinearLayout = (LinearLayout)mInflater.findViewById(R.id.body_building_linearlayout);
        mDb = DbUtils.create(getActivity());
        mlist = new ArrayList<AbsMetroNode>();
        mMetroAdapter = new MetroAdapter(mlist,getActivity());

        /**
         * 测试按钮
         * */
        adf = (TextView)mInflater.findViewById(R.id.test);
        adf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BodyBuildingSettingActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,Constant.FRAGMENT_TAG_BUILD);
                startActivity(intent);
            }
        });

     }

    @Override
    public void onResume() {
        super.onResume();
        mInitThread = new ReflashMetroLayoutThread();
        mInitThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //打断线程
        if(mInitThread.isAlive())
            mInitThread.interrupt();
    }

    private class ReflashMetroLayoutThread extends Thread{
        /**
         * 这个是否刷新需不需要在xml里面保存一下，只要没有修改过db里面的相关内容，就还是
         * 用原来的东西，修改后再重新刷新
         * */
        private boolean needReflash;
        @Override
        public void run() {
            if(needReflash) {
                ArrayList<AbsMetroNode> list_temp = new ArrayList<AbsMetroNode>();
//            DbUtils mDb_temp = DbUtils.create(BodyBuildingFragment.this.getActivity());
                try {
                    List<BodyBuildingBean> listFromDb = mDb.findAll(BodyBuildingBean.class);
                    if (listFromDb != null) {
                        for (BodyBuildingBean bbb : listFromDb) {
                            list_temp.add(bbb);
                        }
                        mMetroAdapter.setNodeList(list_temp);
//            mMetroAdapter.postInvalidateMv();
                        //通知刷新
                        Message message = new Message();
                        message.what = Constant.MESSAGE_UPDATE_BODY_FRAGMENT;
                        mHandler.sendMessage(message);
                    }
                } catch (DbException dbe) {
                    dbe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.needReflash = false;
            }
        }//end run

        public void setNeedReflash(boolean isNeed){
            this.needReflash = isNeed;
        }
    };
}
