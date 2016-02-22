package com.example.blacksnowz195.smarttrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.blacksnowz195.smarttrip.ButtonBehaviours.ButtonBehaviours;
import com.example.blacksnowz195.smarttrip.GoogleUltil.GoogleEntertainmentHelper;
import com.example.blacksnowz195.smarttrip.GoogleUltil.GoogleReadPhotoRef;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class CityEntertainment extends Activity implements  OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    String[] availableType = {"lodging", "food", "park", "shopping_mall", "movie_theater"};
    GoogleMap map;
    String city;
    List<CheckBox> listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        city = intent.getStringExtra("City");
        listType = new ArrayList<>();
        setContentView(R.layout.activity_city_entertainment);
        SetUpMap();
        SetUpCheckBox();


    }

    @Override
    public void onBackPressed() {
        GoogleEntertainmentHelper.getInstance().Clear();
        AttractionManager.getInstance().Clear();
        super.onBackPressed();
    }

    void SetUpCheckBox()
    {
        final CheckBox hotel = (CheckBox)findViewById(R.id.hotel);
        hotel.setChecked(false);
        hotel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!GoogleEntertainmentHelper.getInstance().IsDoneLoading())
                    return;
                if ( isChecked) {
                    for (CheckBox box : listType) {
                        if (box != hotel) {
                            box.setChecked(false);
                        }
                    }
                    ButtonBehaviours.OnCheckedTypeChange("lodging", hotel, listType);
                }
            }
        });
        final CheckBox Restaurant = (CheckBox)findViewById(R.id.restaurant);
        Restaurant.setChecked(false);
        Restaurant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!GoogleEntertainmentHelper.getInstance().IsDoneLoading())
                    return;

                if ( isChecked)
                    ButtonBehaviours.OnCheckedTypeChange("food", Restaurant, listType);
            }
        });
        final CheckBox Park = (CheckBox)findViewById(R.id.park);
        Park.setChecked(false);
        Park.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!GoogleEntertainmentHelper.getInstance().IsDoneLoading())
                    return;
                if ( isChecked)
                 ButtonBehaviours.OnCheckedTypeChange("park", Park, listType);
            }
        });
        final CheckBox Shop = (CheckBox)findViewById(R.id.shop);
        Shop.setChecked(false);
        Shop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!GoogleEntertainmentHelper.getInstance().IsDoneLoading())
                    return;
                if ( isChecked)
                    ButtonBehaviours.OnCheckedTypeChange("shopping_mall", Shop, listType);
            }
        });
        final CheckBox Theator = (CheckBox)findViewById(R.id.theator);
        Theator.setChecked(false);
        Theator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!GoogleEntertainmentHelper.getInstance().IsDoneLoading())
                    return;
                if ( isChecked)
                    ButtonBehaviours.OnCheckedTypeChange("movie_theater", Theator, listType);
            }
        });
        this.listType.add(hotel);
        this.listType.add(Restaurant);
        this.listType.add(Park);
        this.listType.add(Theator);
        this.listType.add(Shop);



    }
    private void SetUpMap() {
        if (this.map == null)
        {
            ((MapFragment)this.getFragmentManager().findFragmentById(R.id.gmap)).getMapAsync(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_entertainment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
   

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        try {
            GoogleEntertainmentHelper.getInstance().LoadPossibleEntertainment(city,  availableType, googleMap, this);
            UiSettings mapUI = map.getUiSettings();
            mapUI.setAllGesturesEnabled(true);
            mapUI.setCompassEnabled(true);
            mapUI.setZoomControlsEnabled(true);
            this.map.setInfoWindowAdapter(this);
            this.map.setOnInfoWindowClickListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        final View infoBox ;
        LayoutInflater inflater = LayoutInflater.from(this);
        infoBox = inflater.inflate(R.layout.info_box, null, false);
        final Attraction attraction = AttractionManager.getInstance().FindAttractionBasedOnMarker(marker);
        if ( attraction != null)
        {
            ((TextView)infoBox.findViewById(R.id.place_name)).setText(attraction.getName());
            ((TextView)infoBox.findViewById(R.id.place_address)).setText(attraction.getAddress());
            ((TextView)infoBox.findViewById(R.id.place_rating)).setText(attraction.getRating());
            GoogleReadPhotoRef googleReadPhotoRef = new GoogleReadPhotoRef();
            Object[] toPass = new Object[2];
            toPass[0] = attraction.getPhotoRef();
            toPass[1] = infoBox.findViewById(R.id.place_photo);
            googleReadPhotoRef.execute(toPass);

        }
        return infoBox;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Attraction attraction = AttractionManager.getInstance().FindAttractionBasedOnMarker(marker);
        if (attraction == null){
            Log.d("ClickMarker", "Attraction not found");
            return ;
        }
        Intent intent = new Intent();
        intent.putExtra("PlaceID", attraction.getAttractionID());
        intent.putExtra("Name", attraction.getName());
        ButtonBehaviours.SwitchActivity(this, PlaceDetail.class, intent);
    }
}
