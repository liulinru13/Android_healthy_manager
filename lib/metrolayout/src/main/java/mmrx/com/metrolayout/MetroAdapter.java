package mmrx.com.metrolayout;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mmrx on 2015/5/3.
 */
public class MetroAdapter {

    protected Context mContext;
    protected List<AbsMetroNode> mNodeList;
    protected HashMap<String,List<AbsMetroNode>> mNodeMap;
    protected List<LinearLayout> mLayoutList;
    protected List<MetroView> mvList;

    public MetroAdapter(List<AbsMetroNode> nodeList,Context context){
        this.mNodeList = nodeList;
        this.mNodeMap = new HashMap<String,List<AbsMetroNode>>();
        this.mLayoutList = null;
        this.mContext = context;
        this.mvList = new ArrayList<MetroView>();
        wrapMetroNode();
    }

    public void setNodeList(List<AbsMetroNode> nodeList){
        this.mNodeList = nodeList;
        wrapMetroNode();
    }

    public List<LinearLayout> getLayoutList(){
        return this.mLayoutList;
    }

    private void wrapMetroNode(){
        if(mNodeList == null)
            return;

        for(AbsMetroNode amn : mNodeList){
            String weight = Integer.toString(amn.getmWeight());
            List<AbsMetroNode> list = null;
            //不存在该键
            if( (list = this.mNodeMap.get(weight)) == null){
                list = new LinkedList<AbsMetroNode>();
                list.add(amn);
                this.mNodeMap.put(weight,list);
            }
            //存在
            else{
                list.add(amn);
            }
        }//for hashmap

        this.mLayoutList = new ArrayList<LinearLayout>();
        int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, this.mContext.getResources().getDisplayMetrics());

        //遍历map
        for(String key:this.mNodeMap.keySet()){
            List<AbsMetroNode> list_temp = this.mNodeMap.get(key);
            int column = Integer.valueOf(key);
            LinearLayout linear = getNewLinear();
            linear.setWeightSum(column);
            //遍历list
            for(int i=0;i<list_temp.size();i++){
                AbsMetroNode amn_temp = list_temp.get(i);
                MetroView mv = new MetroView(this.mContext);
                mv.setAttribute(amn_temp,mContext);
                //设置布局属性
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                //设置左margin
                lp.setMargins(marginLeft,0,0,0);
                //设置weight
                lp.weight = 1;
                //设置高宽
//                lp.height = ;
//                lp.width = 0;
                mv.setLayoutParams(lp);
                //添加点击事件
                amn_temp.setThisNode(amn_temp);
                amn_temp.setThisView(mv);
                mv.setOnClickListener(amn_temp);
                linear.addView(mv);
                this.mvList.add(mv);
                //判断是否应该换行了
                if((i+1)%column == 0){
                    this.mLayoutList.add(linear);
                    linear = getNewLinear();
                    linear.setWeightSum(column);
                    continue;
                }
            }//end list
            this.mLayoutList.add(linear);
        }//end map

        Collections.shuffle(this.mLayoutList);
    }

    public void invalidateMv(){
        for(MetroView mv:this.mvList)
            mv.invalidate();
    }
    public void postInvalidateMv(){
        for(MetroView mv:this.mvList)
            mv.postInvalidate();
    }

    private LinearLayout getNewLinear(){
        LinearLayout linear = new LinearLayout(this.mContext);
        //水平布局
        linear.setOrientation(LinearLayout.HORIZONTAL);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
               5, this.mContext.getResources().getDisplayMetrics());
        //设置padding
        linear.setPadding(0,padding,padding,padding);
        //设置宽高
        ViewGroup.LayoutParams lp;

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MetroConstant.DEFAULT_ROW_HEIGHT,
                this.mContext.getResources().getDisplayMetrics());

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(lp);
        return linear;
    }

}
