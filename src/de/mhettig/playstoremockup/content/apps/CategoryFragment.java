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
import de.mhettig.playstoremockup.main.Handler;

/**
 * Displays each category
 * In other words: the screen when accessing a specific category like "Business" (apps)
 * 
 * @author Markus Hettig
 *
 */
public class CategoryFragment extends FragmentActivity implements ActionBar.TabListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	int categoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_products);
		
		categoryId = getIntent().getExtras().getInt("categoryId");
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(Handler.getCategory(categoryId));
		actionBar.setIcon(R.drawable.ic_menu_play_store);
		
		// Create the adapter that will return a fragment for each of the sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar
					.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

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
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
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
			bundle.putInt("categoryId", categoryId);
			
			switch (position) {
			case 0:
				bundle.putInt("type", ProductsFragment.TOP_PAID);
				break;
			case 1:
				bundle.putInt("type", ProductsFragment.TOP_FREE);
				break;
			case 2:
				bundle.putInt("type", ProductsFragment.TOP_GROSSING);
				break;
			case 3:
				bundle.putInt("type", ProductsFragment.TOP_NEW_PAID);
				break;
			case 4:
				bundle.putInt("type", ProductsFragment.TOP_NEW_FREE);
				break;
			}
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.tab_title_paid);
			case 1:
				return getString(R.string.tab_title_free);
			case 2:
				return getString(R.string.tab_title_grossing);
			case 3:
				return getString(R.string.tab_title_newPaid);
			case 4:
				return getString(R.string.tab_title_newFree);
			}
			return null;
		}
	}

}
