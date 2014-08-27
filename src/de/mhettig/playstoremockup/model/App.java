package de.mhettig.playstoremockup.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

public class App {
	private int id;							//unique app id
	private int categoryId;					//the category, this app belongs to
	private String name;
	private String description;
	private String vendor;
	private String price;					//price == "" -> free
	private Bitmap icon;
	private int rating1;
	private int rating2;
	private int rating3;
	private int rating4;
	private int rating5;
	private String version;
	private int fileSize;					//also used to show a realistic updating dowload progressbar
	private int downloadAmount;
	private Permission[] permissions;
	private List<Bitmap> screenshots;
	private Comment[] comments;
	
	//random int-values to offer sorting
	private int sortingTopPaid;
	private int sortingTopFree;
	private int sortingTopGrossing;
	private int sortingTopNewPaid;
	private int sortingTopNewFree;
	private int sortingTrending;
	
	private boolean isInstalled = false;	//prevent users from installing an app twice
	private boolean isRelevant;				//seperate study-relevant apps from dummy app	

	public static App fromJson(Context context, String filePath) {
		InputStream is = null;
		try {
			is = context.getAssets().open(filePath);
		} catch (IOException e1) {
			Log.e("JSON", filePath + " does not exist");
		}
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String jsonString = writer.toString();
		Gson gson = new Gson();
		App app = null;
		try {
			app = gson.fromJson(jsonString, App.class);
		} catch (Exception e) {
			Log.e("JSON", filePath + " could not be parsed");
			Log.e("JSON", e.getMessage());
		}
		return app;
	}
	
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public void setScreenshots(List<Bitmap> screenshots) {
		this.screenshots = screenshots;
	}

	public void setAppId(int appId) {
		this.id = appId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setSorting(int sortingTopPaid, int sortingTopFree, int sortingTopGrossing, int sortingTopNewPaid, int sortingTopNewFree, int sortingTrending) {
		this.sortingTopPaid = sortingTopPaid;
		this.sortingTopFree = sortingTopFree;
		this.sortingTopGrossing = sortingTopGrossing;
		this.sortingTopNewPaid = sortingTopNewPaid;
		this.sortingTopNewFree = sortingTopNewFree;
		this.sortingTrending = sortingTrending;
	}

	public int getId() {
		return id;
	}
	
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public Permission[] getPermissions() {
		return permissions;
	}

	public boolean isInstalled() {
		return isInstalled;
	}

	public void setInstalled(boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVendor() {
		return vendor;
	}

	public String getPrice() {
		return price;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public int getRating1() {
		return rating1;
	}
	
	public int getRating2() {
		return rating2;
	}
	
	public int getRating3() {
		return rating3;
	}
	
	public int getRating4() {
		return rating4;
	}
	
	public int getRating5() {
		return rating5;
	}
	
	public int getRatingCount() {
		return rating1 + rating2 + rating3 + rating4 + rating5;
	}
	
	public double getAvrRatingNumber() {
		double tmp = (rating1 * 1) + (rating2 * 2) + (rating3 * 3) + (rating4 * 4) + (rating5 * 5);
		return tmp / getRatingCount();
	}
	
	public void setRatingStars(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, Drawable starFull, Drawable starHalf) {
		double tmp = getAvrRatingNumber();				

		if (0.25 <= tmp && tmp < 0.75) {
			star1.setImageDrawable(starHalf);
		} else if (0.75 <= tmp) {
			star1.setImageDrawable(starFull);
		}
		
		if (1.25 <= tmp && tmp < 1.75) {
			star2.setImageDrawable(starHalf);
		} else if (1.75 <= tmp) {
			star2.setImageDrawable(starFull);
		}
		
		if (2.25 <= tmp && tmp < 2.75) {
			star3.setImageDrawable(starHalf);
		} else if (2.75 <= tmp) {
			star3.setImageDrawable(starFull);
		}
		
		if (3.25 <= tmp && tmp < 3.75) {
			star4.setImageDrawable(starHalf);
		} else if (3.75 <= tmp) {
			star4.setImageDrawable(starFull);
		}
		
		if (4.25 <= tmp && tmp < 4.75) {
			star5.setImageDrawable(starHalf);
		} else if (4.75 <= tmp) {
			star5.setImageDrawable(starFull);
		}
	}

	public String getVersion() {
		return version;
	}

	public int getFileSize() {
		return fileSize;
	}

	public int getDownloadAmount() {
		return downloadAmount;
	}

	public List<Bitmap> getScreenshots() {
		return screenshots;
	}

	public Comment[] getComments() {
		return comments;
	}
	
	public boolean isRelevant() {
		return isRelevant;
	}
	
	/* Comperator-Methods for sorting  */
	public static Comparator<App> SORT_BY_TOP_PAID = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.sortingTopPaid < other.sortingTopPaid) { return 1; }
    	  	if (one.sortingTopPaid > other.sortingTopPaid) { return -1; }
    	    return 0;
        }
    };
    
	public static Comparator<App> SORT_BY_TOP_FREE = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.isRelevant && !other.isRelevant) { return -1; }
        	if (!one.isRelevant && other.isRelevant) {return 1; }
        	if (one.isRelevant && other.isRelevant) {return 0; }
        	if (one.sortingTopFree < other.sortingTopFree) { return 1; }
    	  	if (one.sortingTopFree > other.sortingTopFree) { return -1; }
    	    return 0;
        }
    };
    
	public static Comparator<App> SORT_BY_TOP_GROSSING = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.sortingTopGrossing < other.sortingTopGrossing) { return 1; }
    	  	if (one.sortingTopGrossing > other.sortingTopGrossing) { return -1; }
    	    return 0;
        }
    };
    
	public static Comparator<App> SORT_BY_TOP_NEW_PAID = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.sortingTopNewPaid < other.sortingTopNewPaid) { return 1; }
    	  	if (one.sortingTopNewPaid > other.sortingTopNewPaid) { return -1; }
    	    return 0;
        }
    };
    
	public static Comparator<App> SORT_BY_TOP_NEW_FREE = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.isRelevant && !other.isRelevant) { return -1; }
        	if (!one.isRelevant && other.isRelevant) {return 1; }
        	if (one.isRelevant && other.isRelevant) {return 0; }
        	if (one.sortingTopNewFree < other.sortingTopNewFree) { return 1; }
    	  	if (one.sortingTopNewFree > other.sortingTopNewFree) { return -1; }
    	    return 0;
        }
    };
    
	public static Comparator<App> SORT_BY_TRENDING = new Comparator<App>() {
        public int compare(App one, App other) {
        	if (one.sortingTrending < other.sortingTrending) { return 1; }
    	  	if (one.sortingTrending > other.sortingTrending) { return -1; }
    	    return 0;
        }
    };
	
}
