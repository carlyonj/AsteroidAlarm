package AsteroidData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jordan on 7/15/2017.
 */

public class DateSampled {

    @SerializedName("2015-09-11")
    @Expose
    private List<Asteroid> asteroids;

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }
}
