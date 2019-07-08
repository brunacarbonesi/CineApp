package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.adapter.ComingSoonMoviesAdapter;
import com.bcarbonesi.retroftiresquestapi.adapter.MoviesAdapter;
import com.bcarbonesi.retroftiresquestapi.model.City;
import com.bcarbonesi.retroftiresquestapi.model.ComingSoonMovie;
import com.bcarbonesi.retroftiresquestapi.model.LocationService;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.MovieItem;
import com.bcarbonesi.retroftiresquestapi.model.MovieService;
import com.bcarbonesi.retroftiresquestapi.model.State;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String BASE_URL = "https://api-content.ingresso.com/v0/";

    private static final String cityId = "31";

    private static Retrofit retrofit;
    public RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context mContext;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        getMoviesInTheatersList();


        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_cine_app_vertical_simple_foreground);
        //actionBar.setTitle(" Welcome!");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tabLayout = findViewById(R.id.simpleTabLayout);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("In Theaters");
        firstTab.setIcon(R.drawable.ic_theaters);
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Coming Soon");
        secondTab.setIcon(R.drawable.ic_comming_soon);
        tabLayout.addTab(secondTab);

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            // get the current selected tab's position and replace the fragment accordingly
                //Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        getMoviesInTheatersList();
                        //Log.d("teste","inTheaters");
                        break;
                    case 1:
                        getComingSoonMoviesList();
                        //Log.d("teste","comingSoon");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getMoviesInTheatersList() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieService movieService = retrofit.create(MovieService.class);

        Call<List<Movie>> call = movieService.getEventsByCity(cityId);

        call.enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                List<Movie> movies = response.body();

                int numberOfColumns = 3;

                MoviesAdapter adapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());

                recyclerView.setAdapter(adapter);

                // Define a layout for RecyclerView
                mLayoutManager = new GridLayoutManager(mContext, numberOfColumns);
                recyclerView.setLayoutManager(mLayoutManager);


                // Conferindo o preenchimento da lista
                /*
                if (movies != null) {
                    if (movies.size() > 0) {
                        Log.d("Movies list:", "preenchida");
                        Movie firstMovie = movies.get(0);
                        // Teste Logs para conferir o preenchimento das listas
                        Log.d("First Movie name:", firstMovie.title);


                        if (firstMovie.images != null) {
                            // Teste Logs para conferir o preenchimento das listas
                            Log.d("URL: ", "preenchido");

                            // Teste Logs para conferir o preenchimento das listas
                            String firstMovieUrl = firstMovie.images.get(0).url;
                            Log.d("First movie poster:", firstMovieUrl);

                        }


                    } else {
                        Log.d("First Movie Name:", "erro");
                        showEmptyContentScreen();
                    }

                }
                */

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, t.toString());
                showErrorScreen();
            }

        });
    }

    private void getComingSoonMoviesList() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieService comingSoonMovieService = retrofit.create(MovieService.class);

        Call<List<Movie>> call = comingSoonMovieService.getEventsComingSoon();

        call.enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                List<Movie> comingSoonMovies = response.body();

                int numberOfColumns = 3;

                ComingSoonMoviesAdapter adapter = new ComingSoonMoviesAdapter(comingSoonMovies, R.layout.list_item_movie, getApplicationContext());

                recyclerView.setAdapter(adapter);

                // Define a layout for RecyclerView
                mLayoutManager = new GridLayoutManager(mContext, numberOfColumns);
                recyclerView.setLayoutManager(mLayoutManager);


                if (comingSoonMovies != null) {
                    if (comingSoonMovies.size() > 0) {
                        Log.d("Movies list:", "preenchida");
                        //ComingSoonMovie firstMovieComing = comingSoonMovies.get(0);
                        // Teste Logs para conferir o preenchimento das listas
                        //Log.d("Movie Coming name:", firstMovieComing.title);
                        ;

                        //if (firstMovieComing.images != null) {
                            // Teste Logs para conferir o preenchimento das listas
                           // Log.d("URL Coming: ", "preenchido");

                            // Teste Logs para conferir o preenchimento das listas
                            //String firstMovieComingUrl = firstMovieComing.images.get(0).url;
                            //Log.d("Coming Movie poster:", firstMovieComingUrl);

                            /*Picasso.with(getApplicationContext())
                                .load(firstMovieUrl)
                                .placeholder(android.R.drawable.sym_def_app_icon)
                                .into(imagePosterUrl);*/
                        //}


                    } else {
                        Log.d("Coming Movie Name:", "erro coming movie");
                        showEmptyContentScreen();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, t.toString());
                showErrorScreen();
            }

        });
    }

    private void showEmptyContentScreen() {
        Log.d("States list:", "vazia");
    }

    private void showErrorScreen() {
        Log.d("States list:", "erro");
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

    public void animateIntent(View view)
    {
        Intent intent = new Intent(this, DetailActivity.class);
        String transitionName = getString(R.string.transition_name);

        View viewStart = findViewById(R.id.recycler_view);

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transitionName);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}

