package com.zhangbz.pals.ui;

import com.zhangbz.pals.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义的组合控件，包含两个TextView，一个CheckBox，一个View
 * @author Administrator
 *
 */
public class SettingItemView extends RelativeLayout {

	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;

	private void initView(Context context) {
		//布局文件-->view，并且加载到SettingItemView
		View.inflate(context, R.layout.setting_item_view, this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
	}
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * 带有两个构造函数的方法，布局文件使用的时候调用
	 * @param context
	 * @param attrs
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}
	
	/**
	 * 校验组合控件是否被选中
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}
	
	/**
	 * 设置组合控件的状态
	 */
	public void setChecked(boolean checked) {
		cb_status.setChecked(checked);
	}
	
	/**
	 * 设置组合控件的描述信息
	 */
	public void setDesc(String text) {
		tv_desc.setText(text);
	}
	
}
