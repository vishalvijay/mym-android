package com.matrix.mym.controller.interfaces;

import java.util.ArrayList;

import com.matrix.mym.model.News;

public interface NewsInternetLoader {
	public void onLoadingFinsh(ArrayList<News> newsArrayList);

	public void onLoadingStarted();

	public void onLoadingFailier();

	public void onLoadingFinsh(News news);
}
