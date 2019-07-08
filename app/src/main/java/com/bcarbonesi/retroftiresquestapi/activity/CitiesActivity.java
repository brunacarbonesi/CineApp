package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import android.widget.TextView;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.adapter.CitiesAdapter;
import com.bcarbonesi.retroftiresquestapi.adapter.MoviesAdapter;
import com.bcarbonesi.retroftiresquestapi.model.City;
import com.bcarbonesi.retroftiresquestapi.model.LocationService;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.MovieService;
import com.bcarbonesi.retroftiresquestapi.model.State;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiesActivity extends AppCompatActivity {

    private static final String TAG = CitiesActivity.class.getSimpleName();

    public static final String BASE_URL = "https://api-content.ingresso.com/v0/";

    private static final String cityId = "31";

    private static Retrofit retrofit;
    public RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<State> cities;
    TextView textViewState;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        textViewState = findViewById(R.id.textViewState);
        recyclerView = findViewById(R.id.recyclerViewCityList);

        getStateList();

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
                cities = response.body();

                CitiesAdapter adapter = new CitiesAdapter(cities, R.layout.list_item_city, getApplicationContext());

                recyclerView.setAdapter(adapter);

                // Define a layout for RecyclerView
                mLayoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(mLayoutManager);

                textViewState.setText(cities.get(0).name);


                if (cities != null) {
                    if (cities.size() > 0) {
                        //Log.d("Cities list:", "preenchida");
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
        Log.d("Cities list:", "vazia");
    }

    private void showErrorScreen() {
        Log.d("Cities list:", "erro");
    }

    // MENU NA ACTION BAR ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_cities, menu);
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
