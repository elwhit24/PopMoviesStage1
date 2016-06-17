package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 * lots of info&code snippets from developer site:
 * https://developer.android.com/training/material/lists-cards.html
 */
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private ArrayList<String> posterURL_ArrayList;
    private ArrayList<String> backdropURL_ArrayList;
    private ArrayList<String> favsPosterURLs;
    private Context context;
    private int width;
    private View convertView;
    private Boolean favorites;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PosterAdapter(Context context, int screenWidth) {

        this.posterURL_ArrayList = new ArrayList<>();
        this.backdropURL_ArrayList = new ArrayList<>();
        this.context = context;
        this.width = screenWidth;
        this.favorites = false;
    }

    /*public PosterAdapter(Context context, ArrayList<String> favoritesPosterURLs, int screenWidth) {
        this.favsPosterURLs = favoritesPosterURLs;
        this.context = context;
        this.width = screenWidth;
    }*/

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

    // Create new views (invoked by the layout manager)
    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        int width = this.width;
        double height = width*3/2;
        String posterPath;

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.fragment_main, (ViewGroup) convertView);
        }

        posterPath = this.getPosterURL_ArrayList().get(position);

        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_view_main);
        Picasso.with(context)
                .load(posterPath)
                .fit()
                .into(iconView);

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

