package com.matrix.mym.controller.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.matrix.mym.controller.interfaces.LeaderBoardInternetLoader;
import com.matrix.mym.controller.interfaces.NetWorthLoader;
import com.matrix.mym.model.LeaderBoardUser;
import com.matrix.mym.model.User;
import com.matrix.mym.utils.MymRestClient;

public class LeaderBoardController extends JsonHttpResponseHandler {
	private Context context;
	private LeaderBoardInternetLoader leaderBoardInternetLoader;
	protected String TAG = "LeaderBoardController";

	public LeaderBoardController(Context context,
			LeaderBoardInternetLoader leaderBoardInternetLoader) {
		this.context = context;
		this.leaderBoardInternetLoader = leaderBoardInternetLoader;
	}

	public void exicuteGetUserByUuid(User user) {
		RequestParams params = new RequestParams();
		params.put(LeaderBoardUser.PARAM_UUID, user.getUuid(context));
		MymRestClient.get("/users/by_uuid", params, this);
	}

	public void createLeaderBoardUser(final User user) {
		leaderBoardInternetLoader.onLoadingStarted();
		user.getNetBalance(context, new NetWorthLoader() {

			@Override
			public void onComplete(double money) {
				try {
					JSONObject jsonParams = new JSONObject();
					StringEntity entity = null;
					jsonParams.put(LeaderBoardUser.PARAM_UUID,
							user.getUuid(context));
					jsonParams.put(LeaderBoardUser.PARAM_NAME,
							user.getName(context));
					jsonParams.put(LeaderBoardUser.PARAM_MONAY, money);

					entity = new StringEntity(jsonParams.toString());
					MymRestClient.post(context, "/users", entity,
							LeaderBoardController.this);
				} catch (JSONException e) {
				} catch (UnsupportedEncodingException e) {
				}
			}
		});

	}

	public void getLeaderBoard() {
		RequestParams params = new RequestParams();
		MymRestClient.get("/users", params, this);
	}

	@Override
	public void onSuccess(JSONArray response) {
		super.onSuccess(response);
		ArrayList<LeaderBoardUser> leaderBoardUsers = new ArrayList<LeaderBoardUser>();
		for (int i = 0; i < response.length(); i++) {
			try {
				LeaderBoardUser leaderBoardUser = LeaderBoardUser
						.create(response.getJSONObject(i));
				leaderBoardUser.setRank(i + 1);
				leaderBoardUsers.add(leaderBoardUser);
			} catch (JSONException e) {
			}
		}
		leaderBoardInternetLoader.onLoadingFinsh(leaderBoardUsers);
	}

	@Override
	public void onSuccess(int statusCode, JSONObject response) {
		super.onSuccess(statusCode, response);
		try {
			LeaderBoardUser leaderBoardUser = LeaderBoardUser.create(response
					.getJSONObject(LeaderBoardUser.PARAM_USER));
			leaderBoardUser.setRank(response
					.getLong(LeaderBoardUser.PARAM_RANK));
			leaderBoardInternetLoader.onLoadingFinsh(leaderBoardUser);
		} catch (JSONException e) {
			leaderBoardInternetLoader.onLoadingFinsh();
		}
	}

	@Override
	public void onFailure(Throwable e, JSONObject errorResponse) {
		super.onFailure(e, errorResponse);
		leaderBoardInternetLoader.onLoadingFailier();
	}
}