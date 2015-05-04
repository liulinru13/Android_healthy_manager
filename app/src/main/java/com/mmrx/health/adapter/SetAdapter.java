package com.mmrx.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.R;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.fragment.DrugFragment;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends BaseAdapter {

	List<Drug> list;
	LayoutInflater inflater;
	List<Integer> n_list;
	DbUtils dbUtils;
	Context context;
	
	public List<Integer> getN_list() {
		return n_list;
	}
	public SetAdapter(){
		super();
	}
	
	public SetAdapter(List<Drug> list,Context v){
		this.list=list;
		this.context= v;
		this.inflater=LayoutInflater.from(v);
		n_list=new ArrayList<Integer>();
		dbUtils=DbUtils.create(v);
	}
	public List<Drug> getList() {
		return list;
	}


	public void setList(List<Drug> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}


	
	
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View v, ViewGroup arg2) {
		v=inflater.inflate(R.layout.setitem, null);
		CheckBox cb = (CheckBox) v.findViewById(R.id.sitem_cb);
		TextView tv = (TextView) v.findViewById(R.id.sitem_tv);
		tv.setText(list.get(position).getName());
		if (list.get(position).isEdible()) {
			cb.setChecked(true);
			System.out.println("ִ��");
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean f) {
				// TODO Auto-generated method stub
				Drug drug = list.get(position);
				drug.setEdible(f);
				try {
					dbUtils.update(drug, "isEdible");
					Intent intent=new Intent();
					intent.setAction(DrugFragment.ACTION);
					context.sendBroadcast(intent);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				if (f) {
//					if (!n_list.contains(position)) {
//						n_list.add(position);
//					}
//				}else{
//					if (n_list.contains(position)) {
//						n_list.remove(position);
//					}
//				}
			}
		});
		
		
		
		return v;
	}

}
