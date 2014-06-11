package com.matrix.mym.model;

import org.json.JSONException;
import org.json.JSONObject;

public class News {
	public static final String PARAM_NEWS = "news", PARAM_ID = "id",
			PARAM_TITLE = "title", PARAM_CONTENT = "content",
			PARAM_IMAGE_URL = "image_url", PARAM_CREATED_AT = "created_at";

	private long mId;
	private String mTitle, mContent, mImageUrl, mCreatedAt;

	public News(long id, String title, String content, String imageUrl,
			String createdAt) {
		mId = id;
		mTitle = title;
		mContent = content;
		mImageUrl = imageUrl;
		mCreatedAt = createdAt;
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

	public String geTime() {
		return mCreatedAt;
	}

	public static News create(JSONObject jsonObject) throws JSONException {
		long id = jsonObject.getLong(PARAM_ID);
		String title = jsonObject.getString(PARAM_TITLE);
		String content = jsonObject.getString(PARAM_CONTENT);
		String imageUrl = jsonObject.getString(PARAM_IMAGE_URL);
		String createdAt;
		try {
			createdAt = jsonObject.getString(PARAM_CREATED_AT);
		} catch (JSONException e) {
			createdAt = "";
		}
		return new News(id, title, content, imageUrl, createdAt);
	}

}
