package com.matrix.mym.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.mym.R;

public class LeaderBordFragment extends MymMainFragment {

	public LeaderBordFragment() {
		super(R.string.leader_bord);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_leader_bord,
				container, false);
		return rootView;
	}
}
