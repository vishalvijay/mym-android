package com.matrix.mym.controller.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MymDataBase {

	String TAG = "MymDataBase";
	public static final String DATABASE_NAME = "mym.db";
	public static final int DATABASE_VERSION = 2;

	private static SQLiteDatabase db;

	public static void open(Context context) {
		db = new DatabaseHelper(context).getWritableDatabase();
	}

	public static SQLiteDatabase getDb() {
		if (db == null)
			throw new IllegalStateException("Db not opened");
		return db;
	}

	public static void close() {
		if (db == null)
			throw new IllegalStateException("Db not opened");
		if (db.isOpen()) {
			db.close();
			db = null;
		}
	}
}
