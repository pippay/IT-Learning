package com.zhihu.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zhihu.view.XListView;
import com.zhihu.view.XListView.IXListViewListener;

@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements
		IXListViewListener, OnTouchListener, OnGestureListener {

	private XListView mListView;
	private GestureDetector mGestureDetector;
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	long preTime;
	public static final long TWO_SECOND = 2 * 1000;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ActionBar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionBar));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
		// 手势识别
		mGestureDetector = new GestureDetector(this);
		// ListView
		initListView();
	}

	@SuppressLint("ClickableViewAccessibility")
	private void initListView() {
		mListView = (XListView) findViewById(R.id.item_xlist);
		// 生成测试数据
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "这是第" + (i + 1) + "条新闻：冬天跑步南北方分别怎么穿");
			map.put("ItemImage", R.drawable.logo);
			listItem.add(map);
		}
		// Adapter
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,
				R.layout.xlist_item, new String[] { "ItemTitle", "ItemImage" },
				new int[] { R.id.text, R.id.img });
		// XListView
		mListView.setAdapter(mSimpleAdapter);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnTouchListener(this);
		mListView.setLongClickable(true);
		// XListView HeaderView
		View headerView = getLayoutInflater().inflate(
				R.layout.xlist_headerview, null);
		mListView.addHeaderView(headerView);
		// XListView 监听
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MainActivity.this,
						ContentActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		if (id == R.id.action_settings) {
			Toast.makeText(this, "设置选项", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_dark) {
			Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == android.R.id.home) {
			intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_zoomin, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling left
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && e1.getX() <= 5
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling right
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
		}
		return false;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 截获后退键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = new Date().getTime();
			// 如果时间间隔大于2秒, 不处理
			if ((currentTime - preTime) > TWO_SECOND) {
				// 显示消息
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				// 更新时间
				preTime = currentTime;
				// 截获事件,不再处理
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
