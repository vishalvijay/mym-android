package com.matrix.mym.view.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.UserShareAdapter;
import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.view.activity.MymMainActivity;

public class MySharesFragment extends MymMainFragment implements
		CompanyShareLoaddedCallBack {

	protected static final String TAG = "StockStatusFragment";
	private ListView companyShareListView;
	private ProgressBar companyShareLoadingProgressBar;
	private TextView emptyTextView;
	private ArrayAdapter<CompanyShare> adapter;
	private MymMainActivity activity;

	public MySharesFragment() {
		super(R.string.my_shares);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_share, container,
				false);
		initViews(rootView);
		activity = ((MymMainActivity) getActivity());
		return rootView;
	}

	private void initViews(View rootView) {
		companyShareListView = (ListView) rootView
				.findViewById(R.id.lvCompanyShare);
		companyShareLoadingProgressBar = (ProgressBar) rootView
				.findViewById(R.id.pbCompanyShareLoading);
		emptyTextView = (TextView) rootView.findViewById(R.id.tvEmpty);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity.getUser().getMyCompanyShares(getActivity(), this);
	}

	@Override
	public void onComplete(ArrayList<CompanyShare> companyShares) {
		if (!isAdded())
			return;
		adapter = new UserShareAdapter(getActivity(), companyShares,
				activity.getUser());
		companyShareListView.setAdapter(adapter);
		companyShareLoadingProgressBar.setVisibility(View.GONE);
		if (companyShares.size() == 0)
			emptyTextView.setVisibility(View.VISIBLE);
	}
}