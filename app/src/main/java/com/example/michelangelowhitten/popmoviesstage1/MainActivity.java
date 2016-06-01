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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();


        if (savedInstanceState == null) {

            FragmentManager fragManager = getFragmentManager();
            FragmentTransaction fragTransaction = fragManager.beginTransaction()
                    .replace(R.id.container, new MainActivityFragment());
            fragTransaction.commit();
            fragTransaction.replace(R.id.container, new MainActivityFragment());

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_poster_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            //if (mRecyclerView != null) {

            mRecyclerView.setHasFixedSize(true);
            //}

            // use a grid layout manager
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);

            ArrayList<Image> imageArrayList = new ArrayList<>();

            RecyclerView.Adapter mAdapter = new PosterAdapter(context, imageArrayList, getScreenWidth(context));
            mRecyclerView.setAdapter(mAdapter);


        } else {
            Bundle bundle = savedInstanceState.getBundle(M_TAG);
            super.onCreate(bundle);
            setContentView(R.layout.activity_main);
        }
            /*Bundle nBundle = new Bundle();
            bundle.putAll(nBundle);*/
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
        int id = item.getItemId();
        if (item == null) {
            Bundle args = new Bundle();
            args.putParcelable(DetailsFragment.DETAIL_MOVIE, (Parcelable) item);

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