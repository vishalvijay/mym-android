package com.matrix.mym.controller.receivers;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.matrix.mym.R;
import com.matrix.mym.utils.Constance;
import com.matrix.mym.utils.Settings;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.activity.SplashScreenActivity;

public class ShareMarketReminderBroadcastReceiver extends BroadcastReceiver {

	protected String TAG = "ShareMarketReminderBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Utils.showNotification(Constance.REMINDER_NOTIFICATION_ID,
				context.getString(R.string.app_name),
				context.getString(R.string.remaining_time),
				R.drawable.ic_action_time, context, SplashScreenActivity.class);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void register(Context context) {
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context,
				ShareMarketReminderBroadcastReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar calendar = Settings.getMarketStartedTime(context);
		calendar.add(Calendar.MINUTE,
				(Constance.DURATION_OF_GAME_IN_HOUR * 60) - 15);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			service.setExact(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), pending);
		else
			service.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pending);
	}

}
