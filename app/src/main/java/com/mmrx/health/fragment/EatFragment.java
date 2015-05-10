package com.mmrx.health.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mmrx.health.R;
import com.mmrx.health.activity.ResultActivity;
import com.mmrx.health.util.BitmapCache;
import com.mmrx.health.util.MyToast;

public class EatFragment extends Fragment {
	
	private EditText et_roulei;
	private EditText et_zhushi;
	private EditText et_shucai;
	private Button but_dismiss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_eat, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		initview();
		super.onActivityCreated(savedInstanceState);
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		et_roulei = (EditText) getView().findViewById(R.id.eat_et_roulei);
		et_shucai = (EditText) getView().findViewById(R.id.eat_et_shucai);
		et_zhushi = (EditText) getView().findViewById(R.id.eat_et_zhushi);
		but_dismiss = (Button) getView().findViewById(R.id.eat_but_dismiss);
		but_dismiss.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String string = et_roulei.getText().toString();
				if ("".equals(string)) {
					MyToast.showShort(getActivity(), "请填写完整");
					return;
				}
				int roulei = Integer.parseInt(string);
				String string1 = et_shucai.getText().toString();
				if ("".equals(string1)) {
					MyToast.showShort(getActivity(), "请填写完整");
					return;
				}
				int shucai = Integer.parseInt(string);
				String string2 = et_zhushi.getText().toString();
				if ("".equals(string2)) {
					MyToast.showShort(getActivity(), "请填写完整");
					return;
				}
				int zhushi = Integer.parseInt(string);
				/**
				 * 计算热量   并与bmi指数计算  intent 传计算结果
				 */
				
				Intent intent=new Intent(getActivity(),ResultActivity.class);
				int result=roulei*3+shucai*2+zhushi*2;
				intent.putExtra("result", result);
				startActivity(intent);
			}
		});

        RelativeLayout back = (RelativeLayout)getView().findViewById(R.id.fragment_eat_layout);
        back.setBackground(new BitmapDrawable(getResources(),
                BitmapCache.getInstance().getBitmapBlur(R.drawable.fragment_background_food,
                        getActivity(),10,false)));

	}
	

}
