package com.example.blacksnowz195.smarttrip.GoogleUltil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.blacksnowz195.smarttrip.Attraction;
import com.example.blacksnowz195.smarttrip.AttractionManager;
import com.example.blacksnowz195.smarttrip.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by BlackSnowZ195 on 02/08/2015.
 */
public class GoogleEntertainmentHelper implements CommandSearchResult {
    static GoogleEntertainmentHelper instance ;
    public static GoogleEntertainmentHelper getInstance()
    {
        if (instance == null)
        {
            instance = new GoogleEntertainmentHelper();
        }
        return instance;
    }
    GoogleEntertainmentHelper(){

    };
    boolean doneLoading;
    Activity activity;
    TextView loading;
    GoogleMap map;
    public void LoadSpecificPlace(String placeID, CommandSearchResult action){
        StringBuilder googlePlace = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/xml?reference=" + placeID + "&key=AIzaSyCdudumkaD_jwJOV1nHD9LNThq0fH0vyzw");
        GooglePlacesReadDetails googlePlacesReadTask = new GooglePlacesReadDetails();
        Log.d("PlaceID", googlePlace.toString());
        Object[] toPass = new Object[3];
        toPass[0] = googlePlace.toString();
        toPass[1] = action;
        googlePlacesReadTask.execute(toPass);
    }
    public void LoadPossibleEntertainment(String city, String[] availableType, GoogleMap map, Activity activity) throws IOException, ParserConfigurationException
    {
        loading = (TextView)activity.findViewById(R.id.loading);
        doneLoading = false;
        loading.setVisibility(View.VISIBLE);
        StringBuilder googlePlacesUrl;
        for (String type : availableType) {
            String temp = type.toLowerCase();
            temp = temp.replaceAll(" ", "_");
            Log.d("Loading Request", temp + " " + city.replaceAll(" ", "+"));
            googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/xml?");
            googlePlacesUrl.append("query=" + temp + "+" + city.replaceAll(" ", "+"));
            googlePlacesUrl.append("&types=" + temp);
            googlePlacesUrl.append("&key=" + "AIzaSyCdudumkaD_jwJOV1nHD9LNThq0fH0vyzw");
            GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
            Object[] toPass = new Object[3];
            toPass[0] = googlePlacesUrl.toString();
            toPass[1] = this;
            toPass[2] = temp;
            googlePlacesReadTask.execute(toPass);
            this.map  = map;
        }
    }

    @Override
    public void ExecuteResult(final String[] result) {
        Log.d("PlaceID", result[0]);
        Thread thread = new Thread() {
            @Override
            public void run() {
                SAXBuilder saxBuilder = new SAXBuilder();

                Document doc = null;
                try {
                    doc = saxBuilder.build(new StringReader(result[0]));
                } catch (JDOMException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (doc == null) {
                    Log.d("ExecuteResult", "Failed cause of document null");
                    return;
                }

                Element element = doc.getRootElement();
                List<Element> listResult = element.getChildren("result");
                Log.d("ExecuteResult", "Start Reading");
                for (Element place : listResult)
                {
                    AttractionManager.getInstance().AddAttraction(place);
                }
                Log.d("ExecuteResult", "Finished");
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(AttractionManager.getInstance().getFirstAttractionLocation(), 15.0f));
                PlaceMarkers();
                GoogleEntertainmentHelper.this.doneLoading = true;
                GoogleEntertainmentHelper.this.loading.setVisibility(View.INVISIBLE);
            }
        };
        thread.run();
    }

    @Override
    public void ExecutePlaceDetailsResult(String resulst) {

    }

    public void PlaceMarkers(){
        final List<Attraction> attractionList = AttractionManager.getInstance().GetAttractionList();
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (Attraction attraction : attractionList)
                {

                    Marker marker = map.addMarker(new MarkerOptions().position(attraction.getLocation())
                                .title(attraction.getName())
                                .snippet(attraction.getAddress()));
                         marker.setVisible(false);
                    attraction.setMarker(marker);
                }
            }
        };
        thread.run();
    }
    public boolean IsDoneLoading() {
        return  doneLoading;
    }

    public void Clear() {
        this.map = null;
        this.activity = null;
    }
}
