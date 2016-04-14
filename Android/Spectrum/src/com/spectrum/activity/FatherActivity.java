package com.spectrum.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//所有Activity的父类
public class FatherActivity extends Activity {

	protected LinearLayout titlebar;
	protected ImageView actionBtn;
	protected TextView pageTitle;
	public SharedPreferences sharedsp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTint();
		initialize();
	}

	// synchronized 用于线程安全，防止多线程同时创建实例
	private synchronized void initialize() {
		if (sharedsp == null) {
			sharedsp = getSharedPreferences("mrkui", Context.MODE_PRIVATE);
		}
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	protected void setActionBar(boolean arrow, boolean logo, String title) {
		//
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		// 标题
		actionBar.setTitle(title);
		// 决定左上角图标的右侧是否有向左的小箭头, true有小箭头，并且图标可以点击
		actionBar.setDisplayHomeAsUpEnabled(arrow);
		// 使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，
		// 否则，显示应用程序图标，对应id为android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
		actionBar.setDisplayShowHomeEnabled(logo);

		// actionBar.setDisplayUseLogoEnabled(false);
	}

	private void initTint() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		// 创建状态栏的管理实例
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// 激活状态栏设置
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintColor(Color.parseColor("#FF1E90FF"));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onDestroy();
	}

	// 返回按钮
	// 如果当前Activity是taskroot 则返回MainActivity，否则直接finish
	protected class BackListener implements OnClickListener {
		Activity activity;

		public BackListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	}

	protected final static int KEYBOARD_SHOW = 1;
	protected final static int KEYBOARD_HIDE = 2;

	/**
	 * 
	 * @param eText
	 *            和键盘关联的EditText
	 * @param action
	 *            KEYBOARD_SHOW | KEYBOARD_HIDE 表示开启或关闭软键盘的操作码
	 */
	protected void optKeyBoard(EditText eText, int action) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (action == KEYBOARD_HIDE) {
			if (imm.isActive()) {
				imm.hideSoftInputFromWindow(eText.getWindowToken(), 0);
			}
		} else if (action == KEYBOARD_SHOW) {
			imm.showSoftInput(eText, 0);
		}
	}

}
