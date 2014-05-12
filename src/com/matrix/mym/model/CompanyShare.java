package com.matrix.mym.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.MymDataBase;

public class CompanyShare implements Parcelable, Cloneable {
	public static final String STATE = "company_share";
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

	public void changePrice(Context context, double rand, int plusOrMinus) {
		double change = (rand % mPrice) * 0.1f;
		if(plusOrMinus==0)
			mPrice += change;
		else mPrice -= change;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeString(mName);
		dest.writeDouble(mPrice);
		dest.writeDouble(mClosingPrice);
		dest.writeString(mIndustry);
	}

	private CompanyShare(Parcel source) {
		mId = source.readLong();
		mName = source.readString();
		mPrice = source.readDouble();
		mClosingPrice = source.readDouble();
		mIndustry = source.readString();
	}

	public static final Parcelable.Creator<CompanyShare> CREATOR = new Parcelable.Creator<CompanyShare>() {
		@Override
		public CompanyShare createFromParcel(Parcel source) {
			return new CompanyShare(source);
		}

		@Override
		public CompanyShare[] newArray(int size) {
			return new CompanyShare[size];
		}
	};

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
}
