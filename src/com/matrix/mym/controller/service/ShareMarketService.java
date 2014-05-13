package com.matrix.mym.controller.service;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.matrix.mym.controller.ShareMarketManager;
import com.matrix.mym.controller.interfaces.ShareMarketServiceCallBacks;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.utils.Settings;

public class ShareMarketService extends Service implements
		ShareMarketServiceCallBacks {
	protected static final String TAG = "ShareMarketService";
	private final ShareMarketServiceBinder mBinder = new ShareMarketServiceBinder();
	private ShareMarketManager mShareMarketManager;
	private ShareMarketServiceCallBacks shareMarketServiceCallBacks;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mShareMarketManager = new ShareMarketManager(this);
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class ShareMarketServiceBinder extends Binder {
		public ShareMarketService getService() {
			return ShareMarketService.this;
		}
	}

	@Override
	public void onCompanyShareLoaded() {
		if (shareMarketServiceCallBacks != null)
			shareMarketServiceCallBacks.onCompanyShareLoaded();
		mShareMarketManager.startShareMarket();
	}

	@Override
	public void onCompanyShareUpdated() {
		if (shareMarketServiceCallBacks != null)
			shareMarketServiceCallBacks.onCompanyShareUpdated();
	}

	public ArrayList<CompanyShare> getAllCompanyShares() {
		return mShareMarketManager.getAllCompanyShares();
	}

	public void registerCallbacks(
			ShareMarketServiceCallBacks shareMarkerServiceCallBacks) {
		this.shareMarketServiceCallBacks = shareMarkerServiceCallBacks;
	}

	public void unRegisterCallbacks() {
		shareMarketServiceCallBacks = null;
	}

	@Override
	public void onDestroy() {
		if (mShareMarketManager != null)
			mShareMarketManager.stopShareMarket();
		Settings.setShareMarketStartedStatus(getApplicationContext(), false);
		super.onDestroy();
	}

	public void notifyChanges() {
		if (mShareMarketManager.isLoaded())
			onCompanyShareLoaded();
	}
}
