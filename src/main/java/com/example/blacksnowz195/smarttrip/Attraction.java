package com.example.blacksnowz195.smarttrip;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackSnowZ195 on 03/08/2015.
 */
public class Attraction {
    String name;
    String address;
    String photoRef;
    String attractionID;
    String rating;
    List<String> type;
    Element geometry;
    private Marker marker;
    private String icon;

    public String getAttractionID() {
        return attractionID;
    }

    public void setAttractionID(String attractionID) {
        this.attractionID = attractionID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoto() {
        return photoRef;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoto(String photo) {
        this.photoRef = "https://maps.googleapis.com/maps/api/place/photo?"
                        + "&photoreference=" + photo
                        +  "maxwidth=160&maxheight160"
                        + "&key=AIzaSyC6MCLYb9bebRfiG1vLHxIrO7Bmqe4F3qQ";
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public String getPhotoRef() {
        return this.photoRef;
    }

    public void setGeometry(Element geometry) {
        this.geometry = geometry;
    }

    public void addType(String newType) {
        if ( type == null){
            type = new ArrayList<>();
        }
        if (!type.contains(newType))
            this.type.add(newType);
    }

    public LatLng getLocation() {
        Element location = geometry.getChild("location");
        double lat = Double.valueOf(location.getChild("lat").getValue());
        double lng = Double.valueOf(location.getChild("lng").getValue());
        LatLng locate = new LatLng(lat,lng);
        return  locate;
    }

    public boolean ContainType(String checkedType) {
            for (String hasType : this.type){
                if (checkedType.equals(hasType) )
                    return true;
            }
        return false;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void ShowMarker(BitmapDescriptor icon) {
        marker.setIcon(icon);
        marker.setVisible(true);
    }
    public void HideMarker(){
        marker.setVisible(false);
    }

    public Marker GetMarker() {
        return this.marker;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
