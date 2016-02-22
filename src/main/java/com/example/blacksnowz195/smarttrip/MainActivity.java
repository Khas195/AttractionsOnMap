package com.example.blacksnowz195.smarttrip;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.pdf.PdfDocument;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blacksnowz195.smarttrip.ButtonBehaviours.ButtonBehaviours;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.server.converter.ConverterWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity implements FacebookCallback<LoginResult> {
    CallbackManager mCallBackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_main);
        /*
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        mCallBackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallBackManager, this);
        */
        final EditText userCity = (EditText)findViewById(R.id.fieldAskCity);

        Button enterRegion = (Button)findViewById(R.id.enterRegion);
            enterRegion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String city = userCity.getText().toString();
                    if (city == userCity.getHint() || city.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please Enter Your City Name", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Intent message = new Intent();
                        message.putExtra("City", city);
                        ButtonBehaviours.SwitchActivity(MainActivity.this, CityEntertainment.class, message);
                    }
                }
            });
        Button checkFav = (Button)findViewById(R.id.checkFavorite);
        checkFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enter activity show list of favorite places
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onSuccess(LoginResult loginResult) {
        //User user = new User(loginResult.getAccessToken().getUserId());
        Toast.makeText(this, loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode,data);
    }
}
