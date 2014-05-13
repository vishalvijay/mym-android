package com.matrix.mym.utils;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MymRestClient {
	private static final String BASE_URL = "mym-server.herokuapp.com/users";
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params,
			JsonHttpResponseHandler responseHandler) {
		client.addHeader("Accept", "application/json");
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(Context context, String url, StringEntity entity,
			JsonHttpResponseHandler responseHandler) {
		client.addHeader("Accept", "application/json");
		client.post(context, getAbsoluteUrl(url), entity, "application/json",
				responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + "/" + relativeUrl;
	}

}
