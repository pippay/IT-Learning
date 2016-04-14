package com.spectrum.activity;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.spectrum.app.API;
import com.spectrum.widget.materialloadingprogressbar.CircleProgressBar;

import cz.msebera.android.httpclient.Header;

@SuppressLint("NewApi")
public class MainActivity extends FatherActivity implements OnClickListener {

	private RatingBar ratingBar1, ratingBar2, ratingBar3, ratingBar4,
			ratingBar5;
	private EditText textRun, textWeather, textDate;
	private Button submitBtn;
	private CircleProgressBar progressBar;
	private long preTime;
	private static final long TWO_SECOND = 2 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setActionBar(false, false, "Mr. 葵");
		initView();
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

		textRun.setOnClickListener(this);
		textWeather.setOnClickListener(this);
		textDate.setOnClickListener(this);

		submitBtn = (Button) findViewById(R.id.submit_btn);
		submitBtn.setOnClickListener(this);
		//
		progressBar = (CircleProgressBar) findViewById(R.id.progress1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_run:
			// Toast.makeText(this, "跑步", Toast.LENGTH_SHORT).show();
			// 选择跑步圈数
			final String[] runList = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8", "8+" };
			new AlertDialog.Builder(this).setTitle("选择圈数")
					.setItems(runList, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							textRun.setText(runList[which]);
							dialog.dismiss();
						}
					}).show();
			break;
		case R.id.et_weather:
			// Toast.makeText(this, "天气", Toast.LENGTH_SHORT).show();
			// 选择天气
			final String[] weatherList = { "晴", "阴", "雨", "风", "霾", "雪" };
			new AlertDialog.Builder(this)
					.setTitle("选择天气")
					.setItems(weatherList,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									textWeather.setText(weatherList[which]);
									dialog.dismiss();
								}
							}).show();
			break;
		case R.id.et_date:
			// Toast.makeText(this, "选择日期", Toast.LENGTH_SHORT).show();
			// 日期选择器
			Calendar cd = Calendar.getInstance();
			Date date = new Date();
			cd.setTime(date);
			new DatePickerDialog(MainActivity.this, new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					int monthOfYear1 = monthOfYear + 1;
					textDate.setText(year + "-" + monthOfYear1 + "-"
							+ dayOfMonth);
				}
			}, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH),
					cd.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.submit_btn:
			// 获取控件数据
			final float rating1 = ratingBar1.getRating();
			final float rating2 = ratingBar2.getRating();
			final float rating3 = ratingBar3.getRating();
			final float rating4 = ratingBar4.getRating();
			final float rating5 = ratingBar5.getRating();
			final String text_run = textRun.getText().toString().trim();
			final String text_weather = textWeather.getText().toString().trim();
			final String text_date = textDate.getText().toString().trim();
			// 判断是否登录
			if (TextUtils.isEmpty(sharedsp.getString("uid", ""))) {
				Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				break;
			}
			// 判断是否为空
			if (TextUtils.isEmpty(text_run)) {
				Toast.makeText(MainActivity.this, "未选择跑步", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			if (TextUtils.isEmpty(text_weather)) {
				Toast.makeText(MainActivity.this, "未选择天气", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			if (TextUtils.isEmpty(text_date)) {
				Toast.makeText(MainActivity.this, "未选择日期", Toast.LENGTH_SHORT)
						.show();
				break;
			}

			// 保存
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("提示")
					.setMessage("确定保存？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									//
									progressBar.setVisibility(View.VISIBLE);
									// 客户端网络请求
									AsyncHttpClient client_register = new AsyncHttpClient();
									// 创建请求参数的封装的对象
									RequestParams params = new RequestParams();
									params.put("learn", rating1);
									params.put("code", rating2);
									params.put("body", rating3);
									params.put("mood", rating4);
									params.put("fun", rating5);
									params.put("run", text_run);
									params.put("weather", text_weather);
									params.put("date", text_date);
									params.put("uid",
											sharedsp.getString("uid", ""));
									// 执行post方法
									client_register.post(API.TIP_ADD, params,
											new AsyncHttpResponseHandler() {
												@Override
												public void onSuccess(
														int statusCode,
														Header[] headers,
														byte[] responseBody) {
													if (statusCode == 200) {
														try {
															JSONObject response = new JSONObject(
																	new String(
																			responseBody));
															Log.e("ADD_TIP",
																	response.toString());
															if (response
																	.getInt("error") == 1) {
																progressBar
																		.setVisibility(View.INVISIBLE);
																Toast.makeText(
																		MainActivity.this,
																		response.getString("msg"),
																		Toast.LENGTH_SHORT)
																		.show();
															} else {
																Toast.makeText(
																		MainActivity.this,
																		"添加成功",
																		Toast.LENGTH_SHORT)
																		.show();
																// 添加成功后，跳转到详情页
																Intent intent = new Intent();
																intent.setClass(
																		MainActivity.this,
																		TipActivity.class);
																intent.putExtra(
																		"learn",
																		response.getString("learn"));
																intent.putExtra(
																		"code",
																		response.getString("code"));
																intent.putExtra(
																		"body",
																		response.getString("body"));
																intent.putExtra(
																		"mood",
																		response.getString("mood"));
																intent.putExtra(
																		"fun",
																		response.getString("fun"));
																intent.putExtra(
																		"run",
																		response.getString("run"));
																intent.putExtra(
																		"weather",
																		response.getString("weather"));
																intent.putExtra(
																		"date",
																		response.getString("date"));
																progressBar
																		.setVisibility(View.INVISIBLE);
																startActivity(intent);
															}
														} catch (JSONException e) {
															e.printStackTrace();
														}
													} else {
														progressBar
																.setVisibility(View.INVISIBLE);
														Toast.makeText(
																MainActivity.this,
																"网络异常，稍后再试",
																Toast.LENGTH_SHORT)
																.show();
													}
												}

												/**
												 * 失败处理的方法
												 * error：响应失败的错误信息封装到这个异常对象中
												 */
												@Override
												public void onFailure(
														int statusCode,
														Header[] headers,
														byte[] responseBody,
														Throwable error) {
													error.printStackTrace();
													progressBar
															.setVisibility(View.INVISIBLE);
													Toast.makeText(
															MainActivity.this,
															"网络异常，稍后再试",
															Toast.LENGTH_SHORT)
															.show();
												}
											});
								}
							}).setNegativeButton("取消", null).show();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_info) {
			if (TextUtils.isEmpty(sharedsp.getString("uid", ""))) {
				Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, MineActivity.class);
				startActivity(intent);
			}
			return true;
		}
		if (id == R.id.action_theme) {
			Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		if (id == R.id.action_about) {
			Toast.makeText(MainActivity.this, "关于App", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 截获后退键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = new Date().getTime();
			// 如果时间间隔大于2秒, 不处理
			if ((currentTime - preTime) > TWO_SECOND) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				preTime = currentTime;
				// 截获事件,不再处理
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
