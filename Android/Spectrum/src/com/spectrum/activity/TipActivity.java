package com.spectrum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

public class TipActivity extends FatherActivity {

	private String learn, code, body, mood, fun;
	private String run, weather, date;
	private RatingBar ratingBar1, ratingBar2, ratingBar3, ratingBar4,
			ratingBar5;
	private EditText textRun, textWeather, textDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		setActionBar(true, false, "查看详情");
		initIntent();
		initView();
	}

	private void initIntent() {
		Intent intent = getIntent();
		learn = intent.getStringExtra("learn");
		code = intent.getStringExtra("code");
		body = intent.getStringExtra("body");
		mood = intent.getStringExtra("mood");
		fun = intent.getStringExtra("fun");
		run = intent.getStringExtra("run");
		weather = intent.getStringExtra("weather");
		date = intent.getStringExtra("date");
	}

	private void initView() {
		ratingBar1 = (RatingBar) findViewById(R.id.ratingbar1);
		ratingBar2 = (RatingBar) findViewById(R.id.ratingbar2);
		ratingBar3 = (RatingBar) findViewById(R.id.ratingbar3);
		ratingBar4 = (RatingBar) findViewById(R.id.ratingbar4);
		ratingBar5 = (RatingBar) findViewById(R.id.ratingbar5);

		textRun = (EditText) findViewById(R.id.et_run);
		textWeather = (EditText) findViewById(R.id.et_weather);
		textDate = (EditText) findViewById(R.id.et_date);

		ratingBar1.setRating(Float.parseFloat(learn));
		ratingBar2.setRating(Float.parseFloat(code));
		ratingBar3.setRating(Float.parseFloat(body));
		ratingBar4.setRating(Float.parseFloat(mood));
		ratingBar5.setRating(Float.parseFloat(fun));

		textRun.setText(run);
		textWeather.setText(weather);
		textDate.setText(date);
	}

}
