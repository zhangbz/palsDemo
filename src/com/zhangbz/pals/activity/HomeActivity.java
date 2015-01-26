package com.zhangbz.pals.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.zhangbz.pals.R;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends Activity implements OnClickListener {
	protected static final String TAG = "HomeActivity";
	private GridView list_home;
//	private MyAdapter adapter;
	private SharedPreferences sp;
	private static String[] names = { 
		"item1", "item2", "item3",
		"item4", "item5", "item6"
		
	};
	private static int[] ids = {
			R.drawable.expression,
			R.drawable.expression,
			R.drawable.expression,
			R.drawable.expression,
			R.drawable.expression,
			R.drawable.expression,
	};
	
	//bottom start
	private LinearLayout mTabWeixin;
	private LinearLayout mTabFrd;
	private LinearLayout mTabAddress;
	private LinearLayout mTabSettings;

	private ImageButton mImgWeixin;
	private ImageButton mImgFrd;
	private ImageButton mImgAddress;
	private ImageButton mImgSettings;

	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;
	//bottom end
	
//	private EditText et_setup_pwd;
//	private EditText et_setup_confirm;
//	private Button ok;
//	private Button cancel;
//	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		initEvent();
		setSelect(0);
//		sp = getSharedPreferences("config", MODE_PRIVATE);
//		list_home = (GridView)findViewById(R.id.list_home);
//		adapter = new MyAdapter();
//		list_home.setAdapter(adapter);
//		list_home.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent;
//				switch (position) {
//				    case 0://弹出登陆框
//				    	//showSigninDialog();
//				    	break;
//				    case 1://测试登录界面
//				    	intent = new Intent(HomeActivity2.this, LoginActivity.class);
//				    	startActivity(intent);
//				    	break;
//				    case 5://进入item6
//				    	intent = new Intent(HomeActivity2.this,SettingActivity.class);
//				    	startActivity(intent);
//				    	break;
//				    default:
//				    	break;
//				}
//			}
//		});
	}

//	protected void showSigninDialog() {
//		//判断是否设置过密码
//		if (isSetupPwd()) {
//			//已经设置密码了，弹出的是输入对话框
//			showEnterDialog();
//		} else {
//			//没有设置密码，弹出的是设置密码对话框
//			showSetupDialog();
//		}
//	}

//	/**
//	 * 设置密码对话框
//	 */
//	private void showSetupDialog() {
//		AlertDialog.Builder builder = new Builder(HomeActivity.this);
//		//自定义一个布局文件
//		View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_pwd, null);
//		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
//		et_setup_confirm= (EditText) view.findViewById(R.id.et_setup_confirm);
//		ok = (Button) view.findViewById(R.id.ok);
//		cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//把这个对话框取消掉
//				dialog.dismiss();
//			}
//		});
//		ok.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//取出密码
//				String password = et_setup_pwd.getText().toString().trim();
//			    String password_confirm = et_setup_confirm.getText().toString().trim();
//			    if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
//			    	Toast.makeText(HomeActivity.this, "密码为空", 0).show();
//			    	return;
//			    }
//			    //判断是否一致才去保存
//			    if (password.equals(password_confirm)) {
//			    	//一致的话，就保存密码，把对话框取消掉，还要跳转到相应的页面
//			    	Editor editor = sp.edit();
//			    	editor.putString("password", MD5Utils.md5Password(password));//保存加密后的密码
//			    	editor.commit();
//			    	dialog.dismiss();
//			    	LogUtil.i(TAG, "一致的话，记保存密码，把对话框取消掉，还要跳转到相应的页面");
//			    	Intent intent = new Intent(HomeActivity.this, BaseActivity.class);
//			    	startActivity(intent);
//			    } else {
//			    	et_setup_pwd.setText("");
//			    	et_setup_confirm.setText("");
//			    	Toast.makeText(HomeActivity.this, "密码不一致", 0).show();
//			    	return;
//			    }
//			}
//		});
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.show();
//	}
//
//	/**
//	 * 输入密码对话框
//	 */
//	private void showEnterDialog() {
//		AlertDialog.Builder builder = new Builder(HomeActivity.this);
//		//自定义一个布局文件
//		View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_pwd, null);
//		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
//		ok = (Button) view.findViewById(R.id.ok);
//		cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//把这个对话框取消掉
//				dialog.dismiss();
//			}
//		});
//		ok.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//取出密码
//				String password = et_setup_pwd.getText().toString().trim();
//				String savePassword = sp.getString("password", "");
//				if (TextUtils.isEmpty(password)) {
//			    	Toast.makeText(HomeActivity.this, "密码为空", 1).show();
//			    	return;
//			    }
//			    
//			    if (MD5Utils.md5Password(password).equals(savePassword)) {
//			    	//输入密码是我之前设置的密码
//			    	//把对话框取消掉，进入相应页面
//			    	dialog.dismiss();
//			    	Intent intent = new Intent(HomeActivity.this, BaseActivity.class);
//			    	startActivity(intent);
//			    	LogUtil.i(TAG, "把对话框取消掉，进入相应页面");
//			    } else {
//			    	Toast.makeText(HomeActivity.this, "密码错误", 0).show();
//			    	et_setup_pwd.setText("");
//			    	return;
//			    }
//			}
//		});
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.show();
//	}
//
//	/**
//	 * 判断是否设置过密码
//	 * @return
//	 */
//	private boolean isSetupPwd() {
//		String password = sp.getString("password", null);
////		 ctrl+/：一行或多行注释
////		if (TextUtils.isEmpty(password)) {
////			return false;
////		} else {
////			return true;
////		}
//		return !TextUtils.isEmpty(password);
//	}

	private void initEvent()
	{
		mTabWeixin.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabSettings.setOnClickListener(this);
	}

	private void initView()
	{
		mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

		mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
	}

	private void setSelect(int i)
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new WeixinFragment();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			if (mTab02 == null)
			{
				mTab02 = new FrdFragment();
				transaction.add(R.id.id_content, mTab02);
			} else
			{
				transaction.show(mTab02);
				
			}
			mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new AddressFragment();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			mImgAddress.setImageResource(R.drawable.tab_address_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new SettingFragment();
				transaction.add(R.id.id_content, mTab04);
			} else
			{
				transaction.show(mTab04);
			}
			mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
			break;

		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		if (mTab04 != null)
		{
			transaction.hide(mTab04);
		}
	}

	@Override
	public void onClick(View v)
	{
		resetImgs();
		switch (v.getId())
		{
		case R.id.id_tab_weixin:
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			setSelect(1);
			break;
		case R.id.id_tab_address:
			setSelect(2);
			break;
		case R.id.id_tab_settings:
			setSelect(3);
			break;

		default:
			break;
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{
		mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
		mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
		mImgAddress.setImageResource(R.drawable.tab_address_normal);
		mImgSettings.setImageResource(R.drawable.tab_settings_normal);
	}


//	private class MyAdapter extends BaseAdapter{
//
//		@Override
//		public int getCount() {
//			return names.length;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			View view = View.inflate(HomeActivity2.this, R.layout.list_item_home, null);
//			ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
//			TextView tv_info = (TextView) view.findViewById(R.id.tv_item);
//			tv_info.setText(names[position]);
//			iv_item.setImageResource(ids[position]);
//			return view;
//		}
//		
//		@Override
//		public Object getItem(int position) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//	}
}
