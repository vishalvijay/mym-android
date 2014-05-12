package com.matrix.mym.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.QuizAnswerAdapter;
import com.matrix.mym.model.Quiz;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.activity.MymMainActivity;

public class QuizFragment extends MymMainFragment implements
		OnItemClickListener {
	private GridView optionGridView;
	private TextView questionTextView, questionNumberTextView, balanceTextView;
	private QuizAnswerAdapter adapter;
	private MymMainActivity activity;
	private Quiz mQuiz;

	public QuizFragment() {
		super(R.string.quiz);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_quiz, container,
				false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		optionGridView = (GridView) rootView.findViewById(R.id.gvOptions);
		questionTextView = (TextView) rootView.findViewById(R.id.tvQuestion);
		questionNumberTextView = (TextView) rootView
				.findViewById(R.id.tvQuestionNumber);
		balanceTextView = (TextView) rootView.findViewById(R.id.tvBalance);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mQuiz = activity.getQuiz();
		questionTextView.setText(mQuiz.getQuestion());
		adapter = new QuizAnswerAdapter(getActivity(), mQuiz);
		optionGridView.setAdapter(adapter);
		optionGridView.setOnItemClickListener(this);
		questionNumberTextView.setText("Q" + (mQuiz.getId() + 1));
		updateBalance();
	}

	private void updateBalance() {
		String rsString = getString(
				R.string.money,
				Utils.roundAndGetString(activity.getUser().getAccountBalance(
						getActivity())));
		balanceTextView.setText(getString(R.string.balance, rsString));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mQuiz.isAnswerSelected())
			return;
		mQuiz.setSelectedAnswer(position);
		startListAnimation();
	}

	private void startListAnimation() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				for (int i = 0; i < mQuiz.getOptions().size(); i++) {
					mQuiz.increaseAnimationCount();
					publishProgress();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				adapter.notifyDataSetChanged();
			}

			protected void onPostExecute(Void result) {
				showDialog();
			};
		}.execute();
	}

	void showDialog() {
		GoNextQuizDialogFragment newFragment = GoNextQuizDialogFragment
				.newInstance(mQuiz);
		newFragment.show(getFragmentManager(), "dialog");
	}
}
