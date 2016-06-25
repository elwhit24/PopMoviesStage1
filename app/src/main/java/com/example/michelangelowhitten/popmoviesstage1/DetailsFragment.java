package com.example.michelangelowhitten.popmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.NumberFormat;

public class DetailsFragment extends Fragment {

    public static final String D_TAG = DetailsFragment.class.getSimpleName();
    static final String DETAIL_MOVIE = "Movie_Details_Fragment";
    private static final String DETAILSFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;
    public DetailsFragment(){
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTwoPane = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (rootView.findViewById(R.id.details_layout) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.details_layout, new DetailsFragment(), D_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

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


}

