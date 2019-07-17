package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("state")
    public String stateName;

    @SerializedName("urlKey")
    public String urlKey;

    @SerializedName("timeZone")
    public String timeZone;
}
