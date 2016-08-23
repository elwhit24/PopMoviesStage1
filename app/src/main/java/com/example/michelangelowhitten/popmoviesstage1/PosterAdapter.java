package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 * lots of info&code snippets from developer site:
 * https://developer.android.com/training/material/lists-cards.html
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.MyViewHolder> implements View.OnClickListener {

    private ArrayList<String> posterURL_ArrayList;
    private ArrayList<String> backdropURL_ArrayList;
    private ArrayList<String> favsPosterURLs;
    private Context context;
    private View convertView;
    private Boolean favorites;
    private int position;
    MoviesData data;
    ArrayList<AndroidMovie> moviesList;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PosterAdapter(ArrayList<String> posterURL_ArrayList) {

            this.posterURL_ArrayList = posterURL_ArrayList;

        /*this.data = data;
        this.posterURL_ArrayList = getPosterURL_ArrayList();
        this.backdropURL_ArrayList = getBackdropURL_ArrayList();
        this.context = context;
        this.favorites = false;*/
    }

    // Provide a reference to the views for each MoviesData item
    // Complex MoviesData items may need more than one view per item, and
    // you provide access to all the views for a MoviesData item in a view holder


    @Override
    public PosterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PosterAdapter.MyViewHolder holder, final int position) {

        String posterPath;

        for (int i = 0; i < getItemCount(); i++) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_main,
                        (ViewGroup) convertView);
            }
        posterPath = posterURL_ArrayList.get(position);

        RecyclerView posterView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
            posterView.setAdapter(this);
        Picasso.with(context)
                .load(posterPath)
                .fit()
                .into((Target) posterView);

            //posterView.setOnClickListener(new AdapterView.OnClickListener() {

        }
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

    @Override
    public void onClick(View view) {

            AndroidMovie androidMovie = moviesList.get((int) getItemId(position));
            Intent movieIntent = new Intent(context, DetailActivity.class);
            movieIntent.putExtra(Intent.EXTRA_TEXT, androidMovie);
            context.startActivity(movieIntent);

    };


    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

