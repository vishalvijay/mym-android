package com.matrix.mym.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.matrix.mym.view.activity.MymActivity;

@SuppressLint("ValidFragment")
public class MymMainFragment extends Fragment {
	/**
	 * @param titleRsId
	 *            Title of the fragment from resource.
	 */
	public MymMainFragment(int titleRsId) {
		Bundle args = new Bundle();
		args.putInt(MymActivity.FRAGMENT_TITLE, titleRsId);
		setArguments(args);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MymActivity) activity).onSectionAttached(activity
				.getString(getArguments().getInt(MymActivity.FRAGMENT_TITLE)));
	}
}
