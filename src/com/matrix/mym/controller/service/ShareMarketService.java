package com.matrix.mym.controller.service;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.matrix.mym.controller.ShareMarketManager;
import com.matrix.mym.controller.interfaces.ShareMarkerServiceCallBacks;
import com.matrix.mym.model.CompanyShare;

public class ShareMarketService extends Service implements
		ShareMarkerServiceCallBacks {
	protected static final String TAG = "ShareMarketService";
	private final ShareMarketServiceBinder mBinder = new ShareMarketServiceBinder();
	private ShareMarketManager mShareMarketManager;
	private ShareMarkerServiceCallBacks shareMarkerServiceCallBacks;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mShareMarketManager = new ShareMarketManager(getApplicationContext(),
				this);
		return Service.START_NOT_STICKY;
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
		if (shareMarkerServiceCallBacks != null)
			shareMarkerServiceCallBacks.onCompanyShareLoaded();
		mShareMarketManager.startShareMarket();
	}

	@Override
	public void onCompanyShareUpdated() {
		if (shareMarkerServiceCallBacks != null)
			shareMarkerServiceCallBacks.onCompanyShareUpdated();
	}

	public ArrayList<CompanyShare> getAllCompanyShares() {
		return mShareMarketManager.getAllCompanyShares();
	}

	public void registerCallbacks(
			ShareMarkerServiceCallBacks shareMarkerServiceCallBacks) {
		this.shareMarkerServiceCallBacks = shareMarkerServiceCallBacks;
	}

	public void unRegisterCallbacks() {
		this.shareMarkerServiceCallBacks = null;
	}

	@Override
	public void onDestroy() {
		if (mShareMarketManager != null)
			mShareMarketManager.stopShareMarket();
		super.onDestroy();
	}

	public void notifyChanges() {
		if (mShareMarketManager.isLoaded())
			onCompanyShareLoaded();
	}

}
