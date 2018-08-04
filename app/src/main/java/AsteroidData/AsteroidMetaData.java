package AsteroidData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private Map<String, Asteroid[]> dateSampled;
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

    public Map<String, Asteroid[]> getDateSampled() {
        return dateSampled;
    }

    public void setDateSampled(Map<String, Asteroid[]> asteroids) {
        this.dateSampled = asteroids;
    }

}