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
	private static final String COL_LAST_PRICE_CHANGE = "last_price_change";
	public static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_COMPANY_SHARE_TABLE
			+ "("
			+ COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME
			+ " VARCHAR(128) NOT NULL, "
			+ COL_PRICE
			+ " REAL, "
			+ COL_LAST_PRICE_CHANGE + " REAL);";

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
			contentValues.put(COL_LAST_PRICE_CHANGE,
					companyShare.getLastPriceChange());
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
					COL_ID, COL_NAME, COL_PRICE, COL_LAST_PRICE_CHANGE }, null,
					null, null, null, COL_ID);
			while (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
				float price = cursor.getFloat(cursor.getColumnIndex(COL_PRICE));
				float lastPriceChange = cursor.getFloat(cursor
						.getColumnIndex(COL_LAST_PRICE_CHANGE));
				companyShares.add(new CompanyShare(id, name, price,
						lastPriceChange));
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return companyShares;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CompanyShareDB.CREATE_TABLE_NOTIFICATIONS);
		inserCompanyShare(db, "Axis Bank", 100, 0);
		inserCompanyShare(db, "Cipla", 50, 0);
		inserCompanyShare(db, "Bharat Heavy Electricals", 37.50f, 0);
		inserCompanyShare(db, "State Bank Of India", 536, 0);
		inserCompanyShare(db, "HDFC Bank", 300, 0);
		inserCompanyShare(db, "Hero Motocorp", 200, 0);
		inserCompanyShare(db, "Infosys", 995.5f, 0);
		inserCompanyShare(db, "Oil and Natural Gas Corporation", 57, 0);
		inserCompanyShare(db, "Reliance Industries", 122, 0);
		inserCompanyShare(db, "Tata Power", 512, 0);
		inserCompanyShare(db, "Hindalco Industries", 700, 0);
		inserCompanyShare(db, "Tata Steel", 30, 0);
		inserCompanyShare(db, "Larsen & Toubro", 233, 0);
		inserCompanyShare(db, "Mahindra & Mahindra", 55, 0);
		inserCompanyShare(db, "Tata Motors", 654, 0);
		inserCompanyShare(db, "Hindustan Unilever", 111, 0);
		inserCompanyShare(db, "ITC", 234, 0);
		inserCompanyShare(db, "Sesa Sterlite Ltd", 675, 0);
		inserCompanyShare(db, "Wipro", 93, 0);
		inserCompanyShare(db, "Sun Pharmaceutical", 56, 0);
		inserCompanyShare(db, "GAIL", 90, 0);
		inserCompanyShare(db, "ICICI Bank", 100, 0);
		inserCompanyShare(db, "Housing Development Fianance Corporation	", 33,
				0);
		inserCompanyShare(db, "Bharti Airtel", 45, 0);
		inserCompanyShare(db, "Maruti Suzuki", 54, 0);
		inserCompanyShare(db, "Tata Consultancy Services", 23, 0);
		inserCompanyShare(db, "NTPC", 56, 0);
		inserCompanyShare(db, "Dr. Reddy's", 865, 0);
		inserCompanyShare(db, "Bajaj Auto", 432, 0);
		inserCompanyShare(db, "Coal India", 321, 0);
	}

	private static void inserCompanyShare(SQLiteDatabase db, String name,
			float price, float lastPriceChange) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_NAME, name);
		contentValues.put(COL_PRICE, price);
		contentValues.put(COL_LAST_PRICE_CHANGE, lastPriceChange);
		db.insert(TABLE_COMPANY_SHARE_TABLE, COL_ID, contentValues);
	}
}
