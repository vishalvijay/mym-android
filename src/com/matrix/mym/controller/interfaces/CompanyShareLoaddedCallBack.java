package com.matrix.mym.controller.interfaces;

import java.util.ArrayList;

import com.matrix.mym.model.CompanyShare;

public interface CompanyShareLoaddedCallBack {
	public void onComplete(ArrayList<CompanyShare> companyShares);
}
