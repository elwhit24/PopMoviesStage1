package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity onCreate() good");

        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity setContentView() good");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        Log.d(TAG, "MainActivity setSupportActionBar() good");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.d(TAG, "MainActivity fragmentManager.beginTransaction() good");

        MainActivityFragment fragment = new MainActivityFragment();

        Log.d(TAG, "MainActivity new MainActivityFragment() good");

        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();

        Log.d(TAG, "MainActivity fragmentTransaction.commit() good");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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