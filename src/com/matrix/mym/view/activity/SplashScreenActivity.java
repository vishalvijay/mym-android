package com.matrix.mym.view.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matrix.mym.R;
import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.model.User;
import com.matrix.mym.model.UserShare;

public class SplashScreenActivity extends Activity implements
		UserShareLoadedCallBack {
	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spash_screen);
		mUser = new User(getApplicationContext(), this);
	}

	@Override
	public void onComplete(ArrayList<UserShare> userShares) {
		Intent intent = new Intent(getApplicationContext(),
				MymMainActivity.class);
		intent.putExtra(MymMainActivity.USER, mUser);
		startActivity(intent);
	}

}
