package com.matrix.mym.model;

import org.json.JSONException;
import org.json.JSONObject;

public class News {
	public static final String PARAM_NEWS = "news", PARAM_ID = "id",
			PARAM_TITLE = "title", PARAM_CONTENT = "content",
			PARAM_IMAGE_URL = "image_url";

	private long mId;
	private String mTitle, mContent, mImageUrl;

	public News(long id, String title, String content, String imageUrl) {
		mId = id;
		mTitle = title;
		mContent = content;
		mImageUrl = imageUrl;
	}

	public long getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getContent() {
		return mContent;
	}

	public String getImageUrl() {
		return mImageUrl;
	}

	public static News create(JSONObject jsonObject) throws JSONException {
		long id = jsonObject.getLong(PARAM_ID);
		String title = jsonObject.getString(PARAM_TITLE);
		String content = jsonObject.getString(PARAM_CONTENT);
		String imageUrl = jsonObject.getString(PARAM_IMAGE_URL);
		return new News(id, title, content, imageUrl);
	}

}
