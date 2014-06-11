package com.matrix.mym.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.matrix.mym.R;
import com.matrix.mym.model.News;
import com.matrix.mym.view.fragments.NewsDetailsFragment;

public class NewsDetailsActivity extends MymActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		showNewsFragment();
	}

	private void showNewsFragment() {
		long id = getIntent().getLongExtra(News.PARAM_ID, -1);
		if (id == -1)
			new IllegalArgumentException("News id is required");
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, NewsDetailsFragment.getInstance(id))
				.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setTitle(getCurrentTitle());
		return super.onCreateOptionsMenu(menu);
	}
}
