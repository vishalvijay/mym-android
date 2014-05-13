package com.matrix.mym.controller.server;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.matrix.mym.controller.interfaces.LeaderBoardInternetLoader;
import com.matrix.mym.model.LeaderBoardUser;
import com.matrix.mym.model.User;
import com.matrix.mym.utils.MymRestClient;

public class LeaderBoardController extends JsonHttpResponseHandler {
	private Context context;
	private LeaderBoardInternetLoader internetLoader;

	public LeaderBoardController(Context context,
			LeaderBoardInternetLoader internetLoader) {
		this.context = context;
		this.internetLoader = internetLoader;
	}

	public void exicuteGetUserByUuid(String uuid) {
		RequestParams params = new RequestParams();
		params.put(LeaderBoardUser.PARAM_UUID, uuid);
		MymRestClient.get("by_uuid", params, this);
	}
	//
	// public void createLeaderBoardUser(User user) {
	// JSONObject jsonParams = new JSONObject();
	// StringEntity entity = null;
	// try {
	// jsonParams.put(LeaderBoardUser.PARAM_UUID, user.getUuid(context));
	// jsonParams.put(LeaderBoardUser.PARAM_NAME, user.getName(context));
	// jsonParams.put(LeaderBoardUser.PARAM__MONAY,
	// user.getAccountBalance(context));
	// entity = new StringEntity(jsonParams.toString());
	// } catch (JSONException e) {
	// } catch (UnsupportedEncodingException e) {
	// }
	// MissileRestClient.post(context, "comments.json", entity, this);
	// }
	//
	// public void getComments() {
	// RequestParams params = new RequestParams();
	// params.put(PARAM_MISSILE_ID, mMissileId + "");
	// MissileRestClient.get("comments.json", params, this);
	// }
	//
	// @Override
	// public void onSuccess(JSONArray response) {
	// super.onSuccess(response);
	// Gson gson = new Gson();
	// Comment[] comments = gson
	// .fromJson(response.toString(), Comment[].class);
	// mCommentControllerInterface.onLoadingFinsh(comments);
	// }
	//
	// @Override
	// public void onSuccess(int statusCode, JSONObject response) {
	// super.onSuccess(statusCode, response);
	// Gson gson = new Gson();
	// Comment comment = gson.fromJson(response.toString(), Comment.class);
	// mCommentControllerInterface.onCommetCreated(comment);
	// }
}