package com.cool.jordan.asteroidalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import AsteroidData.Asteroid;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name;
    TextView nameDetails;
    TextView distance;
    TextView distanceDetails;
    TextView velocity;
    TextView velocityDetails;
    TextView diameter;
    TextView diameterDetails;
    TextView orbitingBody;
    TextView orbitingBodyDetails;
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
        } else {
            controller.start(this);
        }
    }

    public void setAsteroidOfTheDay(Asteroid asteroid) {
        nameDetails.setText(asteroid.getName());
        Resources res = getResources();
        diameterDetails.setText(String.format(res.getString(R.string.asteroid_diameter_details),
                asteroid.getEstimatedDiameter().getMeters().getEstimatedDiameterMax().toString()));
        velocityDetails.setText(String.format(res.getString(R.string.asteroid_velocity_details),
                asteroid.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerSecond()));
        distanceDetails.setText(String.format(res.getString(R.string.asteroid_distance_details),
                asteroid.getCloseApproachData().get(0).getMissDistance().getKilometers()));
        asteroidUrl = asteroid.getNasaJplUrl();
        orbitingBodyDetails.setText(
                asteroid.getCloseApproachData().get(0).getOrbitingBody());
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
        velocity = findViewById(R.id.asteroid_velocity);
        velocity.setOnClickListener(this);
        velocityDetails = findViewById(R.id.asteroid_velocity_detail);
        velocityDetails.setOnClickListener(this);
        diameter = findViewById(R.id.asteroid_diameter);
        diameter.setOnClickListener(this);
        diameterDetails = findViewById(R.id.asteroid_diameter_detail);
        diameterDetails.setOnClickListener(this);
        orbitingBody = findViewById(R.id.asteroid_orbiting_body);
        orbitingBody.setOnClickListener(this);
        orbitingBodyDetails = findViewById(R.id.asteroid_orbiting_body_detail);
        orbitingBodyDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(asteroidUrl));
        startActivity(intent);
    }

}


