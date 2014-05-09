package com.matrix.mym.model;

import android.content.Context;

import com.matrix.mym.controller.db.MymDataBase;

public class CompanyShare {
	private long mId;
	private String mName;
	private float mPrice;
	private float mLastPriceChange;

	public CompanyShare(long id, String name, float price, float lastPriceChange) {
		mId = id;
		mName = name;
		mPrice = price;
		mLastPriceChange = lastPriceChange;
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

	public float getLastPriceChange() {
		return mLastPriceChange;
	}

	public void chnagePrice(Context context, float rand) {
		mLastPriceChange = rand % (mPrice * 0.1f);
		mPrice += mLastPriceChange;
		MymDataBase.updatePriceAndLastPriceOfCompanyShare(context,
				CompanyShare.this);
	}

	@Override
	public String toString() {
		return getLastPriceChange() + "#" + getName();
	}
}
