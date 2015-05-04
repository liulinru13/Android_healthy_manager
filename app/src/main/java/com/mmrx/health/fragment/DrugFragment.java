package com.mmrx.health.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.R;
import com.mmrx.health.adapter.DrugAdapter;
import com.mmrx.health.bean.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment {
	
	private ListView list;
	List<Drug> d_list;
	private TextView tv;
	public DrugAdapter adapter;
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
		list = (ListView) getView().findViewById(R.id.drug_list);
		tv = (TextView) getView().findViewById(R.id.drug_tv);
		
		dbUtils=DbUtils.create(getActivity());
		d_list=new ArrayList<Drug>();
		try {
			d_list=dbUtils.findAll(Drug.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (d_list==null) {
			d_list=new ArrayList<Drug>();
			tv.setVisibility(View.VISIBLE);
		}
		
//		d_list.add(new Drug(0, "��Ī����", 1429739959613L, 0));
//		d_list.add(new Drug(0, "��Ī����", 1429739959613L, 1429739959613L));
//		d_list.add(new Drug(0, "��Ī����", 1429739959613L, 0));
//		d_list.add(new Drug(0, "��Ī����", 1429739959613L, 0));
//		
		adapter=new DrugAdapter(d_list, getActivity());
		list.setAdapter(adapter);
	}
	
	
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			try {
				d_list=dbUtils.findAll(Drug.class);
				if (adapter!=null) {
					adapter.setList(d_list);
					tv.setVisibility(View.GONE);
					
				}
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	

}
