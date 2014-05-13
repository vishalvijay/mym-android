package com.matrix.mym.controller.receivers;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.matrix.mym.R;
import com.matrix.mym.controller.service.ShareMarketService;
import com.matrix.mym.utils.Constance;
import com.matrix.mym.utils.Settings;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.activity.SplashScreenActivity;

public class ShareMarketTimeUpBroadcastReceiver extends BroadcastReceiver {

	protected String TAG = "ShareMarketTimeUpBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Settings.setShareMarketStartedStatus(context, false);
		Intent serviceIntent = new Intent(context, ShareMarketService.class);
		context.stopService(serviceIntent);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(Constance.REMINDER_NOTIFICATION_ID);
		Utils.showNotification(Constance.REMINDER_NOTIFICATION_ID,
				context.getString(R.string.app_name),
				context.getString(R.string.share_market_closed),
				R.drawable.ic_action_time, context, SplashScreenActivity.class);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void register(Context context) {
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, ShareMarketTimeUpBroadcastReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar calendar = Settings.getMarketStartedTime(context);
		calendar.add(Calendar.HOUR_OF_DAY, Constance.DURATION_OF_GAME_IN_HOUR);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			service.setExact(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), pending);
		else
			service.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pending);
	}

}
