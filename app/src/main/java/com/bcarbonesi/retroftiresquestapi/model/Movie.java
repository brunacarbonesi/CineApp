package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("synopsis")
    public String synopsis;

    @SerializedName("rating")
    public String rating;

    /*
    @SerializedName("originalTitle")
    public String originalTitle;

    @SerializedName("contentRating")
    public String contentRating;

    @SerializedName("duration")
    public String duration;

    @SerializedName("cast")
    public String cast;

    @SerializedName("inPreSale")
    public Boolean inPreSale;

    @SerializedName("type")
    public String type;

    @SerializedName("urlKey")
    public String urlKey;

    @SerializedName("isPlaying")
    public Boolean isPlaying;

    @SerializedName("genres")
    public String genres;
    */

    @SerializedName("images")
    public List<Image> images;

    @SerializedName("city")
    public String city;

    @SerializedName("premiereDate")
    public PremiereDate premiereDate;


}
