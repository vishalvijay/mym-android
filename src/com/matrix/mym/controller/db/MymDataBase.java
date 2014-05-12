package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.model.Quiz;
import com.matrix.mym.model.UserShare;

public class MymDataBase {

	String TAG = "MymDataBase";
	public static final String DATABASE_NAME = "mym.db";
	public static final int DATABASE_VERSION = 1;

	private static MymDataBase singltonObject;
	private DatabaseHelper mDatabaseHelper;
	private CompanyShareDB mCompanyShareDB;
	private UserSharesDB mUserShareDB;
	private QuizDB mQuizDB;

	private MymDataBase(Context context) {
		mDatabaseHelper = new DatabaseHelper(context);
		mCompanyShareDB = new CompanyShareDB(mDatabaseHelper);
		mUserShareDB = new UserSharesDB(mDatabaseHelper);
		mQuizDB = new QuizDB(mDatabaseHelper);
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

	public UserSharesDB getUserSharesDB() {
		return mUserShareDB;
	}

	public QuizDB getQuizDB() {
		return mQuizDB;
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

	public static boolean updatePriceOfCompanyShare(Context context,
			CompanyShare companyShare) {
		return getInstance(context).getCompanyShareDB().updatePrice(
				companyShare);
	}

	public static boolean updateClosingPriceOfCompanyShare(Context context,
			CompanyShare companyShare) {
		return getInstance(context).getCompanyShareDB().updateClosingPrice(
				companyShare);
	}

	public static long saveUserShare(Context context, UserShare userShare) {
		return getInstance(context).getUserSharesDB().saveUserShare(userShare);
	}

	public static UserShare getUserShare(Context context, long companyShareId) {
		return getInstance(context).getUserSharesDB().getUserShare(
				companyShareId);
	}

	public static void getAllUserShares(final Context context,
			final UserShareLoadedCallBack callBack) {
		new AsyncTask<Void, Void, ArrayList<UserShare>>() {

			@Override
			protected ArrayList<UserShare> doInBackground(Void... params) {
				return getInstance(context).getUserSharesDB().getUserShares();
			}

			protected void onPostExecute(ArrayList<UserShare> result) {
				callBack.onComplete(result);
			};

		}.execute();
	}

	public static Quiz getQuiz(Context context, long id) {
		return getInstance(context).getQuizDB().getQuizById(id);
	}
}
