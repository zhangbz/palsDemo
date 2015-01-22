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
		tv_splash_version.setText("版本号： " + getVersionName());
		tv_update_info = (TextView) findViewById(R.id.tv_update_info);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			//检查升级
			checkUpdate();
		} else {
			//自动升级已经关闭
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					//进入主页面
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
			    case SHOW_UPDATE_DIALOG: //显示升级对话框
				    LogUtil.i(TAG, "显示升级对话框");
				    showUpdateDialog();
				    break;
			    case ENTER_HOME: //进入主页面
			    	enterHome();
			    	break;
			    case URL_ERROR: //URL错误
			    	enterHome();
			    	Toast.makeText(MyApplication.getContext(), "URL错误", 0).show();
			    	break;
			    case NETWORK_ERROR: //网络异常
			    	enterHome();
			    	Toast.makeText(MyApplication.getContext(), "网络错误", 0).show();
			    	break;
			    case JSON_ERROR: //JSON解析出错
			    	enterHome();
			    	Toast.makeText(SplashActivity.this, "JSON解析出错", 0).show();
			    	break;
			}
		}
	};
	
	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		//关闭当前Activity页面
		finish();
	}
	
	//弹出升级对话框
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示升级");
		//builder.setCancelable(false);//强制升级功能（除了对话框的选项，其他都不能）
		//监听取消的时间
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//进入主页面
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setMessage(description);
		builder.setPositiveButton("立刻升级", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//下载APK，并且替换安装
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					//如果sdcard存在
					//afnal
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pals2.0.apk", new AjaxCallBack<File>() {
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							t.printStackTrace();
							Toast.makeText(getApplicationContext(), "下载失败", 1).show();
							super.onFailure(t, errorNo, strMsg);
						}
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
							//当前下载百分比
							tv_update_info.setVisibility(View.VISIBLE);
							int progress = (int) (current * 100 / count);
							tv_update_info.setText("下载进度： " + progress + "%");
						}
						public void onSuccess(File t) {
							super.onSuccess(t);
							installAPK(t);
						}
						//安装APK
						private void installAPK(File t) {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
							startActivity(intent);
						}
						
					});
				} else {
					Toast.makeText(getApplicationContext(), "没有sdcard，请安装后再试", 0).show();
				}
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();//销毁对话框
				enterHome();//进入主页面
			}
		});
		builder.show();
	}
	
	/**
	 * 检查是否有新版本，如果有就升级
	 */
	private void checkUpdate() {
		new Thread() {

			@Override
			public void run() {
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();//开始时间
				try {
					URL url = new URL(getString(R.string.serverurl));
					//联网
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//设置请求方法
				    conn.setRequestMethod("GET");
				    conn.setConnectTimeout(8000);
				    conn.setReadTimeout(8000);
				    int code = conn.getResponseCode();
				    if (code == 200) {
				    	//联网成功
					    InputStream is = conn.getInputStream();
					    //把流转换成字符串
					    String result = StreamTools.readFromStream(is);
					    LogUtil.i(TAG, "联网成功" + result);
					    //JSON解析
					    JSONObject obj = new JSONObject(result);
					    version = (String) obj.get("version");
					    description = (String) obj.get("description");
					    apkurl = (String) obj.get("apkurl");
					    //校验是否有新版本
					    if (getVersionName().equals(version)) {
					    	//版本一致，没有新版本，进入主页面
					    	msg.what = ENTER_HOME;
					    } else {
					    	//有新版本，弹出一升级对话框
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
					long endTime = System.currentTimeMillis();//结束时间
					long dTime = endTime - startTime;//花费的时间
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
	 * 得到应用程序的版本名称
	 */
	
	private String getVersionName() {
		//用来管理手机的APK
		PackageManager pm = getPackageManager();
		
		try {
			//得到指定APK的功能清单文件
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
