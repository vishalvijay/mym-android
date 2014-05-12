package com.matrix.mym.utils;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class GoogleAnalyticsManager {

	public static void infomGoogleAnalytics(EasyTracker easyTracker,
			String category, String action, String label, long value) {
		if (Utils.isProduction)
			easyTracker.send(MapBuilder.createEvent(category, action, label,
					value).build());
	}

	public static EasyTracker getGoogleAnalyticsTracker(Context context) {
		return EasyTracker.getInstance(context);
	}

	public static void startGoogleAnalyticsForActivity(Activity activity) {
		if (Utils.isProduction)
			EasyTracker.getInstance(activity).activityStart(activity);
	}

	public static void stopGoogleAnalyticsForActivity(Activity activity) {
		if (Utils.isProduction)
			EasyTracker.getInstance(activity).activityStop(activity);
	}
}
