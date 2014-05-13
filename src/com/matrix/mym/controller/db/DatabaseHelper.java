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
		UserSharesDB.setUpTable(db);
		QuizDB.setUpTable(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTable(db, CompanyShareDB.TABLE_NAME);
		dropTable(db, UserSharesDB.TABLE_NAME);
		dropTable(db, QuizDB.TABLE_NAME);
		onCreate(db);
	}

	private void dropTable(SQLiteDatabase db, String tableName) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName + ";");
	}
}
