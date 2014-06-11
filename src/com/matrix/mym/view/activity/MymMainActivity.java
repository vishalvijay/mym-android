package com.matrix.mym.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.Window;

import com.matrix.mym.R;
import com.matrix.mym.model.Quiz;
import com.matrix.mym.model.User;
import com.matrix.mym.view.fragments.AboutFragment;
import com.matrix.mym.view.fragments.HelpFragment;
import com.matrix.mym.view.fragments.LeaderBoardFragment;
import com.matrix.mym.view.fragments.MySharesFragment;
import com.matrix.mym.view.fragments.NavigationDrawerFragment;
import com.matrix.mym.view.fragments.NewsFragment;
import com.matrix.mym.view.fragments.QuizFragment;
import com.matrix.mym.view.fragments.StockStatusFragment;
import com.matrix.mym.view.fragments.VirtualShareMarketFragment;

public class MymMainActivity extends MymActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	protected static final String TAG = "MymMainActivity";
	public static final String STATE_USER = "user";
	public static final String STATE_QUIZ = "quiz";
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private User mUser;
	private Quiz mQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_PROGRESS);
		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_mym_main);
		if (savedInstanceState == null) {
			mUser = getIntent().getParcelableExtra(STATE_USER);
			loadQuiz();
		} else {
			mUser = savedInstanceState.getParcelable(STATE_USER);
			mQuiz = savedInstanceState.getParcelable(STATE_QUIZ);
		}
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		onSectionAttached(getTitle().toString());
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new QuizFragment();
			break;
		case 1:
			fragment = new VirtualShareMarketFragment();
			break;
		case 2:
			fragment = new StockStatusFragment();
			break;
		case 3:
			fragment = new MySharesFragment();
			break;
		case 4:
			fragment = new LeaderBoardFragment();
			break;
		case 5:
			fragment = new NewsFragment();
			break;
		case 6:
			fragment = new HelpFragment();
			break;
		case 7:
			fragment = new AboutFragment();
			break;
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(getCurrentTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.mym_main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	public User getUser() {
		return mUser;
	}

	public Quiz getQuiz() {
		return mQuiz;
	}

	public void loadQuiz() {
		mQuiz = Quiz.getCurrentQuiz(getApplicationContext());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(STATE_USER, mUser);
		outState.putParcelable(STATE_QUIZ, mQuiz);
		super.onSaveInstanceState(outState);
	}

}
