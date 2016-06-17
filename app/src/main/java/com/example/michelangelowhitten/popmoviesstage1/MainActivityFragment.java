package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

/*created by Michelangelo Whitten*/

public class MainActivityFragment extends Fragment {

    private final String MY_API_KEY = "";

    private final String MAF_TAG = MainActivityFragment.class.getSimpleName();
    private final String POP_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=" +
            "popularity.desc&api_key=" + MY_API_KEY;
    private final String POSTER_AND_BACKDROP_URL = "http://image.tmdb.org/t/p/w185/";
    private final String HI_RATED_URL = "http://api.themoviedb.org/3/discover/movie/?" +
            "certification_country=US&certification=R& sort_by=vote_average.desc&api_key="
            + MY_API_KEY;

    ArrayList<String> posterImageUrls;
    ArrayList<String> backdropImageUrls;
    Context mContext;
    PosterAdapter imageAdapter;
    ImageView settingsView;
    String noFetch = "Not able to grab movie info from MovieDB.";
    String noInter = "No internet available at the moment";
    JsonReader jReader;
    ArrayList<AndroidMovie> movieArray;
    ArrayList<String> posterFavs;
    String pref;
    Boolean prefP;
    Boolean prefH;
    Boolean prefF;
    Boolean internet;
    FetchMovieTask fetchMovie;
    SharedPreferences shared_pref;
    PreferenceChangeListener p;
    ArrayList<Image> imageArrayList;
    int width;
    Context context;
    RecyclerView mRecyclerView;

    public MainActivityFragment() {

        this.context = this.getActivity();
        this.posterImageUrls = new ArrayList<>(20);
        this.backdropImageUrls = new ArrayList<>(20);
        this.imageAdapter = new PosterAdapter(this.context, this.width);
        this.internet = false;

        Log.d(MAF_TAG, "TEST...  MAINACTIVITY HAS SCREEN OF WIDTH: " + this.width);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getPopularMovies();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MAF_TAG, "MainActivityFragment onCreateView() started");

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/
        //inflater = getLayoutInflater();
        //inflater.inflate(R.layout.fragment_main, mRecyclerView);

        Log.d(MAF_TAG, "MainActivityFragment onCreateView() good, after strictMode");  //DO NOT START WITHOUT ME

        return mRecyclerView;
    }

    @Override
    public void onStart() {

        Log.d(MAF_TAG, "onStart()...at the start");

        super.onStart();

        /*shared_pref = PreferenceManager.getDefaultSharedPreferences(context);
        p = new PreferenceChangeListener();
        shared_pref.registerOnSharedPreferenceChangeListener(p);*/

        Log.i(MAF_TAG, "super.onStart() ran");

    }

    @Override
    public void onResume() {
        super.onResume();



        Log.i(MAF_TAG, "super.onResume() ran");
    }

    public void getPopularMovies() {

        Log.d(MAF_TAG, "inside at start of getPopularMovies() ----------before Fetch Movies task");

        fetchMovie = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);
        internet = isNetworkAvailable();
        if (internet)
            fetchMovie.execute();
        else {
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getActivity(), noInter, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        /*if(isNetworkAvailable()) {
            fetchMovie = new FetchMovieTask();
        } else Log.e("No internet!", "there is no internet available to pull json");
*/
        Log.d(MAF_TAG, "get pop, after fetchmovie instantiation");
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            Log.i(MAF_TAG, "doInBackground started");

            JSONObject popularMoviesJson = new JSONObject();
            JSONObject highestRatedMoviesJson = new JSONObject();
            String finalJsonString;

            Log.i(MAF_TAG, "after 3 instants in doInBackground made");

            jReader = new JsonReader();

            Log.i(MAF_TAG, "before sort comparison ----------in Fetch Movies task");

            Log.i(MAF_TAG, "pref is initially " + pref);
            pref = "popular";
            Log.i(MAF_TAG, "pref is now " + pref);
            Log.i(MAF_TAG, "NEXT UP");
            //Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);
            Log.i(MAF_TAG, "popularMoviesJson should be null!!");

            try {
                popularMoviesJson = jReader.JsonRead(POP_URL);
                pref = "Popularity";

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            //Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);

            if (popularMoviesJson != null) {
                finalJsonString = popularMoviesJson.toString();
                //System.out.println(MAF_TAG + "popular-finalJsonString is " + finalJsonString);
                try {
                    movieArray = this.getMoviesArray(finalJsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return finalJsonString;

            } else if (pref.equals("highest rated")) {
                try {
                    highestRatedMoviesJson = jReader.JsonRead(HI_RATED_URL);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                //System.out.println(MAF_TAG + "highestRatedMoviesJson is " + highestRatedMoviesJson);

                if (highestRatedMoviesJson != null) {

                    finalJsonString = highestRatedMoviesJson.toString();
                    //System.out.println(MAF_TAG + "highest rated-finalJsonString is " + finalJsonString);

                    try {
                        //Log.i(MAF_TAG, "this is where we are stopping 1");
                        movieArray = this.getMoviesArray(finalJsonString);
                        //Log.i(MAF_TAG, "this is where we are stopping 2");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return finalJsonString;
                }
            }

            Log.i(MAF_TAG, "LET US TEST THIS ONE OUT.  MAINACTIVITY HAS SCREEN OF WIDTH: " +
                    width);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(MAF_TAG, "onPostExecute() running");
            super.onPostExecute(result);

            //mRecyclerView.getAdapter();
            //GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            //mGridLayoutManager.addView(mRecyclerView);

            //mRecyclerView.setLayoutManager(mGridLayoutManager);


            /*if (result != null && imageAdapter != null) {
                imageAdapter = new PosterAdapter(getActivity(), width);
            }*/

        }

        public ArrayList<AndroidMovie> getMoviesArray(String movieJsonStr)
                throws JSONException {

            // JSON Keys

            final String theJSONMainResult = "results";
            final String posterPath = "poster_path";
            final String movieOverview = "overview";
            final String releaseDate = "release_date";
            final String movieId = "id";
            final String movieTitle = "original_title";
            final String backdropPath = "backdrop_path";
            final String voteAverage = "vote_average";

            //if(!(movieJsonStr== null)) {

            JSONObject movieJsonObject = new JSONObject(movieJsonStr);
            JSONArray movieJsonArray = movieJsonObject.getJSONArray("results");
            AndroidMovie aMovie = new AndroidMovie();
            for (int i = 0; i < movieJsonArray.length(); i++) {

                JSONObject movieObject = movieJsonArray.getJSONObject(i);


                aMovie.setPosterImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(posterPath));
                aMovie.setBackdropImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(backdropPath));
                aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                aMovie.setReleaseDate(movieObject.getString(releaseDate));
                aMovie.setId(movieObject.getInt(movieId));
                aMovie.setMovieName(movieObject.getString(movieTitle));
                posterImageUrls.add(aMovie.getPosterImageUrl());
                backdropImageUrls.add(aMovie.getBackdropImageUrl());

                //imageAdapter.onCreateViewHolder(mRecyclerView, aMovie.getMovieId());
                //imageAdapter = (PosterAdapter) mRecyclerView.getAdapter();

                //System.out.println("Poster image file is now " + movieObject.getString(posterPath));
                //System.out.println("Backdrop image file is now " + movieObject.getString(backdropPath));

                //System.out.println("Posters are at " + posterImageUrls);
                //System.out.println("Backdrops are at " + backdropImageUrls);

                Log.v("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                //Log.v("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());
                System.out.print("movieArray is " + movieArray);
            }

            imageAdapter.setPosterURL_ArrayList(posterImageUrls);
            imageAdapter.setBackdropURL_ArrayList(backdropImageUrls);

            return movieArray;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public class PreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            onPrefStart();
        }

        public void onPrefStart() {

            shared_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            p = new PreferenceChangeListener();
            shared_pref.registerOnSharedPreferenceChangeListener(p);

            if (shared_pref.getString("sortby", "popularity").equals("popularity")) {
                getActivity().setTitle("Most Popular Movies");
                prefP = true;
                prefH = false;
            } else if (shared_pref.getString("sortby", "rating").equals("rating")) {
                getActivity().setTitle("Highest Rated Movies");
                prefP = false;
                prefF = false;
            } else if (shared_pref.getString("sortby", "favorites").equals("favorites")) {
                getActivity().setTitle("Favorite Movies");
                prefP = false;
                prefF = true;
            }

            TextView favTextView = new TextView(getActivity());
            GridLayout preferencesLayout = (GridLayout) getActivity().findViewById(R.id.grid_view_main);
            if (prefF) {

                if (posterFavs.size() == 0) {

                    favTextView.setText(R.string.no_favorites);
                    if (preferencesLayout.getChildCount() == 1) {
                        preferencesLayout.addView(favTextView);
                        settingsView.setVisibility(GridView.GONE);
                    }
                } else {
                    settingsView.setVisibility(GridView.VISIBLE);
                    preferencesLayout.removeView(favTextView);
                }

                if (posterFavs != null && getActivity() != null) {
                    mRecyclerView.setHasFixedSize(true);
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    RecyclerView.Adapter mAdapter = new PosterAdapter(context, width);
                    mRecyclerView.setAdapter(mAdapter);

                    Log.d(MAF_TAG, "$$$$$$$$$$$ onPostExecute() after Doggggg%%%%%%");

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);
                }
            } else {
                mRecyclerView.setVisibility(GridView.VISIBLE);
                preferencesLayout.removeView(favTextView);

                if (isNetworkAvailable()) {
                    getPopularMovies();

                } else {
                    TextView noInternetText = new TextView(getActivity());
                    noInternetText.setText(noInter);
                    if (preferencesLayout.getChildCount() == 1) {
                        preferencesLayout.addView(noInternetText);
                    }
                    mRecyclerView.setVisibility(GridView.VISIBLE);
                }
            }
        }
    }
}


