package com.mmrx.health.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.adapter.MoodAdapter;
import com.mmrx.health.bean.Mood;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.L;
import com.mmrx.health.util.MyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoodActivity extends BaseActivity implements OnClickListener{

    SwipeListView mlist;
	EditText et_content;
    Button but_dismiss;
	DbUtils dbUtils;
	List<Mood> m_list;
	MoodAdapter adapter;
	RadioGroup rg;
    String mFragmentTag;

    //title bar 相关设置
    ImageButton mBackBut;
    TextView mTitleTv;
    Button but_more;

	int state=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mood);
		dbUtils = DbUtils.create(this);
        mlist = (SwipeListView)findViewById(R.id.mood_swipe_list);
        mlist.setSwipeListViewListener(new SwipeListViewListener());
		et_content = (EditText) findViewById(R.id.mood_et_content);
		but_dismiss = (Button) findViewById(R.id.mood_but_dismiss);
		rg = (RadioGroup) findViewById(R.id.mood_rg);
        mBackBut = (ImageButton)findViewById(R.id.title_bar_back);
        mBackBut.setOnClickListener(this);
        mTitleTv = (TextView)findViewById(R.id.title_bar_title);
        mTitleTv.setText("心情日记");
        but_more = (Button)findViewById(R.id.title_bar_menu);
        but_more.setVisibility(View.GONE);

        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);

		but_dismiss.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content = et_content.getText().toString();
				if ("".equals(content)) {
					MyToast.showShort(getApplicationContext(), "请输入完整的信息");
					return;
				}
				int checkedRadioButtonId = rg.getCheckedRadioButtonId();
				switch (checkedRadioButtonId) {
				case R.id.mood_rb1:
					/**
					 *1心情好
					 */
					state=1;
					break;
				case R.id.mood_rb2:
					/**
					 *2心情一般
					 */
					state=2;
					break;
				case R.id.mood_rb3:
					/**
					 *3心情不好
					 */
					state=3;
					break;
				default:
					break;
				}
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd hh-mm");
				Date date=new Date(System.currentTimeMillis());
				try {
					dbUtils.save(new Mood(content, time.format(date),state));
					try {
						m_list = dbUtils.findAll(Mood.class);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (m_list == null) {
						m_list = new ArrayList<Mood>();

					}else{
						adapter.setList(m_list);
						et_content.setText("");
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		try {
			m_list = dbUtils.findAll(Mood.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (m_list == null) {
			m_list = new ArrayList<Mood>();
		}
		adapter = new MoodAdapter(m_list, getApplicationContext(),mlist);
		mlist.setAdapter(adapter);
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
                imm.hideSoftInputFromWindow(et_content.getWindowToken(),0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar_back:
                Intent intent = new Intent();
                intent.setClass(this,ManageActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,mFragmentTag);
                startActivity(intent);
                break;
            default:
                break;
        }
    }



    class SwipeListViewListener extends BaseSwipeListViewListener {

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
//            Toast.makeText(getApplicationContext(), m_list.get(position).toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                L.i("SwipeListViewListener-onDismiss");
                m_list.remove(position);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
