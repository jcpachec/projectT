package devinc.punktadmin;


/**
 * Created by maquinte on 5/25/2015.
 */
public class GeoPoint {
    private double longitude;
    private double latitude;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getLatitude() { return this.latitude;  }
    public void setLatitude(double latitude){
       this.latitude = latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

}
