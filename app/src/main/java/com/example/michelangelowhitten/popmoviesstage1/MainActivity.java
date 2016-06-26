package com.example.michelangelowhitten.popmoviesstage1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String MTAG = MainActivity.class.getSimpleName();


    private FragmentManager fragManager = getFragmentManager();
    private RecyclerView recyclerView;
    private PosterAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    PopMoviesFragData data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(MTAG, "in start of MainActivity onCreate");

        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.container, new MainActivityFragment());
        fragTransaction.commit();

        Log.i(MTAG, "after transaction commit");
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

    public PopMoviesFragData getData() {
        return data;
    }
}

