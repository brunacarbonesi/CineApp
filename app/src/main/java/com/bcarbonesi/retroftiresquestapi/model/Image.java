package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("url")
    public String url;

    @SerializedName("type")
    public String typePoster;
}
