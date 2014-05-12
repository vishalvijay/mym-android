package com.matrix.mym.model;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.MymDataBase;
import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.utils.Settings;

public class User implements UserShareLoadedCallBack, Parcelable {
	protected static final String TAG = "User";
	private ArrayList<UserShare> mUserShares;
	private UserShareLoadedCallBack userShareLoadedCallBack;
	private boolean isLoaded = false;
	private UserCallBacks userCallBacks;

	public User(Context context, UserShareLoadedCallBack userShareLoadedCallBack) {
		this.userShareLoadedCallBack = userShareLoadedCallBack;
		MymDataBase.getAllUserShares(context, this);
	}

	@Override
	public void onComplete(ArrayList<UserShare> userShares) {
		mUserShares = userShares;
		isLoaded = true;
		if (userShareLoadedCallBack != null)
			userShareLoadedCallBack.onComplete(mUserShares);
	}

	public double getAccountBalance(Context context) {
		return Settings.getUserMoney(context);
	}

	public void setAccountBalance(Context context, double money) {
		Settings.setUserMoney(context, money);
		if (userCallBacks != null)
			userCallBacks.onUserPriceChange();
	}

	public void updateAccountBalance(Context context, double money) {
		money += Settings.getUserMoney(context);
		Settings.setUserMoney(context, money);
		if (userCallBacks != null)
			userCallBacks.onUserPriceChange();
	}

	public ArrayList<UserShare> getUserShares() {
		if (mUserShares == null)
			throw new IllegalStateException("UserShare not yet loaded");
		return mUserShares;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void buyCompanyShare(Context context, CompanyShare companyShare,
			long quantity) {
		UserShare oldUserShare = findUserShare(companyShare);
		if (oldUserShare == null) {
			oldUserShare = new UserShare(companyShare.getId(), quantity);
			oldUserShare.save(context);
			mUserShares.add(oldUserShare);
		} else {
			oldUserShare.addQuantity(quantity);
			oldUserShare.update(context);
		}
		updateAccountBalance(context, -companyShare.getPrice() * quantity);
	}

	private UserShare findUserShare(CompanyShare companyShare) {
		for (UserShare userShare : mUserShares) {
			if (companyShare.getId() == userShare.getCompanyShareId()) {
				return userShare;
			}
		}
		return null;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (isLoaded ? 1 : 0));
		dest.writeTypedList(mUserShares);
	}

	private User() {
		mUserShares = new ArrayList<UserShare>();
	}

	private User(Parcel source) {
		this();
		isLoaded = source.readByte() != 0;
		source.readTypedList(mUserShares, UserShare.CREATOR);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public double getNetBalance(Context context) {
		return 0;
	}

	public static interface UserCallBacks {
		public void onUserPriceChange();
	}

	public void registerUserCallBack(UserCallBacks userCallBacks) {
		this.userCallBacks = userCallBacks;
	}

	public void unRegisterUserCallBack() {
		userCallBacks = null;
	}

	public long getQuantityByCompanyShareId(long companyShareId) {
		long quantity = 0;
		for (UserShare userShare : mUserShares) {
			if (userShare.getCompanyShareId() == companyShareId) {
				quantity = userShare.getQuantity();
				break;
			}
		}
		return quantity;
	}

	public void sellCompanyShare(Context context, CompanyShare companyShare,
			long quantity) {
		UserShare userShare = findUserShare(companyShare);
		if (userShare == null)
			throw new IllegalStateException("You can't sell this CompanyShare");
		userShare.addQuantity(-quantity);
		userShare.update(context);
		updateAccountBalance(context, companyShare.getPrice() * quantity);
	}
}
