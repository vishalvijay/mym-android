package com.matrix.mym.model;

import java.util.Random;

import android.os.AsyncTask;

import com.matrix.mym.controller.interfaces.ShareMarketCallBacks;

public class CompanyShare {
	private long mId;
	private String mName;
	private float mPrice;
	private float mLastPrice;

	private ShareMarketCallBacks shareMarketCallBacks;
	private boolean isPriceChanging = false;

	public CompanyShare(long id, String name, float price, float lastPrice) {
		mId = id;
		mName = name;
		mPrice = price;
		mLastPrice = lastPrice;
	}

	public String getName() {
		return mName;
	}

	public float getPrice() {
		return mPrice;
	}

	public void setCurrentPrice(float price) {
		mPrice = price;
	}

	public long getId() {
		return mId;
	}

	public float getLastPrice() {
		return mLastPrice;
	}

	public void registerShareMarjetCallBack(
			ShareMarketCallBacks shareMarketCallBacks) {
		this.shareMarketCallBacks = shareMarketCallBacks;
	}

	public void unRegisterShareMarjetCallBack() {
		shareMarketCallBacks = null;
	}

	public void startChangePrice() {
		new AsyncTask<Void, Void, Void>() {
			protected void onPreExecute() {
				if (shareMarketCallBacks != null)
					shareMarketCallBacks.startedPriceChaning(getId());
				isPriceChanging = true;
			};

			@Override
			protected Void doInBackground(Void... params) {
				Random random = new Random(System.currentTimeMillis());
				while (isPriceChanging) {
				}
				return null;
			}

			protected void onPostExecute(Void result) {
				if (shareMarketCallBacks != null)
					shareMarketCallBacks.stoppedPriceChaning(mId);
			};

			@Override
			protected void onProgressUpdate(Void... values) {
				if (shareMarketCallBacks != null)
					shareMarketCallBacks.priceChanged(mId);
			};

		}.execute();
	}

	public void stopChangePrice() {
		isPriceChanging = false;
	}
}
