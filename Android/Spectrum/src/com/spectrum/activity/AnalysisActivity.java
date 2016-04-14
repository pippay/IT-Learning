package com.spectrum.activity;

import android.os.Bundle;

public class AnalysisActivity extends FatherActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analysis);
		setActionBar(true, false, "数据分析");
		initView();
	}

	private void initView() {
	}

}
