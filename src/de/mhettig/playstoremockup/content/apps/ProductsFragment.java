package de.mhettig.playstoremockup.content.apps;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import de.mhettig.playstoremockup.R;

/**
 * Displays the main screen when accessing "APPS"
 * 
 * @author Markus Hettig
 *
 */
public class ProductsFragment extends FragmentActivity implements ActionBar.TabListener {

	//declares the type of screen to be loaded for the tab
	public static final int CATEGORIES = 0;
	public static final int HOME = 1;
	public static final int TOP_PAID = 2;
	public static final int TOP_FREE = 3;
	public static final int TOP_GROSSING = 4;
	public static final int TOP_NEW_PAID = 5;
	public static final int TOP_NEW_FREE = 6;
	public static final int TRENDING = 7;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_products);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(getString(R.string.title_apps));
		actionBar.setIcon(R.drawable.ic_menu_play_store);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg_apps));
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar
					.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		actionBar.setSelectedNavigationItem(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new TabFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("categoryId", -1);
			
			switch (position) {
			case 0:
				bundle.putInt("type", CATEGORIES);
				break;
			case 1:
				bundle.putInt("type", HOME);
				break;
			case 2:
				bundle.putInt("type", TOP_PAID);
				break;
			case 3:
				bundle.putInt("type", TOP_FREE);
				break;
			case 4:
				bundle.putInt("type", TOP_GROSSING);
				break;
			case 5:
				bundle.putInt("type", TOP_NEW_PAID);
				break;
			case 6:
				bundle.putInt("type", TOP_NEW_FREE);
				break;
			case 7:
				bundle.putInt("type", TRENDING);
				break;
			}
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 8;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.tab_title_categories);
			case 1:
				return getString(R.string.tab_title_home);
			case 2:
				return getString(R.string.tab_title_paid);
			case 3:
				return getString(R.string.tab_title_free);
			case 4:
				return getString(R.string.tab_title_grossing);
			case 5:
				return getString(R.string.tab_title_newPaid);
			case 6:
				return getString(R.string.tab_title_newFree);
			case 7:
				return getString(R.string.tab_title_trending);
			}
			return null;
		}
	}

}
