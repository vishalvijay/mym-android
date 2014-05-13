package com.matrix.mym.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LeaderBoardUser {
	public static final String PARAM_USER = "user", PARAM_UUID = "uuid",
			PARAM_NAME = "name", PARAM_MONAY = "money", PARAM_RANK = "rank";

	private String mUuid, mName;
	private double mMoney;
	private long mRank;

	public LeaderBoardUser(String uuid, String name, double money) {
		mUuid = uuid;
		mName = name;
		mMoney = money;
	}

	public String getName() {
		return mName;
	}

	public String getUuid() {
		return mUuid;
	}

	public double getMoney() {
		return mMoney;
	}

	public long getRank() {
		return mRank;
	}

	public void setRank(long rank) {
		mRank = rank;
	}

	public static LeaderBoardUser create(JSONObject jsonObject)
			throws JSONException {
		String uuid = jsonObject.getString(PARAM_UUID);
		String name = jsonObject.getString(PARAM_NAME);
		double money = jsonObject.getDouble(PARAM_MONAY);
		return new LeaderBoardUser(uuid, name, money);
	}
}
