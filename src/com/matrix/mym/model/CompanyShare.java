package com.matrix.mym.model;

import android.content.Context;

import com.matrix.mym.controller.db.MymDataBase;

public class CompanyShare {
	private long mId;
	private String mName;
	private double mPrice;
	private double mClosingPrice;
	private String mIndustry;

	public CompanyShare(long id, String name, double price,
			double closingPrice, String industry) {
		mId = id;
		mName = name;
		mPrice = price;
		mClosingPrice = closingPrice;
		mIndustry = industry;
	}

	public String getName() {
		return mName;
	}

	public double getPrice() {
		return mPrice;
	}

	public long getId() {
		return mId;
	}

	public double getLastPriceChange() {
		return mPrice - mClosingPrice;
	}

	public double getClosingPrice() {
		return mClosingPrice;
	}

	public void changePrice(Context context, double rand) {
		double change = (rand % mPrice) * 0.1f;
		mPrice += change;
		MymDataBase.updatePriceOfCompanyShare(context, this);
	}

	@Override
	public String toString() {
		return getLastPriceChange() + "#" + getName();
	}

	public String getIndustry() {
		return mIndustry;
	}

	public boolean isPositiveChange() {
		return mPrice >= mClosingPrice;
	}

	public void saveClosingPrice(Context context) {
		mClosingPrice = mPrice;
		MymDataBase.updateClosingPriceOfCompanyShare(context, this);
	}
}
