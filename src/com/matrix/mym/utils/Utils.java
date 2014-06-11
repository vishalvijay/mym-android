package com.matrix.mym.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class Utils {
	public static final boolean isProduction = false;

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

	public static boolean isValidCalender(Calendar calendar) {
		return calendar.get(Calendar.YEAR) != Constance.INVALID_CALENDAR_YEAR;
	}

	public static String roundAndGetString(double value) {
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(value);
	}

	public static String getHumanReadableString(Calendar calendar) {
		calendar.add(Calendar.HOUR_OF_DAY, Constance.DURATION_OF_GAME_IN_HOUR);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		String amPm = "AM";
		if (calendar.get(Calendar.AM_PM) == Calendar.PM)
			amPm = "PM";
		return hour + ":" + minute + " " + amPm;
	}

	public static String getHumanReadableDateTime(String dateTime) {
		String result;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constance.HUMAN_READABLE_DATE_FORMATE, Locale.getDefault());
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			result = dateFormat.format(getCalender(dateTime).getTime());
		} catch (ParseException e) {
			result = "";
		}
		return result;
	}

	public static Calendar getCalender(String dateTime) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constance.DEFAULT_DATE_FORMATE, Locale.getDefault());
		calendar.setTime(dateFormat.parse(dateTime));
		return calendar;
	}

	public static void showNotification(long id, String titleString,
			String messageString, int iconResId, Context context,
			Class<?> className) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(iconResId)
				.setContentTitle(titleString)
				.setStyle(
						new NotificationCompat.BigTextStyle()
								.bigText(messageString))
				.setContentText(messageString);
		Intent intent = new Intent(context, className);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(className);
		stackBuilder.addNextIntent(intent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.setSound(RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = mBuilder.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify((int) id, notification);
	}

	public static String generateUserName() {
		return "user_" + new Random().nextInt(9999999);
	}
}
