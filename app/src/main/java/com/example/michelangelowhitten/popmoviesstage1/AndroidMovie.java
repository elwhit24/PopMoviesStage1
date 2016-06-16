package com.example.michelangelowhitten.popmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

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

    public AndroidMovie() {
    }

    private AndroidMovie(Parcel in) {
        this.movieId = in.readInt();
        this.movieTitle = in.readString();
        this.posterImageUrl = in.readString();
        this.backdropImageUrl = in.readString();
        this.releaseDate = in.readString();
        this.plotSynopsis = in.readString();
        this.voteAverage = in.readDouble();
    }

    public int getMovieId() {
        return this.movieId;
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

    public void setPosterImageUrl(String posterUrl) {
        this.posterImageUrl = posterUrl;
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
    }

    public void setBackdropImageUrl(String backdropUrl) {
        this.backdropImageUrl = backdropUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
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
