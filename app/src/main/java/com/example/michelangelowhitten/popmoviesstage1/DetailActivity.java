package com.example.michelangelowhitten.popmoviesstage1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Mike on 6/12/2016.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";

    DetailsFragment detailsFragment;

    public DetailActivity() {
        this.detailsFragment = new DetailsFragment();
    }
    /*public DetailActivity(DetailsFragment detailsFragment) {
        this.detailsFragment = detailsFragment;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}