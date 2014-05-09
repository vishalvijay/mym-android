package com.matrix.mym.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	private static String PREFS_USER_MONEY = "USER_MONEY";

	public static void setUserMoney(Context context, float money) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putFloat(PREFS_USER_MONEY, money);
		edit.commit();
	}

	public static float getUserMoney(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getFloat(PREFS_USER_MONEY, 500);
	}
}
