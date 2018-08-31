package com.cool.jordan.asteroidalarm;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private MainActivity.OnAsteroidCallback onAsteroidCallback;
    Context context;

    public void start(Context context) {
        this.context = context;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        AsteroidApi asteroidApi = retrofit.create(AsteroidApi.class);
        Call<AsteroidMetaData> call = asteroidApi.loadAsteroids(startDate, endDate, BuildConfig.ApiKey);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AsteroidMetaData> call, Response<AsteroidMetaData> response) {
        if (response.isSuccessful()) {
            AsteroidMetaData asteroidList = response.body();
            if (asteroidList != null && asteroidList.getDateSampled() != null) {
                Map<String, Asteroid[]> day = asteroidList.getDateSampled();
                for (Asteroid[] asteroids : day.values()) {
                    if (asteroids[0] != null) {
                        Gson gson = new Gson();
                        SharedPreferences sharedPreferences = context.getSharedPreferences(AsteroidApp.ASTEROID_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AsteroidApp.DAILY_KEY, gson.toJson(asteroids[0]));
                        editor.apply();
                        if (onAsteroidCallback != null) {
                            onAsteroidCallback.onAsteroidReceived(asteroids[0]);
                        }
                        return;
                    }
                }
            }
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
