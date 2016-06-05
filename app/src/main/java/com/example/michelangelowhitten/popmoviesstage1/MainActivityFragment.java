package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/*created by Michelangelo Whitten over a period of many months of learning*/

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
    FetchMovieTask fetchMovie;
    SharedPreferences shared_pref;
    PreferenceChangeListener p;
    ArrayList<Image> imageArrayList;
    int width;
    Context context;
    RecyclerView mRecyclerView;

    public MainActivityFragment() {

        this.setArguments(Bundle.PARCELABLE_WRITE_RETURN_VALUE);
        this.setArguments(MainActivity.WINDOW_SERVICE.getClass().getModifiers());
        this.context = this.getActivity();
        this.imageArrayList = new ArrayList<>();
        Log.d(MAF_TAG, "TEST...  MAINACTIVITY HAS SCREEN OF WIDTH: " + width);
    }

    private int setArguments(int width) {
        this.width = width;

        Log.d(MAF_TAG, "TEST...  MAINACTIVITY SET this.width TO: " + width);

        return this.width;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.d(MAF_TAG, "after onCreate in fragment has executed...");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MAF_TAG, "MainActivityFragment onCreateView() started");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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

        getPopularMovies();

        Log.d(MAF_TAG, "after getPopularMovies() ran after super.onStart() ran");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(MAF_TAG, "super.onResume() ran");
    }

    public void getPopularMovies() {

        Log.d(MAF_TAG, "inside at start of getPopularMovies() ----------before Fetch Movies task");

        if(isNetworkAvailable()) {
            fetchMovie = new FetchMovieTask();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);

        Log.d(MAF_TAG, "get pop, after fetchmovie instantiation");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        final String FETCH_TAG = FetchMovieTask.class.getSimpleName();

            public FetchMovieTask() {
                Log.d(MAF_TAG, "Fetch Constructor ran");
            }

            protected String doInBackground(String... params) {

                Log.i(FETCH_TAG, "doInBackground started");

                JSONObject popularMoviesJson = new JSONObject();
                JSONObject highestRatedMoviesJson = new JSONObject();
                String finalJsonString;

                Log.i(FETCH_TAG, "after 3 instants in doInBackground made");

                jReader = new JsonReader();

                Log.i(MAF_TAG, "before sort comparison ----------in Fetch Movies task");

                if (pref == null) {

                    Log.i(MAF_TAG, "pref is " + pref);
                    pref = "popular";
                    Log.i(MAF_TAG, "pref is now " + pref);
                    Log.i(MAF_TAG, "NEXT UP");
                    Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);
                    Log.i(MAF_TAG, "popularMoviesJson should be null!!");
                }

                    try {
                        popularMoviesJson = jReader.JsonRead(POP_URL);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);

                    if (popularMoviesJson != null) {
                        finalJsonString = popularMoviesJson.toString();
                        System.out.println(MAF_TAG + "popular-finalJsonString is " + finalJsonString);
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
                        System.out.println(MAF_TAG + "highestRatedMoviesJson is " + highestRatedMoviesJson);

                        if (highestRatedMoviesJson != null) {

                        finalJsonString = highestRatedMoviesJson.toString();
                            System.out.println(MAF_TAG + "highest rated-finalJsonString is " + finalJsonString);

                        try {
                            movieArray = this.getMoviesArray(finalJsonString);

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
        protected void onPostExecute(String string) {
            Log.i(FETCH_TAG, "onPostExecute() running");

            LayoutInflater inflater;
            inflater = (LayoutInflater)mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            mRecyclerView.setHasFixedSize(true);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            RecyclerView.Adapter mAdapter = new PosterAdapter(context, posterImageUrls, width);
            mRecyclerView.setAdapter(mAdapter);

            Log.i(FETCH_TAG, "$$$$$$$$$$$ onPostExecute() b4 %%%%%%<<<PICS>>>>");

            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main, null);

            Log.i(FETCH_TAG, "$$$$$$$$$$$ onPostExecute() after %%%%%%<<<PICS>>>>");
        }

        public ArrayList<AndroidMovie> getMoviesArray(String movieJsonStr)
                throws JSONException {

            // JSON Keys
            final String posterPath = "poster_path";
            final String movieOverview = "overview";
            final String releaseDate = "release_date";
            final String movieId = "id";
            final String movieTitle = "original_title";
            final String backdropPath = "backdrop_path";
            final String voteAverage = "vote_average";

            if(!(movieJsonStr== null)) {

                JSONObject movieJsonObject = new JSONObject(movieJsonStr);
                JSONArray movieJsonArray = movieJsonObject.getJSONArray("results");

                for (int i = 0; i < movieJsonArray.length(); i++) {

                    JSONObject movieObject = movieJsonArray.getJSONObject(i);
                    AndroidMovie aMovie = new AndroidMovie();

                    aMovie.setPosterUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(posterPath));
                    Log.v("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                    posterImageUrls.add(aMovie.getPosterImageUrl());
                    aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                    aMovie.setReleaseDate(movieObject.getString(releaseDate));
                    aMovie.setId(movieObject.getInt(movieId));
                    aMovie.setMovieName(movieObject.getString(movieTitle));
                    aMovie.setBackdropImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(backdropPath));
                    Log.v("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());
                    backdropImageUrls.add(aMovie.getPosterImageUrl());
                    aMovie.setRating(movieObject.getDouble(voteAverage));
                }
            }
            return movieArray;
        }
    }

    public class JsonReader {

        JSONObject json;
        String jsonText;

        public JsonReader() {
            this.json = new JSONObject();
            this.jsonText = "";
        }

        public JSONObject JsonRead(String url) throws IOException, JSONException {
            try {
                return readJsonFromUrl(url);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return readJsonFromUrl(url);
        }

        public String readIt(Reader reader) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            int count;
            while ((count = reader.read()) != -1) {
                stringBuilder.append((char) count);
            }
            return stringBuilder.toString();
        }

        public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                this.jsonText = readIt(bufferedReader);
                this.json = new JSONObject(jsonText);
                return json;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (final IOException e) {
                        Log.e("error w/ retrieval", "error closing stream", e);
                    }
                }
            }
        }
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
            GridLayout preferencesLayout = (GridLayout) getActivity().findViewById(R.id.gridView);
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
                    RecyclerView.Adapter mAdapter = new PosterAdapter(context, posterFavs, width);
                    mRecyclerView.setAdapter(mAdapter);

                    Log.d(MAF_TAG, "$$$$$$$$$$$ onPostExecute() after Doggggg%%%%%%");

                    LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
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
                    mRecyclerView.setVisibility(GridView.GONE);
                }
            }
        }
    }
}

//List<ItemObject> rowListItem = getAllItemList();
//lLayout = new GridLayoutManager(MainActivity.this, 4);

/*
        RecyclerView recyclerView = (RecyclerView) cFragGridView.findViewById(R.id.poster_recycler_view);
        *//*rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);*//*

        PosterAdapter mainAdapter = new PosterAdapter(getActivity(), posterImageUrls);
        recyclerView.setAdapter(mainAdapter);*/