package com.cool.jordan.asteroidalarm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import AsteroidData.Asteroid;
import AsteroidData.AsteroidMetaData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jordan on 7/8/2017.
 */

public class Controller implements Callback<AsteroidMetaData> {

    private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/";
    public static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AsteroidApi asteroidApi = retrofit.create(AsteroidApi.class);
        Call<AsteroidMetaData> call = asteroidApi.loadAsteroids(DATE);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AsteroidMetaData> call, Response<AsteroidMetaData> response) {
        Log.e("zzz", "zzz call  " + call.request().toString());
        if(response.isSuccessful()) {
            AsteroidMetaData asteroidList = response.body();
            Log.e("zzz", "zzz call  " + asteroidList.getElementCount());
            if (asteroidList.getDateSampled() != null) {
                Log.e("zzz", "zzz succ " + asteroidList.getDateSampled().size());
                Log.e("zzz", "zzz succ " + asteroidList.getDateSampled());
                Map<String, Asteroid[]> test = asteroidList.getDateSampled();
                for (Asteroid[] asteroids : test.values()) {
                    Log.e("zzz", "zzz test " + asteroids.length);
                    for (int i = 0; i < asteroids.length; i++) {
                        Log.e("zzz", "zzz asteroid " + i + " " + asteroids[i].getIsPotentiallyHazardousAsteroid());
                    }
                }
               // Log.e("zzz", "zzz asteroids " + asteroidList.getDateSampled().getAsteroids().size());
//                for (Asteroid asteroid : asteroidList.getDateSampled().getAsteroids()) {
//                    if (asteroid.getIsPotentiallyHazardousAsteroid()) {
//                        Log.e("zzz", "zzz asteroid HAZARD " + asteroid.getName() + " approach date "
//                                + asteroid.getCloseApproachData().get(0).getCloseApproachDate()
//                                + " SIZE " + asteroid.getEstimatedDiameter().getMeters().getEstimatedDiameterMax()
//                                + " orbiting body + " + asteroid.getCloseApproachData().get(0).getOrbitingBody());
//                    }
//                }
            }
        } else {
            Log.e("zzz", "error: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<AsteroidMetaData> call, Throwable t) {
        Log.e("zzz", "zzz fail  " + call.request().toString());
        Log.e("zzz", "zzz fail " + call.toString());
        Log.e("zzz", "zzz fail " + t.getLocalizedMessage());
        t.printStackTrace();
    }
}
