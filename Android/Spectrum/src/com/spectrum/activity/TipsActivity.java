package com.spectrum.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.spectrum.adapter.TipAdapter;
import com.spectrum.app.API;
import com.spectrum.bean.Tip;
import com.spectrum.widget.materialloadingprogressbar.CircleProgressBar;

import cz.msebera.android.httpclient.Header;

public class TipsActivity extends FatherActivity {

	private TipAdapter tipAdapter;
	private ArrayList<Tip> tipList;
	private ListView listView;
	//
	int progress = 0;
	private Handler handler = new Handler();
	private CircleProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tips);
		setActionBar(true, false, "历史数据");
		initView();
		getTips();
	}

	private void initProgress() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// progressBar.setVisibility(View.INVISIBLE);
			}
		}, 3000);
	}

	private void initView() {
		progressBar = (CircleProgressBar) findViewById(R.id.progress1);
		//
		listView = (ListView) findViewById(R.id.tip_list);
		tipList = new ArrayList<Tip>();
		tipAdapter = new TipAdapter(TipsActivity.this, tipList);
		listView.setAdapter(tipAdapter);
	}

	private void getTips() {
		// 客户端网络请求
		AsyncHttpClient client_register = new AsyncHttpClient();
		// 创建请求参数的封装的对象
		RequestParams params = new RequestParams();
		params.put("uid", sharedsp.getString("uid", ""));
		// 执行post方法
		client_register.post(API.TIP_LIST, params,
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
									progressBar.setVisibility(View.INVISIBLE);
									Toast.makeText(TipsActivity.this,
											response.getString("msg"),
											Toast.LENGTH_SHORT).show();
								} else {
									progressBar.setVisibility(View.INVISIBLE);
									JSONArray tipArray = response
											.getJSONArray("tip");
									if (tipArray.length() == 0) {
										Toast.makeText(TipsActivity.this,
												"没有记录呦", Toast.LENGTH_SHORT)
												.show();
									} else {
										tipList.clear();
										for (int i = 0; i < tipArray.length(); i++) {
											JSONObject tipObj = tipArray
													.getJSONObject(i);
											Tip tip = new Tip();
											tip.setWeather(tipObj
													.getString("weather"));
											tip.setRun(tipObj.getString("run"));
											tip.setDate(tipObj
													.getString("date"));
											tipList.add(tip);
										}
										tipAdapter.notifyDataSetChanged();
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							progressBar.setVisibility(View.INVISIBLE);
							Toast.makeText(TipsActivity.this, "网络异常，稍后再试",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						error.printStackTrace();
						progressBar.setVisibility(View.INVISIBLE);
						Toast.makeText(TipsActivity.this, "网络异常，稍后再试",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
