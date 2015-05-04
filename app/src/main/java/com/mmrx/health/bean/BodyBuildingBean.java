package com.mmrx.health.bean;

import android.view.View;

import com.mmrx.health.util.L;

import mmrx.com.metrolayout.AbsMetroNode;
import mmrx.com.metrolayout.MetroConstant;

/**
 * Created by mmrx on 2015/5/3.
 */
public class BodyBuildingBean extends AbsMetroNode {
    private int id;
    private int checkNum;

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

    public BodyBuildingBean(){
        super();
    }
    public BodyBuildingBean(String mTitle, String mDetail, int mIcon, int mWeight,
                            MetroConstant.MetroStyle mStyle,
                            int mTitleSize, int mDetailSize, int mColor,
                            boolean mIsCheck){
        super(mTitle,mDetail,mIcon,mWeight,mStyle,mTitleSize,mDetailSize,mColor,mIsCheck);
    }
    public void onClick(View v) {
        L.i("BodyBuildingBean--onClick");
        if(this.thisNode != null && this.thisView != null){
            if(!this.thisNode.ismIsCheck()) {
                this.thisView.setCheck(true);
                this.thisNode.setmIsCheck(true);
            }
        }
    }//end on click
}
