package com.example.michelangelowhitten.popmoviesstage1;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Mike on 6/18/2016.
 */
public class MoviesData {

    private final String MY_API_KEY = "6b8fe412e3a3da14c6a1847deb895f09";
    private final String POP_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=" +
              "popularity.desc&api_key=" + MY_API_KEY;
    private final String POSTER_AND_BACKDROP_URL = "http://image.tmdb.org/t/p/w185/";
    private final String HI_RATED_URL = "http://api.themoviedb.org/3/discover/movie/?" +
              "certification_country=US&certification=R& sort_by=vote_average.desc&api_key=" + MY_API_KEY;
    private final String noFetch = "Not able to grab movie info from MovieDB.";
    private final String noInter = "No internet available at the moment";

    private ArrayList<String> posterImageUrls;
    private ArrayList<String> backdropImageUrls;
    private ArrayList<AndroidMovie> movieArray;
    private ArrayList<String> posterFavorites;
    private ArrayList<Image> imageArrayList;
    private PosterAdapter adapter;
    private String pref;
    private Boolean prefP;
    private Boolean prefH;
    private Boolean prefF;
    private Boolean internet;

    public String getMY_API_KEY() {
        return MY_API_KEY;
    }

    public String getPOP_URL() {
        return POP_URL;
    }

    public String getPOSTER_AND_BACKDROP_URL() {
        return POSTER_AND_BACKDROP_URL;
    }

    public String getHI_RATED_URL() {
        return HI_RATED_URL;
    }

    public ArrayList<String> getPosterImageUrls() {
        return posterImageUrls;
    }

    public void setPosterImageUrls(ArrayList<String> posterImageUrls) {
        this.posterImageUrls = posterImageUrls;
    }

    public ArrayList<String> getBackdropImageUrls() {
        return backdropImageUrls;
    }

    public void setBackdropImageUrls(ArrayList<String> backdropImageUrls) {
        this.backdropImageUrls = backdropImageUrls;
    }

    public ArrayList<AndroidMovie> getMovieArray() {
        return movieArray;
    }

    public void setMovieArray(ArrayList<AndroidMovie> movieArray) {
        this.movieArray = movieArray;
    }

    public ArrayList<String> getPosterFavorites() {
        return posterFavorites;
    }

    public void setPosterFavorites(ArrayList<String> posterFavorites) {
        this.posterFavorites = posterFavorites;
    }

    public ArrayList<Image> getImageArrayList() {
        return imageArrayList;
    }

    public void setImageArrayList(ArrayList<Image> imageArrayList) {
        this.imageArrayList = imageArrayList;
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public Boolean getPrefP() {
        return prefP;
    }

    public void setPrefP(Boolean prefP) {
        this.prefP = prefP;
    }

    public Boolean getPrefH() {
        return prefH;
    }

    public void setPrefH(Boolean prefH) {
        this.prefH = prefH;
    }

    public Boolean getPrefF() {
        return prefF;
    }

    public void setPrefF(Boolean prefF) {
        this.prefF = prefF;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }
}
