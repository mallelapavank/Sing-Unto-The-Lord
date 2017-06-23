package com.parse.sing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TinglishSongs extends AppCompatActivity {

    String language = "";
    ListView songList;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<Integer> numberList = new ArrayList<Integer>();
    ArrayList<String> numAndSongList = new ArrayList<String>();
    ArrayList<String> songNumber = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinglish_songs);

        Intent intent = getIntent();
        language = intent.getStringExtra("language");
        setTitle(language);
        Toast.makeText(this, language + " language selected", Toast.LENGTH_SHORT).show();

        songList = (ListView) findViewById(R.id.tinglishList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, numAndSongList);
        numAndSongList.clear();
        numAndSongList.add("Loading song list..");
        songList.setAdapter(arrayAdapter);

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), FullSong.class);
                if(numAndSongList.size() > position) {
                    intent.putExtra("title", numAndSongList.get(position));
                    intent.putExtra("songNumber", songNumber.get(position));
                    intent.putExtra("language", language);
                    startActivity(intent);
                } else {
                    Log.i("List", "not found");
                }
            }
        });

        //Read from database
        ParseQuery<ParseObject> query = ParseQuery.getQuery(language);
        //query.whereEqualTo("language", language);
        //num, title, content, language
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    numAndSongList.clear();
                    for (ParseObject object : objects) {
                        songNumber.add(object.getString("title"));
                        Log.i("Number", String.valueOf((object.getInt("number"))));
                        Log.i("Title", (object.getString("title")));
                        numAndSongList.add(object.getInt("number") + ". " + object.getString("title"));
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });


    }
}
