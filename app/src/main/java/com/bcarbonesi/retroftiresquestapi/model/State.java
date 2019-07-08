package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {

    @SerializedName("name")
    public String name;

    @SerializedName("uf")
    public String uf;

    @SerializedName("cities")
    public List<City> cities;
}
