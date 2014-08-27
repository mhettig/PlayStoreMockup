package de.mhettig.playstoremockup.content.apps;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.main.Handler;
import de.mhettig.playstoremockup.model.App;

public class ProductsAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<App> appList;
	private int layout;

	public ProductsAdapter(int layout, Context context, int type, int categoryId) {
		super();
		this.layout = layout;
		this.context = context;
		this.appList = Handler.getAppList(categoryId, type);
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return appList.size();
	}

	public Object getItem(int position) {
		return appList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(layout, null);
		
		App app = (App) getItem(position);
		String title = position + 1 + ". " + app.getName();
		String vendor = app.getVendor();
		String price = app.getPrice() + "€";
		
		TextView titleView = (TextView) convertView.findViewById(R.id.productRowTitle);
		TextView vendorView = null;
		TextView priceView = (TextView) convertView.findViewById(R.id.productRowPrice);
		
		ImageView rating1 = (ImageView) convertView.findViewById(R.id.productRowRating1);
		ImageView rating2 = (ImageView) convertView.findViewById(R.id.productRowRating2);
		ImageView rating3 = (ImageView) convertView.findViewById(R.id.productRowRating3);
		ImageView rating4 = (ImageView) convertView.findViewById(R.id.productRowRating4);
		ImageView rating5 = (ImageView) convertView.findViewById(R.id.productRowRating5);
		Drawable ratingHalf = context.getResources().getDrawable(R.drawable.ic_rate_star_market_half);
		Drawable ratingFull = context.getResources().getDrawable(R.drawable.ic_rate_star_market_on);
		
		if (app.isInstalled()) {
			price = "INSTALLED"; //TODO add check-icon like in the original store
			rating1.setVisibility(View.INVISIBLE);
			rating2.setVisibility(View.INVISIBLE);
			rating3.setVisibility(View.INVISIBLE);
			rating4.setVisibility(View.INVISIBLE);
			rating5.setVisibility(View.INVISIBLE);
		} else if (app.getPrice().equals("")) {
			price = "FREE";
		}		
		
		switch(layout) {
		case R.layout.product_row:
			vendorView = (TextView) convertView.findViewById(R.id.productRowVendor);
			vendorView.setText(vendor);
			break;
		case R.layout.product_grid_small:
			title = app.getName();
			ratingHalf = context.getResources().getDrawable(R.drawable.ic_rate_mini_star_market_half);
			ratingFull = context.getResources().getDrawable(R.drawable.ic_rate_mini_star_market_on);
		case R.layout.product_grid_big:
			title = app.getName();
			break;
		}

		titleView.setText(title);
		priceView.setText(price);	
		app.setRatingStars(rating1, rating2, rating3, rating4, rating5, ratingFull, ratingHalf);

		ImageView icon = (ImageView) convertView.findViewById(R.id.productRowIcon);
		icon.setImageBitmap(app.getIcon());

		if (app.isRelevant()) {
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent;
					intent = new Intent().setClass(context, AppDetailActivity.class);
					intent.putExtra("id", ((App) getItem(position)).getId());
					context.startActivity(intent);
				}
			});
		}

		return convertView;
	}

}
