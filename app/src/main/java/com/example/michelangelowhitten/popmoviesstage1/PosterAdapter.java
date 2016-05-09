package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterAdapter extends ArrayAdapter<AndroidMovie> {

    private Context context;
    int layoutResourceId;
    ArrayList<AndroidMovie> popMovieArrayList;
    int movieCount;

    public PosterAdapter(Context context, int resource, ArrayList<AndroidMovie> popMovieArrayList) {
        super(context, resource, popMovieArrayList);
        this.context = context;
        this.layoutResourceId = resource;
        this.popMovieArrayList = popMovieArrayList;
    }

    public int getCount() {

        if (popMovieArrayList != null) {
            movieCount = popMovieArrayList.size();
        }
        return movieCount;
    }

    public long getItemId(int position) {
        return 0;
    }

    /*public View getView(ImageView view){
        return view;
    }*/

    // create new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        AndroidMovie movieItem = getItem(position);
        if (convertView == null) {

            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 400));
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {
            imageView = (ImageView) convertView;
        }

        /*Picasso.with(context).
                load(movieItem.getPosterImageUrl()).
                noFade().
                fit().
                centerCrop().
                into(imageView);*/

        Picasso.with(getContext())
                .load(movieItem.getPosterImageUrl())
                .into(imageView);
        return imageView;
    }
}