package com.zhangbz.pals.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangbz.pals.R;
import com.zhangbz.pals.util.LogUtil;
import com.zhangbz.pals.util.MyApplication;
import com.zhangbz.pals.util.StreamTools;

public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	protected static final int SHOW_UPDATE_DIALOG = 0;
	protected static final int ENTER_HOME = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	private TextView tv_splash_version;
	private String description;
	private String version;
	private String apkurl;
	private TextView tv_update_info;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾�ţ� " + getVersionName());
		tv_update_info = (TextView) findViewById(R.id.tv_update_info);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			//�������
			checkUpdate();
		} else {
			//�Զ������Ѿ��ر�
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					//������ҳ��
					enterHome();
				}
			}, 20000);
		} 

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(2000);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			    case SHOW_UPDATE_DIALOG: //��ʾ�����Ի���
				    LogUtil.i(TAG, "��ʾ�����Ի���");
				    showUpdateDialog();
				    break;
			    case ENTER_HOME: //������ҳ��
			    	enterHome();
			    	break;
			    case URL_ERROR: //URL����
			    	enterHome();
			    	Toast.makeText(MyApplication.getContext(), "URL����", 0).show();
			    	break;
			    case NETWORK_ERROR: //�����쳣
			    	enterHome();
			    	Toast.makeText(MyApplication.getContext(), "�������", 0).show();
			    	break;
			    case JSON_ERROR: //JSON��������
			    	enterHome();
			    	Toast.makeText(SplashActivity.this, "JSON��������", 0).show();
			    	break;
			}
		}
	};
	
	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		//�رյ�ǰActivityҳ��
		finish();
	}
	
	//���������Ի���
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ����");
		//builder.setCancelable(false);//ǿ���������ܣ����˶Ի����ѡ����������ܣ�
		//����ȡ����ʱ��
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//������ҳ��
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setMessage(description);
		builder.setPositiveButton("��������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//����APK�������滻��װ
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					//���sdcard����
					//afnal
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pals2.0.apk", new AjaxCallBack<File>() {
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							t.printStackTrace();
							Toast.makeText(getApplicationContext(), "����ʧ��", 1).show();
							super.onFailure(t, errorNo, strMsg);
						}
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
							//��ǰ���ذٷֱ�
							tv_update_info.setVisibility(View.VISIBLE);
							int progress = (int) (current * 100 / count);
							tv_update_info.setText("���ؽ��ȣ� " + progress + "%");
						}
						public void onSuccess(File t) {
							super.onSuccess(t);
							installAPK(t);
						}
						//��װAPK
						private void installAPK(File t) {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
							startActivity(intent);
						}
						
					});
				} else {
					Toast.makeText(getApplicationContext(), "û��sdcard���밲װ������", 0).show();
				}
			}
		});
		builder.setNegativeButton("�´���˵", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();//���ٶԻ���
				enterHome();//������ҳ��
			}
		});
		builder.show();
	}
	
	/**
	 * ����Ƿ����°汾������о�����
	 */
	private void checkUpdate() {
		new Thread() {

			@Override
			public void run() {
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();//��ʼʱ��
				try {
					URL url = new URL(getString(R.string.serverurl));
					//����
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//�������󷽷�
				    conn.setRequestMethod("GET");
				    conn.setConnectTimeout(8000);
				    conn.setReadTimeout(8000);
				    int code = conn.getResponseCode();
				    if (code == 200) {
				    	//�����ɹ�
					    InputStream is = conn.getInputStream();
					    //����ת�����ַ���
					    String result = StreamTools.readFromStream(is);
					    LogUtil.i(TAG, "�����ɹ�" + result);
					    //JSON����
					    JSONObject obj = new JSONObject(result);
					    version = (String) obj.get("version");
					    description = (String) obj.get("description");
					    apkurl = (String) obj.get("apkurl");
					    //У���Ƿ����°汾
					    if (getVersionName().equals(version)) {
					    	//�汾һ�£�û���°汾��������ҳ��
					    	msg.what = ENTER_HOME;
					    } else {
					    	//���°汾������һ�����Ի���
					    	msg.what = SHOW_UPDATE_DIALOG;
					    }
				    }
				} catch (MalformedURLException e) {
					msg.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = NETWORK_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = JSON_ERROR;
					e.printStackTrace();
				} finally {
					long endTime = System.currentTimeMillis();//����ʱ��
					long dTime = endTime - startTime;//���ѵ�ʱ��
					if(dTime < 3000) {
						try {
							Thread.sleep(3000 - dTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	/**
	 * �õ�Ӧ�ó���İ汾����
	 */
	
	private String getVersionName() {
		//���������ֻ���APK
		PackageManager pm = getPackageManager();
		
		try {
			//�õ�ָ��APK�Ĺ����嵥�ļ�
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
