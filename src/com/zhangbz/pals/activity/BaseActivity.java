package com.zhangbz.pals.activity;

import com.zhangbz.pals.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity {
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("configed", MODE_PRIVATE);
		//判断一下，是否做过设置向导，如果没有做过就跳转到设置向导页面，否者就留在当前页面
		boolean configed = sp.getBoolean("configed", false);
		if (configed) {
			//就留在该页面
			setContentView(R.layout.activity_base);			
		} else {
			//还没有做过设置向导
			Intent intent = new Intent(this, setup1Activity.class);
			startActivity(intent);
			//关闭当前页面
			finish();
		}
	}
	
	/**
	 * 重新进入setup
	 * @param view
	 */
	public void reEnterSetup(View view) {
		Intent intent = new Intent(this, setup1Activity.class);
		startActivity(intent);
		//关闭当前页面
		finish();
	}
}
