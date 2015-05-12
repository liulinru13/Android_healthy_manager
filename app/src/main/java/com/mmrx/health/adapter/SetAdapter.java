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

import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.R;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.fragment.DrugFragment;
import com.mmrx.health.util.L;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends BaseAdapter {

	List<Drug> list;
	LayoutInflater inflater;
	List<Integer> n_list;
	DbUtils dbUtils;
	Context context;
	SwipeListView mSwipeList;
	public List<Integer> getN_list() {
		return n_list;
	}
	public SetAdapter(){
		super();
	}
	
	public SetAdapter(List<Drug> list,Context v,SwipeListView mSwipeList){
		this.list=list;
		this.context= v;
		this.inflater=LayoutInflater.from(v);
		n_list=new ArrayList<Integer>();
		dbUtils=DbUtils.create(v);
        this.mSwipeList = mSwipeList;
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
        ViewHolder holder = null;
        if(v == null){
            v=inflater.inflate(R.layout.drug_list_item, null);
            holder = new ViewHolder();
            holder.isAlarm = (CheckBox) v.findViewById(R.id.sitem_cb);
            holder.drugName = (TextView) v.findViewById(R.id.sitem_tv);
            holder.deleteTv = (TextView) v.findViewById(R.id.drug_tv_delete);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        holder.drugName.setText(list.get(position).getName());
		if (list.get(position).isEdible()) {
            holder.isAlarm.setChecked(true);
		}
        holder.isAlarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean f) {
				// TODO Auto-generated method stub
				Drug drug = list.get(position);
				drug.setEdible(f);
				try {
					dbUtils.update(drug, "isEdible");
					Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
					intent.setAction(DrugFragment.ACTION);
					context.sendBroadcast(intent);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drug drug_temp = list.remove(position);
                L.i("MoodAdapter-deletebn");
                try{
                    dbUtils.deleteById(Drug.class,drug_temp.getId());
                }catch (com.lidroid.xutils.exception.DbException dbe){
                    dbe.printStackTrace();
                }
                notifyDataSetChanged();
                mSwipeList.closeOpenedItems();
            }
        });
		
		return v;
	}

    private class ViewHolder{
        TextView drugName;
        CheckBox isAlarm;
        TextView deleteTv;
    }

}
