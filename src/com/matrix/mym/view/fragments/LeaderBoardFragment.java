package com.matrix.mym.view.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.controller.adapter.LeaderBoardAdapter;
import com.matrix.mym.controller.interfaces.LeaderBoardInternetLoader;
import com.matrix.mym.controller.server.LeaderBoardController;
import com.matrix.mym.model.LeaderBoardUser;
import com.matrix.mym.view.activity.MymMainActivity;

public class LeaderBoardFragment extends MymMainFragment implements
		OnClickListener, LeaderBoardInternetLoader {

	private LinearLayout leadeBoardLinearLayout;
	private ListView leaderBoardListView;
	private Button retryButton;
	private TextView stausTextView;
	private EditText nameEditText;
	private ImageButton submitImageButton;
	private ProgressBar progressBar;
	private MymMainActivity activity;

	private LeaderBoardController mLeaderBoardController;

	public LeaderBoardFragment() {
		super(R.string.leaderboard);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_leaderboard,
				container, false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		leadeBoardLinearLayout = (LinearLayout) rootView
				.findViewById(R.id.llLeaderBoard);
		leaderBoardListView = (ListView) rootView
				.findViewById(R.id.lvLeaderBoard);
		retryButton = (Button) rootView.findViewById(R.id.btRetry);
		stausTextView = (TextView) rootView.findViewById(R.id.tvStatus);
		nameEditText = (EditText) rootView.findViewById(R.id.etName);
		submitImageButton = (ImageButton) rootView.findViewById(R.id.ibSubmit);
		progressBar = (ProgressBar) rootView.findViewById(R.id.pbLoading);
		retryButton.setOnClickListener(this);
		submitImageButton.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLeaderBoardController = new LeaderBoardController(getActivity(), this);
		mLeaderBoardController.createLeaderBoardUser(activity.getUser());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibSubmit:
			submitName();
			break;

		case R.id.btRetry:
			reopen();
			break;
		}
	}

	private void reopen() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
		activity.onNavigationDrawerItemSelected(4);
	}

	private void submitName() {
		String name = nameEditText.getText().toString().trim();
		if (name.length() == 0) {
			nameEditText.setError(getString(R.string.name_cant_be_blank));
			return;
		}
		activity.getUser().setName(getActivity(), name);
		reopen();
	}

	@Override
	public void onLoadingFinsh(ArrayList<LeaderBoardUser> leaderBoardUsers) {
		if (!isAdded())
			return;
		leaderBoardListView.setAdapter(new LeaderBoardAdapter(activity,
				leaderBoardUsers));
		leadeBoardLinearLayout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onLoadingStarted() {
		if (!isAdded())
			return;
		leadeBoardLinearLayout.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingFailier() {
		if (!isAdded())
			return;
		progressBar.setVisibility(View.GONE);
		retryButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingFinsh(LeaderBoardUser leaderBoardUser) {
		if (!isAdded())
			return;
		String status = String.format(getString(R.string.leader_board_status),
				leaderBoardUser.getName(), leaderBoardUser.getRank());
		stausTextView.setText(status);
		mLeaderBoardController.getLeaderBoard();
	}

	@Override
	public void onLoadingFinsh() {
		mLeaderBoardController.exicuteGetUserByUuid(activity.getUser());
	}
}
