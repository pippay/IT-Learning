package com.spectrum.activity;

import android.os.Bundle;

public class AboutActivity extends FatherActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setActionBar(true, false, "关于 Mr.葵");
		initView();
	}

	private void initView() {
	}

}
