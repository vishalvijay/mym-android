package com.matrix.mym.view.activity;

import android.support.v7.app.ActionBarActivity;

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
}
