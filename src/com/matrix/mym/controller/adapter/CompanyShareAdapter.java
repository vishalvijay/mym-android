package com.matrix.mym.controller.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.CompanyShare;

@SuppressLint("ViewConstructor")
public class CompanyShareAdapter extends SupportArrayAdapter<CompanyShare> {

	private boolean isForSatatus = false;

	public CompanyShareAdapter(Context context,
			ArrayList<CompanyShare> companyShares) {
		super(context, companyShares);
	}

	public CompanyShareAdapter(Context context,
			ArrayList<CompanyShare> companyShares, boolean isForStatus) {
		super(context, companyShares);
		this.isForSatatus = isForStatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.share_list_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.tvShareName);

			viewHolder.priceTextView = (TextView) convertView
					.findViewById(R.id.tvSharePrice);

			viewHolder.priceChangeTextView = (TextView) convertView
					.findViewById(R.id.tvSharePriceChange);

			viewHolder.industryTextView = (TextView) convertView
					.findViewById(R.id.tvShareIndustry);

			viewHolder.menuImageButton = (ImageButton) convertView
					.findViewById(R.id.ibMenuButton);
			if (isForSatatus)
				viewHolder.menuImageButton.setVisibility(View.GONE);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		CompanyShare companyShare = getItem(position);
		viewHolder.nameTextView.setText(companyShare.getName());
		DecimalFormat df = new DecimalFormat("###.##");
		viewHolder.priceTextView.setText(df.format(companyShare.getPrice()));
		setUpPriceeChangeTextView(viewHolder.priceChangeTextView, df,
				companyShare);
		viewHolder.industryTextView.setText(companyShare.getIndustry());
		return convertView;
	}

	private void setUpPriceeChangeTextView(TextView priceChangeTextView,
			DecimalFormat df, CompanyShare companyShare) {
		if (companyShare.getLastPriceChange() == 0)
			priceChangeTextView.setVisibility(View.GONE);
		else {
			priceChangeTextView.setVisibility(View.VISIBLE);
			priceChangeTextView.setText(df.format(companyShare
					.getLastPriceChange()));
			int color;
			if (companyShare.isPositiveChange())
				color = getContext().getResources().getColor(
						R.color.share_positive);
			else
				color = getContext().getResources().getColor(
						R.color.share_negative);
			priceChangeTextView.setTextColor(color);
		}
	}

	private static class ViewHolder {
		public TextView nameTextView, priceTextView, priceChangeTextView,
				industryTextView;
		public ImageButton menuImageButton;
	}

}
