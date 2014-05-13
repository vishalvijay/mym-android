package com.matrix.mym.controller;

import java.util.ArrayList;
import java.util.Random;

import android.os.Handler;

import com.matrix.mym.controller.db.CompanyShareDB;
import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.controller.interfaces.ShareMarketServiceCallBacks;
import com.matrix.mym.model.CompanyShare;

public class ShareMarketManager implements CompanyShareLoaddedCallBack {
	protected static final int TIME_LIMIT = 5000;
	protected static final String TAG = "ShareMarketManager";
	private ArrayList<CompanyShare> mCompanyShares;
	private ShareMarketServiceCallBacks shareMarketServiceCallBacks;
	private boolean isLoaded = false;
	private boolean isPriceChanging = false;
	private Thread thread;

	public ShareMarketManager(
			ShareMarketServiceCallBacks shareMarkerServiceCallBacks) {
		this.shareMarketServiceCallBacks = shareMarkerServiceCallBacks;
		CompanyShareDB.getCompanyShares(this);
	}

	public void startShareMarket() {
		if (thread != null)
			return;
		if (mCompanyShares == null)
			throw new IllegalStateException("CompanyShare not yet loaded");
		if (mCompanyShares.size() == 0)
			throw new RuntimeException(
					"There should be atleast one CompanyShare in db");
		isPriceChanging = true;
		final Handler handler = new Handler();
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Random random = new Random(System.currentTimeMillis());
				while (isPriceChanging) {
					int limit = random.nextInt((mCompanyShares.size() / 3) + 1);
					for (int i = 0; i < limit; i++) {
						CompanyShare companyShare = mCompanyShares.get(random
								.nextInt(mCompanyShares.size()));
						double priceChange = (random.nextDouble() % companyShare
								.getPrice()) * 0.1;
						companyShare.addPrice(random.nextBoolean() ? priceChange
								: -priceChange);
					}
					handler.post(new Runnable() {

						@Override
						public void run() {
							shareMarketServiceCallBacks.onCompanyShareUpdated();
						}
					});
					try {
						Thread.sleep(random.nextInt(TIME_LIMIT));
					} catch (InterruptedException e) {
						break;
					}
				}
				for (CompanyShare companyShare : mCompanyShares) {
					companyShare.close();
				}
			}
		});
		thread.start();

	}

	public void stopShareMarket() {
		isPriceChanging = false;
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	@Override
	public void onComplete(ArrayList<CompanyShare> companyShares) {
		mCompanyShares = companyShares;
		isLoaded = true;
		shareMarketServiceCallBacks.onCompanyShareLoaded();
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
