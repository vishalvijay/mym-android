package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.matrix.mym.controller.interfaces.CompanyShareLoaddedCallBack;
import com.matrix.mym.model.CompanyShare;

public class CompanyShareDB {
	public static final String TABLE_NAME = "company_share";
	public static final String COL_ID = "_id";
	public static final String COL_NAME = "name";
	public static final String COL_PRICE = "price";
	public static final String COL_CLOSING_PRICE = "closing_price";
	public static final String COL_INDUSTRY = "industry";
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + "(" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME
			+ " VARCHAR(128) NOT NULL, " + COL_PRICE + " REAL NOT NULL, "
			+ COL_CLOSING_PRICE + " REAL NOT NULL, " + COL_INDUSTRY
			+ " VARCHAR(128) NOT NULL);";

	public static boolean update(CompanyShare companyShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues
					.put(COL_CLOSING_PRICE, companyShare.getClosingPrice());
			contentValues.put(COL_PRICE, companyShare.getPrice());
			result = MymDataBase.getDb().update(TABLE_NAME, contentValues,
					COL_ID + "=? ", new String[] { companyShare.getId() + "" });
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

	public static void getCompanyShares(
			final CompanyShareLoaddedCallBack callBack) {
		new AsyncTask<Void, Void, ArrayList<CompanyShare>>() {

			@Override
			protected ArrayList<CompanyShare> doInBackground(Void... params) {
				ArrayList<CompanyShare> companyShares = new ArrayList<CompanyShare>();
				try {
					Cursor cursor = MymDataBase.getDb().query(
							TABLE_NAME,
							new String[] { COL_ID, COL_NAME, COL_PRICE,
									COL_CLOSING_PRICE, COL_INDUSTRY }, null,
							null, null, null, COL_ID);
					while (cursor.moveToNext()) {
						long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
						String name = cursor.getString(cursor
								.getColumnIndex(COL_NAME));
						double price = cursor.getDouble(cursor
								.getColumnIndex(COL_PRICE));
						double closingPrice = cursor.getDouble(cursor
								.getColumnIndex(COL_CLOSING_PRICE));
						String industry = cursor.getString(cursor
								.getColumnIndex(COL_INDUSTRY));
						companyShares.add(new CompanyShare(id, name, price,
								closingPrice, industry));
					}
					cursor.close();
				} catch (IllegalStateException ex) {
				}
				return companyShares;
			}

			protected void onPostExecute(ArrayList<CompanyShare> result) {
				callBack.onComplete(result);
			};

		}.execute();
	}

	public static CompanyShare getCompanyShare(long id) {
		CompanyShare companyShare = null;
		try {
			Cursor cursor = MymDataBase.getDb().query(
					TABLE_NAME,
					new String[] { COL_ID, COL_NAME, COL_PRICE,
							COL_CLOSING_PRICE, COL_INDUSTRY }, COL_ID + "=? ",
					new String[] { id + "" }, null, null, null);
			if (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
				double price = cursor.getDouble(cursor
						.getColumnIndex(COL_PRICE));
				double closingPrice = cursor.getDouble(cursor
						.getColumnIndex(COL_CLOSING_PRICE));
				String industry = cursor.getString(cursor
						.getColumnIndex(COL_INDUSTRY));
				companyShare = new CompanyShare(id, name, price, closingPrice,
						industry);
			}
			cursor.close();
		} catch (IllegalStateException ex) {
		}
		return companyShare;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CompanyShareDB.CREATE_TABLE);
		inserCompanyShare(db, "Traxis Bank", 1675, "Banking");
		inserCompanyShare(db, "Kepla", 385.35, "Pharmaceuticals");
		inserCompanyShare(db, "Bright Heavy Electricals Ltd", 199.75,
				"Electrical equipment");
		inserCompanyShare(db, "Estate bank of India", 2241.15, "Banking");
		inserCompanyShare(db, "CDFH Bank", 787.10, "Banking");
		inserCompanyShare(db, "Zorro Motorcorp", 2282.00, "Automotive");
		inserCompanyShare(db, "Timposys", 3149.05, "Information Technology");
		inserCompanyShare(db, "Oil and Steam Gas Corporation", 352.75,
				"Oil and gas");
		inserCompanyShare(db, "Brilliance Industires", 1028.05, "Oil and gas");
		inserCompanyShare(db, "Toto Power", 81.25, "Power");
		inserCompanyShare(db, "Rundalco Industries", 143.25,
				"Metals and Mining");
		inserCompanyShare(db, "Toto Steel", 425.10, "Steel");
		inserCompanyShare(db, "Larsenl & Telbo", 1374, "Conglomerate");
		inserCompanyShare(db, "Moneyindra & Moneyindra", 1114.05, "Automotive");
		inserCompanyShare(db, "Toto Motors", 438.10, "Automotive");
		inserCompanyShare(db, "Hindustani Multilever", 565.00, "Consumer goods");
		inserCompanyShare(db, "HIGHTC", 353, "Conglomerate");
		inserCompanyShare(db, "Sesi Sterlite Ltd", 186.50, "Iron and Steel");
		inserCompanyShare(db, "Swipro", 504.85, "Information Technology");
		inserCompanyShare(db, "Bun Pharmaceutical", 612.25, "Pharmaceuticals");
		inserCompanyShare(db, "GAYLE", 376.35, "Oil and gas");
		inserCompanyShare(db, "UCUCU Bank", 1397.80, "Banking");
		inserCompanyShare(db, "Flat Development Finance Corporation", 901.50,
				"Housing Finance");
		inserCompanyShare(db, "Aarti Chairtel", 318.20, "Telecommunication");
		inserCompanyShare(db, "Clarity Buzuki", 2021.25, "Automotive");
		inserCompanyShare(db, "Toto Consultancy Services", 2141.05,
				"Information Technology");
		inserCompanyShare(db, "TNTPIC", 120.00, "Power");
		inserCompanyShare(db, "Dr. Eveready’s", 2726.05, "Pharmaceuticals");
		inserCompanyShare(db, "Jabab Auto", 1939.95, "Automotive");
		inserCompanyShare(db, "Qoal India", 320.25, "Metals and Mining");
	}

	private static void inserCompanyShare(SQLiteDatabase db, String name,
			double price, String industry) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_NAME, name);
		contentValues.put(COL_PRICE, price);
		contentValues.put(COL_CLOSING_PRICE, price);
		contentValues.put(COL_INDUSTRY, industry);
		db.insert(TABLE_NAME, COL_ID, contentValues);
	}
}