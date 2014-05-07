package com.matrix.mym.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.mym.R;

public class VirtualShareMarketFragment extends MymMainFragment {

	public VirtualShareMarketFragment() {
		super(R.string.virtualsharemarket);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_virtual_share_market, container, false);
		return rootView;
	}

}
