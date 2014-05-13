package com.matrix.mym.controller.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.model.User;
import com.matrix.mym.utils.Utils;

@SuppressLint("ViewConstructor")
public class UserShareAdapter extends SupportArrayAdapter<CompanyShare> {
	private User mUser;

	public UserShareAdapter(Context context, ArrayList<CompanyShare> objects,
			User user) {
		super(context, objects);
		mUser = user;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(
					R.layout.user_share_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.tvShareName);
			viewHolder.quantityTextView = (TextView) convertView
					.findViewById(R.id.tvShareQuantity);
			viewHolder.priceTextView = (TextView) convertView
					.findViewById(R.id.tvSharePrice);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		CompanyShare companyShare = getItem(position);
		viewHolder.nameTextView.setText(companyShare.getName());
		long quantity = mUser.getUserShares().get(position).getQuantity();
		viewHolder.quantityTextView.setText("x " + quantity);
		viewHolder.priceTextView.setText(getContext().getString(R.string.money,
				Utils.roundAndGetString(companyShare.getPrice() * quantity)));
		return convertView;
	}

	private static class ViewHolder {
		public TextView nameTextView, quantityTextView, priceTextView;
	}

}
