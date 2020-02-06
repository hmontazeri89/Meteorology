package ir.vhamyar.meteorology.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityModel {


    @Expose
    @SerializedName("org")
    private String org;
    @Expose
    @SerializedName("asn")
    private String asn;
    @Expose
    @SerializedName("languages")
    private String languages;
    @Expose
    @SerializedName("currency")
    private String currency;
    @Expose
    @SerializedName("country_calling_code")
    private String country_calling_code;
    @Expose
    @SerializedName("utc_offset")
    private String utc_offset;
    @Expose
    @SerializedName("timezone")
    private String timezone;
    @Expose
    @SerializedName("longitude")
    private double longitude;
    @Expose
    @SerializedName("latitude")
    private double latitude;
    @Expose
    @SerializedName("in_eu")
    private boolean in_eu;
    @Expose
    @SerializedName("continent_code")
    private String continent_code;
    @Expose
    @SerializedName("country_name")
    private String country_name;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("region_code")
    private String region_code;
    @Expose
    @SerializedName("region")
    private String region;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("ip")
    private String ip;

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry_calling_code() {
        return country_calling_code;
    }

    public void setCountry_calling_code(String country_calling_code) {
        this.country_calling_code = country_calling_code;
    }

    public String getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(String utc_offset) {
        this.utc_offset = utc_offset;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean getIn_eu() {
        return in_eu;
    }

    public void setIn_eu(boolean in_eu) {
        this.in_eu = in_eu;
    }

    public String getContinent_code() {
        return continent_code;
    }

    public void setContinent_code(String continent_code) {
        this.continent_code = continent_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
