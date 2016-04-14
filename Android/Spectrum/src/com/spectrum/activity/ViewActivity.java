package com.spectrum.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.spectrum.app.API;

import cz.msebera.android.httpclient.Header;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends FatherActivity {

	private TextView view;
	private int id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		setActionBar(true, false, "查看详情");
		initView();
		getData();
	}

	private void initView() {
		//
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);

		view = (TextView) findViewById(R.id.view);

	}

	private void getData() {
		// 客户端网络请求，获取短信验证码
		AsyncHttpClient client_register = new AsyncHttpClient();
		// 创建请求参数的封装的对象
		RequestParams params = new RequestParams();
		params.put("id", id);
		// 执行get方法
		client_register.post(API.TIP_VIEW, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == 200) {
							try {
								JSONObject response = new JSONObject(
										new String(responseBody));
								Log.e("ADD_TIP", response.toString());
								if (response.getInt("error") == 1) {
									Toast.makeText(ViewActivity.this,
											response.getString("msg"),
											Toast.LENGTH_SHORT).show();
								} else {

									// JSONObject
									// data = new
									// JSONObject(
									// response.getString("data"));

									Toast.makeText(ViewActivity.this, "获取成功",
											Toast.LENGTH_SHORT).show();
									view.setText(response.getString("weather")
											.toString());

								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(ViewActivity.this, "网络异常，稍后再试",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						error.printStackTrace();
						Toast.makeText(ViewActivity.this, "网络异常，稍后再试",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
