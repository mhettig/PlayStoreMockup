package de.mhettig.playstoremockup.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Comment {
	
	private String title;
	private String comment;
	private int rating;
	private String date;
	
	public Comment(String title, String comment, int rating, String date) {
		this.title = title;
		this.comment = comment;
		this.rating = rating;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setRatingStars(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, Drawable starFull) {			

		if (rating >= 1) {
			star1.setImageDrawable(starFull);
		}
		
		if (rating >= 2) {
			star2.setImageDrawable(starFull);
		}
		
		if (rating >= 3) {
			star3.setImageDrawable(starFull);
		}
		
		if (rating >= 4) {
			star4.setImageDrawable(starFull);
		}
		
		if (rating >= 5) {
			star5.setImageDrawable(starFull);
		}
	}

	public String getDate() {
		return date;
	}
}
