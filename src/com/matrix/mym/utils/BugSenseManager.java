package com.matrix.mym.utils;

import android.app.Activity;

import com.bugsense.trace.BugSenseHandler;

public class BugSenseManager {
	public static void initBugSense(Activity activity) {
		if (Utils.isProduction)
			BugSenseHandler.initAndStartSession(activity,
					Constance.BUG_SENSE_KEY);
	}
}
