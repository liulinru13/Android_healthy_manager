package com.mmrx.health.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.R;
import com.mmrx.health.adapter.DrugAdapter;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.util.BitmapCache;
import com.mmrx.health.util.L;

import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment {
	
//	private ListView list;
    SwipeListView slv;
	List<Drug> d_list;
	private TextView tv;
	public DrugAdapter mAdapter;
	DbUtils dbUtils;
	public static final String ACTION="com.by.wql.broatcast";
	public DrugFragment(){
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_drug, null);
	}
	@Override
	public void onDestroyView() {

		getActivity().unregisterReceiver(receiver);
		super.onDestroyView();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {


		IntentFilter filter=new IntentFilter();
		filter.addAction(ACTION);
		getActivity().registerReceiver(receiver, filter);
		init();
		
		
		
		super.onActivityCreated(savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
//		list = (ListView) getView().findViewById(R.id.drug_list);
        slv = (SwipeListView)getView().findViewById(R.id.drug_swipe_list);
        slv.setSwipeListViewListener(new SwipeListViewListener());
		tv = (TextView) getView().findViewById(R.id.drug_tv);

		
		dbUtils=DbUtils.create(getActivity());
		d_list=new ArrayList<Drug>();
		try {
			d_list=dbUtils.findAll(Drug.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (d_list==null || d_list.size() == 0) {
			d_list=new ArrayList<Drug>();
			tv.setVisibility(View.VISIBLE);
		}

        mAdapter=new DrugAdapter(d_list, getActivity(),slv,this);
        slv.setAdapter(mAdapter);

        RelativeLayout back = (RelativeLayout)getView().findViewById(R.id.fragment_drug_layout);
//        back.setBackground(new BitmapDrawable(getResources(),
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_drug,
//                        getActivity(),15,false)));
        back.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_drug,
                        getActivity(),15,false)));
	}
	
	
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			try {
				d_list=dbUtils.findAll(Drug.class);
				if (mAdapter!=null) {
                    mAdapter.setList(d_list);
					tv.setVisibility(View.GONE);
					
				}
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

    class SwipeListViewListener extends BaseSwipeListViewListener {

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            L.i("DrugFragment--onClickFrontView");

        }
        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            L.i("SwipeListViewListener-onDismiss");
            for (int position : reverseSortedPositions) {
                d_list.remove(position);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(d_list.size() == 0)
            listIsEmpty(true);
        else
            listIsEmpty(false);
    }

    public void listIsEmpty(boolean isEmpty){
        if(isEmpty)
            tv.setVisibility(View.VISIBLE);
        else
            tv.setVisibility(View.GONE);
    }
	
	

}
