package com.example.michelangelowhitten.popmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mike on 5/5/2016.
 */
public class AndroidMovie implements Parcelable {

    private static final String TAG = "AndroidMovie";

    private String movieTitle,posterImageUrl,backdropImageUrl,plotSynopsis,releaseDate;
    private Double voteAverage;
    private int movieId;

    public final Parcelable.Creator<AndroidMovie> CREATOR = new Parcelable.Creator<AndroidMovie>() {

        @Override
        public AndroidMovie createFromParcel(Parcel parcel) {
            return new AndroidMovie(parcel);
        }

        @Override
        public AndroidMovie[] newArray(int size) {
            return new AndroidMovie[size];
        }
    };

    public AndroidMovie (int item_id, String item_poster_path, String item_backdrop_path, String item_overview,
                         String item_release_date, String item_title,
                         double item_vote_average) {
        movieId = item_id;
        posterImageUrl = item_poster_path;
        plotSynopsis = item_overview;
        releaseDate = item_release_date;
        movieTitle = item_title;
        voteAverage = item_vote_average;
        backdropImageUrl = item_backdrop_path;

    }

    private AndroidMovie(Parcel in) {
        movieId = in.readInt();
        movieTitle = in.readString();
        posterImageUrl = in.readString();
        backdropImageUrl = in.readString();
        releaseDate = in.readString();
        plotSynopsis = in.readString();
        voteAverage = in.readDouble();
    }

    public AndroidMovie() {

    }

    public int getMovieId() {
        return movieId;
    }

    public void setId(int id) {
        this.movieId = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieName(String movieName) {
        this.movieTitle = movieName;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterImageUrl = posterUrl;
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }
    public void setPlotSynopsis(String overView) {
        this.plotSynopsis = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
    public void setRating(double rating) {
        this.voteAverage = rating;
    }

   // @Override
    public int describeContents() {
        return 0;
    }

   // @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(movieTitle);
        dest.writeString(posterImageUrl);
        dest.writeString(backdropImageUrl);
        dest.writeString(plotSynopsis);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }
}
