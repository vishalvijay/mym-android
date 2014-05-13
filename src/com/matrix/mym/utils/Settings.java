package com.matrix.mym.utils;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Settings {
	private static String PREFS_USER_MONEY = "user_money";
	private static String PREFS_IS_SHARE_MARKET_STARTED = "is_share_market_started";
	private static String PREFS_MARKET_STARTED_TIME = "market_started_time";
	private static String PREFS_CURRENT_QUIZ = "current_quiz";
	private static String PREFS_SPREE = "spree";
	private static String PREFS_USER_NAME = "user_name";

	private static Editor putDouble(Editor edit, String key, double value) {
		return edit.putLong(key, Double.doubleToRawLongBits(value));
	}

	private static double getDouble(SharedPreferences prefs, String key,
			double defaultValue) {
		return Double.longBitsToDouble(prefs.getLong(key,
				Double.doubleToLongBits(defaultValue)));
	}

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

	public static void setMarketStartedTime(Context context, Calendar calendar) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putLong(PREFS_MARKET_STARTED_TIME, calendar.getTimeInMillis())
				.commit();
	}

	public static Calendar getMarketStartedTime(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		long calenderMilli = prefs.getLong(PREFS_MARKET_STARTED_TIME, -1);
		Calendar calendar = Calendar.getInstance();
		if (calenderMilli == -1)
			calendar.set(Calendar.YEAR, Constance.INVALID_CALENDAR_YEAR);
		else
			calendar.setTimeInMillis(calenderMilli);
		return calendar;
	}

	public static void setCurrentQuiz(Context context, long id) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putLong(PREFS_CURRENT_QUIZ, id).commit();
	}

	public static long getCurrentQuiz(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getLong(PREFS_CURRENT_QUIZ, 0);
	}

	public static void setSpree(Context context, int id) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(PREFS_SPREE, id).commit();
	}

	public static int getSpree(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getInt(PREFS_SPREE, 1);
	}

	public static String getUserName(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String name = prefs.getString(PREFS_USER_NAME, "");
		if (name.equals(""))
			name = Utils.generateUserName();
		setUserName(context, name);
		return name;
	}

	public static void setUserName(Context context, String name) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(PREFS_USER_NAME, name).commit();
	}
}
