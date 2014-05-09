package com.matrix.mym.controller.interfaces;

import java.util.ArrayList;

import com.matrix.mym.model.UserShare;

public interface UserShareLoadedCallBack {
	public void onComplete(ArrayList<UserShare> userShares);
}
