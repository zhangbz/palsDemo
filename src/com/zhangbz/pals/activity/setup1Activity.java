package com.zhangbz.pals.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zhangbz.pals.R;

public class setup1Activity extends BaseSetupActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this, setup2Activity.class);
		startActivity(intent);
		finish();
		//要求在finish()或者startActivity(intent)后面执行
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);		
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		
	}
}
