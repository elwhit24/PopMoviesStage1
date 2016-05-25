package com.example.michelangelowhitten.popmoviesstage1;

/**
 * Created by Mike on 5/5/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    private Context context;
    private ArrayList<String> imageUrls;
    int layoutResourceId;
    private ArrayList<AndroidMovie> popMovieArrayList;
    int movieCount;
    ImageView poster;

    public PosterAdapter() {

    }

    public ArrayList<AndroidMovie> getPopMovieArrayList() {
        return popMovieArrayList;
    }

    public void setPopMovieArrayList(ArrayList<AndroidMovie> popMovieArrayList) {
        this.popMovieArrayList = popMovieArrayList;
    }

    /*public PosterAdapter(Context context, ArrayList<String> imageUrls) {
        //super(context, R.layout.fragment_main, imageUrls);
        *//*private final LayoutInflater inflater;*//*

        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);

        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public MyViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.movie_poster);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(context, DetailsFragment.DetailActivity.class);
                    intent.putExtra(DetailsFragment.DetailActivity.EXTRA_MOVIE, getPopMovieArrayList().get(position));
                    context.startActivity(intent);
                }
            });
     return viewHolder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        AndroidMovie movie = getPopMovieArrayList().get(position);
        Picasso.with(context)
                .load(movie.getPosterImageUrl())
                .placeholder(R.color.colorAccent)
                .into(holder.poster);
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return (getPopMovieArrayList() == null) ? 0 : getPopMovieArrayList().size();
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

        recyclerView.setOnItemClickListener(new Image.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AndroidMovie movieItem = movieArray.get(position);
                    Intent movieIntent = new Intent(getActivity(), DetailsFragment.DetailActivity.class).
                            putExtra(Intent.EXTRA_TEXT, movieItem);
                    startActivity(movieIntent);
                }
            });
/*public int getItemCount() {

        if (popMovieArrayList != null) {
            movieCount = popMovieArrayList.size();
        }
        return movieCount;
    }
        Picasso.with(context)
                .load(popMovieArrayList.get(position).getPosterImageUrl())
                .fit()
                .into((ImageView) convertView);

        return convertView;
    }

//    public PosterAdapter(Context context, int resource, ArrayList<AndroidMovie> popMovieArrayList) {
//        super(context, resource, popMovieArrayList);
//        this.context = context;
//        this.layoutResourceId = resource;
//        this.popMovieArrayList = popMovieArrayList;
//
//    }

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