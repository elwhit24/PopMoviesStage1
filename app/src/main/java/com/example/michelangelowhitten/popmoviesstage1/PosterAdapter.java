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

        ImageView iconView = (ImageView) convertView.findViewById(R.id.poster_view);
        //iconView.setImageResource(Integer.parseInt(position);
        Picasso.with(context)
                .load(posterPath)
                .fit()
                .into(iconView);

        /*final ImageView imageView = (ImageView) holder.view.findViewById(R.id.recyclerView);

            Picasso.with(context)
                    .load(posterURL_ArrayList.get(position))
                    .noFade()
                    .fit()
                    //.resize(width, width)
                    .centerCrop()
                    .into(imageView);*/
    }


    /*public View getView(int position, View convertView, ViewGroup parent) {
        //Gets the Movie Posters object from the ArrayAdapter at the appropriate position.

         View v = convertView;
        String url;
        if (v == null) {
            v = mLayoutInflater.inflate(layoutId, parent, false);
        }
        ImageView imageView = (ImageView) v.findViewById(imageViewID);
        url = getItem(position);
        Picasso.with(context).load(url).into(imageView);
return v;

        //Adapters recycle views to AdapterViews.
        //If this is a new View object we're getting, then inflate the layout.
        //If not, this view already has the layout inflated from a previous call to getView,
        //and we modify the View widgets as usual.


        return convertView;
    }*/

    // Return the size of your dataset (invoked by the layout manager)
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

/*
if (item == null) {
        Bundle args = new Bundle();
        args.putParcelable(DetailsFragment.DETAIL_MOVIE, (Parcelable) item);

        Fragment dFrag = new Fragment();
        dFrag.setArguments(args);

        getSupportFragmentManager().beginTransaction()
        .replace(R.id.movie_detail, dFrag, DetailsFragment.D_TAG)
        .commit();
        } else {
        startActivity(new Intent(this, Settings.class));
        return true;
        }

        return true;  //super.onOptionsItemSelected(movieItem);

Picasso.with(getContext())
        .load(movieItem.getPosterImageUrl())
        .into(imageView);*/



/*public ArrayList<AndroidMovie> getPopMovieArrayList() {
        return popMovieArrayList;
    }

    public void setPopMovieArrayList() {

    }*/