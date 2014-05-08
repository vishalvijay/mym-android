package com.matrix.mym.controller.interfaces;

public interface ShareMarketCallBacks {
	public void priceChanged(long id);

	public void startedPriceChaning(long id);

	public void stoppedPriceChaning(long id);
}
