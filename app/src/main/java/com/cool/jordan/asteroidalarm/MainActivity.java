package com.cool.jordan.asteroidalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import AsteroidData.Asteroid;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.cool.jordan.asteroidalarm.AsteroidApp.CHANNEL_ID;
import static com.cool.jordan.asteroidalarm.AsteroidApp.DAILY_ASTEROID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name;
    TextView nameDetails;
    TextView distance;
    TextView distanceDetails;
    TextView diameter;
    TextView diameterDetails;
    String asteroidUrl = "";

    public interface OnAsteroidCallback {
        void onAsteroidReceived(Asteroid asteroid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        Controller controller = new Controller();
        controller.setAsteroidCallbackListener(new OnAsteroidCallback() {
            @Override
            public void onAsteroidReceived(Asteroid asteroid) {
                setAsteroidOfTheDay(asteroid);
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences(AsteroidApp.ASTEROID_PREF, Context.MODE_PRIVATE);
        String asteroidJson = sharedPreferences.getString(AsteroidApp.DAILY_KEY, "");
        Gson gson = new Gson();
        Asteroid dailyAsteroid = gson.fromJson(asteroidJson, Asteroid.class);
        if (dailyAsteroid != null) {
            setAsteroidOfTheDay(dailyAsteroid);
        }
    }

    public void setAsteroidOfTheDay(Asteroid asteroid) {
        nameDetails.setText(asteroid.getName());
        Resources res = getResources();
        diameterDetails.setText(String.format(res.getString(R.string.asteroid_diameter_details),
                asteroid.getEstimatedDiameter().getMeters().getEstimatedDiameterMax().toString()));
        distanceDetails.setText(String.format(res.getString(R.string.asteroid_distance_details),
                asteroid.getCloseApproachData().get(0).getMissDistance().getKilometers()));
        asteroidUrl = asteroid.getNasaJplUrl();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(asteroidUrl));
        startActivity(intent);
    }

    private void setViews() {
        name = findViewById(R.id.asteroid_name);
        name.setOnClickListener(this);
        nameDetails = findViewById(R.id.asteroid_name_detail);
        nameDetails.setOnClickListener(this);
        distance = findViewById(R.id.asteroid_distance);
        distance.setOnClickListener(this);
        distanceDetails = findViewById(R.id.asteroid_distance_detail);
        distanceDetails.setOnClickListener(this);
        diameter = findViewById(R.id.asteroid_diameter);
        diameter.setOnClickListener(this);
        diameterDetails = findViewById(R.id.asteroid_diameter_detail);
        diameterDetails.setOnClickListener(this);
    }

}


