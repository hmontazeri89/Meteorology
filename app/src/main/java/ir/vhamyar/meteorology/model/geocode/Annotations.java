
package ir.vhamyar.meteorology.model.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Annotations {

    @SerializedName("DMS")
    @Expose
    private DMS dMS;
    @SerializedName("MGRS")
    @Expose
    private String mGRS;
    @SerializedName("Maidenhead")
    @Expose
    private String maidenhead;
    @SerializedName("Mercator")
    @Expose
    private Mercator mercator;
    @SerializedName("OSM")
    @Expose
    private OSM oSM;
    @SerializedName("UN_M49")
    @Expose
    private UNM49 uNM49;
    @SerializedName("callingcode")
    @Expose
    private Integer callingcode;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("geohash")
    @Expose
    private String geohash;
    @SerializedName("qibla")
    @Expose
    private Double qibla;
    @SerializedName("roadinfo")
    @Expose
    private Roadinfo roadinfo;
    @SerializedName("sun")
    @Expose
    private Sun sun;
    @SerializedName("timezone")
    @Expose
    private Timezone timezone;
    @SerializedName("what3words")
    @Expose
    private What3words what3words;

    public DMS getDMS() {
        return dMS;
    }

    public void setDMS(DMS dMS) {
        this.dMS = dMS;
    }

    public String getMGRS() {
        return mGRS;
    }

    public void setMGRS(String mGRS) {
        this.mGRS = mGRS;
    }

    public String getMaidenhead() {
        return maidenhead;
    }

    public void setMaidenhead(String maidenhead) {
        this.maidenhead = maidenhead;
    }

    public Mercator getMercator() {
        return mercator;
    }

    public void setMercator(Mercator mercator) {
        this.mercator = mercator;
    }

    public OSM getOSM() {
        return oSM;
    }

    public void setOSM(OSM oSM) {
        this.oSM = oSM;
    }

    public UNM49 getUNM49() {
        return uNM49;
    }

    public void setUNM49(UNM49 uNM49) {
        this.uNM49 = uNM49;
    }

    public Integer getCallingcode() {
        return callingcode;
    }

    public void setCallingcode(Integer callingcode) {
        this.callingcode = callingcode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public Double getQibla() {
        return qibla;
    }

    public void setQibla(Double qibla) {
        this.qibla = qibla;
    }

    public Roadinfo getRoadinfo() {
        return roadinfo;
    }

    public void setRoadinfo(Roadinfo roadinfo) {
        this.roadinfo = roadinfo;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public What3words getWhat3words() {
        return what3words;
    }

    public void setWhat3words(What3words what3words) {
        this.what3words = what3words;
    }

}
