package com.matrix.mym.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.Quiz;

@SuppressLint("ViewConstructor")
public class QuizAnswerAdapter extends SupportArrayAdapter<String> {
	private Quiz mQuiz;

	public QuizAnswerAdapter(Context context, Quiz quiz) {
		super(context, quiz.getOptions());
		mQuiz = quiz;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(
					R.layout.quiz_answer_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.answerTextView = (TextView) convertView
					.findViewById(R.id.tvAnswer);
			viewHolder.optionTextView = (TextView) convertView
					.findViewById(R.id.tvOption);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.answerTextView.setText(getItem(position));
		viewHolder.optionTextView.setText(((char) (65 + position)) + "");
		if (mQuiz.isAnswerSelected() && position <= mQuiz.getAnimationCount()) {
			int color;
			if (position == mQuiz.getAnswer())
				color = getContext().getResources().getColor(
						R.color.share_positive);
			else
				color = getContext().getResources().getColor(
						R.color.share_negative);
			viewHolder.optionTextView.setBackgroundColor(color);
		}
		return convertView;
	}

	private static class ViewHolder {
		public TextView answerTextView, optionTextView;
	}

}
