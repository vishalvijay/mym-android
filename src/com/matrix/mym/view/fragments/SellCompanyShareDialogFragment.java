package com.matrix.mym.view.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.CompanyShare;
import com.matrix.mym.utils.Utils;
import com.matrix.mym.view.activity.MymMainActivity;

public class SellCompanyShareDialogFragment extends DialogFragment implements
		OnClickListener, TextWatcher {

	private MymMainActivity activity;
	private CompanyShare mCompanyShare;
	private TextView titleTextView, currentPriceTextView, balanceTextView,
			totalTextView, shareNumberTextView;
	private Button submitButton;
	private EditText numberEditText;
	private long quantity;

	public static SellCompanyShareDialogFragment newInstance(
			CompanyShare companyShare) {
		SellCompanyShareDialogFragment f = new SellCompanyShareDialogFragment();
		Bundle args = new Bundle();
		args.putParcelable(CompanyShare.STATE, companyShare);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
		View rootView = inflater.inflate(
				R.layout.fragment_dialog_base_company_share, container, false);
		initViews(rootView);
		activity = (MymMainActivity) getActivity();
		return rootView;
	}

	private void initViews(View rootView) {
		titleTextView = (TextView) rootView.findViewById(R.id.tvTitle);
		numberEditText = (EditText) rootView.findViewById(R.id.etNumber);
		numberEditText.addTextChangedListener(this);
		FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.flBody);
		submitButton = (Button) rootView.findViewById(R.id.btSubmit);
		submitButton.setOnClickListener(this);
		View v = getLayoutInflater(null).inflate(
				R.layout.fragment_sell_company_share, fl, false);
		fl.addView(v);
		currentPriceTextView = (TextView) v.findViewById(R.id.tvCurrentPrice);
		balanceTextView = (TextView) v.findViewById(R.id.tvBalance);
		totalTextView = (TextView) v.findViewById(R.id.tvTotalBalance);
		shareNumberTextView = (TextView) v.findViewById(R.id.tvShareNums);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		mCompanyShare = getArguments().getParcelable(CompanyShare.STATE);
		quantity = activity.getUser().getQuantityByCompanyShareId(
				mCompanyShare.getId());
		if (quantity == 0) {
			dismiss();
			Utils.showInfoToast(getActivity(), R.string.you_dont_have_share);
			return;
		}
		titleTextView.setText(mCompanyShare.getName());
		changeMoneyTextView(currentPriceTextView, mCompanyShare.getPrice(),
				R.string.current_price);
		setDefaultBalance();
		setDefaultQuantity();
		submitButton.setText(R.string.sell);
	}

	private void setDefaultQuantity() {
		shareNumberTextView.setText(getString(R.string.you_have_shares,
				quantity));
	}

	@Override
	public void onClick(View v) {
		long quantity = Integer.parseInt(numberEditText.getText().toString());
		if (quantity == 0) {
			numberEditText
					.setError(getString(R.string.should_be_greater_than_0));
			return;
		}
		activity.getUser().sellCompanyShare(getActivity(), mCompanyShare,
				quantity);
		dismiss();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		submitButton.setEnabled(false);
		if (s.length() == 0) {
			totalTextView.setText("");
			setDefaultBalance();
			return;
		}
		long n = Integer.parseInt(s.toString());
		if (n <= quantity) {
			changeMoneyTextView(totalTextView, n * mCompanyShare.getPrice(),
					R.string.you_will_get);
			changeMoneyTextView(balanceTextView,
					activity.getUser().getAccountBalance(getActivity())
							+ (n * mCompanyShare.getPrice()),
					R.string.your_balance_will_be);
			shareNumberTextView.setText(getString(R.string.remaining_shares,
					quantity - n));
			submitButton.setEnabled(true);
		} else {
			totalTextView.setText(R.string.not_enough_shares);
			setDefaultBalance();
			setDefaultQuantity();
		}
	}

	private void setDefaultBalance() {
		changeMoneyTextView(balanceTextView, activity.getUser()
				.getAccountBalance(activity), R.string.your_balance);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	private void changeMoneyTextView(TextView textView, double money, int resId) {
		textView.setText(getString(resId, Utils.roundAndGetString(money)));
	}
}
