/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.sing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

    String selectedLanguage = "";
    String[] languageList = {"English", "Hindi", "Hinglish", "Telugu", "Tinglish"};

    public void redirectActivity(){
        if (selectedLanguage.equals("English")) {
            Intent intent = new Intent(getApplicationContext(), EnglishSongs.class);
            intent.putExtra("language", selectedLanguage);
            startActivity(intent);
        } else if (selectedLanguage.equals("Telugu")){
            Intent intent = new Intent(getApplicationContext(), TeluguSongs.class);
            intent.putExtra("language", selectedLanguage);
            startActivity(intent);
        } else if (selectedLanguage.equals("Hindi")){
            Intent intent = new Intent(getApplicationContext(), HindiSongs.class);
            intent.putExtra("language", selectedLanguage);
            startActivity(intent);
        } else if (selectedLanguage.equals("Hinglish")){
            Intent intent = new Intent(getApplicationContext(), HinglishSongs.class);
            intent.putExtra("language", selectedLanguage);
            startActivity(intent);
        } else if (selectedLanguage.equals("Tinglish")){
            Intent intent = new Intent(getApplicationContext(), TinglishSongs.class);
            intent.putExtra("language", selectedLanguage);
            startActivity(intent);
        }
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



      //Create anonymous user
      if(ParseUser.getCurrentUser() == null) {

          ParseAnonymousUtils.logIn(new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if (e == null) {
                      Log.i("Anonymous", "Login Successful");
                  } else {
                      Log.i("Anonymous", "Login Failed");
                  }
              }
          });
      }

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

    @Override
    protected void onStart() {
        super.onStart();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");

        builder.setItems(languageList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedLanguage = languageList[which];
                Log.i("Clicked", selectedLanguage);

                //Write to database
                ParseUser.getCurrentUser().put("language", selectedLanguage);
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        redirectActivity();
                    }
                });

            }
        });

        builder.setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}