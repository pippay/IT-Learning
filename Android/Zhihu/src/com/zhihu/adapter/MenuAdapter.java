package com.zhihu.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhihu.activity.R;
import com.zhihu.bean.Menu;

@SuppressLint("InflateParams")
public class MenuAdapter extends BaseAdapter {

	private ArrayList<Menu> menuList;
	private Activity activity;
	private LayoutInflater inflater;
	private int focusNum = 0;

	public MenuAdapter() {
		super();
	}

	public MenuAdapter(Activity activity, ArrayList<Menu> menuList) {
		this.activity = activity;
		this.menuList = menuList;
	}

	@Override
	public int getCount() {
		return menuList.size();
	}

	@Override
	public Object getItem(int i) {
		return menuList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	// 更新记录
	public void changeItem(int position, Menu menu) {
		menuList.remove(position);
		menuList.add(position, menu);
		this.notifyDataSetChanged();
	}

	// 删除记录
	public void removeItem(int position) {
		menuList.remove(position);
		this.notifyDataSetChanged();
	}

	// 新增记录
	public void addItem(int position, Menu menu) {
		menuList.add(position, menu);
		this.notifyDataSetChanged();
	}

	// 记录排序
	public void sortItem(int position, Menu menu) {
		menuList.remove(position);
		menuList.add(focusNum, menu);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (inflater == null) {
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.text);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Menu menu = menuList.get(position);
		holder.textView.setText(menu.getName());
		if (menu.isFocus() == true) {
			holder.imageView.setImageResource(R.drawable.icon_right);
			holder.imageView.setEnabled(false);
		} else {
			holder.imageView.setImageResource(R.drawable.icon_plus);
			holder.imageView.setEnabled(true);
		}
		holder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 当前设置为已关注
				menu.setFocus(true);
				// 更新menuList排序
				sortItem(position, menu);
				Toast.makeText(activity, "关注成功", Toast.LENGTH_SHORT).show();
				focusNum++;
			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView textView;
		private ImageView imageView;
	}

}