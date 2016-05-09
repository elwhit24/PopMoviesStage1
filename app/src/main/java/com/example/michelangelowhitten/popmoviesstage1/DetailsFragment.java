package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.NumberFormat;

/**
 * Created by Mike on 5/5/2016.
 */
public class DetailsFragment extends Fragment {

    /*final String LOG_TAG = DetailsFragment.class.getSimpleName();
    private static final String TAG = "MovieDetailsFrag";*/

    public DetailsFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

            AndroidMovie movieItem = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            TextView movieName = (TextView) rootView.findViewById(R.id.movieName);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.movieThumb);
            TextView synopsis = (TextView) rootView.findViewById(R.id.overview);
            TextView rating = (TextView) rootView.findViewById(R.id.rating);
            TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);

            movieName.setText(movieItem.getMovieTitle());
            Picasso.with(getActivity()).load(movieItem.getBackdropImageUrl()).into(imageView);
            synopsis.setText(movieItem.getPlotSynopsis());
            String stringRating = NumberFormat.getInstance().format(movieItem.getVoteAverage());
            rating.setText(stringRating);
            String stringRelDate = NumberFormat.getInstance().format(movieItem.getReleaseDate());
            releaseDate.setText(stringRelDate);

        }

        return rootView;
    }

    public class DetailActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.fragment_detail);
            /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);*/
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }
}

