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
import com.bcarbonesi.retroftiresquestapi.activity.DetailActivity;
import com.bcarbonesi.retroftiresquestapi.model.Image;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.PremiereDate;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private String posterMovieUrl;


    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int i) {
        movieViewHolder.bind(movies, i);

        posterMovieUrl = movieViewHolder.bind(movies, i);

        Picasso.with(context)
                .load(posterMovieUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(movieViewHolder.movieImage);
        //movieViewHolder.movieTitle.setText(movies.get(i).getTitle());
        //movieViewHolder.data.setText(movies.get(i).getReleaseDate());
        //movieViewHolder.movieDescription.setText(movies.get(i).getOverview());
        //movieViewHolder.rating.setText(movies.get(i).rating);

        //movieViewHolder.movieDate.setText(movies.get(i).premiereDates.get(i).dayAndMonth);

        //Log.d("transition name",movieViewHolder.movieImage.getTransitionName());


        movieViewHolder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_title", movies.get(i).title);
                intent.putExtra("image_url", movies.get(i).images.get(0).url);
                intent.putExtra("movie_description", movies.get(i).synopsis);
                intent.putExtra("rating", movies.get(i).rating);
                //intent.putExtra("movie_premiere_date", movies.get(i).premiereDates.get(i).dayAndMonth);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }


    @Override public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        ImageView movieImage;
        TextView movieTitle;

        //TextView movieDate;

        //TextView data;
        //TextView movieDescription;
        //TextView rating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.title);

            //movieDate = itemView.findViewById(R.id.movie_date_coming_soon);

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


/*
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        ImageView movieImage;
        TextView movieTitle;

        //TextView data;
        //TextView movieDescription;
        //TextView rating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.title);

            //data = itemView.findViewById(R.id.date);
            //movieDescription = itemView.findViewById(R.id.description);
            //rating = itemView.findViewById(R.id.rating);



        }

        public String bind(List<Movie> movies, int i) {

            Movie movie = movies.get(i);
            String posterMovieUrl = "";

            for (int j = 0; j < movie.images.size(); j++) {

                final Image image = movie.images.get(j);

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

*/
