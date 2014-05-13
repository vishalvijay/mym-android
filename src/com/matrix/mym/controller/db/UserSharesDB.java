package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.matrix.mym.controller.interfaces.UserShareLoadedCallBack;
import com.matrix.mym.model.UserShare;

public class UserSharesDB {
	public static final String TABLE_NAME = "user_share";
	public static final String COL_ID = "_id";
	public static final String COL_COMPANY_SHARE_ID = "company_share_id";
	public static final String COL_QUANTITY = "quantity";
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + "(" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_COMPANY_SHARE_ID
			+ " INTEGER NOT NULL UNIQUE, " + COL_QUANTITY
			+ " INTEGER  NOT NULL, FOREIGN KEY(" + COL_COMPANY_SHARE_ID
			+ ") REFERENCES " + CompanyShareDB.TABLE_NAME + " ("
			+ CompanyShareDB.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

	public static long save(UserShare userShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_COMPANY_SHARE_ID,
					userShare.getCompanyShareId());
			contentValues.put(COL_QUANTITY, userShare.getQuantity());
			result = MymDataBase.getDb().insertOrThrow(TABLE_NAME, COL_ID,
					contentValues);
		} catch (SQLException e) {
			result = -1;
		}
		return result;
	}

	public static void getUserShares(final UserShareLoadedCallBack callBack) {
		new AsyncTask<Void, Void, ArrayList<UserShare>>() {

			@Override
			protected ArrayList<UserShare> doInBackground(Void... params) {
				ArrayList<UserShare> userShares = new ArrayList<UserShare>();
				try {
					Cursor cursor = MymDataBase.getDb().query(
							TABLE_NAME,
							new String[] { COL_ID, COL_COMPANY_SHARE_ID,
									COL_QUANTITY }, null, null, null, null,
							COL_ID);
					while (cursor.moveToNext()) {
						long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
						long companyShareId = cursor.getLong(cursor
								.getColumnIndex(COL_COMPANY_SHARE_ID));
						long quantity = cursor.getLong(cursor
								.getColumnIndex(COL_QUANTITY));
						userShares.add(new UserShare(id, companyShareId,
								quantity));
					}
					cursor.close();
				} catch (IllegalStateException ex) {
				}
				return userShares;
			}

			protected void onPostExecute(ArrayList<UserShare> result) {
				callBack.onComplete(result);
			};

		}.execute();
	}

	public static UserShare getUserShare(long companyShareId) {
		UserShare userShare = null;
		try {
			Cursor cursor = MymDataBase.getDb()
					.query(TABLE_NAME,
							new String[] { COL_ID, COL_COMPANY_SHARE_ID,
									COL_QUANTITY },
							COL_COMPANY_SHARE_ID + "=? ",
							new String[] { companyShareId + "" }, null, null,
							null);
			if (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				long quantity = cursor.getLong(cursor
						.getColumnIndex(COL_QUANTITY));
				userShare = new UserShare(id, companyShareId, quantity);
			}
			cursor.close();
		} catch (IllegalStateException ex) {
		}
		return userShare;
	}

	public static boolean update(UserShare userShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_QUANTITY, userShare.getQuantity());
			result = MymDataBase.getDb().update(TABLE_NAME, contentValues,
					COL_ID + "=? ", new String[] { userShare.getId() + "" });
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	public static boolean delete(UserShare userShare) {
		long result;
		try {
			result = MymDataBase.getDb().delete(TABLE_NAME, COL_ID + "=? ",
					new String[] { userShare.getId() + "" });
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

}
