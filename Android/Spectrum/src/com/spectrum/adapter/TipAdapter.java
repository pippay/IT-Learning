package com.spectrum.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spectrum.activity.R;
import com.spectrum.bean.Tip;

public class TipAdapter extends BaseAdapter {

	private Context context;
	private List<Tip> tipList;
	private LayoutInflater inflater;

	public TipAdapter(Context context, List<Tip> tipList) {
		this.tipList = tipList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return tipList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return tipList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clearList() {
		tipList.clear();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_tip, null);
			holder = new ViewHolder();
			holder.weatherView = (TextView) convertView
					.findViewById(R.id.weather);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Tip tip = tipList.get(position);

		holder.weatherView.setText("跑步：" + tip.getRun() + "，天气："
				+ tip.getWeather() + "，日期：" + tip.getDate());

		return convertView;
	}

	private class ViewHolder {
		private TextView weatherView;
	}

}
