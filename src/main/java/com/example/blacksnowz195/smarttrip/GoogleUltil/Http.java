package com.example.blacksnowz195.smarttrip.GoogleUltil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BlackSnowZ195 on 02/08/2015.
 */
public class Http {

    public String read(String httpUrl) throws IOException {
        String httpData = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            httpData = stringBuffer.toString();
            bufferedReader.close();
        } catch (Exception e) {
            Log.d("Exception - reading Http url", e.toString());
        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return httpData;
    }
    public Bitmap readPhoto(String httpUrl) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(httpUrl);
            Log.d("HttpReadPhoto", "line1");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            Log.d("HttpReadPhoto", "line2");
            httpURLConnection.connect();
            Log.d("HttpReadPhoto", String.valueOf(httpURLConnection.getResponseCode()));
            Log.d("HttpReadPhoto", httpURLConnection.getErrorStream().toString());
            inputStream = httpURLConnection.getInputStream();

            Log.d("HttpReadPhoto", "line4");
            inputStream = new BufferedInputStream(url.openStream());
            if (inputStream == null){
                Log.d("HttpReadPhoto", "inputstream still null");
            }
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.d("HttpReadPhoto", e.toString());
        } finally {
            if (inputStream != null)
                inputStream.close();
            httpURLConnection.disconnect();
        }
        return bitmap;
    }
}
