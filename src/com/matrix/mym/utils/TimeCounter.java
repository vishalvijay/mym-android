package com.matrix.mym.utils;

import java.util.Calendar;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.matrix.mym.R;

public class TimeCounter extends CountDownTimer {
	protected static String TAG = "TimeCounter";
	private TextView mTimerTextView;

	public TimeCounter(TextView timerTextView, Calendar calendar) {
		super(getSeconds(calendar), 1000);
		mTimerTextView = timerTextView;
	}

	private static long getSeconds(Calendar calendar) {
		calendar.add(Calendar.HOUR_OF_DAY, Constance.DURATION_OF_GAME_IN_HOUR);
		Calendar currentCalendar = Calendar.getInstance();
		return calendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
	}

	@Override
	public void onTick(long millisUntilFinished) {
		long minutes = millisUntilFinished / (1000 * 60);
		long second = (millisUntilFinished - (minutes * 1000 * 60)) / 1000;
		String time = appendZero(minutes) + ":" + appendZero(second);
		mTimerTextView.setText(time);
	}

	private String appendZero(long i) {
		if (i < 10)
			return "0" + i;
		return i + "";
	}

	@Override
	public void onFinish() {
		mTimerTextView.setText(R.string.time_up);
	}

}
