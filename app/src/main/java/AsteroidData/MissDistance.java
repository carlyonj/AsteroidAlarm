package AsteroidData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jordan on 7/8/2017.
 */


public class MissDistance {

    @SerializedName("astronomical")
    @Expose
    private String astronomical;
    @SerializedName("lunar")
    @Expose
    private String lunar;
    @SerializedName("kilometers")
    @Expose
    private String kilometers;
    @SerializedName("miles")
    @Expose
    private String miles;

    public String getAstronomical() {
        return astronomical;
    }

    public void setAstronomical(String astronomical) {
        this.astronomical = astronomical;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

}
