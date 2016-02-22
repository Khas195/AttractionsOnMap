package com.example.blacksnowz195.smarttrip.GoogleUltil;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by BlackSnowZ195 on 02/08/2015.
 */
public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String[]> {
    String googlePlacesData = null;
    CommandSearchResult action;
    @Override
    protected String[] doInBackground(Object... inputObj) {
        String type = null;
        try {
            String googlePlacesUrl = (String) inputObj[0];
            action = (CommandSearchResult) inputObj[1];
            type = (String) inputObj[2];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        String result[] = new String[2];
        result[0] = googlePlacesData;
        result[1] = type;
        return  result;
    }

    @Override
    protected void onPostExecute(String[] result) {
            action.ExecuteResult(result);
        }
}