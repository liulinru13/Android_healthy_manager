package com.mmrx.health.bean;

import android.view.View;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.mmrx.health.util.L;
import com.mmrx.health.util.MyUtils;

import mmrx.com.metrolayout.AbsMetroNode;
import mmrx.com.metrolayout.MetroConstant;

/**
 * Created by mmrx on 2015/5/3.
 */
@Table(name="body_building")
public class BodyBuildingBean extends AbsMetroNode {
    @Id
    private int id;
    private int checkNum;
    //记录上次打卡的时间
    private String lastCheckDate = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public String getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(String lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public BodyBuildingBean(){
        super();
    }
    public BodyBuildingBean(String mTitle, String mDetail, int mIcon, int mWeight,
                            MetroConstant.MetroStyle mStyle,
                            int mTitleSize, int mDetailSize, int mColor,
                            boolean mIsCheck){
        super(mTitle,mDetail,mIcon,mWeight,mStyle,mTitleSize,mDetailSize,mColor,mIsCheck,true);
    }
    public void onClick(View v) {
        L.i("BodyBuildingBean--onClick");
        if(this.thisNode != null && this.thisView != null){
            if(this.thisNode.ismCheckSetting() && !this.thisNode.ismIsCheck()) {
                this.thisView.setCheck(true);
//                this.thisNode.setmIsCheck(true);
                //如果日期为空或者和当前日期不相等，计做打卡一次
                if(this.lastCheckDate == null || !this.lastCheckDate.equals(MyUtils.getSystemDate())){
                    this.lastCheckDate = MyUtils.getSystemDate();
                    L.i("BodyBuildingBean--onClick--date is" + this.lastCheckDate);
                    this.checkNum++;
                }

            }//end if
        }
    }//end on click
}
