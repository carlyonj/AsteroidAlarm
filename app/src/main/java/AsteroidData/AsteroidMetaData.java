package AsteroidData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.collection.ArrayMap;

/**
 * Created by Jordan on 7/8/2017.
 */

public class AsteroidMetaData {

//    @SerializedName("links")
//    @Expose
//    private transient List<Links> links;
    @SerializedName("element_count")
    @Expose
    private Integer elementCount;
    @SerializedName("near_earth_objects")
    @Expose
    private ArrayMap<String, Asteroid[]> dateSampled;
//
//    public List<Links> getLinks() {
//        return links;
//    }
//
//    public void setLinks(List<Links> links) {
//        this.links = links;
//    }

    public Integer getElementCount() {
        return elementCount;
    }

    public void setElementCount(Integer elementCount) {
        this.elementCount = elementCount;
    }

    public ArrayMap<String, Asteroid[]> getDateSampled() {
        return dateSampled;
    }

    public void setDateSampled(ArrayMap<String, Asteroid[]> asteroids) {
        this.dateSampled = asteroids;
    }

}