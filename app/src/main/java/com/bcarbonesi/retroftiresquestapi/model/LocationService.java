package com.bcarbonesi.retroftiresquestapi.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationService {

    @GET("states")
    Call<List<State>> getStates();

    @GET("states/city")
    Call<List<City>> getCities();

}
