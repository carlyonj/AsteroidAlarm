package com.cool.jordan.asteroidalarm;

import android.util.Log;

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

public class DangerousController implements Callback<AsteroidMetaData> {
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
        AsteroidApi asteroidApi = retrofit.create(AsteroidApi.class);
        Call<AsteroidMetaData> call = asteroidApi.loadAsteroids(startDate, startDate, BuildConfig.ApiKey);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AsteroidMetaData> call, Response<AsteroidMetaData> response) {
        if (response.isSuccessful()) {
            AsteroidMetaData asteroidList = response.body();
            if (asteroidList != null && asteroidList.getDateSampled() != null) {
                Map<String, Asteroid[]> day = asteroidList.getDateSampled();
                Asteroid[] asteroidsForDay = day.values().iterator().next();
                for (Asteroid asteroid : asteroidsForDay) {
                    if (asteroid.getIsPotentiallyHazardousAsteroid()) {
                        String distanceString = asteroid.getCloseApproachData().get(0)
                                .getMissDistance().getLunar()
                                .substring(0, 6);
                        if (Double.parseDouble(distanceString) < 10) {
                            AsteroidApp.sendNotification();
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
