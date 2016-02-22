package com.example.blacksnowz195.smarttrip.ButtonBehaviours;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;

import com.example.blacksnowz195.smarttrip.Attraction;
import com.example.blacksnowz195.smarttrip.AttractionManager;
import com.example.blacksnowz195.smarttrip.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;


/**
 * Created by BlackSnowZ195 on 01/08/2015.
 */
public class ButtonBehaviours {
    public static void SwitchActivity(Activity root, Class destination, Intent message) {
        Intent switchActivity = new Intent(root, destination);
        switchActivity.putExtras(message);
        root.startActivity(switchActivity);
    }


    public static void OnCheckedTypeChange(String checkedType, CheckBox type, List<CheckBox> listBox) {
        for ( CheckBox box : listBox){
            if ( box != type)
                box.setChecked(false);
        }
        int markerIcon = ChooseMarker(checkedType);
        List<Attraction> attractionList = AttractionManager.getInstance().GetAttractionList();
        for (Attraction attraction : attractionList) {
            if (!attraction.ContainType(checkedType)) {
                attraction.HideMarker();
            } else {
                attraction.ShowMarker(BitmapDescriptorFactory.fromResource(markerIcon));
            }
        }
    }

    private static int ChooseMarker(String checkedType) {
        if ( checkedType == "lodging"){
            return R.drawable.lodging;
        }
        else if (checkedType == "park"){
            return R.drawable.park;
        }
        else if (checkedType == "food"){
            return R.drawable.food;
        }
        else if (checkedType == "shopping_mall"){
            return R.drawable.shopping_mall;
        }
        else {
            return R.drawable.movie_theater;
        }
    }


    public static void OpenWebSite(String url, Activity activity) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }
}
