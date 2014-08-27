package de.mhettig.playstoremockup.content.apps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.main.Handler;
import de.mhettig.playstoremockup.model.App;

public class ScreenshotActivity extends Activity {

	private App app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_screenshot);

		int appId = 0;
		int screenshotPosition = 0;
		Bundle delivered = getIntent().getExtras(); 
		if (delivered != null && !delivered.isEmpty()) {
			appId = delivered.getInt("appID");
			screenshotPosition = delivered.getInt("screenshotPosition");
		}
    	app = Handler.getApp(appId);
    	
		ViewPager viewPager = (ViewPager) findViewById(R.id.appDetailScreenshot);
	    ImagePagerAdapter adapter = new ImagePagerAdapter(app.getScreenshots(), this);
	    viewPager.setAdapter(adapter);
	    viewPager.setCurrentItem(screenshotPosition);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	return true; }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { return true; }

	
	private class ImagePagerAdapter extends PagerAdapter {
		private List<Bitmap> screenshots;
		private Context context;
		
		public ImagePagerAdapter(List<Bitmap> list, Context context) {
			this.screenshots = list;
			this.context = context;
		}
		
		@Override
		public int getCount() {
			return screenshots.size();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageBitmap(screenshots.get(position));
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((ImageView) arg1);
		}
	}
}

