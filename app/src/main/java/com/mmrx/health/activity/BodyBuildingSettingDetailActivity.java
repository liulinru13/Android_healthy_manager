package com.mmrx.health.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.bean.BodyBuildingBean;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.L;

import mmrx.com.metrolayout.MetroConstant;

public class BodyBuildingSettingDetailActivity extends BaseActivity {

    //title bar 相关设置
    @ViewInject(R.id.title_bar_back)
    ImageButton mBackBut;
    TextView mTitleTv;
    Button mBut_more;
    @ViewInject(R.id.body_detail_title_et)
    EditText mTitleEdit;
    @ViewInject(R.id.body_detail_detail_et)
    EditText mDetailEdit;
    @ViewInject(R.id.body_plan_type_spinner)
    Spinner mBuildType;
    @ViewInject(R.id.body_card_type_spinner)
    Spinner mCardStyle;
    @ViewInject(R.id.body_card_color_tv)
    TextView mCardColorText;
    @ViewInject(R.id.body_card_color_puple)
    Button mBn1;
    @ViewInject(R.id.body_card_color_blue)
    Button mBn2;
    @ViewInject(R.id.body_card_color_blue_green)
    Button mBn3;
    @ViewInject(R.id.body_card_color_green)
    Button mBn4;
    @ViewInject(R.id.body_card_color_yellow)
    Button mBn5;
    @ViewInject(R.id.body_chekc_rg)
    RadioGroup mRg;
    @ViewInject(R.id.body_detail_save_but)
    Button mSave;
    @ViewInject(R.id.body_detail_quit_but)
    Button mQuit;

    @ResInject(id=R.array.body_detail_plan_type_arr,type= ResType.StringArray)
    String[] mBuildTypeArr;
    @ResInject(id=R.array.body_detail_card_type_arr,type= ResType.StringArray)
    String[] mCardStyleArr;

    final int[] mIconArr = new int[]{R.drawable.icon_basketball100,R.drawable.icon_bicycle100,
            R.drawable.icon_football100,R.drawable.icon_run100,R.drawable.icon_swim100,
            R.drawable.icon_others100};
    final MetroConstant.MetroStyle[] mcardStyle = new MetroConstant.MetroStyle[]{
            MetroConstant.MetroStyle.HORIZONTAL, MetroConstant.MetroStyle.VERTICAL,
            MetroConstant.MetroStyle.ICON_ONLY};

    int mCardColor = Color.parseColor("#ff831bd2");
    String mFragmentTag;
    int mBeanID = -1;
    BodyBuildingBean bbb;
    DbUtils mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_building_setting_detail);
        ViewUtils.inject(this);
        init();
    }
    private void init(){
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("健身计划");
        mBut_more = (Button)findViewById(R.id.title_bar_menu);
        mBut_more.setVisibility(View.GONE);
        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);
        mDb = DbUtils.create(this.getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("BodyBuildingSettingDetailActivity--onResume");
        //判断是否是修改已有的计划
        mBeanID = getIntent().getIntExtra(Constant.BODY_BUILDING_BEAN_ID, -1);
        if(mBeanID!=-1){
            try{
                bbb = mDb.findById(BodyBuildingBean.class,mBeanID);
            }catch (DbException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            fillContent();
        }
        else
            bbb = null;
    }

    private void fillContent(){
        mTitleEdit.setText(bbb.getmTitle());
        mDetailEdit.setText(bbb.getmDetail());
        //设置运动类型
        int icon = bbb.getmIcon();
        int buildTypeIndex = mBuildTypeArr.length-1;
        if(mIconArr.length<=mBuildTypeArr.length)
            for(int i=0;i<mIconArr.length;i++){
                if(icon == mIconArr[i]){
                    buildTypeIndex = i;
                    break;
                }
            }//end for
        mBuildType.setSelection(buildTypeIndex);
        //设置卡片样式
        MetroConstant.MetroStyle style = bbb.getmStyle();
        int cardStyleIndex = 0;
        if(mCardStyleArr.length<=mcardStyle.length)
            for(int i=0;i<mCardStyleArr.length;i++){
                if(style == mcardStyle[i]){
                    cardStyleIndex = i;
                    break;
                }
            }//end for
        mCardStyle.setSelection(cardStyleIndex);
        //卡片颜色
        int color = bbb.getmColor();
        mCardColorText.setTextColor(color);
        //是否打卡
        if(!bbb.ismIsCheck())
            ((RadioButton)findViewById(R.id.body_check_no)).setChecked(true);
    }

    //更换颜色
    @OnClick({R.id.body_card_color_puple,R.id.body_card_color_blue,R.id.body_card_color_blue_green,
            R.id.body_card_color_green,R.id.body_card_color_yellow})
    public void colorChoose(View colorBn){
        mCardColor = Color.parseColor(colorBn.getTag().toString());
        mCardColorText.setTextColor(mCardColor);
    }
    //返回和退出事件
    @OnClick({R.id.title_bar_back,R.id.body_detail_quit_but})
    public void backEvent(View back){
        returnActivity();
    }
    //提交计划
    @OnClick(R.id.body_detail_save_but)
    public void saveEvent(View save){
        //标题
        String title = mTitleEdit.getText().toString();
        //内容
        String detail = mDetailEdit.getText().toString();
        //运动类型
        String buildTypeStr = (String)mBuildType.getSelectedItem();
        if(buildTypeStr == null)
            buildTypeStr = "其他";
        int icon = mIconArr[mIconArr.length-1];
        //和内容进行对比,选择图片
        if(mBuildTypeArr != null && mBuildTypeArr.length<=mIconArr.length)
            for(int i=0;i<mBuildTypeArr.length;i++){
                if(buildTypeStr.equals(mBuildTypeArr[i])){
                    icon = mIconArr[i];
                    break;
                }
            }//end for
        //选择卡片样式
        String cardTypeStr = (String)mCardStyle.getSelectedItem();
        MetroConstant.MetroStyle cardType = mcardStyle[0];
        if(cardTypeStr != null && mCardStyleArr.length<=mcardStyle.length)
            for(int i=0;i<mCardStyleArr.length;i++){
                if(cardTypeStr.equals(mCardStyleArr[i])){
                    cardType = mcardStyle[i];
                    break;
                }
            }//end for

        //是否打卡
        boolean check = true;
        if(mRg.getCheckedRadioButtonId() == R.id.body_check_no)
            check = false;
        //产生2-3之间的随机数
//        int weight = (int)((Math.random()+1)*2);
        int weight = 2;
        if(bbb == null) {
            bbb = new BodyBuildingBean(title, detail, icon, weight, cardType,
                    MetroConstant.DEFAULT_TITLE_SIZE, MetroConstant.DEFAULT_DETAIL_SIZE,
                    mCardColor, check);
            //存入数据库
            try {
                mDb.save(bbb);
            } catch (DbException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //保存修改
        else{
            mBeanID = bbb.getId();
            bbb.setmTitle(title);
            bbb.setmDetail(detail);
            bbb.setmIcon(icon);
            bbb.setmWeight(weight);
            bbb.setmStyle(cardType);
            bbb.setmColor(mCardColor);
            bbb.setmIsCheck(check);
            L.i("BodyBuildingSettingDetailActivity--saveEvent--保存已有修改，id为" + mBeanID);
            try {
                mDb.update(bbb);
            } catch (DbException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //返回
        returnActivity();
    }

    private void returnActivity(){
        Intent intent = new Intent(this,BodyBuildingSettingActivity.class);
        intent.putExtra(Constant.FRAGMENT_TAG,mFragmentTag);
        startActivity(intent);
    }

    //将软键盘关掉
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // 关闭软键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 得到InputMethodManager的实例
            if (imm.isActive())
            {
                // 如果开启
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
//                        InputMethodManager.HIDE_NOT_ALWAYS);
                // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                imm.hideSoftInputFromWindow(mTitleEdit.getWindowToken(),0);
                imm.hideSoftInputFromWindow(mDetailEdit.getWindowToken(),0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
