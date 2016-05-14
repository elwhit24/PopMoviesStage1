package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Log.d(TAG, "main onCreate successful");
        Log.d(TAG, "fragment Transaction.add fragment_main_layout.beginTransaction() good");
        Log.d(TAG, "MainActivity new MainActivityFragment() good");
        Log.d(TAG, "MainActivity setSupportActionBar() good");
        Log.d(TAG, "addToBackStack worked");
        Log.d(TAG, "this is after setContentView in main good");*/

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        MainActivityFragment fragMain = new MainActivityFragment();

        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.add(R.id.fragment_one, fragMain, "PopFragment");
        fragTransaction.addToBackStack("Fragment");
        fragTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu done");

        return true;
    }

    /*/*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}