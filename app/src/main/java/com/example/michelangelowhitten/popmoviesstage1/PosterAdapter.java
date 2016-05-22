package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class PosterAdapter extends ArrayAdapter<AndroidMovie> {

    public Context context;
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

    // create new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView = (ImageView) inflater.inflate(R.layout.movie_item, parent, false);
            imageView.setLayoutParams(new GridView.LayoutParams(50, 60));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context)
                .load(popMovieArrayList.get(position).getPosterImageUrl())
                .into(imageView);

        return imageView;
    }


}

/*for(int i = 0; i <= 20; i++) {

        }*/
/*Picasso.with(getContext())
        .load(movieItem.getPosterImageUrl())
        .into(imageView);*/

/*Picasso.with(context).
                load(movieItem.getPosterImageUrl()).
                noFade().
                fit().
                centerCrop().
                into(imageView);*/