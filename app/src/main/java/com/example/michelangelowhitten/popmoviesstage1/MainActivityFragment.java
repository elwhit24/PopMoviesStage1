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

/*created by Michelangelo Whitten over a period of many months of learning*/

public class MainActivityFragment extends Fragment {

    private final String MY_API_KEY = "6b8fe412e3a3da14c6a1847deb895f09";

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
    GridView gridView;

    public MainActivityFragment() {

        this.context = getActivity();
        this.posterImageUrls = new ArrayList<>(20);
        this.backdropImageUrls = new ArrayList<>(20);
        this.internet = false;
        this.imageAdapter = new PosterAdapter(context);
        //this.mRecyclerView = new RecyclerView(context);
        //this.gridView = (GridView) mRecyclerView.findViewById(R.id.grid_view_main);
        //this.mRecyclerView = new RecyclerView();

        //Log.d(MAF_TAG, "TEST...  MAINACTIVITY HAS SCREEN OF WIDTH: " + this.width);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_main);

        setHasOptionsMenu(true);

        getPopularMovies();
        //initializeRecyclerView();

        /*PopMoviesFragData data = new PopMoviesFragData(context, posterImageUrls,backdropImageUrls,
                movieArray,posterFavs, imageArrayList, imageAdapter, pref, prefP, prefH, prefF, internet);*/

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = prefs.getString("sort", null);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MAF_TAG, "MainActivityFragment onCreateView() started");



        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/
        //mRecyclerView.findViewById(R.id.recycler_view);
       //mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main, mRecyclerView);
        //mRecyclerView = (RecyclerView) mRecyclerView.findViewById(R.id.grid_view_main);

        //imageAdapter = (PosterAdapter) mRecyclerView.getAdapter();

        //GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
        //mRecyclerView.setHasFixedSize(true);
        /*
        RecyclerView.Adapter mAdapter = new PosterAdapter(context, width);
        mRecyclerView.setAdapter(mAdapter);*/
        //mRecyclerView = inflater.inflate(R.layout.movie_poster, container, false);
        //mRecyclerView = (RecyclerView) rootView.findViewById(R.id.grid_view_main);
       // mRecyclerView = (RecyclerView) rootView.findViewById(R.id.grid_view_main);

       // mRecyclerView.setLayoutManager(mGridLayoutManager);
       /*mRecyclerView.setAdapter(new PosterAdapter(context));

        mRecyclerView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidMovie androidMovie = movieArray.get(getId());
                Intent movieIntent = new Intent(getActivity(), DetailActivity.class);
                movieIntent.putExtra(Intent.EXTRA_TEXT, androidMovie);
                startActivity(movieIntent);
            }
        });
*/
/*
        Log.i(MAF_TAG, "MainActivityFragment onCreateView() good, after strictMode");  //DO NOT START WITHOUT ME
*/

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

    /*@Override
    public void onResume() {
        super.onResume();


        Log.i(MAF_TAG, "super.onResume() ran");
    }*/

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
            Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);
            Log.i(MAF_TAG, "popularMoviesJson should be null!!");

            try {
                popularMoviesJson = jReader.JsonRead(POP_URL);
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
                System.out.println(MAF_TAG + "highestRatedMoviesJson is " + highestRatedMoviesJson);

                if (highestRatedMoviesJson != null) {

                    finalJsonString = highestRatedMoviesJson.toString();
                    System.out.println(MAF_TAG + "highest rated-finalJsonString is " + finalJsonString);



                    return finalJsonString;
                }
            }

            Log.i(MAF_TAG, "LET US TEST THIS ONE OUT.  MAINACTIVITY HAS SCREEN OF WIDTH: " +
                    width);

            return null;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            Log.i(MAF_TAG, "onPostExecute() running");

            try {
                movieArray = this.getMoviesArray(jsonString);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            imageAdapter.setPosterURL_ArrayList(posterImageUrls);
            imageAdapter.setBackdropURL_ArrayList(backdropImageUrls);

            /*mRecyclerView.setAdapter(imageAdapter);

            mRecyclerView.setHasFixedSize(true);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);*/

            //return;
            /*if (results != null && imageAdapter != null) {

                imageAdapter.clear();
                for(AndroidMovie aMovie : results) {
                    imageAdapter.add(aMovie.getPosterImageUrl());
                }

            }*/

            /*GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            RecyclerView.Adapter mAdapter = new PosterAdapter(context, width);
            mRecyclerView.setAdapter(mAdapter);*/

            /*

            ?*Log.i(MAF_TAG, "$$$$$$$$$$$ onPostExecute() b4 %%%%%%<<<PICS>>>>");

            mRecyclerView = (RecyclerView) this.inflater.inflate(R.layout.fragment_main, mRecyclerView);

            Log.i(MAF_TAG, "$$$$$$$$$$$ onPostExecute() after %%%%%%<<<PICS>>>>");*/

            //Inflater inflater;

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

                Log.v("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                posterImageUrls.add(aMovie.getPosterImageUrl());
                backdropImageUrls.add(aMovie.getBackdropImageUrl());
                aMovie.setPosterImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(posterPath));
                aMovie.setBackdropImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(backdropPath));
                aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                aMovie.setReleaseDate(movieObject.getString(releaseDate));
                aMovie.setId(movieObject.getInt(movieId));
                aMovie.setMovieName(movieObject.getString(movieTitle));
                /*System.out.println("posterPath is now " + movieObject.getString(posterPath));

                System.out.println("backdropPath is now " + movieObject.getString(backdropPath));

                System.out.println("Posters are at " + posterImageUrls);
                System.out.println("Backdrops are at " + backdropImageUrls);*/
                Log.v("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());

                //aMovie.setRating(movieObject.getDouble(voteAverage));
            }
            //}


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
                    RecyclerView.Adapter mAdapter = new PosterAdapter(context);
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
                    mRecyclerView.setVisibility(GridView.VISIBLE);
                }
            }
        }
    }
}

/*public class FetchMovieTask extends AsyncTask<String, Void, String> {

        final String MAF_TAG = FetchMovieTask.class.getSimpleName();

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
                    Log.i(MAF_TAG, "popularMoviesJson is " + popularMoviesJson);
                    Log.i(MAF_TAG, "popularMoviesJson should be null!!");

                    try {
                        popularMoviesJson = jReader.JsonRead(POP_URL);
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
        protected void onPostExecute(ArrayList<AndroidMovie results>) {
            Log.i(MAF_TAG, "onPostExecute() running");

            mRecyclerView.setHasFixedSize(true);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            RecyclerView.Adapter mAdapter = new PosterAdapter(context, width);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

            return;
            *//*if (results != null && imageAdapter != null) {

                imageAdapter.clear();
                for(AndroidMovie aMovie : results) {
                    imageAdapter.add(aMovie.getPosterImageUrl());
                }

            }*//*

            *//*GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 20);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            RecyclerView.Adapter mAdapter = new PosterAdapter(context, width);
            mRecyclerView.setAdapter(mAdapter);*//*

            *//*

            ?*Log.i(MAF_TAG, "$$$$$$$$$$$ onPostExecute() b4 %%%%%%<<<PICS>>>>");

            mRecyclerView = (RecyclerView) this.inflater.inflate(R.layout.fragment_main, mRecyclerView);

            Log.i(MAF_TAG, "$$$$$$$$$$$ onPostExecute() after %%%%%%<<<PICS>>>>");*//*

            Inflater inflater;

            mRecyclerView = (RecyclerView) (R.layout.fragment_main, mRecyclerView);


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

                    Log.v("||||||", "the poster path: " + aMovie.getPosterImageUrl());
                    posterImageUrls.add(aMovie.getPosterImageUrl());
                    backdropImageUrls.add(aMovie.getBackdropImageUrl());
                    aMovie.setPosterImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(posterPath));
                    aMovie.setBackdropImageUrl(POSTER_AND_BACKDROP_URL + movieObject.getString(backdropPath));
                    aMovie.setPlotSynopsis(movieObject.getString(movieOverview));
                    aMovie.setReleaseDate(movieObject.getString(releaseDate));
                    aMovie.setId(movieObject.getInt(movieId));
                    aMovie.setMovieName(movieObject.getString(movieTitle));
                    System.out.println("posterPath is now " + movieObject.getString(posterPath));

                    System.out.println("backdropPath is now " + movieObject.getString(backdropPath));

                    System.out.println("Posters are at " + posterImageUrls);
                    System.out.println("Backdrops are at " + backdropImageUrls);
                    Log.v("||||||", "the backdrop path: " + aMovie.getBackdropImageUrl());

                    //aMovie.setRating(movieObject.getDouble(voteAverage));
                }
            //}




            imageAdapter.setPosterURL_ArrayList(posterImageUrls);
            imageAdapter.setBackdropURL_ArrayList(backdropImageUrls);
            return movieArray;
        }
    }*/

    /*public class JsonReader {

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
    }*/

    /*private void getFavorites(){

        Uri uri = PopularMovieContract.MovieEntry.CONTENT_URI;
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = null;

        try {

            cursor = resolver.query(uri, null, null, null, null);

            // clear movies
            movies.clear();

            if (cursor.moveToFirst()){
                do {
                    AndroidMovie movie = new AndroidMovie(cursor.getInt(1), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7));

                    movie.setReviews(cursor.getString(8));
                    movie.setMoviePreviews(cursor.getString(9));
                    movies.add(movie);
                } while (cursor.moveToNext());
            }

        } finally {

            if(cursor != null)
                cursor.close();

        }

    }*/


//List<ItemObject> rowListItem = getAllItemList();
//lLayout = new GridLayoutManager(MainActivity.this, 4);

/*
        RecyclerView recyclerView = (RecyclerView) cFragGridView.findViewById(R.id.poster_recycler_view);
        *//*rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);*//*

        PosterAdapter mainAdapter = new PosterAdapter(getActivity(), posterImageUrls);
        recyclerView.setAdapter(mainAdapter);*/