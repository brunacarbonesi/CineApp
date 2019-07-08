package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieItem {

    @SerializedName("items")
    public List<Movie> items;

    @SerializedName("count")
    public String count;

}
