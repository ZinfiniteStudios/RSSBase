package com.bruins.android.activity.adapter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruins.android.R;
import com.bruins.android.activity.rss.domain.Article;
import com.bruins.android.activity.util.DateUtils;


public class ArticleListAdapter extends ArrayAdapter<Article> {

    private Context mContext;

	public ArticleListAdapter(Activity activity, List<Article> articles) {
		super(activity, 0, articles);
        mContext = getContext();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		View rowView = inflater.inflate(R.layout.fragment_article_list, null);
		Article article = getItem(position);


		TextView textView = (TextView) rowView.findViewById(R.id.article_title_text);
		textView.setText(article.getTitle());

//		TextView dateView = (TextView) rowView.findViewById(R.id.article_listing_smallprint);
		String pubDate = article.getPubDate();
		SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
		Date pDate;
		try {
			pDate = df.parse(pubDate);
			pubDate = "published " + DateUtils.getDateDifference(pDate);
		} catch (ParseException e) {
			Log.e("DATE PARSING", "Error parsing date..");
			pubDate = "Error getting date...";
		}
//		dateView.setText(pubDate);


		if (!article.isRead()){
			LinearLayout row = (LinearLayout) rowView.findViewById(R.id.article_row_layout);
			row.setBackgroundColor(Color.WHITE);
			textView.setTypeface(Typeface.DEFAULT_BOLD);
		}

        Animation animation = null;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        animation.setDuration(500);
        animation.setFillAfter(false);
        animation.setFillBefore(false);
        rowView.startAnimation(animation);
        animation = null;

		return rowView;

	}
}