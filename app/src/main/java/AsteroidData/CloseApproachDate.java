package AsteroidData;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jordan on 7/15/2017.
 */

public class CloseApproachDate {

    //@SerializedName("2015-09-11")
    //@Expose
    private List<Asteroid> asteroids;

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }
}
