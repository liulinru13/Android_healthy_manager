package com.mmrx.health.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mmrx.health.BaseActivity;
import com.mmrx.health.R;
import com.mmrx.health.bean.User;
import com.mmrx.health.util.BitmapCache;
import com.mmrx.health.util.Constant;
import com.mmrx.health.util.MyToast;
import com.mmrx.health.util.SPutil;


public class LogActivity extends BaseActivity {

	@ViewInject(R.id.log_but_dismiss)
	Button but_dismiss;
	@ViewInject(R.id.log_et_name)
	EditText et_name;
	@ViewInject(R.id.log_et_age)
	EditText et_age;
	@ViewInject(R.id.log_rg)
	RadioGroup group;
	@ViewInject(R.id.log_et_tall)
	EditText et_tall;
	@ViewInject(R.id.log_et_weight)
	EditText et_weight;
    @ViewInject(R.id.log_but_return)
    Button et_return;

    SPutil putil;
	String gender="男";
    String mFragmentTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		ViewUtils.inject(this);
        RelativeLayout back = (RelativeLayout)findViewById(R.id.perinfo_layout);
//        back.setBackground(new BitmapDrawable(getResources(),
//                BitmapCache.getInstance().getBitmapBlur(R.drawable.perinfo_background,
//                        this,10,false)));
        back.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapCache.getInstance().getBitmapBlur(R.drawable.perinfo_background,
                        this,10,false)));
        putil=new SPutil(getApplicationContext());
		but_dismiss.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
				
			}
		});

		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                int checkedRadioButtonId = arg0.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.log_rb_female) {
                    gender = "女";
                }
                if (checkedRadioButtonId == R.id.log_rb_male) {
                    gender = "男";
                }

            }
        });

        et_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogActivity.this,ManageActivity.class);
                intent.putExtra(Constant.FRAGMENT_TAG,mFragmentTag);
                startActivity(intent);
            }
        });
		
	}

    @Override
    protected void onResume() {
        super.onResume();
        mFragmentTag = getIntent().getStringExtra(Constant.FRAGMENT_TAG);
        if(putil.ReadLogin()){
            et_return.setVisibility(View.VISIBLE);
            et_name.setText(putil.ReadName());
            et_tall.setText(putil.ReadHeight()+"");
            et_weight.setText(putil.ReadWeight()+"");
            et_age.setText(putil.ReadAge()+"");
            if(putil.ReadGender().equals(gender)){
                ((RadioButton)group.findViewById(R.id.log_rb_male)).setSelected(true);
            }else{
                ((RadioButton)group.findViewById(R.id.log_rb_female)).setSelected(true);
            }

        }
    }

    public void dismiss(){
		String name = et_name.getText().toString();
		if ("".equals(name)) {
			MyToast.showShort(getApplicationContext(), "请输入您的名字");
			return;
		}
		
		String str_tall = et_tall.getText().toString();
		if ("".equals(str_tall)) {
			MyToast.showShort(getApplicationContext(), "请输入您的身高");
			return;
		}
		int tall = Integer.parseInt(str_tall);
		
		String str_weight = et_weight.getText().toString();
		
		if ("".equals(str_weight)) {
			MyToast.showShort(getApplicationContext(), "请输入您的体重");
			return;
		}
		int weight = Integer.parseInt(str_weight);
		
		
		String str_age = et_age.getText().toString();
		
		if ("".equals(str_age)) {
			MyToast.showShort(getApplicationContext(), "请输入您的年龄");
			return;
		}
		int age = Integer.parseInt(str_age);

		User u=new User(name, age, gender, tall, weight);

        putil.WriteLogin(true);
		putil.WriteUser(u);
		Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
		startActivity(intent);
	}
	

	

}
