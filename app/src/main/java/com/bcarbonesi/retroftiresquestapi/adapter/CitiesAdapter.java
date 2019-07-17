package com.bcarbonesi.retroftiresquestapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.activity.DetailActivity;
import com.bcarbonesi.retroftiresquestapi.model.City;
import com.bcarbonesi.retroftiresquestapi.model.Image;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.State;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private List<State> cities;
    private int rowLayout;
    private Context context;

    private String cityName;
    private String stateName;


    public CitiesAdapter(List<State> cities, int rowLayout, Context context) {
        this.cities = cities;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public CitiesAdapter.CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new CitiesAdapter.CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesAdapter.CitiesViewHolder citiesViewHolder, final int i) {


        cityName = citiesViewHolder.bind(cities, i);
        stateName = citiesViewHolder.bindState(cities,i);

        citiesViewHolder.textViewCityNameListLayout.setText(cityName);
        citiesViewHolder.textViewStateNameListLayout.setText(stateName);

        //citiesViewHolder.textViewState.setText(stateName);

        /*
        citiesViewHolder.citiesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("city_name", cities.get(i).name);
                intent.putExtra("state_name", cities.get(i).stateName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
        */
    }


    @Override public int getItemCount() {

        return cities.size();
    }

    public static class CitiesViewHolder extends RecyclerView.ViewHolder {

        LinearLayout citiesLayout;
        TextView textViewCityNameListLayout;
        TextView textViewStateNameListLayout;

        //TextView textViewState;


        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            citiesLayout = itemView.findViewById(R.id.cities_layout);
            textViewCityNameListLayout = itemView.findViewById(R.id.textViewCityNameListLayout);
            textViewStateNameListLayout = itemView.findViewById(R.id.textViewStateNameListLayout);
            //textViewState = itemView.findViewById(R.id.textViewState);
        }

        public String bind(List<State> cities, int i) {

            State city = cities.get(i);
            String cityName = "";

            for (int j = 0; j < city.cities.size(); j++) {
                cityName = city.cities.get(j).name;

                Log.d("cityName", cityName);
            }

            return cityName;
        }

        public String bindState(final List<State> cities, final int i) {

            State city = cities.get(i);
            String stateName = city.name;



            citiesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("cityName", "teste" + cities.get(i).name);

                }
            });


            return stateName;
        }
    }
}

