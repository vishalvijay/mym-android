package com.matrix.mym.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.mym.R;

public class QuizFragment extends MymMainFragment {

	public QuizFragment() {
		super(R.string.quiz);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_quiz, container, false);
		return rootView;
	}

}
