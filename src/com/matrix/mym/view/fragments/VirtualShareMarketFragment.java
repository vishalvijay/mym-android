package com.matrix.mym.view.fragments;

import java.util.Calendar;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.CompanyShareAdapter;
import com.matrix.mym.controller.interfaces.NetWorthLoader;
import com.matrix.mym.controller.interfaces.ShareMarketServiceCallBacks;
import com.matrix.mym.controller.receivers.ShareMarketReminderBroadcastReceiver;
import com.matrix.mym.controller.receivers.ShareMarketTimeUpBroadcastReceiver;
import com.matrix.mym.controller.service.ShareMarketService;
import com.matrix.mym.model.User.UserCallBacks;
import com.matrix.mym.utils.Constance;
import com.matrix.mym.utils.Settings;
import com.matrix.mym.utils.TimeCounter;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.activity.MymMainActivity;

public class VirtualShareMarketFragment extends MymMainFragment implements
		ShareMarketServiceCallBacks, OnClickListener, UserCallBacks {

	protected static final String TAG = "VirtualShareMarketFragment";
	private ListView companyShareListView;
	private ProgressBar companyShareLoadingProgressBar;
	private TextView timerTextView, balanceTextView, netTextView,
			marketStatusTextView;
	private RelativeLayout marketRelativeLayout, notStartedRelativeLayout;
	private ShareMarketService shareMarketService;
	private CompanyShareAdapter adapter;
	private MymMainActivity activity;
	private boolean isUnbindeService = false;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			ShareMarketService.ShareMarketServiceBinder b = (ShareMarketService.ShareMarketServiceBinder) binder;
			shareMarketService = b.getService();
			shareMarketService
					.registerCallbacks(VirtualShareMarketFragment.this);
			shareMarketService.notifyChanges();
		}

		public void onServiceDisconnected(ComponentName className) {
			onShareMarketStop();
			shareMarketService.unRegisterCallbacks();
			shareMarketService = null;
		}
	};
	private CountDownTimer timeCounter;

	public VirtualShareMarketFragment() {
		super(R.string.the_market);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_virtual_share_market, container, false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		companyShareListView = (ListView) rootView
				.findViewById(R.id.lvCompanyShare);
		companyShareLoadingProgressBar = (ProgressBar) rootView
				.findViewById(R.id.pbCompanyShareLoading);
		timerTextView = (TextView) rootView
				.findViewById(R.id.tvShareMarketTimer);
		balanceTextView = (TextView) rootView.findViewById(R.id.tvBalance);
		netTextView = (TextView) rootView.findViewById(R.id.tvNet);
		marketStatusTextView = (TextView) rootView
				.findViewById(R.id.tvShareMarketStatus);
		rootView.findViewById(R.id.btStartMarket).setOnClickListener(this);
		marketRelativeLayout = (RelativeLayout) rootView
				.findViewById(R.id.rlMarket);
		notStartedRelativeLayout = (RelativeLayout) rootView
				.findViewById(R.id.rlMarketNotStarted);
	}

	@Override
	public void onCompanyShareLoaded() {
		if (!isAdded())
			return;
		if (adapter == null) {
			adapter = new CompanyShareAdapter(getActivity(),
					shareMarketService.getAllCompanyShares());
			companyShareListView.setAdapter(adapter);
			companyShareLoadingProgressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCompanyShareUpdated() {
		if (!isAdded())
			return;
		adapter.notifyDataSetChanged();
	}

	public void onShareMarketStop() {
		if (!isAdded())
			return;
		if (timeCounter != null) {
			timeCounter.onFinish();
			timeCounter.cancel();
		}
		Utils.showInfoToast(getActivity(), R.string.time_up);
		reopenIt();
	}

	private void reopenIt() {
		activity.onNavigationDrawerItemSelected(1);
	}

	@Override
	public void onResume() {
		super.onResume();
		startMarketSerive();
	}

	private void startMarketSerive() {
		if (Settings.isShareMarketStarted(getActivity())) {
			Intent bindIntent = new Intent(getActivity(),
					ShareMarketService.class);
			if (isShareMarketServiceRunning() == false)
				getActivity().startService(bindIntent);
			isUnbindeService = getActivity().bindService(bindIntent,
					mConnection, 0);
			if (timeCounter != null)
				timeCounter.start();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (isUnbindeService && shareMarketService != null)
			try {
				getActivity().unbindService(mConnection);
			} catch (Exception ex) {
				Log.e(TAG, ex.getMessage());
			}
		if (timeCounter != null)
			timeCounter.cancel();
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity.getUser().registerUserCallBack(this);
		if (Settings.isShareMarketStarted(getActivity()))
			doShareMarketStarted();
		else
			doShareMarketNotStarted();
	}

	private void doShareMarketStarted() {
		companyShareLoadingProgressBar.setVisibility(View.VISIBLE);
		notStartedRelativeLayout.setVisibility(View.GONE);
		marketRelativeLayout.setVisibility(View.VISIBLE);
		updateBalances();
		timerTextView.setVisibility(View.VISIBLE);
		Calendar calendar = Settings.getMarketStartedTime(getActivity());
		timeCounter = new TimeCounter(timerTextView, calendar);
		marketStatusTextView.setText(getString(R.string.market_will_close_at,
				Utils.getHumanReadableString(Settings
						.getMarketStartedTime(getActivity()))));
	}

	private void updateBalances() {
		balanceTextView.setText(getBalance());
		setNetBalance();
	}

	private void doShareMarketNotStarted() {
		notStartedRelativeLayout.setVisibility(View.VISIBLE);
		marketRelativeLayout.setVisibility(View.GONE);
		updateBalances();
		timerTextView.setVisibility(View.INVISIBLE);
		Calendar calendar = Settings.getMarketStartedTime(getActivity());
		if (Utils.isValidCalender(calendar))
			marketStatusTextView.setText(getString(R.string.market_closed_at,
					Utils.getHumanReadableString(calendar)));
		else
			marketStatusTextView.setText(R.string.market_never_started);
	}

	private String setNetBalance() {
		activity.getUser().getNetBalance(getActivity(), new NetWorthLoader() {

			@Override
			public void onComplete(double money) {
				netTextView.setText(getString(R.string.net_balance,
						Utils.roundAndGetString(money)));
			}
		});
		return "";
	}

	private String getBalance() {
		String rsString = getString(
				R.string.money,
				Utils.roundAndGetString(activity.getUser().getAccountBalance(
						getActivity())));
		return getString(R.string.balance, rsString);
	}

	@Override
	public void onClick(View v) {
		startMarket();
	}

	private void startMarket() {
		Settings.setMarketStartedTime(getActivity(), Calendar.getInstance());
		Settings.setShareMarketStartedStatus(getActivity(), true);
		ShareMarketTimeUpBroadcastReceiver.register(getActivity());
		ShareMarketReminderBroadcastReceiver.register(getActivity());
		reopenIt();
	}

	@Override
	public void onUserPriceChange() {
		if (!isAdded())
			return;
		updateBalances();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		activity.getUser().unRegisterUserCallBack();
	}
}
