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

public class BuyCompanyShareDialogFragment extends DialogFragment implements
		OnClickListener, TextWatcher {

	private MymMainActivity activity;
	private CompanyShare mCompanyShare;
	private TextView titleTextView, currentPriceTextView, balanceTextView,
			totalTextView;
	private Button submitButton;
	private EditText numberEditText;

	public static BuyCompanyShareDialogFragment newInstance(
			CompanyShare companyShare) {
		BuyCompanyShareDialogFragment f = new BuyCompanyShareDialogFragment();
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
				R.layout.fragment_buy_company_share, fl, false);
		fl.addView(v);
		currentPriceTextView = (TextView) v.findViewById(R.id.tvCurrentPrice);
		balanceTextView = (TextView) v.findViewById(R.id.tvBalance);
		totalTextView = (TextView) v.findViewById(R.id.tvTotalBalance);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		mCompanyShare = getArguments().getParcelable(CompanyShare.STATE);
		titleTextView.setText(mCompanyShare.getName());
		changeMoneyTextView(currentPriceTextView, mCompanyShare.getPrice(),
				R.string.current_price);
		setDefaultBalance();
		submitButton.setText(R.string.buy);
	}

	@Override
	public void onClick(View v) {
		long quantity = Integer.parseInt(numberEditText.getText().toString());
		if (quantity == 0) {
			numberEditText
					.setError(getString(R.string.should_be_greater_than_0));
			return;
		}
		activity.getUser().buyCompanyShare(getActivity(), mCompanyShare,
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
		double pay = n * mCompanyShare.getPrice();
		double balance = activity.getUser().getAccountBalance(activity) - pay;
		if (balance > 0) {
			changeMoneyTextView(totalTextView, pay, R.string.you_need_to_pay);
			changeMoneyTextView(balanceTextView, activity.getUser()
					.getAccountBalance(activity) - pay,
					R.string.your_balance_will_be);
			submitButton.setEnabled(true);
		} else {
			totalTextView.setText(R.string.not_enough_balance);
			setDefaultBalance();
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
