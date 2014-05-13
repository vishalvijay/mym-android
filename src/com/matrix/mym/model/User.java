package com.matrix.mym.model;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.UserSharesDB;
import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.controller.interfaces.NetWorthLoader;
import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.utils.Settings;
import com.matrix.mym.utils.SystemFeatureChecker;

public class User implements UserShareLoadedCallBack, Parcelable {
	protected static final String TAG = "User";
	private ArrayList<UserShare> mUserShares;
	private UserShareLoadedCallBack userShareLoadedCallBack;
	private boolean isLoaded = false;
	private UserCallBacks userCallBacks;

	public User(UserShareLoadedCallBack userShareLoadedCallBack) {
		this.userShareLoadedCallBack = userShareLoadedCallBack;
		UserSharesDB.getUserShares(this);
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
			oldUserShare.save();
			mUserShares.add(oldUserShare);
		} else {
			oldUserShare.addQuantity(quantity);
			oldUserShare.update();
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

	public void getNetBalance(final Context context,
			final NetWorthLoader netWorthLoader) {
		getMyCompanyShares(context, new CompanyShareLoaddedCallBack() {

			@Override
			public void onComplete(ArrayList<CompanyShare> companyShares) {
				double result = 0;
				for (int i = 0; i < companyShares.size(); i++) {
					result += companyShares.get(i).getPrice()
							* mUserShares.get(i).getQuantity();
				}
				netWorthLoader.onComplete(result + getAccountBalance(context));
			}
		});
	}

	public void getMyCompanyShares(Context context,
			final CompanyShareLoaddedCallBack callBack) {

		new AsyncTask<Void, Void, ArrayList<CompanyShare>>() {

			@Override
			protected ArrayList<CompanyShare> doInBackground(Void... params) {
				ArrayList<CompanyShare> result = new ArrayList<CompanyShare>();
				for (UserShare userShare : mUserShares) {
					result.add(CompanyShare.getById(userShare
							.getCompanyShareId()));
				}
				return result;
			}

			protected void onPostExecute(
					java.util.ArrayList<CompanyShare> result) {
				callBack.onComplete(result);
			};
		}.execute();

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
		if (userShare.getQuantity() == 0) {
			mUserShares.remove(userShare);
			userShare.delete();
		} else
			userShare.update();
		updateAccountBalance(context, companyShare.getPrice() * quantity);
	}

	public String getUuid(Context context) {
		return SystemFeatureChecker.getDeviceUuid(context);
	}

	public String getName(Context context) {
		return Settings.getUserName(context);
	}

	public void setName(Context context, String name) {
		Settings.setUserName(context, name);
	}
}
