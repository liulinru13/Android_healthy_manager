package mmrx.com.metrolayout;

import android.util.Log;
import android.view.View;

/**
 * Created by mmrx on 2015/5/3.
 */
public abstract class AbsMetroNode implements View.OnClickListener{
    protected String mTitle = "运动";
    protected String mDetail = "动起来~~";
    protected int mIcon = MetroConstant.DEFAULT_ICON;
    protected int mWeight = 2;
    protected MetroConstant.MetroStyle mStyle = MetroConstant.MetroStyle.HORIZONTAL;
    protected int mTitleSize = MetroConstant.DEFAULT_TITLE_SIZE;
    protected int mDetailSize = MetroConstant.DEFAULT_DETAIL_SIZE;
    protected int mColor = MetroConstant.DEFAULT_COLOR;
    protected boolean mIsCheck = false;

    protected AbsMetroNode thisNode;
    protected MetroView thisView;

    public AbsMetroNode(String mTitle, String mDetail, int mIcon, int mWeight, MetroConstant.MetroStyle mStyle,
                           int mTitleSize, int mDetailSize, int mColor, boolean mIsCheck) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mIcon = mIcon;
        this.mWeight = mWeight;
        this.mStyle = mStyle;
        this.mTitleSize = mTitleSize;
        this.mDetailSize = mDetailSize;
        this.mColor = mColor;
        this.mIsCheck = mIsCheck;
    }

    protected AbsMetroNode() {
    }

    @Override
    public void onClick(View v) {
        Log.i("AbsMetroNode","onClick");
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public MetroConstant.MetroStyle getmStyle() {
        return mStyle;
    }

    public void setmStyle(MetroConstant.MetroStyle mStyle) {
        this.mStyle = mStyle;
    }

    public int getmTitleSize() {
        return mTitleSize;
    }

    public void setmTitleSize(int mTitleSize) {
        this.mTitleSize = mTitleSize;
    }

    public int getmDetailSize() {
        return mDetailSize;
    }

    public void setmDetailSize(int mDetailSize) {
        this.mDetailSize = mDetailSize;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public boolean ismIsCheck() {
        return mIsCheck;
    }

    public void setmIsCheck(boolean mIsCheck) {
        this.mIsCheck = mIsCheck;
    }

    public void setThisNode(AbsMetroNode thisNode) {
        this.thisNode = thisNode;
    }
    public void setThisView(MetroView thisView) {
        this.thisView = thisView;
    }

}
