package com.bcarbonesi.retroftiresquestapi.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    //@GET("templates/nowplaying/{cityId}/partnership/{partnership}")
    //Call<List<MovieItem>> getMoviesInTheaters(@Path("cityId") String cityId);

    @GET("events/city/{cityId}")
    Call<List<Movie>> getEventsByCity(@Path("cityId") String cityId);

    @GET("events/coming-soon")
    Call<List<Movie>> getEventsComingSoon();



}
