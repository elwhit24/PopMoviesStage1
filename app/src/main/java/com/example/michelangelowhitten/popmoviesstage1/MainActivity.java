package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String MTAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int numCol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MTAG, "in start of MainActivity onCreate");

        if (numOfColumnsForOrientation() == 2) {
            numCol = 2;
        }
        if (numOfColumnsForOrientation() == 3) {
            numCol = 3;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(MTAG, "toolbar create");

        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.container, new MainActivityFragment());
        fragTransaction.commit();

        Log.d(MTAG, "after transaction commit");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(MTAG, "in onCreateOptionsMenu after inflater done");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(MTAG, "in start of onOptionsItemSelected");

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            Log.d(MTAG, "onOptionsItemSelected, inside true condition of if");

            return true;
        }
        Log.d(MTAG, "in onOptionsItemSelected, if false");

        return super.onOptionsItemSelected(item);
    }

    //code provided by https://gist.github.com/geniushkg
    public int numOfColumnsForOrientation() {
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int numOfColoums = 0;
        if (width < height) {
            // portrait mode
            numOfColoums = 2;
            if (width > 600) { // for tablet sw600
                numOfColoums = 3;
            }
        } else {
            // landscape mode
            numOfColoums = 3;
            if (width > 600) { // for tablet sw600
                numOfColoums = 3;
            }
        }
        return numOfColoums;
    }
}