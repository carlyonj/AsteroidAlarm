package com.cool.jordan.asteroidalarm;

import java.util.List;

import AsteroidData.AsteroidMetaData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface AsteroidApi {
    @GET("feed?&api_key=" + BuildConfig.ApiKey)
    Call<AsteroidMetaData> loadAsteroids(@Query("start_date") String date);
}
