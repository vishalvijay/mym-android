package com.matrix.mym.model;

import android.content.Context;

import com.matrix.mym.controller.db.MymDataBase;

public class CompanyShare {
	private long mId;
	private String mName;
	private float mPrice;
	private float mLastPrice;
	private String mIndustry;

	public CompanyShare(long id, String name, float price, float lastPrice,
			String industry) {
		mId = id;
		mName = name;
		mPrice = price;
		mLastPrice = lastPrice;
		mIndustry = industry;
	}

	public String getName() {
		return mName;
	}

	public float getPrice() {
		return mPrice;
	}

	public long getId() {
		return mId;
	}

	public float getLastPriceChange() {
		return mPrice - mLastPrice;
	}

	public float getLastPrice() {
		return mLastPrice;
	}

	public void changePrice(Context context, float rand) {
		float change = (rand % mPrice) * 0.1f;
		mLastPrice = mPrice;
		mPrice += change;
		MymDataBase.updatePriceAndLastPriceOfCompanyShare(context,
				CompanyShare.this);
	}

	@Override
	public String toString() {
		return getLastPriceChange() + "#" + getName();
	}

	public String getIndustry() {
		return mIndustry;
	}

	public boolean isPositiveChange() {
		return mPrice >= mLastPrice;
	}
}
