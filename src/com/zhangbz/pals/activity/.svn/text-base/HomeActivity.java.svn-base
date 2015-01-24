package com.zhangbz.pals.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangbz.pals.R;

public class HomeActivity extends Activity {
	private GridView list_home;
	private MyAdapter adapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		list_home = (GridView)findViewById(R.id.list_home);
		adapter = new MyAdapter();
		list_home.setAdapter(adapter);
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				    case 5://½øÈëitem6
				    	intent = new Intent(HomeActivity.this,SettingActivity.class);
				    	startActivity(intent);
				    	break;
				    default:
				    	break;
				}
			}
		});
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
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
