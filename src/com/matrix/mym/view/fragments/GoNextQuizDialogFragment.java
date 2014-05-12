package com.matrix.mym.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.Quiz;
import com.matrix.mym.utils.Constance;
import com.matrix.mym.utils.Settings;
import com.matrix.mym.view.activity.MymMainActivity;

public class GoNextQuizDialogFragment extends DialogFragment {
	private static final int TIME_DELAY_SEC = 6;
	private TextView titleTextView, messageTextView, timerTextView;
	private Quiz mQuiz;
	private MymMainActivity activity;

	public static GoNextQuizDialogFragment newInstance(Quiz quiz) {
		GoNextQuizDialogFragment f = new GoNextQuizDialogFragment();
		Bundle args = new Bundle();
		args.putParcelable(MymMainActivity.STATE_QUIZ, quiz);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(STYLE_NO_TITLE);
		setCancelable(false);
		View rootView = inflater.inflate(R.layout.fragment_go_next_quiz,
				container, false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		titleTextView = (TextView) rootView.findViewById(R.id.tvTitle);
		messageTextView = (TextView) rootView.findViewById(R.id.tvMessage);
		timerTextView = (TextView) rootView.findViewById(R.id.tvTimer);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		mQuiz = getArguments().getParcelable(MymMainActivity.STATE_QUIZ);
		showTimer();
		if (mQuiz.isCurrectAnswer())
			showCurrectAnswer();
		else
			showWrongAnswer();
	}

	private void showWrongAnswer() {
		titleTextView.setText(R.string.wrong_answer);
		messageTextView.setText(getString(R.string.currect_answer_is, mQuiz
				.getOptions().get(mQuiz.getAnswer())));
	}

	private void showCurrectAnswer() {
		titleTextView.setText(R.string.congratzz);
		double added = Settings.getSpree(getActivity())
				* Constance.DEFAULT_QUIZ_MONEY;
		activity.getUser().updateAccountBalance(getActivity(), added);
		String message = String.format(
				getString(R.string.currect_answer_message),
				Settings.getSpree(getActivity()), Constance.DEFAULT_USER_MONEY,
				added);
		messageTextView.setText(message);
	}

	private void showTimer() {
		timerTextView.setText(TIME_DELAY_SEC + "");
		new AsyncTask<Void, Integer, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				for (int i = 0; i < TIME_DELAY_SEC; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					publishProgress(i);
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				timerTextView.setText((TIME_DELAY_SEC - values[0]) + "");
			};

			protected void onPostExecute(Void result) {
				Quiz.moveToNextQuiz(getActivity(), mQuiz);
				activity.loadQuiz();
				dismiss();
				activity.onNavigationDrawerItemSelected(0);
			};

		}.execute();
	}
}
