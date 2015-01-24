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
		//�ж�һ�£��Ƿ����������򵼣����û����������ת��������ҳ�棬���߾����ڵ�ǰҳ��
		boolean configed = sp.getBoolean("configed", false);
		if (configed) {
			//�����ڸ�ҳ��
			setContentView(R.layout.activity_base);			
		} else {
			//��û������������
			Intent intent = new Intent(this, setup1Activity.class);
			startActivity(intent);
			//�رյ�ǰҳ��
			finish();
		}
	}
	
	/**
	 * ���½���setup
	 * @param view
	 */
	public void reEnterSetup(View view) {
		Intent intent = new Intent(this, setup1Activity.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
	}
}
