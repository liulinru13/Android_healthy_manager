package com.mmrx.health.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mmrx.health.R;
import com.mmrx.health.bean.Drug;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrugAdapter extends BaseAdapter {

	
	private List<Drug>  list;
	private LayoutInflater inflater;
	
	
	public DrugAdapter() {
		super();
	}
	

	public DrugAdapter(List<Drug> list, Context context) {
		super();
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}


	public void setList(List<Drug> list){
		this.list=list;
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

	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		
		v=inflater.inflate(R.layout.item, null);
		
		TextView tv_name = (TextView) v.findViewById(R.id.item_tv_name);
		TextView tv_state = (TextView) v.findViewById(R.id.item_tv_state);
		TextView tv_baozhiqi = (TextView) v.findViewById(R.id.item_tv_baozhiqi);
		LinearLayout la = (LinearLayout) v.findViewById(R.id.item_lay);
		
		Drug drug = list.get(position);

		if (drug.isEdible()) {
			la.setVisibility(View.VISIBLE);
		}
		int month = drug.getBaozhiqi();
		
		 Long Interval=(long) (30L*24L*60L*60L*1000L);
		
		
		long a=drug.getShengchanriqi()+(month*Interval);
		
		Date date=new Date(a);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(dateFormat.format(date)+"-----ʱ��");
		
	
		
		if (a>System.currentTimeMillis()) {
			tv_state.setVisibility(View.GONE);
		}
		
		tv_name.setText(drug.getName());
		tv_baozhiqi.setText("������ �� "+drug.getBaozhiqi()+"����");
		return v;
	}
	
	
}
