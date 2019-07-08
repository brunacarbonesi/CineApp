package com.bcarbonesi.retroftiresquestapi.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.activity.ComingSoonDetailActivity;
import com.bcarbonesi.retroftiresquestapi.model.Image;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ComingSoonMoviesAdapter extends RecyclerView.Adapter<ComingSoonMoviesAdapter.ComingSoonMovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private String posterMovieUrl;
    private String movieDateUrl;


    public ComingSoonMoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ComingSoonMoviesAdapter.ComingSoonMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new ComingSoonMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComingSoonMovieViewHolder comingSoonMovieViewHolder, final int i) {
        //comingSoonMovieViewHolder.bind(movies, i);

        posterMovieUrl = comingSoonMovieViewHolder.bind(movies, i);
        movieDateUrl = movies.get(i).premiereDate.dayOfWeek + ", " + movies.get(i).premiereDate.dayAndMonth;
        movieDateUrl = StringUtils.capitalize(movieDateUrl);

        Picasso.with(context)
                .load(posterMovieUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(comingSoonMovieViewHolder.movieImage);

        comingSoonMovieViewHolder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ComingSoonDetailActivity.class);
                intent.putExtra("movie_title", movies.get(i).title);
                intent.putExtra("image_url", movies.get(i).images.get(0).url);
                intent.putExtra("movie_description", movies.get(i).synopsis);
                intent.putExtra("movie_date", movieDateUrl);
                //intent.putExtra("movie_premiere_date", movies.get(i).premiereDates.get(i).dayAndMonth);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }


    @Override public int getItemCount() {
        return movies.size();
    }

    public static class ComingSoonMovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        ImageView movieImage;
        TextView movieTitle;

        //TextView movieDate;

        //TextView data;
        //TextView movieDescription;
        //TextView rating;

        public ComingSoonMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.title);

            //movieDate = itemView.findViewById(R.id.textViewComingSoon);

            //data = itemView.findViewById(R.id.date);
            //movieDescription = itemView.findViewById(R.id.description);
            //rating = itemView.findViewById(R.id.rating);

        }

        public String bind(List<Movie> movies, int i) {

            Movie movie = movies.get(i);
            String posterMovieUrl = "";

            for (int j = 0; j < movie.images.size(); j++) {

                final Image image = movie.images.get(j);

                //final String premiereDate = movie.premiereDates.get(j).dayAndMonth;

                if (image.typePoster.equals("PosterPortrait")) {
                    posterMovieUrl = image.url;
                }

            }

            moviesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return posterMovieUrl;
        }
    }
}
