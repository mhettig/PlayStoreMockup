package de.mhettig.playstoremockup.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ScrollView;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.content.apps.CategoryFragment;
import de.mhettig.playstoremockup.content.apps.ProductsAdapter;
import de.mhettig.playstoremockup.content.apps.ProductsFragment;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		if (Handler.getCategoryList() == null) {
			Handler.fillStore(this);			
		}
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(getString(R.string.title_home));
		actionBar.setIcon(R.drawable.ic_menu_play_store);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg_home));
		
		initDecor();
	}

	private void initDecor() {
		GridView gridView1 = (GridView) findViewById(R.id.home_gridView1);
		ProductsAdapter productsAdapter1 = new ProductsAdapter(R.layout.product_grid_big, this, ProductsFragment.TOP_GROSSING, -1);
		gridView1.setAdapter(productsAdapter1);
		
		GridView gridView2 = (GridView) findViewById(R.id.home_gridView2);
		ProductsAdapter productsAdapter2 = new ProductsAdapter(R.layout.product_grid_small, this, -1, -1);
		gridView2.setAdapter(productsAdapter2);
		
		ScrollView scrollView = (ScrollView) findViewById(R.id.home_scrollView);
		scrollView.smoothScrollTo(0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
		
	public void openContent(View view) {
		Intent intent = new Intent();
		
		switch (view.getId()) {		
		case R.id.button_home_apps:
			intent.setClass(this, ProductsFragment.class);
			startActivity(intent);
			break;
		case R.id.button_home_games:
			intent.setClass(this, CategoryFragment.class);
			intent.putExtra("categoryId", 0); //0 = GAMES
			break;			
		case R.id.button_home_movies:

			break;
		case R.id.button_home_music:

			break;
		case R.id.button_home_books:

			break;
		case R.id.button_home_magazines:

			break;
		}
		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_home_wishlist:
            return true;
		case R.id.menu_home_accounts:
            return true;
        case R.id.menu_home_settings:
            return true;
        case R.id.menu_home_help:
            return true;
        case R.id.menu_home_imprint:
            return true;
        default:
        	return true;
		}

	}

}
