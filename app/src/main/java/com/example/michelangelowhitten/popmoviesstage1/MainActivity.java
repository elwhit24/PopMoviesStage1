package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String MTAG = MainActivity.class.getSimpleName();
    private static final String DETAILSFRAGMENT_TAG = "DFTAG";

    private FragmentManager fragManager = getFragmentManager();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int sWidth;
    Context context;
    ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MTAG, "in start of MainActivity onCreate");

        context = getApplicationContext();
        sWidth = getScreenWidth(context);
        setContentView(R.layout.activity_main);

        stringArrayList = new ArrayList<>(20);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.container, new MainActivityFragment());
        fragTransaction.commit();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new PosterAdapter(context, stringArrayList, this.sWidth);
        recyclerView.setLayoutManager((GridLayoutManager) layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        Log.i(MTAG, "after 2nd transaction commit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(MTAG, "in onCreateOptionsMenu after inflater done");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(MTAG, "in start of onOptionsItemSelected");

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            Log.i(MTAG, "onOptionsItemSelected, inside true condition of if");

            return true;
        }
        Log.i(MTAG, "in onOptionsItemSelected, if false");

        return super.onOptionsItemSelected(item);
    }
    public int getScreenWidth(Context context) {
        Log.i(MTAG, "in start of getScreenWidth method");

        int width;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x / 2;

        return width;
    }
}

/*
if (findViewById(R.id.details_layout) != null) {
        mTwoPane = true;
        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.details_layout, new DetailsFragment(), DETAILSFRAGMENT_TAG)
        .commit();
        }
        } else {
        mTwoPane = false;
        }*/
