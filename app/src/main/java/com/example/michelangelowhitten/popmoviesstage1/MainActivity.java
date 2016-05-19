package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentHostCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    PosterAdapter imageAdapter;

    MainActivityFragment fragMain;
    ArrayList<AndroidMovie> movieArrayList = new ArrayList<>(20);
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Log.d(TAG, "main onCreate successful");
        Log.d(TAG, "fragment Transaction.add fragment_main_layout.beginTransaction() good");
        Log.d(TAG, "MainActivity new MainActivityFragment() good");
        Log.d(TAG, "MainActivity setSupportActionBar() good");
        Log.d(TAG, "this is after setContentView in main good");*/

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        fragMain = new MainActivityFragment();
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.add(R.id.grid_view, fragMain, "Fragment");
        fragTransaction.addToBackStack("Fragment");
        fragTransaction.commit();

        Log.d(TAG, "after transaction commit");

        fragMain.onAttach(mContext);

        mContext = fragMain.getActivity();
        movieArrayList = fragMain.movieArray;
        imageAdapter = new PosterAdapter(fragMain.mContext, fragMain.position, fragMain.movieArray);

        System.out.println("movieArrayList test print: ");
        fragMain.imageAdapter = this.imageAdapter;
    }

    public PosterAdapter getImageAdapter() {
        return this.imageAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu done");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}