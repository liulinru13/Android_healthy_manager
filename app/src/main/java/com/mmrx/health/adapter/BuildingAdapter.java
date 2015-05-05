package com.mmrx.health.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.mmrx.health.R;
import com.mmrx.health.bean.BodyBuildingBean;
import com.mmrx.health.util.L;

import java.util.List;

/**
 * Created by mmrx on 2015/5/3.
 */
public class BuildingAdapter extends BaseAdapter{

    List<BodyBuildingBean> mList;
    LayoutInflater inflater;
    Context mContext;
    SwipeListView mSwipeList;

    public BuildingAdapter(){
        super();
    }


    public List<BodyBuildingBean> getList() {
        return this.mList;
    }


    public void setList(List<BodyBuildingBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public BuildingAdapter(List<BodyBuildingBean> list,Context context,SwipeListView swipeList){
        this.mList=list;
        this.inflater=LayoutInflater.from(context);
        this.mContext = context;
        this.mSwipeList = swipeList;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public View getView(final int position, View v, ViewGroup arg2) {
        ViewHolder holder = null;
        if(v == null){
            v=inflater.inflate(R.layout.body_building_setting_list_litm, null);
            holder = new ViewHolder();
            holder.titleText = (TextView)v.findViewById(R.id.build_tv_title);
            holder.detailText = (TextView)v.findViewById(R.id.build_tv_detail);
            holder.checkNumText = (TextView)v.findViewById(R.id.build_tv_check_num);
            holder.isCheckText = (TextView)v.findViewById(R.id.build_tv_is_check);
            holder.deleteTv = (TextView)v.findViewById(R.id.build_tv_delete);
//            holder.deleteTv.setTag(position);
            v.setTag(holder);
        }
        else{
            holder = (ViewHolder)v.getTag();
        }

        final BodyBuildingBean bbb = mList.get(position);
        holder.titleText.setText(bbb.getmTitle());
        //加粗
        TextPaint tp = holder.titleText.getPaint();
        tp.setFakeBoldText(true);

        holder.detailText.setText(bbb.getmDetail());
        String checkNum = mContext.getResources().getString(R.string.body_check_str)
                +bbb.getCheckNum()+mContext.getResources().getString(R.string.body_day_str);
        holder.checkNumText.setText(checkNum);

        TextPaint tp_isCheck = holder.isCheckText.getPaint();
        tp_isCheck.setFakeBoldText(true);
        if(bbb.ismIsCheck()){
            holder.isCheckText.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.isCheckText.setText(mContext.getResources().getString(R.string.body_check_state_true));
        }else{
            holder.isCheckText.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.isCheckText.setText(mContext.getResources().getString(R.string.body_check_state_false));
        }
        final TextView isCheckText_final = holder.isCheckText;
        holder.isCheckText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bbb.ismIsCheck()){
                    isCheckText_final.setText(mContext.getResources().getString(R.string.body_check_state_false));
                    bbb.setmIsCheck(false);
                }else{
                    isCheckText_final.setText(mContext.getResources().getString(R.string.body_check_state_true));
                    bbb.setmIsCheck(true);
                }
                DbUtils dbUtils = DbUtils.create(mContext);
                //更新
                try{
                    dbUtils.saveOrUpdate(bbb);
                }catch (com.lidroid.xutils.exception.DbException dbe){
                    dbe.printStackTrace();
                }
            }
        });

        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyBuildingBean plan_temp = mList.remove(position);
                DbUtils dbUtils = DbUtils.create(mContext);
                L.i("BuildingAdapter-deletebn");
                try{
                    dbUtils.deleteById(BodyBuildingBean.class,plan_temp.getId());
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
        TextView titleText;
        TextView detailText;
        TextView checkNumText;
        TextView isCheckText;
        TextView deleteTv;
    }
}
