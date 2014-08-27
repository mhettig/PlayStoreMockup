package de.mhettig.playstoremockup.content.apps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import de.mhettig.playstoremockup.R;

/**
 * Simple class that fills the tabs.
 * Used to show the different categories and displays a list of apps for a given category
 * Also used for top lists like "top free" or "top grossing" 
 * 
 * @author Markus Hettig
 */
public class TabFragment extends Fragment {
		
	private ProductsAdapter productsAdapter1;
	private ProductsAdapter productsAdapter2;
	private ProductsAdapter productsAdapter3;
	
	public TabFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Activity activity = getActivity();

		View rootView0 = null;
		
		switch (getArguments().getInt("type")) {
		case ProductsFragment.CATEGORIES:
			View rootView1 = inflater.inflate(R.layout.fragment_listview, container, false);
			ListView listView1 = (ListView) rootView1.findViewById(R.id.fragment_listView);
			rootView0 = rootView1;
			
			CategoriesAdapter categoriesAdapter = new CategoriesAdapter(activity);	
			listView1.setAdapter(categoriesAdapter);
			break;
		case ProductsFragment.HOME:
			View rootView2 = inflater.inflate(R.layout.fragment_home, container, false);
			rootView0 = rootView2;
			
			GridView gridView1 = (GridView) rootView2.findViewById(R.id.apphome_gridView1);
			GridView gridView2 = (GridView) rootView2.findViewById(R.id.apphome_gridView2);
			GridView gridView3 = (GridView) rootView2.findViewById(R.id.apphome_gridView3);
			
			productsAdapter1 = new ProductsAdapter(R.layout.product_grid_small, activity, -1, getArguments().getInt("categoryId"));
			productsAdapter2 = new ProductsAdapter(R.layout.product_grid_big, activity, ProductsFragment.TOP_GROSSING, getArguments().getInt("categoryId"));
			productsAdapter3 = new ProductsAdapter(R.layout.product_grid_small, activity, ProductsFragment.TRENDING, getArguments().getInt("categoryId"));
			
			gridView1.setAdapter(productsAdapter1);
			gridView2.setAdapter(productsAdapter2);
			gridView3.setAdapter(productsAdapter3);
			
			break;
			
		case ProductsFragment.TRENDING:
			View rootView3 = inflater.inflate(R.layout.fragment_gridview, container, false);
			GridView listView2 = (GridView) rootView3.findViewById(R.id.fragment_gridLayout);
			rootView0 = rootView3;
			
			productsAdapter1 = new ProductsAdapter(R.layout.product_grid_small, activity, getArguments().getInt("type"), getArguments().getInt("categoryId"));
			listView2.setAdapter(productsAdapter1);
			
			break;
		default:
			View rootView4 = inflater.inflate(R.layout.fragment_listview, container, false);
			ListView listView3 = (ListView) rootView4.findViewById(R.id.fragment_listView);
			rootView0 = rootView4;
			
			productsAdapter1 = new ProductsAdapter(R.layout.product_row, activity, getArguments().getInt("type"), getArguments().getInt("categoryId"));
			listView3.setAdapter(productsAdapter1);
			listView3.setDividerHeight(0);
		}

		return rootView0;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//to show "installed" instead of "free" (or price) in the overview screen when the user has installed an app and returns to the overview screen
		if (productsAdapter1 != null) {
			productsAdapter1.notifyDataSetChanged();
		}
		if (productsAdapter2 != null) {
			productsAdapter2.notifyDataSetChanged();
		}
		if (productsAdapter3 != null) {
			productsAdapter3.notifyDataSetChanged();
		}
	}

}
