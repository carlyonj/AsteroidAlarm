package AsteroidData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimatedDiameter {

    @SerializedName("kilometers")
    @Expose
    private Kilometers kilometers;
    @SerializedName("meters")
    @Expose
    private Meters meters;
    @SerializedName("miles")
    @Expose
    private Miles miles;
    @SerializedName("feet")
    @Expose
    private Feet feet;

    public Kilometers getKilometers() {
        return kilometers;
    }

    public void setKilometers(Kilometers kilometers) {
        this.kilometers = kilometers;
    }

    public Meters getMeters() {
        return meters;
    }

    public void setMeters(Meters meters) {
        this.meters = meters;
    }

    public Miles getMiles() {
        return miles;
    }

    public void setMiles(Miles miles) {
        this.miles = miles;
    }

    public Feet getFeet() {
        return feet;
    }

    public void setFeet(Feet feet) {
        this.feet = feet;
    }


    public class Kilometers {
        @SerializedName("estimated_diameter_min")
        @Expose
        private Double estimatedDiameterMin;
        @SerializedName("estimated_diameter_max")
        @Expose
        private Double estimatedDiameterMax;

        public Double getEstimatedDiameterMin() {
            return estimatedDiameterMin;
        }

        public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
            this.estimatedDiameterMin = estimatedDiameterMin;
        }

        public Double getEstimatedDiameterMax() {
            return estimatedDiameterMax;
        }

        public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
            this.estimatedDiameterMax = estimatedDiameterMax;
        }

    }


    public class Meters {

        @SerializedName("estimated_diameter_min")
        @Expose
        private Double estimatedDiameterMin;
        @SerializedName("estimated_diameter_max")
        @Expose
        private Double estimatedDiameterMax;

        public Double getEstimatedDiameterMin() {
            return estimatedDiameterMin;
        }

        public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
            this.estimatedDiameterMin = estimatedDiameterMin;
        }

        public Double getEstimatedDiameterMax() {
            return estimatedDiameterMax;
        }

        public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
            this.estimatedDiameterMax = estimatedDiameterMax;
        }

    }

    public class Miles {

        @SerializedName("estimated_diameter_min")
        @Expose
        private Double estimatedDiameterMin;
        @SerializedName("estimated_diameter_max")
        @Expose
        private Double estimatedDiameterMax;

        public Double getEstimatedDiameterMin() {
            return estimatedDiameterMin;
        }

        public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
            this.estimatedDiameterMin = estimatedDiameterMin;
        }

        public Double getEstimatedDiameterMax() {
            return estimatedDiameterMax;
        }

        public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
            this.estimatedDiameterMax = estimatedDiameterMax;
        }

    }

    public class Feet {

        @SerializedName("estimated_diameter_min")
        @Expose
        private Double estimatedDiameterMin;
        @SerializedName("estimated_diameter_max")
        @Expose
        private Double estimatedDiameterMax;

        public Double getEstimatedDiameterMin() {
            return estimatedDiameterMin;
        }

        public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
            this.estimatedDiameterMin = estimatedDiameterMin;
        }

        public Double getEstimatedDiameterMax() {
            return estimatedDiameterMax;
        }

        public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
            this.estimatedDiameterMax = estimatedDiameterMax;
        }

    }

}
