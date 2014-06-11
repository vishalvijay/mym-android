package com.matrix.mym.view.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.NewsAdapter;
import com.matrix.mym.controller.interfaces.NewsInternetLoader;
import com.matrix.mym.controller.server.NewsController;
import com.matrix.mym.model.News;
import com.matrix.mym.view.activity.MymMainActivity;
import com.matrix.mym.view.activity.NewsDetailsActivity;

public class NewsFragment extends MymMainFragment implements OnClickListener,
		NewsInternetLoader, OnItemClickListener {
	private Button retryButton;
	private ProgressBar progressBar;
	private ListView newsListView;

	private MymMainActivity activity;
	private NewsController mNewsController;
	private NewsAdapter adapter;

	public NewsFragment() {
		super(R.string.news);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		newsListView = (ListView) rootView.findViewById(R.id.lvNews);
		newsListView.setOnItemClickListener(this);
		progressBar = (ProgressBar) rootView.findViewById(R.id.pbLoading);
		retryButton = (Button) rootView.findViewById(R.id.btRetry);
		retryButton.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		mNewsController = new NewsController(this);
		mNewsController.getAllNews();
	}

	@Override
	public void onClick(View v) {
		activity.onNavigationDrawerItemSelected(5);
	}

	@Override
	public void onLoadingFinsh(ArrayList<News> newsArrayList) {
		if (!isAdded())
			return;
		adapter = new NewsAdapter(getActivity(), newsArrayList);
		newsListView.setAdapter(adapter);
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onLoadingStarted() {
		if (!isAdded())
			return;
		progressBar.setVisibility(View.VISIBLE);
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
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (adapter == null)
			return;
		Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
		intent.putExtra(News.PARAM_ID, adapter.getItem(position).getId());
		getActivity().startActivity(intent);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.news, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			onClick(null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
