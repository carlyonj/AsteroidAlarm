package com.cool.jordan.asteroidalarm;

import java.util.List;

import AsteroidData.AsteroidMetaData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface AsteroidApi {
    @GET("feed?")
    Call<AsteroidMetaData> loadAsteroids(@Query("start_date") String startDate,
                                         @Query("end_date") String endDate,
                                         @Query("api_key") String apiKey);
}
