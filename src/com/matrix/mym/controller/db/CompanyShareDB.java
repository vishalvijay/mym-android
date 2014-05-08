package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.matrix.mym.model.CompanyShare;

public class CompanyShareDB {
	public static final String TABLE_COMPANY_SHARE_TABLE = "company_share";
	private static final String COL_ID = "_id";
	private static final String COL_NAME = "name";
	private static final String COL_PRICE = "price";
	private static final String COL_LAST_PRICE = "last_price";
	public static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_COMPANY_SHARE_TABLE
			+ "("
			+ COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME
			+ " VARCHAR(128) NOT NULL, "
			+ COL_PRICE
			+ " REAL, "
			+ COL_LAST_PRICE + " REAL);";

	public static final String DROP_TABLE_RESULT_USN_HISTORY = "DROP TABLE IF EXISTS "
			+ TABLE_COMPANY_SHARE_TABLE + ";";

	private DatabaseHelper mDatabaseHelper;

	public CompanyShareDB(DatabaseHelper databaseHelper) {
		mDatabaseHelper = databaseHelper;
	}

	synchronized public boolean updatePriceAndLastPrice(
			CompanyShare companyShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_PRICE, companyShare.getPrice());
			contentValues.put(COL_LAST_PRICE, companyShare.getLastPrice());
			SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
			result = db.update(TABLE_COMPANY_SHARE_TABLE, contentValues, COL_ID
					+ "=? ", new String[] { companyShare.getId() + "" });
			db.close();
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

	synchronized public ArrayList<CompanyShare> getCompanyShares() {
		ArrayList<CompanyShare> companyShares = new ArrayList<CompanyShare>();
		try {
			SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_COMPANY_SHARE_TABLE, new String[] {
					COL_ID, COL_NAME, COL_PRICE, COL_LAST_PRICE }, null, null,
					null, null, COL_ID);
			while (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
				float price = cursor.getFloat(cursor.getColumnIndex(COL_PRICE));
				float lastPrice = cursor.getFloat(cursor
						.getColumnIndex(COL_LAST_PRICE));
				companyShares.add(new CompanyShare(id, name, price, lastPrice));
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return companyShares;
	}

}
