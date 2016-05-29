package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fragManager = getFragmentManager();
            FragmentTransaction fragTransaction = fragManager.beginTransaction()
                    .replace(R.id.container, new MainActivityFragment());
            fragTransaction.commit();
            fragTransaction.replace(R.id.container, new MainActivityFragment());
        } else {
            Bundle bundle = savedInstanceState.getBundle(TAG);
            super.onCreate(bundle);
            setContentView(R.layout.activity_main);
        }
            /*Bundle nBundle = new Bundle();
            bundle.putAll(nBundle);*/
            Log.d(TAG, "after transaction commit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu done");

        return true;
    }

    //@Override
    public boolean onOptionsItemSelected(Menu menuItem) {

        if (menuItem == null) {
            Bundle args = new Bundle();
            args.putParcelable(DetailsFragment.DETAIL_MOVIE, (Parcelable) menuItem);

            Fragment dFrag = new Fragment();
            dFrag.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail, dFrag, DetailsFragment.D_TAG)
                    .commit();
        } else {
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        return true;  //super.onOptionsItemSelected(movieItem);
    }
}