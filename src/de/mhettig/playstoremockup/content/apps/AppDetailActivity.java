package de.mhettig.playstoremockup.content.apps;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.main.Handler;
import de.mhettig.playstoremockup.model.App;
import de.mhettig.playstoremockup.model.Comment;

public class AppDetailActivity extends Activity {
	
	private App app;
	private boolean freeApp;
	boolean descToogle = false;
	
	Button installButton;
	ProgressBar installPB;
	TextView installTV;
	
	Drawable ratingStarHalf;
	Drawable ratingStarFull;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdetail);  
        
        //init ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(getString(R.string.title_apps));
		actionBar.setIcon(R.drawable.ic_menu_play_store);
			
		//get App Id
		int appId = 0;
		Bundle delivered = getIntent().getExtras(); 
		if (delivered != null && !delivered.isEmpty()) {
			appId = delivered.getInt("id");
		}
    	app = Handler.getApp(appId);
    	freeApp = app.getPrice().equals("");
    	
    	//for rating stars
		ratingStarHalf = getResources().getDrawable(R.drawable.ic_rate_star_market_half);
		ratingStarFull = getResources().getDrawable(R.drawable.ic_rate_star_market_on);
		
    	initHeader();
    	initScreenshots();
    	initDetails();
    	initDescription();   
    	initRating();
    	initComments();
    	
    	//TODO add more app details
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

	private void initHeader() {
		//set icon
    	ImageView icon = (ImageView) findViewById(R.id.appDetailIcon);
    	icon.setImageBitmap(app.getIcon());
    	
    	//init views & buttons
    	TextView appTitleView = (TextView) findViewById(R.id.appDetailAppTitle);
    	TextView appVendorView = (TextView) findViewById(R.id.appDetailAppVendor);
    	installButton = (Button) findViewById(R.id.appDetailButton);
    	installPB = (ProgressBar) findViewById(R.id.appDetail_inst_pb);
    	installTV = (TextView) findViewById(R.id.appDetail_inst_tv);
    	
    	if (app.isInstalled()) {
    		installButton.setText("UNINSTALL");
    		installButton.setBackgroundResource(R.xml.appdetail_btn_uninstall);
    		installButton.setTextColor(getResources().getColor(R.color.greyDetails));
    		installButton.setEnabled(false);
    	}
    	if (!freeApp) {
    		installButton.setText(app.getPrice());
    	}
    	    	
    	
    	//fill views & buttons with content
    	appTitleView.setText(app.getName());
    	appVendorView.setText(app.getVendor());
    	
    	installButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (freeApp) {
					PermissionsDialog dialog = new PermissionsDialog();
					Bundle bundle = new Bundle();
					bundle.putInt("appID", app.getId());
					dialog.setArguments(bundle);
					dialog.show(getFragmentManager(), "");
				} else {
					Toast toast = Toast.makeText(AppDetailActivity.this, "Buying is diabled in this version", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
	}

	@SuppressLint("SimpleDateFormat")
	private void initDetails() {
		TextView descViewLeft1stRow = (TextView) findViewById(R.id.appDetailDescLeft1stRow);
		TextView descViewLeft2ndRow = (TextView) findViewById(R.id.appDetailDescLeft2ndRow);
    	TextView descViewRight = (TextView) findViewById(R.id.appDetailDescRight);
		
		ImageView ratingStar1 = (ImageView) findViewById(R.id.appDetailRatingStars1Desc);
		ImageView ratingStar2 = (ImageView) findViewById(R.id.appDetailRatingStars2Desc);
		ImageView ratingStar3 = (ImageView) findViewById(R.id.appDetailRatingStars3Desc);
		ImageView ratingStar4 = (ImageView) findViewById(R.id.appDetailRatingStars4Desc);
		ImageView ratingStar5 = (ImageView) findViewById(R.id.appDetailRatingStars5Desc);
		
		Calendar cal  = Calendar.getInstance();
		Date time = cal.getTime();
		DateFormat formatter = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
    	
		app.setRatingStars(ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5, ratingStarFull, ratingStarHalf);
    	String detailsTmpLeft1stRow = app.getRatingCount() + "";
    	String detailsTmpLeft2ndRow = app.getDownloadAmount() + "+ downloads";
    	String detailsTmpRight = formatter.format( time ) + "\n" + app.getFileSize() + "KB";
	
    	descViewLeft1stRow.setText(detailsTmpLeft1stRow);
    	descViewLeft2ndRow.setText(detailsTmpLeft2ndRow);
    	descViewRight.setText(detailsTmpRight);
	}

    private void initDescription() {
    	final TextView detailsView = (TextView) findViewById(R.id.appDetailDescription);
    	detailsView.setText(app.getDescription());
    	
    	final Button descBtn = (Button) findViewById(R.id.appDetailDescriptionBtn);
    	
    	final int height = detailsView.getLayoutParams().height;
    	detailsView.getLayoutParams().height = 250;
		detailsView.requestLayout();
		
		
		OnClickListener descListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (descToogle) {
					descBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_menu_expander_minimized_holo_light), null);
					detailsView.getLayoutParams().height = 250;
				} else {
					descBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_menu_expander_maximized_holo_light), null);
					detailsView.getLayoutParams().height = height;
				}
				descToogle = !descToogle;
				
				detailsView.requestLayout();			
			}
		};
		
		descBtn.setOnClickListener(descListener);
		detailsView.setOnClickListener(descListener);
    }
    
	private void initRating() {
		int ratingCount = app.getRatingCount(); 
		int rating5 = app.getRating5();
		int rating4 = app.getRating4();
		int rating3 = app.getRating3();
		int rating2 = app.getRating2();
		int rating1 = app.getRating1();
				
		//box on the left
		TextView tvAvrRating = (TextView) findViewById(R.id.appDetailRatingAvr);
    	TextView tvAvrRatingCount = (TextView) findViewById(R.id.appDetailRatingCount);
		ImageView ratingStar1 = (ImageView) findViewById(R.id.appDetailRatingStars1);
		ImageView ratingStar2 = (ImageView) findViewById(R.id.appDetailRatingStars2);
		ImageView ratingStar3 = (ImageView) findViewById(R.id.appDetailRatingStars3);
		ImageView ratingStar4 = (ImageView) findViewById(R.id.appDetailRatingStars4);
		ImageView ratingStar5 = (ImageView) findViewById(R.id.appDetailRatingStars5);
    	
    	DecimalFormat df = new DecimalFormat("@@");
    	df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		df.setRoundingMode(RoundingMode.HALF_EVEN);
    	tvAvrRating.setText(df.format(app.getAvrRatingNumber()));
    	app.setRatingStars(ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5, ratingStarFull, ratingStarHalf);
    	tvAvrRatingCount.setText(String.valueOf(ratingCount));
    	
    	
    	//progressbars
    	ProgressBar pbRating5 = (ProgressBar) findViewById(R.id.appDetailRatingPB5);
    	ProgressBar pbRating4 = (ProgressBar) findViewById(R.id.appDetailRatingPB4);
    	ProgressBar pbRating3 = (ProgressBar) findViewById(R.id.appDetailRatingPB3);
    	ProgressBar pbRating2 = (ProgressBar) findViewById(R.id.appDetailRatingPB2);
    	ProgressBar pbRating1 = (ProgressBar) findViewById(R.id.appDetailRatingPB1);
    	
    	pbRating5.setMax(ratingCount);
    	pbRating4.setMax(ratingCount);
    	pbRating3.setMax(ratingCount);
    	pbRating2.setMax(ratingCount);
    	pbRating1.setMax(ratingCount);
    	
    	pbRating5.setProgress(rating5);
    	pbRating4.setProgress(rating4);
    	pbRating3.setProgress(rating3);
    	pbRating2.setProgress(rating2);
    	pbRating1.setProgress(rating1);
    	
    	
    	//counters on the right
    	TextView tvRating5Count = (TextView) findViewById(R.id.appDetailRating5);
    	TextView tvRating4Count = (TextView) findViewById(R.id.appDetailRating4);
    	TextView tvRating3Count = (TextView) findViewById(R.id.appDetailRating3);
    	TextView tvRating2Count = (TextView) findViewById(R.id.appDetailRating2);
    	TextView tvRating1Count = (TextView) findViewById(R.id.appDetailRating1);
    	
    	tvRating5Count.setText(String.valueOf(rating5));
    	tvRating4Count.setText(String.valueOf(rating4));
    	tvRating3Count.setText(String.valueOf(rating3));
    	tvRating2Count.setText(String.valueOf(rating2));
    	tvRating1Count.setText(String.valueOf(rating1));
	}

	private void initComments() {
		LinearLayout comments = (LinearLayout) findViewById(R.id.appDetailCommentLL);
    	LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	TextView tv;
    	for (Comment c : app.getComments()) {
    		View v = vi.inflate(R.layout.comment, null);
    		
    		ImageView ratingStar1 = (ImageView) v.findViewById(R.id.appDetailCommentStar1);
    		ImageView ratingStar2 = (ImageView) v.findViewById(R.id.appDetailCommentStar2);
    		ImageView ratingStar3 = (ImageView) v.findViewById(R.id.appDetailCommentStar3);
    		ImageView ratingStar4 = (ImageView) v.findViewById(R.id.appDetailCommentStar4);
    		ImageView ratingStar5 = (ImageView) v.findViewById(R.id.appDetailCommentStar5);
    		
    		c.setRatingStars(ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5, ratingStarFull);
    		
    		tv = (TextView) v.findViewById(R.id.appDetailCommentTitle);
    		tv.setText(c.getTitle());
    		tv = (TextView) v.findViewById(R.id.appDetailCommentDate);
    		tv.setText(c.getDate());
    		tv = (TextView) v.findViewById(R.id.appDetailCommentBody);
    		tv.setText(c.getComment());

    	    comments.addView(v);
    	}
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
      
    private void initScreenshots() {
    	LinearLayout screenshotLL = (LinearLayout) findViewById(R.id.appDetailScreenshotLL);
    	List<Bitmap> screenshots = app.getScreenshots();
    	
            for (int i = 0; i < screenshots.size(); i++) {
            	final int j = i;
        		ImageView v = new ImageView(this);
        		v.setImageBitmap(screenshots.get(i));

        		float density = getResources().getDisplayMetrics().density;
        		int finalDimens = (int)(185 * density);
        		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(finalDimens, finalDimens);
        		layoutParams.setMargins(-70, 0, -40, 0);
        		v.setLayoutParams(layoutParams);

        		v.setScaleType(ScaleType.CENTER_INSIDE);
        		
        		v.setAdjustViewBounds(true);
                v.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	Intent intent = new Intent(getApplicationContext(), ScreenshotActivity.class);
                    	intent.putExtra("appID", app.getId());
                    	intent.putExtra("screenshotPosition", j);
                    	AppDetailActivity.this.startActivity(intent);
                    }
                });

                screenshotLL.addView(v);
        }
    }
    
    public void startFakeInstallation() {
    	app.setInstalled(true);
    	
    	installButton.setVisibility(View.INVISIBLE);
    	installPB.setVisibility(View.VISIBLE);
    	installTV.setVisibility(View.VISIBLE);
    	
    	new UpdateProgress().execute();
    }
    
	private class UpdateProgress extends AsyncTask<Void, Integer, Void> {
		private int fileSize = app.getFileSize();
		
		@Override
		protected void onPreExecute() {
			installPB.setMax(fileSize);
		}

		@Override
		protected Void doInBackground(Void... nothing) {
			Random rand = new Random();
			
			int steps = rand.nextInt(5) + 5;
			int stepsValue = fileSize / steps;
			
			for (int i = 0; i < steps; i++) {
				publishProgress(i * stepsValue);
				
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//Download finished
			publishProgress(-1);
			try {
				Thread.sleep(1100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			installPB.setProgress(values[0]);
			
			installTV.setText(values[0] + "KB/" + fileSize + "KB");
			
			if (values[0] == -1) {
				installTV.setText("Installing...");
				installPB.setIndeterminate(true);
			}
		}

		@Override
		protected void onPostExecute(Void result) {
	    	installPB.setVisibility(View.INVISIBLE);
	    	installTV.setVisibility(View.INVISIBLE);
	    	
	    	installButton.setVisibility(View.VISIBLE);
	    	installButton.setText("UNINSTALL");
	    	installButton.setBackgroundResource(R.xml.appdetail_btn_uninstall);
	    	installButton.setTextColor(getResources().getColor(R.color.greyDetails));
	    	installButton.setEnabled(false);
		}
	}
}
