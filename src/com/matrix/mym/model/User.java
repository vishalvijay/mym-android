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
	}

	public void updateAccountBalance(Context context, double money) {
		money += Settings.getUserMoney(context);
		Settings.setUserMoney(context, money);
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
}
