package com.matrix.mym.controller.interfaces;

import java.util.ArrayList;

import com.matrix.mym.model.LeaderBoardUser;

public interface LeaderBoardInternetLoader {
	public void onLoadingFinsh(ArrayList<LeaderBoardUser> leaderBoardUsers);

	public void onLoadingStarted();

	public void onLoadingFailier();

	public void onLoadingFinsh(LeaderBoardUser leaderBoardUser);

	public void onLoadingFinsh();
}
