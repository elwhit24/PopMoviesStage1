package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.*;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private final String MY_API_KEY = "6b8fe412e3a3da14c6a1847deb895f09";

    private final String POP_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + MY_API_KEY;
    private final String HI_RATED_URL = "http://api.themoviedb.org/3/discover/movie/?certification_country=US&certification=R&" +
                                        "sort_by=vote_average.desc&api_key=" + MY_API_KEY;
    private final String POSTER_AND_BACKDROP_URL = "http://image.tmdb.org/t/p/w185/";


    ArrayList<AndroidMovie> movieArray;
    private String pref;
    LayoutInflater inflater;
    ViewGroup container;
    private View rootView;
    PosterAdapter imageAdapter;
    GridView gridview;
    String noFetch = "Not able to grab movie info from MovieDB.";
    String noInter = "No internet available at the moment";
    JsonReader jReader;
    ArrayList<String> posterArray;
    Boolean prefP;
    Boolean prefH;
    Boolean internet;
    Boolean prefF;
    FetchMovieTask fetchMovie;
    SharedPreferences shared_preferences;


    public MainActivityFragment() {
        movieArray = new ArrayList<>();
        //gridview = new GridView(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        shared_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //gridview = (GridView) rootView.findViewById(R.id.grid_view);

        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("Preference", "Most Popular");
        editor.apply();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("before fragment inflater ----------in Fragment");


        this.inflater = inflater;
        this.container = container;

        //imageAdapter = new PosterAdapter(getActivity(), R.layout.movie_item, movieArray);

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //imageAdapter = new PosterAdapter(getActivity(), R.layout.movie_item, movieArray);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                imageAdapter.getItem(position);
                AndroidMovie pm = imageAdapter.getItem(position);
                Intent movieIntent = new Intent(getActivity(), DetailsFragment.DetailActivity.class);
                movieIntent.putExtra(Intent.EXTRA_TEXT, pm);
                startActivity(movieIntent);
            }
        });
        return rootView;
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

    @Override
    public void onStart() {
        super.onStart();
        getPopularMovies();
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

    public void getPopularMovies() {

        System.out.println("after getPopularMovies() ----------before Fetch Movies task");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);
        internet = isNetworkAvailable();
        if(internet)
            fetchMovie.execute();
        else
        {
            System.out.println("THIS IS SCREWED UP, CONNECTION IS");
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getActivity(), " " + noInter + " ", duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        //final Context mContext = getContext();

        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            JSONObject popularMoviesJson = new JSONObject();
            JSONObject highestRatedMoviesJson = new JSONObject();
            String finalJsonString;

            jReader = new JsonReader();

            System.out.println("before sort comparison ----------in Fetch Movies task");

            if (pref == null) {
                pref = "popular";
                try {
                    popularMoviesJson = jReader.JsonRead(POP_URL);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (popularMoviesJson != null) {
                    finalJsonString = popularMoviesJson.toString();
                    return finalJsonString;
                }
            } else if (pref.equals("highest rated")) {
                try {
                    highestRatedMoviesJson = jReader.JsonRead(HI_RATED_URL);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (highestRatedMoviesJson != null) {
                    finalJsonString = highestRatedMoviesJson.toString();
                    try {
                        movieArray = this.getPopularMoviesArray(finalJsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return finalJsonString;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            if(string.length()==0)
            {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getContext(), noFetch, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                super.onPostExecute(string);
                imageAdapter = new PosterAdapter(getContext(), 0, movieArray);
                imageAdapter.getView(0, getView(), gridview);
            }
        }

        private ArrayList<AndroidMovie> getPopularMoviesArray(String movieJsonStr)
                throws JSONException {

            // JSON Keys
            final String posterPath = "poster_path";
            final String movieOverview = "overview";
            final String releaseDate = "release_date";
            final String movieId = "id";
            final String movieTitle = "original_title";
            final String backdropPath = "backdrop_path";
            final String voteAverage = "vote_average";

            movieArray.clear();

            if(!(movieJsonStr== null)) {

                JSONObject movieJsonObject = new JSONObject(movieJsonStr);
                JSONArray movieJsonArray = movieJsonObject.getJSONArray("results");

                for (int i = 0; i < movieJsonArray.length(); i++) {

                    JSONObject movieObject = movieJsonArray.getJSONObject(i);

                    AndroidMovie aMovie = new AndroidMovie();
                    aMovie.setPosterUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(posterPath));
                    Log.v("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                    aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                    aMovie.setReleaseDate(movieObject.getString(releaseDate));
                    aMovie.setId(movieObject.getInt(movieId));
                    aMovie.setMovieName(movieObject.getString(movieTitle));
                    aMovie.setPosterUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(backdropPath));
                    Log.v("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());
                    aMovie.setRating(movieObject.getDouble(voteAverage));

                    movieArray.add(aMovie);
                }
            }
            return movieArray;
        }
    }

    private class PreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            gridview.setAdapter(null);
            onPrefStart();
        }
    }

    public void onPrefStart() {

        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        PreferenceChangeListener listener = new PreferenceChangeListener();
        prefs.registerOnSharedPreferenceChangeListener(listener);

        if (prefs.getString("sortby", "popularity").equals("popularity")) {
            getActivity().setTitle("Most Popular Movies");
            prefP = true;
            prefH = false;
        } else if (prefs.getString("sortby", "rating").equals("rating")) {
            getActivity().setTitle("Highest Rated Movies");
            prefP = false;
            prefF = false;
        } else if (prefs.getString("sortby", "favorites").equals("favorites")) {
            getActivity().setTitle("Favorite Movies");
            prefP = false;
            prefF = true;
        }

        TextView favTextView = new TextView(getActivity());
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.fragment);
        if (prefF) {

            if (posterArray.size() == 0) {

                favTextView.setText(R.string.no_favorites);
                if (layout.getChildCount() == 1) {
                    layout.addView(favTextView);
                    gridview.setVisibility(GridView.GONE);
                }
            } else {
                gridview.setVisibility(GridView.VISIBLE);
                layout.removeView(favTextView);
            }

            if (posterArray != null && getActivity() != null) {
                PosterAdapter adapter = new PosterAdapter(getActivity(), imageAdapter.layoutResourceId, movieArray );
                gridview.setAdapter(adapter);
            }
        } else {
            gridview.setVisibility(GridView.VISIBLE);
            layout.removeView(favTextView);

            if (isNetworkAvailable()) {
                new FetchMovieTask().execute();
            } else {
                TextView noInternetText = new TextView(getActivity());
                noInternetText.setText(noInter);
                if (layout.getChildCount() == 1) {
                    layout.addView(noInternetText);
                }
                gridview.setVisibility(GridView.GONE);
            }
        }

    }
}


