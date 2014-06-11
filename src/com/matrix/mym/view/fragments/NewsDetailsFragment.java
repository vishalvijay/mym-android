package com.matrix.mym.view.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.controller.interfaces.NewsInternetLoader;
import com.matrix.mym.controller.server.NewsController;
import com.matrix.mym.model.News;
import com.matrix.mym.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("ValidFragment")
public class NewsDetailsFragment extends MymMainFragment implements
		OnClickListener, NewsInternetLoader {
	private Button retryButton;
	private ProgressBar progressBar;
	private ScrollView newsDetailsScrollView;
	private TextView titleTextView, timeTextView, contentTextView;
	private ImageView newsImageView;

	private NewsController mNewsController;

	private long mId = -1;
	private News mNews;

	private NewsDetailsFragment() {
		super(R.string.news);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news_details,
				container, false);
		initViews(rootView);
		return rootView;
	}

	private void initViews(View rootView) {
		newsDetailsScrollView = (ScrollView) rootView
				.findViewById(R.id.svNewsDetails);
		titleTextView = (TextView) rootView.findViewById(R.id.tvTitle);
		timeTextView = (TextView) rootView.findViewById(R.id.tvTime);
		contentTextView = (TextView) rootView.findViewById(R.id.tvContent);
		newsImageView = (ImageView) rootView.findViewById(R.id.ivNews);
		progressBar = (ProgressBar) rootView.findViewById(R.id.pbLoading);
		retryButton = (Button) rootView.findViewById(R.id.btRetry);
		retryButton.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mId = getArguments().getLong(News.PARAM_ID);
		mNewsController = new NewsController(this);
		loadNews();
	}

	private void loadNews() {
		if (mId == -1)
			new IllegalArgumentException("News id not yet set");
		mNewsController.getNewsById(mId);
	}

	@Override
	public void onClick(View v) {
		loadNews();
	}

	@Override
	public void onLoadingFinsh(ArrayList<News> newsArrayList) {
		if (!isAdded())
			return;
	}

	@Override
	public void onLoadingStarted() {
		if (!isAdded())
			return;
		progressBar.setVisibility(View.VISIBLE);
		newsDetailsScrollView.setVisibility(View.GONE);
		retryButton.setVisibility(View.GONE);
	}

	@Override
	public void onLoadingFailier() {
		if (!isAdded())
			return;
		progressBar.setVisibility(View.GONE);
		retryButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingFinsh(News news) {
		if (!isAdded())
			return;
		progressBar.setVisibility(View.GONE);
		mNews = news;
		fillThePage();
	}

	private void fillThePage() {
		titleTextView.setText(mNews.getTitle());
		timeTextView.setText(Utils.getHumanReadableDateTime(mNews.geTime()));
		contentTextView.setText(mNews.getContent());
		ImageLoader.getInstance().displayImage(mNews.getImageUrl(),
				newsImageView);
		newsDetailsScrollView.setVisibility(View.VISIBLE);
	}

	public static NewsDetailsFragment getInstance(long id) {
		NewsDetailsFragment fragment = new NewsDetailsFragment();
		Bundle extras = fragment.getArguments();
		extras.putLong(News.PARAM_ID, id);
		fragment.setArguments(extras);
		return fragment;
	}

}
