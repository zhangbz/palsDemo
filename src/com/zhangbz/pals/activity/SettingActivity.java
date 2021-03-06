package com.zhangbz.pals.activity;

import com.zhangbz.pals.R;
import com.zhangbz.pals.ui.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	//设置是否开启自动更新
	private SharedPreferences sp;
	private SettingItemView siv_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		//初始设置是否开启自动更新
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		boolean update = sp.getBoolean("update", true);
		if (update) {
			//自动升级已经开启
			siv_update.setChecked(true);
		} else {
			//自动升级已经关闭
			siv_update.setChecked(false);
		}		
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				//判断是否有选中
				//已经打开自动升级了
				if (siv_update.isChecked()) {
					siv_update.setChecked(false);
					editor.putBoolean("update", false);
				} else {
					//没有打开自动升级的话
					siv_update.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();
			}
		});
	}
}
