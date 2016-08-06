package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 * lots of info&code snippets from developer site:
 * https://developer.android.com/training/material/lists-cards.html
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private ArrayList<String> posterURL_ArrayList;
    private ArrayList<String> backdropURL_ArrayList;
    private ArrayList<String> favsPosterURLs;
    private Context context;
    private View convertView;
    private Boolean favorites;
    private int position;
    MoviesData data;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PosterAdapter(Context context, MoviesData data) {

        this.data = data;
        this.posterURL_ArrayList = getPosterURL_ArrayList();
        this.backdropURL_ArrayList = getBackdropURL_ArrayList();
        this.context = context;
        this.favorites = false;
    }

    // Provide a reference to the views for each MoviesData item
    // Complex MoviesData items may need more than one view per item, and
    // you provide access to all the views for a MoviesData item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each MoviesData item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PosterAdapter.ViewHolder holder, int position) {


        String posterPath;

/*
        for (int i = 0; i < getItemCount(); i++) {
*/

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_main,
                        (ViewGroup) convertView);
            }
        posterPath = posterURL_ArrayList.get(position);

        ImageView posterView = (ImageView) convertView.findViewById(R.id.grid_view_main);
        Picasso.with(context)
                .load(posterPath)
                .fit()
                .into(posterView);
        /*}*/
    }

        @Override
    public int getItemCount() {
        return posterURL_ArrayList.size();
    }

    public ArrayList<String> getPosterURL_ArrayList() {
        return posterURL_ArrayList;
    }

    public void setPosterURL_ArrayList(ArrayList<String> posterURL_ArrayList) {
        this.posterURL_ArrayList = posterURL_ArrayList;
    }
    public ArrayList<String> getBackdropURL_ArrayList() {
        return backdropURL_ArrayList;
    }

    public void setBackdropURL_ArrayList(ArrayList<String> backdropURL_ArrayList) {
        this.backdropURL_ArrayList = backdropURL_ArrayList;
    }
}

