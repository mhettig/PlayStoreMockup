package de.mhettig.playstoremockup.content.apps;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.main.Handler;

public class CategoriesAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<String> categoryList;

	public CategoriesAdapter(Context context) {
		super();
		this.context = context;
		this.categoryList = Handler.getCategoryList();
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return categoryList.size();
	}

	public Object getItem(int position) {
		return categoryList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.category_row, null);

		TextView categoryTitle = (TextView) view.findViewById(R.id.categoryRowTitle);

		categoryTitle.setText((String) getItem(position));
		categoryTitle.setTextColor(context.getResources().getColor(R.color.content_apps));
		


		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent;
				intent = new Intent().setClass(context, CategoryFragment.class);
				intent.putExtra("categoryId", position);
				context.startActivity(intent);
			}
		});

		return view;
	}

}
