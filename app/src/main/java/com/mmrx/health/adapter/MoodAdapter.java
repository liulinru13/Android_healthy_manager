package com.mmrx.health.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.mmrx.health.R;
import com.mmrx.health.bean.Mood;
import com.mmrx.health.util.L;

import java.util.List;

public class MoodAdapter extends BaseAdapter{

	
	List<Mood> list;
	LayoutInflater inflater;
    Context mContext;
    SwipeListView mSwipeList;
	
	public MoodAdapter(){
		super();
	}
	
	
	public List<Mood> getList() {
		return list;
	}


	public void setList(List<Mood> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}


	public MoodAdapter(List<Mood> list,Context context,SwipeListView swipeList){
		this.list=list;
		this.inflater=LayoutInflater.from(context);
        this.mContext = context;
        this.mSwipeList = swipeList;
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
            v=inflater.inflate(R.layout.mood_list_item, null);
            holder = new ViewHolder();
            holder.contentText = (TextView)v.findViewById(R.id.mood_tv_content);
            holder.timeText = (TextView)v.findViewById(R.id.mood_tv_time);
            holder.deleteTv = (TextView)v.findViewById(R.id.mood_tv_delete);
//            holder.deleteTv.setTag(position);
            v.setTag(holder);
        }
        else{
            holder = (ViewHolder)v.getTag();
        }
		String s_state="";
		Mood mood = list.get(position);
        holder.timeText.setText(mood.getTime());
		int state = mood.getState();
		switch (state) {
		case 1:
            holder.contentText.setTextColor(Color.GREEN);
            TextPaint tp = holder.contentText.getPaint();
            tp.setFakeBoldText(true);
			s_state="心情好  ";
			break;
		case 2:
            holder.contentText.setTextColor(Color.BLUE);
            TextPaint tp2 = holder.contentText.getPaint();
            tp2.setFakeBoldText(true);
			s_state="心情一般  ";
			break;
		case 3:
            holder.contentText.setTextColor(Color.RED);
            TextPaint tp3 = holder.contentText.getPaint();
            tp3.setFakeBoldText(true);
			s_state="心情不好  ";
			break;

		default:
			break;
		}
        holder.contentText.setText(s_state+mood.getContent());
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mood mood_temp = list.remove(position);
                DbUtils dbUtils = DbUtils.create(mContext);
                L.i("MoodAdapter-deletebn");
                try{
                    dbUtils.deleteById(Mood.class,mood_temp.getId());
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
        TextView timeText;
        TextView contentText;
        TextView deleteTv;
    }

}
