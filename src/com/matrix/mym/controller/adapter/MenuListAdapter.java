package com.matrix.mym.controller.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.NavMenuItem;

public class MenuListAdapter extends ArrayAdapter<NavMenuItem> {

	public MenuListAdapter(Context context, List<NavMenuItem> navMenuItems) {
		super(context, getLayout(), navMenuItems);
	}

	@SuppressLint("InlinedApi")
	private static int getLayout() {
		int layout = android.R.layout.simple_list_item_1;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			layout = android.R.layout.simple_list_item_activated_1;
		}
		return layout;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = (TextView) super.getView(position, convertView,
				parent);
		textView.setCompoundDrawablesWithIntrinsicBounds(getItem(position)
				.getIconRid(), 0, 0, 0);
		textView.setTextColor(getContext().getResources().getColor(
				R.color.white));
		int viewPadding = (int) getContext().getResources().getDimension(
				R.dimen.common_padding);
		textView.setPadding(viewPadding, viewPadding, viewPadding, viewPadding);
		return textView;
	}

}
