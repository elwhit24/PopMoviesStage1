package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.IsolatedContext;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String M_TAG = MainActivity.class.getSimpleName();
    private int width;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        this.width = getScreenWidth(context);

        if (savedInstanceState == null) {

            FragmentManager fragManager = getFragmentManager();
            FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.commit();
            fragTransaction.replace(R.id.container, new MainActivityFragment());

            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            if (mRecyclerView != null) {

            mRecyclerView.setHasFixedSize(true);
            }

            // use a grid layout manager
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);

            ArrayList<String> posterURL_ArrayList = new ArrayList<>();

            RecyclerView.Adapter mAdapter = new PosterAdapter(context, posterURL_ArrayList, getScreenWidth(context));
            mRecyclerView.setAdapter(mAdapter);

        } else {
            Bundle bundle = savedInstanceState.getBundle(M_TAG);
            super.onCreate(bundle);
            setContentView(R.layout.activity_main);
        }
            Bundle nBundle = new Bundle();
            nBundle.putAll(nBundle);
            Log.d(M_TAG, "after transaction commit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(M_TAG, "onCreateOptionsMenu done");

        return true;
    }

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(M_TAG, "onOptionsItemSelected is running!");

        /*int id = item.getItemId();

        Intent intent = new Intent();

        switch (id) {
            case R.string.movie_popularity_view :
                GridLayoutManager PopularityGridLayoutManager = new GridLayoutManager(this, 2);
                mRecyclerView.setLayoutManager(PopularityGridLayoutManager);
                startActivity(intent);

            case R.string.movie_ratings_view :
                Context c = getApplicationContext();

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                //mRecyclerView.setHasFixedSize(true);
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 20);
                mRecyclerView.setLayoutManager(mGridLayoutManager);

                ArrayList<String> posterURL_ArrayList = new ArrayList<>();

                RecyclerView.Adapter mAdapter = new PosterAdapter(c, posterURL_ArrayList, getScreenWidth(c));
                mRecyclerView.setAdapter(mAdapter);
        }*/

        return true;
    }

    public int getScreenWidth(Context context) {
        int screenWidth;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x / 2;

        return screenWidth;
    }
}