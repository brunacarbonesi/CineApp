package com.bcarbonesi.retroftiresquestapi.model;

import com.google.gson.annotations.SerializedName;

public class PremiereDate {

    @SerializedName("localDate")
    public String localDate;

    @SerializedName("dayOfWeek")
    public String dayOfWeek;

    @SerializedName("dayAndMonth")
    public String dayAndMonth;

    @SerializedName("year")
    public String year;

    @SerializedName("isToday")
    public Boolean isToday;
}
