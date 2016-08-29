package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String MTAG = MainActivity.class.getSimpleName();
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
        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$>

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$<

        Log.d(MTAG, "toolbar create");

        FragmentManager fragManager = getFragmentManager();
        android.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.add(R.id.container, new MainActivityFragment());
        fragTransaction.commit();

       // PosterAdapter posterAdapter = new PosterAdapter(getApplicationContext());

        Log.d(MTAG, "after transaction commit");
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$>

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(MTAG, "in onCreateOptionsMenu after inflater done");

        return true;
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$<

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
        final int version = android.os.Build.VERSION.SDK_INT;
        final int width;
        final int height;
        if (version >= 13)
        {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        else
        {
            display = getWindowManager().getDefaultDisplay();
            width = display.getWidth();
            height = display.getHeight();
        }

        int numOfColumns;
        if (width < height) {
            // portrait mode
            numOfColumns = 2;
            if (width > 600) { // for tablet sw600
                numOfColumns = 3;
            }
        } else {
            // landscape mode
            numOfColumns = 3;
            if (width > 600) { // for tablet sw600
                numOfColumns = 3;
            }
        }
        return numOfColumns;
    }
}