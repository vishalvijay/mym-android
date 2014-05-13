package com.matrix.mym.utils;

import android.app.Application;

import com.matrix.mym.controller.db.MymDataBase;

public class MymApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		MymDataBase.open(getApplicationContext());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		MymDataBase.close();
	}
}