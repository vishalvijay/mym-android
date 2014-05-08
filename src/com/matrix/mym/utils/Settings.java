package com.matrix.mym.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	private static String PREFS_CURRENT_MONEY = "CURRENT_MONEY";

	public static void setCurrentUserMoney(Context context, float money) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putFloat(PREFS_CURRENT_MONEY, money);
		edit.commit();
	}

	public static float getCurrentMoney(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getFloat(PREFS_CURRENT_MONEY, 500);
	}
}
