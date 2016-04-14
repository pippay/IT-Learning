package com.spectrum.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.spectrum.app.API;
import com.spectrum.util.TextUtil;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends FatherActivity implements OnClickListener {

	private Button loginBtn;
	private EditText phone, passwd;
	private String phoneText, passwdText;
	private TextView forgetBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setActionBar(true, false, "登录");
		initView();
	}

	private void initView() {
		phone = (EditText) findViewById(R.id.login_phone);
		passwd = (EditText) findViewById(R.id.login_pwd);
		loginBtn = (Button) findViewById(R.id.login_btn);
		forgetBtn = (TextView) findViewById(R.id.forget_password);
		loginBtn.setOnClickListener(this);
		forgetBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			phoneText = phone.getText().toString().trim();
			passwdText = passwd.getText().toString().trim();
			if (TextUtils.isEmpty(phoneText)) {
				Toast.makeText(this, "手机号不为空！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (!TextUtil.isMobileNum(phoneText)) {
				Toast.makeText(this, "手机号格式错误！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (TextUtils.isEmpty(passwdText)) {
				Toast.makeText(this, "密码不为空！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (!TextUtil.isPassword(passwdText)) {
				Toast.makeText(this, "密码最少六位！", Toast.LENGTH_SHORT).show();
				break;
			}
			//
			// 客户端网络请求
			AsyncHttpClient client_register = new AsyncHttpClient();
			// 创建请求参数的封装的对象
			RequestParams params = new RequestParams();
			params.put("phone", phoneText);
			params.put("password", passwdText);
			// 执行post方法
			client_register.post(API.USER_LOGIN, params,
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
										Toast.makeText(LoginActivity.this,
												response.getString("msg"),
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(
												LoginActivity.this,
												response.getString("msg")
														+ "："
														+ response
																.getString("username"),
												Toast.LENGTH_SHORT).show();
										sharedsp.edit()
												.putString(
														"uid",
														response.getString("uid"))
												.putString(
														"username",
														response.getString("username"))
												.commit();
										// 添加成功后，跳转到个人中心
										Intent intent = new Intent();
										intent.setClass(LoginActivity.this,
												MineActivity.class);
										startActivity(intent);
										finish();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(LoginActivity.this, "网络异常，稍后再试",
										Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							error.printStackTrace();
							Toast.makeText(LoginActivity.this, "网络异常，稍后再试",
									Toast.LENGTH_SHORT).show();
						}
					});
			break;
		case R.id.forget_password:
			// 忘记密码
			Toast.makeText(LoginActivity.this, "忘记密码尚未上线，敬请期待～",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
