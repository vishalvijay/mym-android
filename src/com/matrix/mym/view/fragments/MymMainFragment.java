package com.matrix.mym.view.fragments;

import com.matrix.mym.view.activity.MymMainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class MymMainFragment extends Fragment {
	/**
	 * @param titleRsId
	 *            Title of the fragment from resource.
	 */
	public MymMainFragment(int titleRsId) {
		Bundle args = new Bundle();
		args.putInt(MymMainActivity.FRAGMENT_TITLE, titleRsId);
		setArguments(args);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MymMainActivity) activity).onSectionAttached(activity
				.getString(getArguments()
						.getInt(MymMainActivity.FRAGMENT_TITLE)));
	}
}
