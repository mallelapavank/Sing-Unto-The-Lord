package com.parse.sing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class FullSong extends AppCompatActivity {

    TextView songTextView;
    String song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_song);

        songTextView = (TextView) findViewById(R.id.songTextView);
        songTextView.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String songNumber = intent.getStringExtra("songNumber");
        String language = intent.getStringExtra("language");
        setTitle(title);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(language);
        query.whereEqualTo("title", songNumber);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object : objects) {
                    song = object.getString("content");
                    Log.i("Content", song);
                    songTextView.setText(song);
                }
            }
        });





    }
}
