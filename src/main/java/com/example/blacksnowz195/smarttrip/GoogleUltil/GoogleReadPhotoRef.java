package com.example.blacksnowz195.smarttrip.GoogleUltil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BlackSnowZ195 on 04/08/2015.
 */
public class GoogleReadPhotoRef extends AsyncTask<Object, Integer, Bitmap> {
    CommandSearchResult action;
    ImageView view;
    @Override
    protected Bitmap doInBackground(Object... inputObj) {
        Bitmap bitmap = null;
        try
        {
            String googlePlacesUrl = (String) inputObj[0];
            view = (ImageView) inputObj[1];
            Http http = new Http();
            bitmap  = http.readPhoto(googlePlacesUrl);
        } catch (IOException e) {
            Log.d("HttpReadPhoto", "Stop at GoogleReadPhotoRef");
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }


}
