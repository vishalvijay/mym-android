package com.matrix.mym.controller.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.matrix.mym.controller.interfaces.NewsInternetLoader;
import com.matrix.mym.model.News;
import com.matrix.mym.utils.MymRestClient;

public class NewsController extends JsonHttpResponseHandler {
	private NewsInternetLoader newsInternetLoader;
	protected String TAG = "NewsController";

	public NewsController(NewsInternetLoader newsInternetLoader) {
		this.newsInternetLoader = newsInternetLoader;
	}

	public void getNewsById(long id) {
		newsInternetLoader.onLoadingStarted();
		RequestParams params = new RequestParams();
		MymRestClient.get("/news/" + id + ".json", params, this);
	}

	public void getAllNews() {
		newsInternetLoader.onLoadingStarted();
		RequestParams params = new RequestParams();
		MymRestClient.get("/news.json", params, this);
	}

	@Override
	public void onSuccess(JSONArray response) {
		super.onSuccess(response);
		ArrayList<News> newsArrayList = new ArrayList<News>();
		for (int i = 0; i < response.length(); i++) {
			try {
				newsArrayList.add(News.create(response.getJSONObject(i)));
			} catch (JSONException e) {
			}
		}
		newsInternetLoader.onLoadingFinsh(newsArrayList);
	}

	@Override
	public void onSuccess(int statusCode, JSONObject response) {
		super.onSuccess(statusCode, response);
		try {
			newsInternetLoader.onLoadingFinsh(News.create(response));
		} catch (JSONException e) {
			newsInternetLoader.onLoadingFailier();
		}
	}

	@Override
	public void onFailure(Throwable e, JSONObject errorResponse) {
		super.onFailure(e, errorResponse);
		newsInternetLoader.onLoadingFailier();
	}
}