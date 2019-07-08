package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcarbonesi.retroftiresquestapi.R;
import com.squareup.picasso.Picasso;

public class ComingSoonDetailActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coming_soon);
        Log.d(TAG, "onCreate: started.");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.theaters);
        actionBar.setTitle("Info");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url")
                && getIntent().hasExtra("movie_title")
                && getIntent().hasExtra("movie_description")
                && getIntent().hasExtra("movie_date"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String movieTitle = getIntent().getStringExtra("movie_title");
            String movieDescription = getIntent().getStringExtra("movie_description");
            String moviePremiereDate = getIntent().getStringExtra("movie_date");

            setImage(imageUrl, movieTitle, movieDescription, moviePremiereDate);
        }
    }


    private void setImage(String imageUrl, String movieTitle, String movieDescription, String moviePremiereDate){
        Log.d(TAG, "setImage: setting the image and name to widgets.");

        TextView name = findViewById(R.id.movie_title);
        name.setText(movieTitle);

        TextView textViewComingSoon = findViewById(R.id.textViewComingSoon);
        textViewComingSoon.setText(moviePremiereDate);

        ImageView movieImage;
        movieImage = findViewById(R.id.movie_image);

        Log.d("Nome da Imagem", imageUrl);

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(movieImage);

        TextView movie_description = findViewById(R.id.movie_description);
        movie_description.setText(movieDescription);

    }

    // MENU NA ACTION BAR ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // COMPLETED (7) Override onOptionsItemSelected to handle clicks on the refresh button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            //mForecastAdapter.setWeatherData(null);
            //loadWeatherData();
            return true;
        }
        else if (id == R.id.action_app_info) {
            return true;
        }
        else if (id == R.id.action_developer_info) {

        }
        else if (id == R.id.action_search) {
            return true;
        }
        else if (id == R.id.cityName) {
            Intent intent = new Intent (this, CitiesActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

}