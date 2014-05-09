package com.matrix.mym.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.MymDataBase;

public class UserShare implements Parcelable {
	private long mId;
	private long mCompanyShareId;
	private long mQuantity;

	public UserShare(long id, long companyShareId, long quantity) {
		mId = id;
		mCompanyShareId = companyShareId;
		mQuantity = quantity;
	}

	public UserShare(Context context, long companyShareId, long quantity) {
		mCompanyShareId = companyShareId;
		mQuantity = quantity;
		long id = MymDataBase.saveUserShare(context, this);
		if (id != -1)
			mId = id;
	}

	public long getCompanyShareId() {
		return mCompanyShareId;
	}

	public long getQuantity() {
		return mQuantity;
	}

	public long getId() {
		return mId;
	}

	public void save(Context context) {
		MymDataBase.saveUserShare(context, this);
	}

	public static UserShare getInstance(Context context, long companyShareId) {
		return MymDataBase.getUserShare(context, companyShareId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeLong(mCompanyShareId);
		dest.writeLong(mQuantity);
	}

	private UserShare(Parcel source) {
		mId = source.readLong();
		mCompanyShareId = source.readLong();
		mQuantity = source.readLong();
	}

	public static final Parcelable.Creator<UserShare> CREATOR = new Parcelable.Creator<UserShare>() {
		@Override
		public UserShare createFromParcel(Parcel source) {
			return new UserShare(source);
		}

		@Override
		public UserShare[] newArray(int size) {
			return new UserShare[size];
		}
	};
}
