package com.zhihu.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ContentActivity extends ActionBarActivity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		// ActionBar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionBar));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.arrows_left);
		// WebView
		initView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://daily.zhihu.com/story/7439946");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_share) {
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_collect) {
			Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_comment) {
			Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_like) {
			Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
