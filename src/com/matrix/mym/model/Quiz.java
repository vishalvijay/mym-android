package com.matrix.mym.model;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.matrix.mym.controller.db.QuizDB;
import com.matrix.mym.utils.Settings;

public class Quiz implements Parcelable {
	public static final int TYPE_4_OPTIONS = 0, TYPE_BOOLEAN = 1;
	private long mId;
	private String mQuestion;
	private ArrayList<String> mOptions;
	private int mAnswer;
	private int mType;
	private int selectedAnswer = -1;
	private int animationCount = -1;

	public Quiz(long id, String quistion, ArrayList<String> options,
			int answer, int type) {
		mId = id;
		mQuestion = quistion;
		mOptions = options;
		mAnswer = answer;
		mType = type;
	}

	public long getId() {
		return mId;
	}

	public String getQuestion() {
		return mQuestion;
	}

	public ArrayList<String> getOptions() {
		return mOptions;
	}

	public boolean isCurrectAnswer() {
		if (selectedAnswer == -1)
			throw new IllegalStateException("Select an answer first");
		return selectedAnswer == mAnswer;
	}

	public int getType() {
		return mType;
	}

	public void setSelectedAnswer(int option) {
		selectedAnswer = option;
	}

	public boolean isAnswerSelected() {
		return selectedAnswer != -1;
	}

	public int getAnswer() {
		return mAnswer;
	}

	public static Quiz getCurrentQuiz(Context context) {
		return QuizDB.getQuiz(Settings.getCurrentQuiz(context));
	}

	public static void moveToNextQuiz(Context context, Quiz currentQuiz) {
		if (currentQuiz.isCurrectAnswer())
			Settings.setSpree(context, Settings.getSpree(context) + 1);
		else
			Settings.setSpree(context, 1);
		long next = Settings.getCurrentQuiz(context) + 1;
		Quiz quiz = QuizDB.getQuiz(next);
		if (quiz == null)
			next = 0;
		Settings.setCurrentQuiz(context, next);
	}

	public int getAnimationCount() {
		return animationCount;
	}

	public void increaseAnimationCount() {
		animationCount++;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeString(mQuestion);
		dest.writeInt(mAnswer);
		dest.writeInt(mType);
		dest.writeInt(animationCount);
		dest.writeInt(selectedAnswer);
		dest.writeStringList(mOptions);
	}

	private Quiz() {
		mOptions = new ArrayList<String>();
	}

	private Quiz(Parcel source) {
		this();
		mId = source.readLong();
		mQuestion = source.readString();
		mAnswer = source.readInt();
		mType = source.readInt();
		animationCount = source.readInt();
		selectedAnswer = source.readInt();
		source.readStringList(mOptions);
	}

	public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
		@Override
		public Quiz createFromParcel(Parcel source) {
			return new Quiz(source);
		}

		@Override
		public Quiz[] newArray(int size) {
			return new Quiz[size];
		}
	};
}
