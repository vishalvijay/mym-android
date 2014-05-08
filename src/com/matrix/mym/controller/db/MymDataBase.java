package com.matrix.mym.controller.db;

import java.util.ArrayList;

import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.model.CompanyShare;

import android.content.Context;
import android.os.AsyncTask;

public class MymDataBase {

	String TAG = "MymDataBase";
	public static final String DATABASE_NAME = "mym.db";
	public static final int DATABASE_VERSION = 1;

	private static MymDataBase singltonObject;
	private DatabaseHelper mDatabaseHelper;
	private CompanyShareDB mCompanyShareDB;

	private MymDataBase(Context context) {
		mDatabaseHelper = new DatabaseHelper(context);
		mCompanyShareDB = new CompanyShareDB(mDatabaseHelper);
	}

	private static MymDataBase getInstance(Context context) {
		if (singltonObject == null)
			singltonObject = new MymDataBase(context);
		return singltonObject;
	}

	public static void closeDb() {
		if (singltonObject != null) {
			singltonObject.mDatabaseHelper.close();
			singltonObject = null;
		}
	}

	synchronized public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public CompanyShareDB getCompanyShareDB() {
		return mCompanyShareDB;
	}

	public static void getAllCompanyShares(final Context context,
			final CompanyShareLoaddedCallBack callBack) {
		new AsyncTask<Void, Void, ArrayList<CompanyShare>>() {

			@Override
			protected ArrayList<CompanyShare> doInBackground(Void... params) {
				return getInstance(context).getCompanyShareDB()
						.getCompanyShares();
			}

			protected void onPostExecute(
					java.util.ArrayList<CompanyShare> result) {
				callBack.onComplete(result);
			};

		}.execute();
	}

	public static boolean updatePriceAndLastPriceOfCompanyShare(
			Context context, CompanyShare companyShare) {
		return getInstance(context).getCompanyShareDB()
				.updatePriceAndLastPrice(companyShare);
	}
}
