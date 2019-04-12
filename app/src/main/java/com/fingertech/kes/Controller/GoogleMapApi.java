package com.fingertech.kes.Controller;

import com.fingertech.kes.Service.ResponseMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapApi {

    @GET("place/autocomplete/json")
    public Call<ResponseMap.Predictions> getPlacesAutoComplete(
            @Query("input") String input,
            @Query("types") String types,
            @Query("language") String language,
            @Query("key") String key
    );

}