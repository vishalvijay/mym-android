package com.matrix.mym.controller.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	String TAG = "DatabaseHelper";

	public DatabaseHelper(Context context) {
		super(context, MymDataBase.DATABASE_NAME, null,
				MymDataBase.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CompanyShareDB.setUpTable(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(getDropTableQuery(CompanyShareDB.TABLE_COMPANY_SHARE_TABLE));
		onCreate(db);
	}

	private String getDropTableQuery(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName + ";";
	}
}
