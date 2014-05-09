package com.matrix.mym.controller;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.os.AsyncTask;

import com.matrix.mym.controller.db.MymDataBase;
import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.controller.interfaces.ShareMarkerServiceCallBacks;
import com.matrix.mym.model.CompanyShare;

public class ShareMarketManager implements CompanyShareLoaddedCallBack {
	protected static final int TIME_LIMIT = 5000;
	protected static final String TAG = "ShareMarketManager";
	private ArrayList<CompanyShare> mCompanyShares;
	private Context context;
	private ShareMarkerServiceCallBacks shareMarkerServiceCallBacks;
	private boolean isLoaded = false;
	private boolean isPriceChanging = false;

	public ShareMarketManager(Context context,
			ShareMarkerServiceCallBacks shareMarkerServiceCallBacks) {
		this.context = context;
		this.shareMarkerServiceCallBacks = shareMarkerServiceCallBacks;
		MymDataBase.getAllCompanyShares(this.context, this);
	}

	public void startShareMarket() {
		if (mCompanyShares == null)
			throw new IllegalStateException("CompanyShare not yet loaded");
		new AsyncTask<Void, Void, Void>() {

			protected void onPreExecute() {
				isPriceChanging = true;
			};

			@Override
			protected Void doInBackground(Void... params) {
				Random random = new Random(System.currentTimeMillis());
				while (isPriceChanging) {
					for (int i = 0; i < random
							.nextInt((mCompanyShares.size() + 1) / 3); i++) {
						CompanyShare companyShare = mCompanyShares.get(random
								.nextInt(mCompanyShares.size() - 1));
						companyShare.chnagePrice(context, random.nextFloat());
					}
					publishProgress();
					try {
						Thread.sleep(random.nextInt(TIME_LIMIT));
					} catch (InterruptedException e) {
					}
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				shareMarkerServiceCallBacks.onCompanyShareUpdated();
			};

		}.execute();
	}

	public void stopShareMarket() {
		isPriceChanging = true;
	}

	@Override
	public void onComplete(ArrayList<CompanyShare> companyShares) {
		mCompanyShares = companyShares;
		isLoaded = true;
		shareMarkerServiceCallBacks.onCompanyShareLoaded();
	}

	public ArrayList<CompanyShare> getAllCompanyShares() {
		if (mCompanyShares != null)
			return mCompanyShares;
		else
			throw new IllegalStateException("CompanyShare not yet loaded");
	}

	public boolean isLoaded() {
		return isLoaded;
	}
}
