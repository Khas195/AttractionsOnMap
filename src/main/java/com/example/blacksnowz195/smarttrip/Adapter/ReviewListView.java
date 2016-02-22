package com.example.blacksnowz195.smarttrip.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.blacksnowz195.smarttrip.R;
import com.example.blacksnowz195.smarttrip.Review;

import java.util.List;

/**
 * Created by BlackSnowZ195 on 05/08/2015.
 */
public class ReviewListView extends ArrayAdapter<Review> {
    List<Review> result ;
    Activity activity;
    public ReviewListView(Activity context, List<Review> result) {
        super(context, R.layout.review_layout, result);
        this.result = result;
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View reviewView = convertView;
        LayoutInflater inflater = LayoutInflater.from(activity);
        if (reviewView == null){
            reviewView = inflater.inflate(R.layout.review_layout, parent, false);
        }

        Review curReview = result.get(position);

        ((TextView)reviewView.findViewById(R.id.review_text)).setText(curReview.GetText());
        ((TextView)reviewView.findViewById(R.id.review_author)).setText(curReview.GetAuthor());
        ((TextView)reviewView.findViewById(R.id.review_rating)).setText("Rating :" + curReview.GetRating());

        return reviewView;
    }
}
