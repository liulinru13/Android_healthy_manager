
package com.mmrx.health.activity;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.fragment.BodyBuildingFragment;
import com.mmrx.health.fragment.DrugFragment;
import com.mmrx.health.fragment.EatFragment;
import com.mmrx.health.fragment.SleepFrament;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.SPutil;

public class ManageActivity extends BaseActivity implements OnClickListener {
	DrugFragment drugFragment;
	EatFragment eatFragment;
	SleepFrament sleepFrament;
    BodyBuildingFragment buildFrament;
//	Fragment[] fragments;

    //底部的四个按钮
    LinearLayout mBottomBn_eat;
    LinearLayout mBottomBn_sle;
    LinearLayout mBottomBn_med;
    LinearLayout mBottomBn_bui;

    FragmentManager mFragmentManager;

	//title bar 相关设置
    ImageButton mBackBut;
    TextView mTitleTv;
    Button but_more;

	int currentTabsIndex;
	int index=0;
	LinearLayout[] tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);
		initView();
//		drugFragment = new DrugFragment();
//		eatFragment = new EatFragment();
//		sleepFrament = new SleepFrament();
//        buildFrament = new BodyBuildingFragment();
//		fragments=new Fragment[]{eatFragment,sleepFrament,drugFragment,buildFrament};
//		FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
//		beginTransaction.add(R.id.id_content, eatFragment).show(eatFragment).commit();
        setTabSelection(0);
	}

	private void initView() {
		// TODO Auto-generated method stub
		tabs = new LinearLayout[4];
        RelativeLayout bottomBar = (RelativeLayout)findViewById(R.id.bottom_bar_layout);
        RelativeLayout titleBar = (RelativeLayout)findViewById(R.id.titlebar_layout);
		tabs[0] = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_eat);
		tabs[1] = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_sle);
		tabs[2] = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_med);
        tabs[3] = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_building);
		tabs[0].setOnClickListener(this);
		tabs[1].setOnClickListener(this);
		tabs[2].setOnClickListener(this);
        tabs[3].setOnClickListener(this);
		but_more = (Button) titleBar.findViewById(R.id.title_bar_menu);
		but_more.setOnClickListener(this);
		tabs[index].setSelected(true);

        mFragmentManager = getFragmentManager();

        mBottomBn_eat = (LinearLayout)findViewById(R.id.bottom_bar_eat);
        mBottomBn_sle = (LinearLayout)findViewById(R.id.bottom_bar_sle);
        mBottomBn_med = (LinearLayout)findViewById(R.id.bottom_bar_med);
        mBottomBn_bui = (LinearLayout)findViewById(R.id.bottom_bar_building);

        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mBackBut.setVisibility(View.GONE);
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("健康管家");

//        mBottomBn_eat.setOnClickListener(this);
//        mBottomBn_sle.setOnClickListener(this);
//        mBottomBn_med.setOnClickListener(this);
//        mBottomBn_bui.setOnClickListener(this);
	}

    @Override
    protected void onResume() {
        super.onResume();
        String tag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);
        if(tag == null)
            return;
        if(tag.equals(Constant.FRAGMENT_TAG_BUILD)){
            setTabSelection(3);
        }else if(tag.equals(Constant.FRAGMENT_TAG_EAT)){
            setTabSelection(0);
        }else if(tag.equals(Constant.FRAGMENT_TAG_SLEEP)){
            setTabSelection(1);
        }else if(tag.equals(Constant.FRAGMENT_TAG_DRUG)){
            setTabSelection(2);
        }
    }


    public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bottom_bar_eat:
            setTabSelection(0);
			break;
		case R.id.bottom_bar_sle:
            setTabSelection(1);
			break;
		case R.id.bottom_bar_med:
            setTabSelection(2);
			break;
        case R.id.bottom_bar_building:
            setTabSelection(3);
            break;
		case R.id.title_bar_menu:
			showMenu();
			break;
		default:
			break;
		}
	}

    private void setTabSelection(int index) {
        //开启一个事务
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        resetBottomBar();
        hideAllFragment(fragmentTransaction);
        switch (index) {
            case 0:

                //设置bottom bar按钮的状态
                ((Button)mBottomBn_eat.findViewById(R.id.bottom_bar_eat_bn))
                        .setBackgroundColor(getResources().getColor(R.color.bottom_bar_pressed));
                ((Button)mBottomBn_eat.findViewById(R.id.bottom_bar_eat_bn))
                        .setTextColor(getResources().getColor(R.color.bottom_bar_text_pressed));
                if(eatFragment == null){
                    eatFragment = new EatFragment();
                    fragmentTransaction.add(R.id.id_content,eatFragment);
                }else{
                    fragmentTransaction.show(eatFragment);
                }
                break;
            case 1:

                //设置bottom bar按钮的状态
                ((Button)mBottomBn_sle.findViewById(R.id.bottom_bar_sle_bn))
                        .setBackgroundColor(getResources().getColor(R.color.bottom_bar_pressed));
                ((Button)mBottomBn_sle.findViewById(R.id.bottom_bar_sle_bn))
                        .setTextColor(getResources().getColor(R.color.bottom_bar_text_pressed));
                if(sleepFrament == null){
                    sleepFrament = new SleepFrament();
                    fragmentTransaction.add(R.id.id_content,sleepFrament);
                }else{
                    fragmentTransaction.show(sleepFrament);
                }
                break;
            case 2:

                //设置bottom bar按钮的状态
                ((Button)mBottomBn_med.findViewById(R.id.bottom_bar_med_bn))
                        .setBackgroundColor(getResources().getColor(R.color.bottom_bar_pressed));
                ((Button)mBottomBn_med.findViewById(R.id.bottom_bar_med_bn))
                        .setTextColor(getResources().getColor(R.color.bottom_bar_text_pressed));
                if(drugFragment == null){
                    drugFragment = new DrugFragment();
                    fragmentTransaction.add(R.id.id_content,drugFragment);
                }else{
                    fragmentTransaction.show(drugFragment);
                }
                break;
            case 3:

                //设置bottom bar按钮的状态
                ((Button)mBottomBn_bui.findViewById(R.id.bottom_bar_bui_bn))
                        .setBackgroundColor(getResources().getColor(R.color.bottom_bar_pressed));
                ((Button)mBottomBn_bui.findViewById(R.id.bottom_bar_bui_bn))
                        .setTextColor(getResources().getColor(R.color.bottom_bar_text_pressed));
                if(buildFrament == null){
                    buildFrament = new BodyBuildingFragment();
                    fragmentTransaction.add(R.id.id_content,buildFrament);
                }else{
                    fragmentTransaction.show(buildFrament);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();

    }



    //将bottom bar的按钮状态都清除了
    private void resetBottomBar(){
        ((Button)mBottomBn_eat.findViewById(R.id.bottom_bar_eat_bn))
                .setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));
        ((Button)mBottomBn_eat.findViewById(R.id.bottom_bar_eat_bn)).
                setTextColor(getResources().getColor(R.color.bottom_bar_text_normal));
        ((Button)mBottomBn_sle.findViewById(R.id.bottom_bar_sle_bn))
                .setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));
        ((Button)mBottomBn_sle.findViewById(R.id.bottom_bar_sle_bn)).
                setTextColor(getResources().getColor(R.color.bottom_bar_text_normal));
        ((Button)mBottomBn_med.findViewById(R.id.bottom_bar_med_bn))
                .setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));
        ((Button)mBottomBn_med.findViewById(R.id.bottom_bar_med_bn)).
                setTextColor(getResources().getColor(R.color.bottom_bar_text_normal));
        ((Button)mBottomBn_bui.findViewById(R.id.bottom_bar_bui_bn))
                .setBackgroundColor(getResources().getColor(R.color.bottom_bar_normal));
        ((Button)mBottomBn_bui.findViewById(R.id.bottom_bar_bui_bn)).
                setTextColor(getResources().getColor(R.color.bottom_bar_text_normal));
    }

    /**
     * 隐藏所有fragment
     * */
    private void hideAllFragment(FragmentTransaction transaction){
        if(drugFragment != null)
            transaction.hide(drugFragment);
        if(eatFragment != null)
            transaction.hide(eatFragment);
        if(sleepFrament != null)
            transaction.hide(sleepFrament);
        if(buildFrament != null)
            transaction.hide(buildFrament);
    }

	class MyDialog extends Dialog {

		private Window window = null;

		public MyDialog(Context context) {
			super(context);
		}

		public void showDialog(int layoutResID, int x, int y) {
			setContentView(layoutResID);

			windowDeploy(x, y);

            // 设置触摸对话框意外的地方取消对话框
			setCanceledOnTouchOutside(true);
			show();
		}

        // 设置窗口显示
		public void windowDeploy(int x, int y) {
			window = getWindow();

			window.setBackgroundDrawableResource(R.color.tran);
			WindowManager.LayoutParams wl = window.getAttributes();

			wl.x = x;
			wl.y = y;

			wl.gravity = Gravity.BOTTOM;
			window.setAttributes(wl);
		}
	}

    private String getFragmentTag(){
        switch (currentTabsIndex){
            case 0:
                return Constant.FRAGMENT_TAG_EAT;
            case 1:
                return Constant.FRAGMENT_TAG_SLEEP;
            case 2:
                return Constant.FRAGMENT_TAG_DRUG;
            case 3:
                return Constant.FRAGMENT_TAG_BUILD;
        }
        return "";
    }
	
	private void showMenu() {
		final MyDialog dialog = new MyDialog(ManageActivity.this);
		dialog.showDialog(R.layout.tab_item, 0, 0);
		TextView tv1 = (TextView) dialog.findViewById(R.id.tab_tv1);
		TextView tv2 = (TextView) dialog.findViewById(R.id.tab_tv2);
		TextView tv3 = (TextView) dialog.findViewById(R.id.tab_tv3);
		TextView tv4 = (TextView) dialog.findViewById(R.id.tab_tv4);
		// tv1.getLayoutParams().width=ScreenUtils.getScreenWidth(FirstActivity.this)-10;

		tv1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/**
				 * 我的晴雨表
				 * 
				 * 
				 */
				
				Intent intent=new Intent(ManageActivity.this,MoodActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,getFragmentTag());
				startActivity(intent);
				dialog.cancel();
			}
		});
		
		tv2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/**
				 *药品录入
				 */
				
				Intent intent=new Intent(ManageActivity.this,SaveDrugActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,getFragmentTag());
				startActivity(intent);
				dialog.cancel();
			}
		});
		tv3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/**
				 *设置
				 */
				
				Intent intent=new Intent(ManageActivity.this,SettingActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,getFragmentTag());
				startActivity(intent);
				dialog.cancel();
			}
		});
		
		tv4.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/**
				 * exit;
				 */
				dialog.cancel();
                SPutil sPutil = new SPutil(getApplicationContext());
                sPutil.WriteLogin(false);
				finish();
			}
		});
	}

//    //将软键盘关掉
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            // 关闭软键盘
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            // 得到InputMethodManager的实例
//            if (imm.isActive())
//            {
//                // 如果开启
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//                // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
////                imm.hideSoftInputFromWindow(et_content.getWindowToken(),0);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
