package com.zhangbz.pals.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangbz.pals.R;
import com.zhangbz.pals.util.LogUtil;
import com.zhangbz.pals.util.MD5Utils;

public class HomeActivity3 extends Activity {
	protected static final String TAG = "HomeActivity";
	private GridView list_home;
	private MyAdapter adapter;
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
	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		list_home = (GridView)findViewById(R.id.list_home);
		adapter = new MyAdapter();
		list_home.setAdapter(adapter);
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				    case 0://������½��
				    	//showSigninDialog();
				    	break;
				    case 1://���Ե�¼����
				    	intent = new Intent(HomeActivity3.this, LoginActivity.class);
				    	startActivity(intent);
				    	break;
				    case 5://����item6
				    	intent = new Intent(HomeActivity3.this,SettingActivity.class);
				    	startActivity(intent);
				    	break;
				    default:
				    	break;
				}
			}
		});
	}

//	protected void showSigninDialog() {
//		//�ж��Ƿ����ù�����
//		if (isSetupPwd()) {
//			//�Ѿ����������ˣ�������������Ի���
//			showEnterDialog();
//		} else {
//			//û���������룬����������������Ի���
//			showSetupDialog();
//		}
//	}

//	/**
//	 * ��������Ի���
//	 */
//	private void showSetupDialog() {
//		AlertDialog.Builder builder = new Builder(HomeActivity.this);
//		//�Զ���һ�������ļ�
//		View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_pwd, null);
//		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
//		et_setup_confirm= (EditText) view.findViewById(R.id.et_setup_confirm);
//		ok = (Button) view.findViewById(R.id.ok);
//		cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//������Ի���ȡ����
//				dialog.dismiss();
//			}
//		});
//		ok.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//ȡ������
//				String password = et_setup_pwd.getText().toString().trim();
//			    String password_confirm = et_setup_confirm.getText().toString().trim();
//			    if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
//			    	Toast.makeText(HomeActivity.this, "����Ϊ��", 0).show();
//			    	return;
//			    }
//			    //�ж��Ƿ�һ�²�ȥ����
//			    if (password.equals(password_confirm)) {
//			    	//һ�µĻ����ͱ������룬�ѶԻ���ȡ��������Ҫ��ת����Ӧ��ҳ��
//			    	Editor editor = sp.edit();
//			    	editor.putString("password", MD5Utils.md5Password(password));//������ܺ������
//			    	editor.commit();
//			    	dialog.dismiss();
//			    	LogUtil.i(TAG, "һ�µĻ����Ǳ������룬�ѶԻ���ȡ��������Ҫ��ת����Ӧ��ҳ��");
//			    	Intent intent = new Intent(HomeActivity.this, BaseActivity.class);
//			    	startActivity(intent);
//			    } else {
//			    	et_setup_pwd.setText("");
//			    	et_setup_confirm.setText("");
//			    	Toast.makeText(HomeActivity.this, "���벻һ��", 0).show();
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
//	 * ��������Ի���
//	 */
//	private void showEnterDialog() {
//		AlertDialog.Builder builder = new Builder(HomeActivity.this);
//		//�Զ���һ�������ļ�
//		View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_pwd, null);
//		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
//		ok = (Button) view.findViewById(R.id.ok);
//		cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//������Ի���ȡ����
//				dialog.dismiss();
//			}
//		});
//		ok.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//ȡ������
//				String password = et_setup_pwd.getText().toString().trim();
//				String savePassword = sp.getString("password", "");
//				if (TextUtils.isEmpty(password)) {
//			    	Toast.makeText(HomeActivity.this, "����Ϊ��", 1).show();
//			    	return;
//			    }
//			    
//			    if (MD5Utils.md5Password(password).equals(savePassword)) {
//			    	//������������֮ǰ���õ�����
//			    	//�ѶԻ���ȡ������������Ӧҳ��
//			    	dialog.dismiss();
//			    	Intent intent = new Intent(HomeActivity.this, BaseActivity.class);
//			    	startActivity(intent);
//			    	LogUtil.i(TAG, "�ѶԻ���ȡ������������Ӧҳ��");
//			    } else {
//			    	Toast.makeText(HomeActivity.this, "�������", 0).show();
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
//	 * �ж��Ƿ����ù�����
//	 * @return
//	 */
//	private boolean isSetupPwd() {
//		String password = sp.getString("password", null);
////		 ctrl+/��һ�л����ע��
////		if (TextUtils.isEmpty(password)) {
////			return false;
////		} else {
////			return true;
////		}
//		return !TextUtils.isEmpty(password);
//	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity3.this, R.layout.list_item_home, null);
			ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
			TextView tv_info = (TextView) view.findViewById(R.id.tv_item);
			tv_info.setText(names[position]);
			iv_item.setImageResource(ids[position]);
			return view;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
