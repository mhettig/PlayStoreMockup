package de.mhettig.playstoremockup.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.content.apps.ProductsFragment;
import de.mhettig.playstoremockup.model.App;

public class Handler {
	private static List<String> categoryList;
	private static List<App> appList;	
	
	/**
	 * returns the whole categoryList
	 */
	public static List<String> getCategoryList() {
		return categoryList;
	}
	
	/**
	 * returns the category, given by the position in the category list
	 */
	public static String getCategory(int position) {
		return categoryList.get(position);
	}
	
	/**
	 * returns a list, containing all apps for a given categoryId
	 * with the given sorting
	 */
	public static List<App> getAppList(int categoryId, int sorting) {
		List<App> resultList = new ArrayList<App>();
		
		for (App app : appList) {
			switch (sorting) {
			case ProductsFragment.TOP_PAID:
			case ProductsFragment.TOP_NEW_PAID:

				if (!app.getPrice().equals("")) {
					if (categoryId == -1) {
						resultList.add(app);
					} else if (app.getCategoryId() == categoryId) {
						resultList.add(app);
					}
				}
				
				break;
			case ProductsFragment.TOP_FREE:
			case ProductsFragment.TOP_NEW_FREE:
				
				if (app.getPrice().equals("")) {
					if (categoryId == -1) {
						resultList.add(app);
					} else if (app.getCategoryId() == categoryId) {
						resultList.add(app);
					}
					
				}
				break;
			default:
				if (categoryId == -1) {
					resultList.add(app);
				} else if (app.getCategoryId() == categoryId) {
					resultList.add(app);
				}
				break;
			}
		}
		
		switch (sorting) {
		case ProductsFragment.TOP_PAID:
			Collections.sort(resultList, App.SORT_BY_TOP_PAID);
			break;
		case ProductsFragment.TOP_FREE:
			Collections.shuffle(resultList);
			Collections.sort(resultList, App.SORT_BY_TOP_FREE);
			break;
		case ProductsFragment.TOP_GROSSING:
			Collections.sort(resultList, App.SORT_BY_TOP_GROSSING);
			break;
		case ProductsFragment.TOP_NEW_PAID:
			Collections.sort(resultList, App.SORT_BY_TOP_NEW_PAID);
			break;
		case ProductsFragment.TOP_NEW_FREE:
			Collections.shuffle(resultList);
			Collections.sort(resultList, App.SORT_BY_TOP_NEW_FREE);
			break;
		case ProductsFragment.TRENDING:
			Collections.sort(resultList, App.SORT_BY_TRENDING);
			break;
		}

		return resultList;
	}
	
	/**
	 * returns an app, given by the app's id
	 * (mainly used to get the informations for the app detail screen)
	 */
	public static App getApp(int id) {
		for (App app : appList) {
			if (app.getId() == id) {
				return app;
			}
		}
		return null;
	}
	
	//generates all apps & categories
	public static void fillStore(Context context){
		categoryList = Arrays.asList(context.getResources().getStringArray(R.array.cat_apps_array));
    	appList = new ArrayList<App>();
    	AssetManager am = context.getAssets();
    	
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inPurgeable = true;
		options.inDither=false;
		Bitmap myBitmap = null;
		
		int uniqueAppId = 0;
    	try {
			for (String categoryName : am.list("apps")) {

				int categoryId = categoryList.indexOf(categoryName);
		        String[] files = am.list("apps/"+categoryName);
				for(String file : files) {
					String categoryPath = "apps/"+categoryName+"/";

					if (file.endsWith(".json")) {
						String baseName = file.substring(0, file.length()-5);
						String basePath = categoryPath+baseName;
						
				    	App app = App.fromJson(context, categoryPath+file);
				    	appList.add(app);
				    	app.setCategoryId(categoryId);
				    	app.setAppId(uniqueAppId);
				    	uniqueAppId++;
				    	
				    	if (hasFile(files, baseName+"_ic.png")) {
				    		myBitmap = BitmapFactory.decodeStream(am.open(basePath+"_ic.png"), null, options);
				    		app.setIcon(myBitmap);
				    	}
				    	List<Bitmap> scs = new ArrayList<Bitmap>();
				    	for (int i = 1; i < 10; i++) {
					    	if (hasFile(files, baseName+"_sc"+i+".jpg")) {
					    		myBitmap = BitmapFactory.decodeStream(am.open(basePath+"_sc"+i+".jpg"), null, options);
					    		scs.add(myBitmap);
					    	}
				    	}
				    	app.setScreenshots(scs);
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//set sorting - in this case: random
    	Random r = new Random();
    	for (App app : appList) {
    		app.setSorting(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt());		
    	}
	}
	
	private static boolean hasFile(String[] files, String file) {
		for (String f : files) {
			if (f.equals(file)) {
				return true;
			}
		}
		return false;
	}	
}
