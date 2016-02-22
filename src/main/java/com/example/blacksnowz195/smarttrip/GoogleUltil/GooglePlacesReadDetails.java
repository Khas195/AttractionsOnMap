package com.example.blacksnowz195.smarttrip.GoogleUltil;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by BlackSnowZ195 on 05/08/2015.
 */
public class GooglePlacesReadDetails  extends AsyncTask<Object, Integer, String> {
    private CommandSearchResult action;

    @Override
    protected void onPostExecute(String s) {
        action.ExecutePlaceDetailsResult(s);
    }

    @Override
    protected String doInBackground(Object... inputObj) {
        String googlePlacesData = null;
        try {
            String googlePlacesUrl = (String) inputObj[0];
            action = (CommandSearchResult) inputObj[1];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return  googlePlacesData;
    }
}
