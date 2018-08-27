package com.cool.jordan.asteroidalarm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import AsteroidData.Asteroid;
import AsteroidData.AsteroidMetaData;
import AsteroidData.CloseApproachDatum;
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
    private MainActivity.OnAsteroidCallback onAsteroidCallback;

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        AsteroidApi asteroidApi = retrofit.create(AsteroidApi.class);
        Call<AsteroidMetaData> call = asteroidApi.loadAsteroids(startDate, endDate, BuildConfig.ApiKey);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AsteroidMetaData> call, Response<AsteroidMetaData> response) {
        List<CloseApproachDatum> distances = new ArrayList<>();

        if(response.isSuccessful()) {
            AsteroidMetaData asteroidList = response.body();
            if (asteroidList.getDateSampled() != null) {
                Map<String, Asteroid[]> test = asteroidList.getDateSampled();
                for (Asteroid[] asteroids : test.values()) {
                    for (int i = 0; i < asteroids.length; i++) {
                        if (i == 0) {
                            onAsteroidCallback.onAsteroidReceived(asteroids[0]);
                        }
                        if (asteroids[i].getIsPotentiallyHazardousAsteroid()) {
                            float close;
                            if (asteroids[i].getCloseApproachData().get(0).getMissDistance().getAstronomical() == null) {
                                close = 9999999;
                            } else {
                                close = Float.parseFloat(asteroids[i].getCloseApproachData().get(0).getMissDistance().getLunar());
                            }
                            distances.add(asteroids[i].getCloseApproachData().get(0));
                            Log.e("zzz", "zzz asteroid " + asteroids[i].getName());
                        }
                    }
                }
            }
        } else {
            Log.e("zzz", "error: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<AsteroidMetaData> call, Throwable t) {
        t.printStackTrace();
    }

    public void setAsteroidCallbackListener(MainActivity.OnAsteroidCallback listener) {
        this.onAsteroidCallback = listener;
    }

}
