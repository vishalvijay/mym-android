package com.matrix.mym.controller.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.LeaderBoardUser;
import com.matrix.mym.utils.Utils;

@SuppressLint("ViewConstructor")
public class LeaderBoardAdapter extends SupportArrayAdapter<LeaderBoardUser> {

	public LeaderBoardAdapter(Context context,
			ArrayList<LeaderBoardUser> objects) {
		super(context, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(
					R.layout.leader_board_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.rankTextView = (TextView) convertView
					.findViewById(R.id.tvRank);
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.tvName);
			viewHolder.moneyTextView = (TextView) convertView
					.findViewById(R.id.tvMoney);
			viewHolder.messageTextView = (TextView) convertView
					.findViewById(R.id.tvMessage);
			convertView.setTag(viewHolder);

		} else
			viewHolder = (ViewHolder) convertView.getTag();
		LeaderBoardUser leaderBoardUser = getItem(position);
		viewHolder.rankTextView.setText(leaderBoardUser.getRank() + "");
		int color;
		if (leaderBoardUser.getRank() == 1) {
			color = getContext().getResources()
					.getColor(R.color.share_positive);
			viewHolder.messageTextView.setVisibility(View.VISIBLE);
		} else {
			color = getContext().getResources().getColor(R.color.navmenu_bg);
			viewHolder.messageTextView.setVisibility(View.INVISIBLE);
		}
		viewHolder.rankTextView.setBackgroundColor(color);
		viewHolder.nameTextView.setText(leaderBoardUser.getName());
		viewHolder.moneyTextView.setText(getContext().getString(R.string.money,
				Utils.roundAndGetString(leaderBoardUser.getMoney())));
		return convertView;
	}

	private static class ViewHolder {
		public TextView rankTextView, nameTextView, moneyTextView,
				messageTextView;
	}

}
