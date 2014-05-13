package com.matrix.mym.model;

public class LeaderBoardUser {
	public static final String PARAM_USER = "user", PARAM_UUID = "uuid",
			PARAM_NAME = "name", PARAM__MONAY = "money", PARAM__RANK = "rank";

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
}
