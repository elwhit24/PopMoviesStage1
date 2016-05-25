package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Collections;

import com.squareup.picasso.Picasso;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    private Context context;
    private ArrayList<String> imageUrls;
    int layoutResourceId;
    ArrayList<AndroidMovie> popMovieArrayList;
    int movieCount;
    ImageView poster;

    public PosterAdapter(Context context, ArrayList<String> imageUrls) {
        //super(context, R.layout.fragment_main, imageUrls);
        /*private final LayoutInflater inflater;*/

        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);

        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.movie_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        System.out.println("onBindViewHolder running!");

        AndroidMovie currentMovie = popMovieArrayList.get(position);
        System.out.println(currentMovie);

        viewHolder.poster.setImageResource(currentMovie.getMovieId());
        Picasso.with(context)
                .load(popMovieArrayList.get(position).getPosterImageUrl())
                .fit()
                .into(viewHolder.poster);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        int position = this.getAdapterPosition();

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidMovie movieItem = popMovieArrayList.get(position);
                    Intent movieIntent = new Intent(context, DetailsFragment.DetailActivity.class).
                            putExtra(Intent.EXTRA_TEXT, movieItem);
                    //(movieIntent);
                }
            });

            poster = (ImageView) view.findViewById(R.id.movie_poster);
        }
    }

    // create new ImageView for each item referenced by the Adapter
    /*public View getView(int position, View convertView, ViewGroup parent) {

        //RecyclerView.ViewHolder viewHolder;

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.fragment_main, parent, false);
           // viewHolder.s = (ImageView) convertView.findViewById(R.id.grid_view);

        }
        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageView = (ImageView) inflater.inflate(R.layout.fragment_main, parent, false);
        //imageView.setLayoutParams(new GridView.LayoutParams(50, 60));
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);

         *//*else {
            imageView = (ImageView) convertView;
        }*//*

        Picasso.with(context)
                .load(popMovieArrayList.get(position).getPosterImageUrl())
                .fit()
                .into((ImageView) convertView);

        return convertView;
    }*/

//    public PosterAdapter(Context context, int resource, ArrayList<AndroidMovie> popMovieArrayList) {
//        super(context, resource, popMovieArrayList);
//        this.context = context;
//        this.layoutResourceId = resource;
//        this.popMovieArrayList = popMovieArrayList;
//
//    }

    public int getCount() {

        if (popMovieArrayList != null) {
            movieCount = popMovieArrayList.size();
        }
        return movieCount;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
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