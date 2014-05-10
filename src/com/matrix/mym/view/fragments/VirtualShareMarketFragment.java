package com.matrix.mym.view.fragments;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.CompanyShareAdapter;
import com.matrix.mym.controller.interfaces.ShareMarkerServiceCallBacks;
import com.matrix.mym.controller.service.ShareMarketService;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.utils.Constance;

public class VirtualShareMarketFragment extends MymMainFragment implements
		ShareMarkerServiceCallBacks {

	protected static final String TAG = "VirtualShareMarketFragment";
	private ListView companyShareListView;
	private ProgressBar companyShareLoadingProgressBar;
	private ShareMarketService shareMarketService;
	private ArrayAdapter<CompanyShare> adapter;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			ShareMarketService.ShareMarketServiceBinder b = (ShareMarketService.ShareMarketServiceBinder) binder;
			shareMarketService = b.getService();
			shareMarketService
					.registerCallbacks(VirtualShareMarketFragment.this);
			shareMarketService.notifyChanges();
		}

		public void onServiceDisconnected(ComponentName className) {
			shareMarketService.unRegisterCallbacks();
			shareMarketService = null;
		}
	};

	public VirtualShareMarketFragment() {
		super(R.string.virtualsharemarket);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_virtual_share_market, container, false);
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
	public void onCompanyShareLoaded() {
		adapter = new CompanyShareAdapter(getActivity(),
				shareMarketService.getAllCompanyShares());
		companyShareListView.setAdapter(adapter);
		companyShareLoadingProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onCompanyShareUpdated() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		Intent bindIntent = new Intent(getActivity(), ShareMarketService.class);
		if (isShareMarketServiceRunning() == false)
			getActivity().startService(bindIntent);
		getActivity().bindService(bindIntent, mConnection, 0);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unbindService(mConnection);
	}

	public boolean isShareMarketServiceRunning() {
		final ActivityManager activityManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		for (RunningServiceInfo runningServiceInfo : services) {
			String tesString = runningServiceInfo.service.getClassName();
			if (tesString.equals(Constance.PACKAGE
					+ ".controller.service.ShareMarketService")) {
				return true;
			}
		}
		return false;
	}
}
