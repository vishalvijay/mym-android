package com.matrix.mym.model;

public class NavMenuItem {
	private String title;
	private int iconRid;

	public NavMenuItem(String title, int iconRId) {
		this.title = title;
		this.iconRid = iconRId;
	}

	public String getTitle() {
		return title;
	}

	public int getIconRid() {
		return iconRid;
	}

	@Override
	public String toString() {
		return " " + title;
	}

}