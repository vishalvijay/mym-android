package com.matrix.mym.utils;

import java.util.UUID;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.matrix.mym.R;

public class SystemFeatureChecker {

	public static boolean isInternetConnection(Context context) {
		ConnectivityManager cn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cn.getActiveNetworkInfo();
		if (nf != null && nf.isConnected() == true)
			return true;
		else
			return false;
	}

	public static int getDisplayWidth(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		return displaymetrics.widthPixels;
	}

	public static int getDisplayHeight(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		return displaymetrics.heightPixels;
	}

	public static int getAppVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getAppVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getAndroidVersion() {
		return Build.VERSION.RELEASE;
	}

	public static void rateAppOnPlayStore(Activity activity) {
		Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			activity.startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			throw e;
		}
	}

	public static void sendFeedback(Activity activity) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "v4appfarm@gmail.com" });
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				activity.getString(R.string.feedback_mail_subject,
						activity.getString(R.string.app_name)));
		String bugReportBody = String.format(
				activity.getString(R.string.feedback_mail_body),
				SystemFeatureChecker.getDeviceName(),
				activity.getString(R.string.app_name),
				SystemFeatureChecker.getAppVersionName(activity),
				SystemFeatureChecker.getAppVersionCode(activity),
				SystemFeatureChecker.getAndroidVersion());
		i.putExtra(Intent.EXTRA_TEXT, bugReportBody);
		try {
			activity.startActivity(Intent.createChooser(i,
					activity.getString(R.string.send_feedback)));
		} catch (android.content.ActivityNotFoundException ex) {
			throw ex;
		}
	}

	public static void openUrlInBrowser(Activity activity, String urlString) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(urlString));
		activity.startActivity(i);
	}

	public static String getDeviceUuid(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return deviceUuid.toString();
	}

	public static String getAppPlayStoreURL() {
		return "https://play.google.com/store/apps/details?id="
				+ Constance.PACKAGE;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return StringOperator.toFullNameFormate(model);
		} else {
			return StringOperator.toFullNameFormate(manufacturer) + " " + model;
		}
	}
}
