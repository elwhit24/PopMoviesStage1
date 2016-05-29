package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 * lots of info&code snippets from developer site:
 * https://developer.android.com/training/material/lists-cards.html
 */
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private ArrayList<AndroidMovie> movieArrayList;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PosterAdapter(Context context, ArrayList<AndroidMovie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final ImageView imageView = (ImageView) holder.view.findViewById(R.id.movie_poster);

        for(int i = 0; i < movieArrayList.size(); i++) {
            Picasso.with(context).
                    load(movieArrayList.get(i).getPosterImageUrl()).
                    noFade().
                    fit().
                    centerCrop().
                    into(imageView);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public ArrayList<AndroidMovie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<AndroidMovie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }
}

/*Picasso.with(getContext())
        .load(movieItem.getPosterImageUrl())
        .into(imageView);*/



/*public ArrayList<AndroidMovie> getPopMovieArrayList() {
        return popMovieArrayList;
    }

    public void setPopMovieArrayList() {

    }*/