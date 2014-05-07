package com.matrix.mym.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
	public static final boolean isProduction = true;

	public static void showErrorToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showInfoToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showErrorToast(Context context, int resId) {
		showErrorToast(context, context.getString(resId));
	}

	public static void showInfoToast(Context context, int resId) {
		showInfoToast(context, context.getString(resId));
	}
}
