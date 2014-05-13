package com.matrix.mym.controller.interfaces;

import com.matrix.mym.model.LeaderBoardUser;

public interface LeaderBoardInternetLoader {
	public void onLoadingFinsh(LeaderBoardUser[] leaderBoardUsers);

	public void onLoadingStarted();

	public void onLoadingFailier();

	public void onLoadingFinsh(LeaderBoardUser leaderBoardUser);
}
