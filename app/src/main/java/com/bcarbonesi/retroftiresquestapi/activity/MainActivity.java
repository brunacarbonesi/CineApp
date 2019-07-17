package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.adapter.CitiesAdapter;
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
import java.util.ArrayList;
import java.util.Collections;
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

    public static final String MY_PREFS_FILENAME = "com.bcarbonesi.sharedpreferencesexample.Names";

    private static Retrofit retrofit;
    public RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context mContext;

    TabLayout tabLayout;

    Button buttonSendCityToSharedPreferences;
    TextView textViewCityChosen;
    AutoCompleteTextView filled_exposed_dropdown;

    private List<State> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSendCityToSharedPreferences = findViewById(R.id.material_button);
        textViewCityChosen = findViewById(R.id.cityChosen);
        filled_exposed_dropdown = findViewById(R.id.filled_exposed_dropdown);

        recyclerView = findViewById(R.id.recycler_view);
        getMoviesInTheatersList();
        getStateList();

        buttonSendCityToSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cidadeEscolhida = filled_exposed_dropdown.getText().toString().trim();
                String idCidadeEscolhida = "teste";

                if (!cidadeEscolhida.isEmpty()) {
                    textViewCityChosen.setText(cidadeEscolhida);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE).edit();
                    editor.putString("cidadeEscolhida", cidadeEscolhida);
                    editor.commit();

                    filled_exposed_dropdown.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Por favor, escolha a cidade que deseja!", Toast.LENGTH_SHORT).show();
                }

                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE);
        String cidadeEscolhida = prefs.getString("cidadeEscolhida", "Rio de Janeiro");

        textViewCityChosen.setText(cidadeEscolhida);


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

                ComingSoonMoviesAdapter adapter = new ComingSoonMoviesAdapter(comingSoonMovies, R.layout.list_item_movie_coming_soon, getApplicationContext());

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

    private void getStateList() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        LocationService locationService = retrofit.create(LocationService.class);

        Call<List<State>> call = locationService.getStates();

        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                states = response.body();

                //String [] cidades = new String[] {""};
                ArrayList<String> cidadesListName = new ArrayList<>();

                for (State state : states) {
                    for(City city : state.cities) {
                        cidadesListName.add(city.name);
                    }
                }

                Collections.sort(cidadesListName);


                //for (int i = 0; i < states.size(); i++) {
                //    State state = states.get(i);
                //}
                
                
                /*ArrayList<String> statesListName = new ArrayList<>();

                for (int i = 0; i < states.size(); i++) {
                    statesListName.add(states.get(i).name);
                    //for (int j = 0 ; j < states.get(j).cities.size(); j++) {
                    //    cidadesListName.add(states.get(j).cities.get(j).name);
                    //}

                    Log.d("estados:" , statesListName.get(i));
                }



                /*
                CitiesAdapter adapter = new CitiesAdapter(cities, R.layout.list_item_city, getApplicationContext());

                recyclerView.setAdapter(adapter);

                // Define a layout for RecyclerView
                mLayoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(mLayoutManager);

                textViewState.setText(cities.get(0).name);
                */

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, cidadesListName);
                AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);

                editTextFilledExposedDropdown.setAdapter(adapter);


                if (states != null) {
                    if (states.size() > 0) {
                        Log.d("Cities list:", "preenchida");
                        //State firstCity = cities.get(0);
                        //Log.d("First state name:", firstCity.name);
                        //Log.d("First State name:", firstCity.cities.get(0).name);

                    } else {
                        showEmptyContentScreen();
                    }

                } else {
                    showErrorScreen();
                }
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
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

