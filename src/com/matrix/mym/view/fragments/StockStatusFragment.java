package com.matrix.mym.view.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.CompanyShareAdapter;
import com.matrix.mym.controller.db.CompanyShareDB;
import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.model.CompanyShare;

public class StockStatusFragment extends MymMainFragment implements
		CompanyShareLoaddedCallBack {

	protected static final String TAG = "StockStatusFragment";
	private ListView companyShareListView;
	private ProgressBar companyShareLoadingProgressBar;
	private ArrayAdapter<CompanyShare> adapter;

	public StockStatusFragment() {
		super(R.string.stock_status);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_stock_status,
				container, false);
		initViews(rootView);
		return rootView;
	}

	private void initViews(View rootView) {
		companyShareListView = (ListView) rootView
				.findViewById(R.id.lvCompanyShare);
		companyShareLoadingProgressBar = (ProgressBar) rootView
				.findViewById(R.id.pbCompanyShareLoading);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		CompanyShareDB.getCompanyShares(this);
	}

	@Override
	public void onComplete(ArrayList<CompanyShare> companyShares) {
		if (!isAdded())
			return;
		adapter = new CompanyShareAdapter(getActivity(), companyShares, true);
		companyShareListView.setAdapter(adapter);
		companyShareLoadingProgressBar.setVisibility(View.GONE);
	}
}