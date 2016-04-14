package com.spectrum.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MineActivity extends FatherActivity implements OnClickListener {

	private LinearLayout historyLayout, analysisLayout, accountLayout,
			aboutLayout, logoutLayout;
	private TextView nameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		setActionBar(true, false, "个人主页");
		initView();
	}

	private void initView() {
		nameView = (TextView) findViewById(R.id.name);
		String name = sharedsp.getString("username", "");
		if (!TextUtils.isEmpty(name)) {
			nameView.setText(name.toString());
		}

		historyLayout = (LinearLayout) findViewById(R.id.history_layout);
		analysisLayout = (LinearLayout) findViewById(R.id.analysis_layout);
		accountLayout = (LinearLayout) findViewById(R.id.account_layout);
		aboutLayout = (LinearLayout) findViewById(R.id.about_layout);
		logoutLayout = (LinearLayout) findViewById(R.id.logout_layout);

		historyLayout.setOnClickListener(this);
		analysisLayout.setOnClickListener(this);
		accountLayout.setOnClickListener(this);
		aboutLayout.setOnClickListener(this);
		logoutLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.history_layout:
			intent = new Intent(this, TipsActivity.class);
			startActivity(intent);
			break;
		case R.id.analysis_layout:
			intent = new Intent(this, AnalysisActivity.class);
			startActivity(intent);
			break;
		case R.id.account_layout:
			intent = new Intent(this, AccountActivity.class);
			startActivity(intent);
			break;
		case R.id.about_layout:
			intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.logout_layout:
			new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage("确定注销登录？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 清空缓存信息
									sharedsp.edit().clear().commit();
									finish();
								}
							}).setNegativeButton("取消", null).show();
			break;
		default:
			break;
		}

	}

}
