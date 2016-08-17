package com.example.michelangelowhitten.popmoviesstage1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/*created by Michelangelo Whitten over a period of many months of learning*/

public class MainActivityFragment extends Fragment {
    private final String MAF_TAG = MainActivityFragment.class.getSimpleName();

   /* private final String MY_API_KEY = "6b8fe412e3a3da14c6a1847deb895f09";

    private final String POP_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=" +
            "popularity.desc&api_key=" + MY_API_KEY;
    private final String POSTER_AND_BACKDROP_URL = "http://image.tmdb.org/t/p/w185/";
    private final String HI_RATED_URL = "http://api.themoviedb.org/3/discover/movie/?" +
            "certification_country=US&certification=R& sort_by=vote_average.desc&api_key="
            + MY_API_KEY;*/
    private RecyclerView recyclerView;

    ArrayList<String> posterImageUrls;
    ArrayList<String> backdropImageUrls;
    PosterAdapter imageAdapter;
    ImageView settingsView;
    String noFetch = "Not able to grab movie info from MovieDB.";
    String noInter = "No internet available at the moment.";
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

    GridLayoutManager mGridLayoutManager;
    MoviesData appData;
    Context mContext;


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public MainActivityFragment() {

        this.mContext = getActivity();
        this.posterImageUrls = new ArrayList<>(20);
        this.backdropImageUrls = new ArrayList<>(20);
        this.internet = false;
        this.appData = new MoviesData();
//        mRecyclerView = new RecyclerView(mContext);

        //this.mRecyclerView = new RecyclerView(context);
        //this.gridView = (GridView) mRecyclerView.findViewById(R.id.grid_view_main);
        //this.mRecyclerView = new RecyclerView();

        //Log.d(MAF_TAG, "TEST...  MAINACTIVITY HAS SCREEN OF WIDTH: " + this.width);
    }

    public MoviesData getAppData() {
        return appData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_main);

        setHasOptionsMenu(true);

        getPopularMovies();
        imageAdapter = new PosterAdapter(movieArray);

        //initializeRecyclerView();
        //appData.setAdapter(imageAdapter);
        //mRecyclerView.setAdapter(appData.getAdapter());


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MAF_TAG, "MainActivityFragment onCreateView() started");

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        View layout = inflater.inflate(R.layout.fragment_main, container);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        //GridLayout gridLayout = (GridLayout) rootView.findViewById((R.id.container_layout));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 20));
        //recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidMovie androidMovie = movieArray.get(getId());
                Intent movieIntent = new Intent(context, DetailActivity.class);
                movieIntent.putExtra(Intent.EXTRA_TEXT, androidMovie);
                startActivity(movieIntent);
            }
        });
        Log.d(MAF_TAG, "MainActivityFragment onCreateView() good, after strictMode");  //DO NOT START WITHOUT ME

        return layout;
    }

    @Override
    public void onStart() {

        Log.d(MAF_TAG, "onStart()...at the start");

        super.onStart();

        shared_pref = PreferenceManager.getDefaultSharedPreferences(context);
        p = new PreferenceChangeListener();
        shared_pref.registerOnSharedPreferenceChangeListener(p);

        Log.d(MAF_TAG, "super.onStart() ran");
    }

    @Override
    public void onResume() {
        super.onResume();

       // appData.setRecyclerView(mRecyclerView);


        Log.d(MAF_TAG, "super.onResume() ran");
    }

    public void getPopularMovies() {

        Log.d(MAF_TAG, "inside at start of getPopularMovies() ----------before Fetch Movies task");

        fetchMovie = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);
        internet = isNetworkAvailable();
        if(internet)
            fetchMovie.execute();
        else
        {
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getActivity(), noInter, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        Log.d(MAF_TAG, "get pop, after fetchmovie instantiation");
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... params) {

            Log.d(LOG_TAG, "doInBackground started");

            JSONObject popularMoviesJson = new JSONObject();
            JSONObject highestRatedMoviesJson = new JSONObject();
            String finalJsonString;

            Log.d(LOG_TAG, "after 3 instants in doInBackground made");

            jReader = new JsonReader();

            Log.d(LOG_TAG, "before sort comparison ----------in Fetch Movies task");

            Log.d(LOG_TAG, "pref is initially " + pref);
            pref = "popular";
            Log.d(LOG_TAG, "pref is now " + pref);
            Log.d(LOG_TAG, "NEXT UP");
            Log.d(LOG_TAG, "popularMoviesJson is " + popularMoviesJson);
            Log.d(LOG_TAG, "popularMoviesJson should be null!!");

            try {
                popularMoviesJson = jReader.JsonRead(appData.getPOP_URL());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            //Log.d(LOG_TAG, "popularMoviesJson is " + popularMoviesJson);

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
                    highestRatedMoviesJson = jReader.JsonRead(appData.getHI_RATED_URL());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(LOG_TAG + "highestRatedMoviesJson is " + highestRatedMoviesJson);

                if (highestRatedMoviesJson != null) {

                    finalJsonString = highestRatedMoviesJson.toString();
                    System.out.println(LOG_TAG + "highest rated-finalJsonString is " + finalJsonString);

                    try {
                movieArray = this.getMoviesArray(finalJsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

                    return finalJsonString;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            Log.d(MAF_TAG, "onPostExecute() running");

           // View rootView = inflater.inflate(R.layout.fragment_main, container, false);
           // mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
           /* mGridLayoutManager = new GridLayoutManager(mContext, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            imageAdapter = new PosterAdapter(context, appData);
            mRecyclerView.setAdapter(imageAdapter);
            mRecyclerView.setHasFixedSize(true);

            appData.setRecyclerView(mRecyclerView);*/

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

                    Log.d("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                    Log.d("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());
                    posterImageUrls.add(aMovie.getPosterImageUrl());
                    backdropImageUrls.add(aMovie.getBackdropImageUrl());
                    aMovie.setPosterImageUrl(appData.getPOSTER_AND_BACKDROP_URL() + movieObject.getString(posterPath));
                    aMovie.setBackdropImageUrl(appData.getPOSTER_AND_BACKDROP_URL() + movieObject.getString(backdropPath));
                    aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                    aMovie.setReleaseDate(movieObject.getString(releaseDate));
                    aMovie.setId(movieObject.getInt(movieId));
                    aMovie.setMovieName(movieObject.getString(movieTitle));
                    /*System.out.println("posterPath is now " + movieObject.getString(posterPath));

                    System.out.println("backdropPath is now " + movieObject.getString(backdropPath));

                    System.out.println("Posters are at " + posterImageUrls);
                    System.out.println("Backdrops are at " + backdropImageUrls);*/


                    //aMovie.setRating(movieObject.getDouble(voteAverage));
                }

            return movieArray;
        }
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

    public class PreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            onPrefStart();
        }

        public void onPrefStart() {

            shared_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            p = new PreferenceChangeListener();
            shared_pref.registerOnSharedPreferenceChangeListener(p);

            if (shared_pref.getString("Sort By:", "popularity").equals("popularity")) {
                getActivity().setTitle("Most Popular Movies");
                appData.setPrefP(true);
                appData.setPrefH(false);
            } else if (shared_pref.getString("Sort By:", "rating").equals("rating")) {
                getActivity().setTitle("Highest Rated Movies");
                appData.setPrefP(false);
                appData.setPrefF(false);
            } else if (shared_pref.getString("Sort By:", "favorites").equals("favorites")) {
                getActivity().setTitle("Favorite Movies");
                appData.setPrefP(false);
                appData.setPrefF(true);
            }

            TextView favTextView = new TextView(getActivity());
            GridLayout preferencesLayout = (GridLayout) getActivity().findViewById(R.id.container_layout);
            if (appData.getPrefF()) {

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

                /*if (appData.getPosterFavorites() != null && getActivity() != null) {
                    mRecyclerView.setHasFixedSize(true);
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    RecyclerView.Adapter mAdapter = imageAdapter;
                    mRecyclerView.setAdapter(mAdapter);

                    Log.d(MAF_TAG, "$$$$$$$$$$$ onPostExecute() after Thissss%%%%%%");

                    LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);
                }
            } else {
                mRecyclerView.setVisibility(GridView.VISIBLE);
                preferencesLayout.removeView(favTextView);*/

                if (isNetworkAvailable()) {
                    appData.setInternet(true);
                    getPopularMovies();

                } else {
                    TextView noInternetText = new TextView(getActivity());
                    appData.setInternet(false);
                    noInternetText.setText(noInter);
                    if (preferencesLayout.getChildCount() == 1) {
                        preferencesLayout.addView(noInternetText);
                    }
                    //mRecyclerView.setVisibility(GridView.VISIBLE);
                }
            }
        }
    }

    public void setData() {
        appData.setPosterImageUrls(posterImageUrls);
        appData.setBackdropImageUrls(backdropImageUrls);
        appData.setImageArrayList(imageArrayList);
        appData.setPosterFavorites(posterFavs);
        appData.setPrefP(true);
        appData.setPrefH(false);
        appData.setPrefF(false);
        appData.setInternet(false);
        appData.setMovieArray(movieArray);

        imageAdapter.setPosterURL_ArrayList(posterImageUrls);
        imageAdapter.setBackdropURL_ArrayList(backdropImageUrls);

    }
}