package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 * lots of info&code snippets from developer site:
 * https://developer.android.com/training/material/lists-cards.html
 */
import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private ArrayList<Image> imageArrayList;
    private Context context;
    int width;

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
    public PosterAdapter(Context context, ArrayList<Image> imageArrayList, int sWidth) {
        this.imageArrayList = imageArrayList;
        this.context = context;
        this.width = sWidth;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        int width = this.width;

        final ImageView imageView = (ImageView) holder.view.findViewById(R.id.recycler_poster_view);

        /*for(int i = 0; i < imageArrayList.size(); i++) {
            Image onePoster = imageArrayList.get(i);
            Picasso.with(context)
                    .load(String.valueOf(onePoster))
                    .noFade()
                    .fit()
                    .resize(width, (width * 2) / 3)
                    .centerCrop()
                    .into(imageView);
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    public ArrayList<Image> getImageArrayList() {
        return imageArrayList;
    }

    public void setImageArrayList(ArrayList<Image> imageArrayList) {
        this.imageArrayList = imageArrayList;
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