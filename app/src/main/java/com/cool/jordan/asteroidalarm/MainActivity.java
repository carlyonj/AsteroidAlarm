package com.cool.jordan.asteroidalarm;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import AsteroidData.Asteroid;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.cool.jordan.asteroidalarm.AsteroidApp.CHANNEL_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name;
    TextView nameDetails;
    TextView distance;
    TextView distanceDetails;
    TextView diameter;
    TextView diameterDetails;
    String asteroidUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.asteroid_name);
        name.setOnClickListener(this);
        nameDetails = findViewById(R.id.asteroid_name_detail);
        distance = findViewById(R.id.asteroid_distance);
        name.setOnClickListener(this);
        distanceDetails = findViewById(R.id.asteroid_distance_detail);
        diameter = findViewById(R.id.asteroid_diameter);
        diameterDetails = findViewById(R.id.asteroid_diameter_detail);

        Controller controller = new Controller();
        controller.setAsteroidCallbackListener(new OnAsteroidCallback() {
            @Override
            public void onAsteroidReceived(Asteroid asteroid) {
                setAsteroidOfTheDay(asteroid);
            }
        });
        controller.start();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WARNING: Potentially Hazardous Asteroid")
                .setContentText("Hazardous Asteroid Approaching in 7 days")
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.alert))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.RED, 1000, 250);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(675, mBuilder.build());
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

    public interface OnAsteroidCallback {
        void onAsteroidReceived(Asteroid asteroid);
    }


}


