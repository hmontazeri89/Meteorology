
package ir.vhamyar.meteorology.model.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roadinfo {

    @SerializedName("drive_on")
    @Expose
    private String driveOn;
    @SerializedName("road")
    @Expose
    private String road;
    @SerializedName("road_type")
    @Expose
    private String roadType;
    @SerializedName("speed_in")
    @Expose
    private String speedIn;

    public String getDriveOn() {
        return driveOn;
    }

    public void setDriveOn(String driveOn) {
        this.driveOn = driveOn;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public String getSpeedIn() {
        return speedIn;
    }

    public void setSpeedIn(String speedIn) {
        this.speedIn = speedIn;
    }

}
