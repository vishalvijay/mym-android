package com.matrix.mym.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.matrix.mym.utils.BugSenseManager;
import com.matrix.mym.utils.GoogleAnalyticsManager;

public class MymActivity extends ActionBarActivity {
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private String mTitle;

	public static final String FRAGMENT_TITLE = "title";

	public void onSectionAttached(String title) {
		mTitle = title;
	}

	public String getCurrentTitle() {
		return mTitle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseManager.initBugSense(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		GoogleAnalyticsManager.startGoogleAnalyticsForActivity(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		GoogleAnalyticsManager.stopGoogleAnalyticsForActivity(this);
	}
}
