package com.zhangbz.pals.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangbz.pals.R;

public class WeixinFragment extends Fragment
{
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
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
				    case 0://µ¯³öµÇÂ½¿ò
				    	//showSigninDialog();
				    	break;
				    case 1://²âÊÔµÇÂ¼½çÃæ
				    	intent = new Intent(WeixinFragment.this, LoginActivity.class);
				    	startActivity(intent);
				    	break;
				    case 5://½øÈëitem6
				    	intent = new Intent(WeixinFragment.this,SettingActivity.class);
				    	startActivity(intent);
				    	break;
				    default:
				    	break;
				}
			}
		});
		return inflater.inflate(R.layout.tab01, container, false);
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity2.this, R.layout.list_item_home, null);
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
