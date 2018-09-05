package com.cool.jordan.asteroidalarm;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import AsteroidData.Asteroid;
import AsteroidData.AsteroidMetaData;
import androidx.collection.ArrayMap;
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
    private Context context;

    DangerousController(Context context) {
        this.context = context;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        AsteroidApi asteroidApi = retrofit.create(AsteroidApi.class);
        Call<AsteroidMetaData> call = asteroidApi.loadAsteroids(startDate, startDate, BuildConfig.ApiKey);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AsteroidMetaData> call, Response<AsteroidMetaData> response) {
        if (response.isSuccessful()) {
            AsteroidMetaData asteroidList = response.body();
            if (asteroidList != null && asteroidList.getDateSampled() != null) {
                ArrayMap<String, Asteroid[]> day = asteroidList.getDateSampled();
                String lastDateKey = "";
                Iterator<String> iterator = day.keySet().iterator();
                while (iterator.hasNext()) {
                    lastDateKey = iterator.next();
                }
                Asteroid[] asteroidsForDay = day.get(lastDateKey);
                for (Asteroid asteroid : asteroidsForDay) {
                    if (asteroid.getIsPotentiallyHazardousAsteroid()) {
                        //TODO make a nice util for conversion of strings to useful numbers
                        String distanceString = asteroid.getCloseApproachData().get(0)
                                .getMissDistance().getLunar()
                                .substring(0, 6);
                        if (Double.parseDouble(distanceString) < 30) {
                            AsteroidApp.sendNotification(context, asteroid);
                        }
                    }
                }
            }
        } else {
            Log.e("DangerousController", "error: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<AsteroidMetaData> call, Throwable t) {
        t.printStackTrace();
    }

}
