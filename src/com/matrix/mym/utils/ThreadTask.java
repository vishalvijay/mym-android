package com.matrix.mym.utils;

import android.os.Handler;

public abstract class ThreadTask<Params, Progress, Result> extends Thread {
	private Handler mHandler;
	private Params[] mParams;

	public ThreadTask(Handler handler) {
		mHandler = handler;
	}

	@Override
	public void run() {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				onPreExecute();
			}
		});
		final Result result = doInBackground(mParams);
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				onPostExecute(result);
			}
		});
	}

	protected void onPreExecute() {
	}

	protected abstract Result doInBackground(
			@SuppressWarnings("unchecked") Params... params);

	protected void onPostExecute(Result result) {
	}

	protected final void publishProgress(
			@SuppressWarnings("unchecked") final Progress... values) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				onProgressUpdate(values);
			}
		});
	}

	protected void onProgressUpdate(
			@SuppressWarnings("unchecked") Progress... values) {
	}

	public void execute(@SuppressWarnings("unchecked") Params... params) {
		mParams = params;
		start();
	}
}
