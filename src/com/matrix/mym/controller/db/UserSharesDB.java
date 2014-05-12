package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

	private DatabaseHelper mDatabaseHelper;

	public UserSharesDB(DatabaseHelper databaseHelper) {
		mDatabaseHelper = databaseHelper;
	}

	synchronized public long saveUserShare(UserShare userShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_COMPANY_SHARE_ID,
					userShare.getCompanyShareId());
			contentValues.put(COL_QUANTITY, userShare.getQuantity());
			SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
			result = db.insertOrThrow(TABLE_NAME, COL_ID, contentValues);
			db.close();
		} catch (SQLException e) {
			result = -1;
		}
		return result;
	}

	synchronized public ArrayList<UserShare> getUserShares() {
		ArrayList<UserShare> userShares = new ArrayList<UserShare>();
		try {
			SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID,
					COL_COMPANY_SHARE_ID, COL_QUANTITY }, null, null, null,
					null, COL_ID);
			while (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				long companyShareId = cursor.getLong(cursor
						.getColumnIndex(COL_COMPANY_SHARE_ID));
				long quantity = cursor.getLong(cursor
						.getColumnIndex(COL_QUANTITY));
				userShares.add(new UserShare(id, companyShareId, quantity));
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return userShares;
	}

	synchronized public UserShare getUserShare(long companyShareId) {
		UserShare userShare = null;
		try {
			SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID,
					COL_COMPANY_SHARE_ID, COL_QUANTITY }, COL_COMPANY_SHARE_ID
					+ "=? ", new String[] { companyShareId + "" }, null, null,
					null);
			if (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				long quantity = cursor.getLong(cursor
						.getColumnIndex(COL_QUANTITY));
				userShare = new UserShare(id, companyShareId, quantity);
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return userShare;
	}

	synchronized public boolean updatePrice(UserShare userShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_QUANTITY, userShare.getQuantity());
			SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
			result = db.update(TABLE_NAME, contentValues, COL_ID + "=? ",
					new String[] { userShare.getId() + "" });
			db.close();
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

}
