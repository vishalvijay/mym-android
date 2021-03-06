package com.matrix.mym.view.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.matrix.mym.R;
import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.model.User;
import com.matrix.mym.model.UserShare;
import com.matrix.mym.utils.BugSenseManager;
import com.matrix.mym.utils.GoogleAnalyticsManager;

public class SplashScreenActivity extends Activity implements
		UserShareLoadedCallBack {
	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseManager.initBugSense(this);
		setContentView(R.layout.activity_spash_screen);
		mUser = new User(this);
	}

	@Override
	public void onComplete(ArrayList<UserShare> userShares) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(),
						MymMainActivity.class);
				intent.putExtra(MymMainActivity.STATE_USER, mUser);
				startActivity(intent);
			}
		}, 5000);
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
