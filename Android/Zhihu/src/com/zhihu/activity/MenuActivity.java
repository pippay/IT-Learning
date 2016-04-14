package com.zhihu.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhihu.adapter.MenuAdapter;
import com.zhihu.bean.Menu;

public class MenuActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout, homeLayout, infoLayout;
	private TextView collect, download;
	private ListView listView;
	private ArrayList<Menu> menuList;
	private MenuAdapter menuAdapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		initView();
		initListView();
	}

	private void initView() {
		backLayout = (LinearLayout) findViewById(R.id.back_layout);
		homeLayout = (LinearLayout) findViewById(R.id.home_layout);
		infoLayout = (LinearLayout) findViewById(R.id.info_layout);
		collect = (TextView) findViewById(R.id.collect);
		download = (TextView) findViewById(R.id.download);
		backLayout.setOnClickListener(this);
		homeLayout.setSelected(true);
		homeLayout.setOnClickListener(this);
		infoLayout.setOnClickListener(this);
		collect.setOnClickListener(this);
		download.setOnClickListener(this);

	}

	private void initListView() {
		listView = (ListView) findViewById(R.id.listview);
		menuList = new ArrayList<Menu>();
		for (int i = 1; i < 21; i++) {
			Menu menu = new Menu();
			menu.setId(i);
			menu.setName("第" + i + "条");
			menu.setFocus(false);
			menuList.add(menu);
		}
		menuAdapter = new MenuAdapter(this, menuList);
		listView.setAdapter(menuAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				finish();
				overridePendingTransition(0, R.anim.activity_zoomout);
			}
		});

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.back_layout:
			finish();
			overridePendingTransition(0, R.anim.activity_zoomout);
			break;
		case R.id.info_layout:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.home_layout:
			Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
			homeLayout.setSelected(true);
			finish();
			overridePendingTransition(0, R.anim.activity_zoomout);
			break;
		case R.id.collect:
			Toast.makeText(this, "我的收藏", Toast.LENGTH_SHORT).show();
			break;
		case R.id.download:
			Toast.makeText(this, "离线下载", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(0, R.anim.activity_zoomout);
	}
}
