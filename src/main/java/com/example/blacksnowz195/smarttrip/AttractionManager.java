package com.example.blacksnowz195.smarttrip;

import android.util.Log;

import com.example.blacksnowz195.smarttrip.GoogleUltil.GoogleReadPhotoRef;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackSnowZ195 on 04/08/2015.
 */
public class AttractionManager {
    static AttractionManager instance;

    public static AttractionManager getInstance(){
        if (instance == null)
        {
            instance = new AttractionManager();
        }
        return instance;
    }

    List<Attraction> mAttractions;
    AttractionManager()
    {
        mAttractions = new ArrayList<>();
    }
    public void AddAttraction(Element place) {
        if (!Exists(place.getChild("name").getValue()))
        {
            Attraction attract = new Attraction();
            attract.setIcon(place.getChild("icon").getValue());
            attract.setName(place.getChild("name").getValue());
            attract.setAddress(place.getChild("formatted_address").getValue());
            if ( place.getChild("photo") != null && place.getChild("photo").getChild("photo_reference")!=null) {
                attract.setPhoto(place.getChild("photo").getChild("photo_reference").getValue());
            }
            else attract.setPhoto("Empty");

            if (place.getChild("rating") != null ) {
                attract.setRating(place.getChild("rating").getValue());
            }
            else attract.setRating("0.0");
            Log.d("PlaceID", place.getChild("reference").getValue());
            attract.setAttractionID(place.getChild("reference").getValue());
            attract.setGeometry(place.getChild("geometry"));
            List<Element> types = place.getChildren("type");
            for ( Element type : types)
            {
                Log.d("Add Type", type.getValue());
                attract.addType(type.getValue());
            }
            this.mAttractions.add(attract);

        }
    }

    private  boolean Exists(String name) {
        for (Attraction attraction : this.mAttractions)
        {
            if (attraction.getName() == name)
                return true;
        }
        return false;
    }

    public LatLng getFirstAttractionLocation() {
        return mAttractions.get(0).getLocation();
    }


    public List<Attraction> GetAttractionList() {
        return this.mAttractions;
    }

    public void Clear() {
        this.mAttractions.clear();
        this.mAttractions = null;
        instance = null;
    }

    public Attraction FindAttractionBasedOnMarker(Marker marker) {
        for ( Attraction attraction : mAttractions){
            if (attraction.GetMarker().getId().equals(marker.getId()) ) {
                return attraction;
            }
        }
        return null;
    }
}
