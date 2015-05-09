package com.mmrx.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.mmrx.health.R;
import com.mmrx.health.bean.Drug;
import com.mmrx.health.fragment.DrugFragment;
import com.mmrx.health.util.L;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrugAdapter extends BaseAdapter {

	
	private List<Drug>  list;
	private LayoutInflater inflater;
    private Context mContext;
    private SwipeListView mSwipeList;
    private DrugFragment fragment;

	
	public DrugAdapter() {
		super();
	}
	

	public DrugAdapter(List<Drug> list, Context context,SwipeListView swipeList,DrugFragment fragment) {
		super();
		this.list = list;
		this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mSwipeList = swipeList;
        this.fragment = fragment;
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

	public View getView(final int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

        if(v == null) {
            v=inflater.inflate(R.layout.drug_item_list, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) v.findViewById(R.id.item_tv_name);
            holder.tv_state = (TextView) v.findViewById(R.id.item_tv_state);
            holder.tv_baozhiqi = (TextView) v.findViewById(R.id.item_tv_baozhiqi);
            holder.drug_tv_delete = (TextView)v.findViewById(R.id.drug_tv_delete);
            v.setTag(holder);
        }
        else{
            holder = (ViewHolder)v.getTag();
        }
		Drug drug = list.get(position);
//		if (drug.isEdible()) {
//            holder.la.setVisibility(View.VISIBLE);
//		}
		int month = drug.getBaozhiqi();
		
		 Long Interval=(long) (30L*24L*60L*60L*1000L);
		
		
		long a=drug.getShengchanriqi()+(month*Interval);
		
		Date date=new Date(a);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat.format(date)+"-----时间");
		if (a>System.currentTimeMillis()) {
            holder.tv_state.setVisibility(View.GONE);
		}
        holder.tv_name.setText(drug.getName());
        holder.tv_baozhiqi.setText("保质期 ： "+drug.getBaozhiqi()+"个月");

        holder.drug_tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drug drug_temp = list.remove(position);
                DbUtils dbUtils = DbUtils.create(mContext);
                L.i("DrugAdapter-deletebn");
                try{
                    dbUtils.deleteById(Drug.class,drug_temp.getId());
                }catch (com.lidroid.xutils.exception.DbException dbe){
                    dbe.printStackTrace();
                }
                notifyDataSetChanged();
                //如果移除后列表为空，显示那个框框
                if(list.size() == 0)
                    fragment.listIsEmpty(true);
                mSwipeList.closeOpenedItems();
            }
        });

		return v;
	}

    private class ViewHolder{
        TextView tv_name;
        TextView tv_state;
        TextView tv_baozhiqi;
        TextView drug_tv_delete;
    }
	
	
}
