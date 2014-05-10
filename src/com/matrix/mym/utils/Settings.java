package com.matrix.mym.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Settings {
	private static String PREFS_USER_MONEY = "user_money";
	private static String PREFS_IS_SHARE_MARKET_STARTED = "is_share_market_started";

	public static void setUserMoney(Context context, double money) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		putDouble(edit, PREFS_USER_MONEY, money).commit();
	}

	public static double getUserMoney(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return getDouble(prefs, PREFS_USER_MONEY, Constance.DEFAULT_USER_MONEY);
	}

	public static void setShareMarketStartedStatus(Context context,
			boolean status) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(PREFS_IS_SHARE_MARKET_STARTED, status);
		edit.commit();
	}

	public static boolean isShareMarketStarted(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getBoolean(PREFS_IS_SHARE_MARKET_STARTED, false);
	}

	private static Editor putDouble(Editor edit, String key, double value) {
		return edit.putLong(key, Double.doubleToRawLongBits(value));
	}

	private static double getDouble(SharedPreferences prefs, String key,
			double defaultValue) {
		return Double.longBitsToDouble(prefs.getLong(key,
				Double.doubleToLongBits(defaultValue)));
	}
}
