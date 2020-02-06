package ir.vhamyar.meteorology.retrofit;

import ir.vhamyar.meteorology.model.city.CityModel;
import ir.vhamyar.meteorology.model.current.Current;
import ir.vhamyar.meteorology.model.forecast.Forecast;
import ir.vhamyar.meteorology.model.geocode.GeoCode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET(".")
    Call<CityModel> getCity();

    @GET("weather?units=metric&lang=fa&appid=637ae624a4593b7fd4dfc6ccaf14db10")
    Call<Current> getCurrentData(@Query("q") String q);

    @GET("forecast?units=metric&lang=fa&appid=637ae624a4593b7fd4dfc6ccaf14db10")
    Call<Forecast> getForecastData(@Query("q") String q);

    // https://api.opencagedata.com/geocode/v1/json?q=35.731694+51.331177&key=3b63f6a12b6e47519bcd8e5cfd256026&language=fa
    @GET("json?key=3b63f6a12b6e47519bcd8e5cfd256026&language=fa")
    Call<GeoCode> getGeoCode(@Query(value = "q", encoded = true) String q);
}
