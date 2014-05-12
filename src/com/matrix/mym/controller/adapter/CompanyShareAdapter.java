package com.matrix.mym.controller.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.fragments.BuyCompanyShareDialogFragment;
import com.matrix.mym.view.fragments.SellCompanyShareDialogFragment;

@SuppressLint("ViewConstructor")
public class CompanyShareAdapter extends SupportArrayAdapter<CompanyShare>
		implements OnClickListener, OnMenuItemClickListener {

	private boolean isForSatatus = false;
	private int mPostionClicked;

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
			viewHolder.menuImageButton.setOnClickListener(this);
			if (isForSatatus) {
				viewHolder.menuImageButton.setVisibility(View.GONE);
				viewHolder.priceChangeTextView.setVisibility(View.GONE);
			}
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		CompanyShare companyShare = getItem(position);
		viewHolder.nameTextView.setText(companyShare.getName());
		setUpPrice(viewHolder.priceTextView,
				Utils.roundAndGetString(companyShare.getPrice()));
		setUpPriceeChangeTextView(viewHolder.priceChangeTextView, companyShare);
		viewHolder.industryTextView.setText(companyShare.getIndustry());
		viewHolder.menuImageButton.setTag(position);
		return convertView;
	}

	private void setUpPrice(TextView textView, String roundAndGetString) {
		if (isForSatatus)
			textView.setText(getContext().getString(R.string.money,
					roundAndGetString));
		else
			textView.setText(roundAndGetString);
	}

	private void setUpPriceeChangeTextView(TextView priceChangeTextView,
			CompanyShare companyShare) {
		if (isForSatatus)
			return;
		if (companyShare.getLastPriceChange() == 0)
			priceChangeTextView.setVisibility(View.GONE);
		else {
			priceChangeTextView.setVisibility(View.VISIBLE);
			priceChangeTextView.setText(Utils.roundAndGetString(companyShare
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

	@Override
	public void onClick(View v) {
		mPostionClicked = (int) v.getTag();
		PopupMenu popup = new PopupMenu(getContext(), v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.company_share, popup.getMenu());
		popup.setOnMenuItemClickListener(this);
		popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem menu) {
		switch (menu.getItemId()) {
		case R.id.buy:
			showBuyPopUp();
			return true;
		case R.id.sell:
			showSellPopUp();
			return true;
		}
		return false;
	}

	private void showSellPopUp() {
		SellCompanyShareDialogFragment fragment = SellCompanyShareDialogFragment
				.newInstance((CompanyShare) getItem(mPostionClicked).clone());
		fragment.show(
				((FragmentActivity) getContext()).getSupportFragmentManager(),
				"sell");
	}

	private void showBuyPopUp() {
		BuyCompanyShareDialogFragment fragment = BuyCompanyShareDialogFragment
				.newInstance((CompanyShare) getItem(mPostionClicked).clone());
		fragment.show(
				((FragmentActivity) getContext()).getSupportFragmentManager(),
				"buy");
	}
}
