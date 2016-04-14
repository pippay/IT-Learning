package com.spectrum.activity;

import android.os.Bundle;

public class AccountActivity extends FatherActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		setActionBar(true, false, "修改密码");
		initView();
	}

	private void initView() {

	}

}
