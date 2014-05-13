package com.matrix.mym.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.UserSharesDB;

public class UserShare implements Parcelable {
	private long mId;
	private long mCompanyShareId;
	private long mQuantity;

	public UserShare(long id, long companyShareId, long quantity) {
		mId = id;
		mCompanyShareId = companyShareId;
		mQuantity = quantity;
	}

	public UserShare(long companyShareId, long quantity) {
		mCompanyShareId = companyShareId;
		mQuantity = quantity;
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

	public void addQuantity(long quantity) {
		mQuantity += quantity;
	}

	public void save() {
		mId = UserSharesDB.save(this);
	}

	public void update() {
		UserSharesDB.update(this);
	}

	public static UserShare getInstance(long companyShareId) {
		return UserSharesDB.getUserShare(companyShareId);
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

	public void delete() {
		UserSharesDB.delete(this);
	}
}
