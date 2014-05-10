package com.matrix.mym.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.mym.R;

public class LeaderBoardFragment extends MymMainFragment {

	public LeaderBoardFragment() {
		super(R.string.leaderboard);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_leaderboard,
				container, false);
		return rootView;
	}
}
