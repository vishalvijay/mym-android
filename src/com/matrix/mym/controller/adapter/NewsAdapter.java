package com.matrix.mym.controller.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.News;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("ViewConstructor")
public class NewsAdapter extends SupportArrayAdapter<News> {

	public NewsAdapter(Context context, ArrayList<News> objects) {
		super(context, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.news_list_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.titleTextView = (TextView) convertView
					.findViewById(R.id.tvTitle);
			viewHolder.contentTextView = (TextView) convertView
					.findViewById(R.id.tvContent);
			viewHolder.newsImageView = (ImageView) convertView
					.findViewById(R.id.ivNews);
			convertView.setTag(viewHolder);

		} else
			viewHolder = (ViewHolder) convertView.getTag();
		News news = getItem(position);
		viewHolder.titleTextView.setText(news.getTitle().replace('\n', ' '));
		viewHolder.contentTextView
				.setText(news.getContent().replace('\n', ' '));
		ImageLoader.getInstance().displayImage(news.getImageUrl(),
				viewHolder.newsImageView);
		return convertView;
	}

	private static class ViewHolder {
		public TextView titleTextView, contentTextView;
		public ImageView newsImageView;
	}
}
