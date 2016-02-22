package com.example.blacksnowz195.smarttrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blacksnowz195.smarttrip.Adapter.ReviewListView;
import com.example.blacksnowz195.smarttrip.ButtonBehaviours.ButtonBehaviours;
import com.example.blacksnowz195.smarttrip.GoogleUltil.CommandSearchResult;
import com.example.blacksnowz195.smarttrip.GoogleUltil.GoogleEntertainmentHelper;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PlaceDetail extends Activity implements CommandSearchResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_place_detail);
        Intent intent = getIntent();
        ((TextView)this.findViewById(R.id.askFav)).setText("Do You Want to Add " + intent.getStringExtra("Name") + " To Your Favorite List ?");

        GoogleEntertainmentHelper.getInstance().LoadSpecificPlace(intent.getStringExtra("PlaceID"), this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_detail, menu);
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
    public void ExecuteResult(String[] result) {

    }

    @Override
    public void ExecutePlaceDetailsResult(final String result) {
        Log.d("ButtonBehaviour","Start Thread");
        Thread thread = new Thread() {
            @Override
            public void run() {
                SAXBuilder saxBuilder = new SAXBuilder();
                Log.d("DetailsResult", result);
                Document doc = null;
                try {
                    doc = saxBuilder.build(new StringReader(result));
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
                element = element.getChild("result");
                ButtonBehaviours.OpenWebSite(element.getChild("url").getValue(), PlaceDetail.this);
            }
        };
        thread.run();

    }


}
