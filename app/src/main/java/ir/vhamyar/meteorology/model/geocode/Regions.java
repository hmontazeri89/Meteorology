
package ir.vhamyar.meteorology.model.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Regions {

    @SerializedName("ASIA")
    @Expose
    private String aSIA;
    @SerializedName("IR")
    @Expose
    private String iR;
    @SerializedName("SOUTHERN_ASIA")
    @Expose
    private String sOUTHERNASIA;
    @SerializedName("WORLD")
    @Expose
    private String wORLD;

    public String getASIA() {
        return aSIA;
    }

    public void setASIA(String aSIA) {
        this.aSIA = aSIA;
    }

    public String getIR() {
        return iR;
    }

    public void setIR(String iR) {
        this.iR = iR;
    }

    public String getSOUTHERNASIA() {
        return sOUTHERNASIA;
    }

    public void setSOUTHERNASIA(String sOUTHERNASIA) {
        this.sOUTHERNASIA = sOUTHERNASIA;
    }

    public String getWORLD() {
        return wORLD;
    }

    public void setWORLD(String wORLD) {
        this.wORLD = wORLD;
    }

}
