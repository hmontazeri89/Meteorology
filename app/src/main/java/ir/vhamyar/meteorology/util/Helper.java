package ir.vhamyar.meteorology.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import ir.vhamyar.meteorology.R;

public class Helper {

    // constants
    public static final String TAG = "tag";
    private static final String CAFE_BAZAAR_DEVELOPER_ID = "hmontazeri";
    public static final String FIND_CITY_API_URL = "https://ipapi.co/json/";
    public static final int REQ_CODE = 591;

//    current -> http://api.openweathermap.org/data/2.5/weather?q=yazd&units=metric&lang=fa&appid=637ae624a4593b7fd4dfc6ccaf14db10
//    forecast -> http://api.openweathermap.org/data/2.5/forecast?q=yazd&units=metric&lang=fa&appid=637ae624a4593b7fd4dfc6ccaf14db10

//    https://api.opencagedata.com/geocode/v1/json?q=35.731694+51.331177&key=3b63f6a12b6e47519bcd8e5cfd256026&language=fa

    private static final String OPEN_WEATHER_API_KEY = "637ae624a4593b7fd4dfc6ccaf14db10";
    public static final String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String GEO_CODE_BASE_URL = "https://api.opencagedata.com/geocode/v1/";
    public static final String ACTION_NETWORK = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_GPS = "android.location.PROVIDERS_CHANGED";

    public static void showOtherProducts(Context context) {
        String uri = "bazaar://collection?slug=by_author&aid=" + CAFE_BAZAAR_DEVELOPER_ID;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (appInstalledOrNot(context, uri)) {
            intent.setData(Uri.parse(uri));
            intent.setPackage("com.farsitel.bazaar");
            context.startActivity(intent);
        } else {
            intent.setData(Uri.parse("https://cafebazaar.ir/developer/hmontazeri/?l=fa"));
            context.startActivity(intent);
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    }

    private static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showSnackBar(View view, String text) {
        final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.setActionTextColor(Color.WHITE);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();

    }

    public static String convert(String faNumbers) {
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };

        for (String[] num : mChars) {

            faNumbers = faNumbers.replace(num[0], num[1]);

        }

        return faNumbers;
    }

    public static void setWeatherIcon(Context mContext, ImageView imageView, String iconId) {
        switch (iconId.charAt(0)) {
            case '2': // Thunderstorm
                Glide.with(mContext).load(R.drawable.thunderstorm).into(imageView);
                break;
            case '3': // Drizzle
            case '5' : // Rain
                Glide.with(mContext).load(R.drawable.drizzle).into(imageView);
                break;
            case '6' : // Snow
                Glide.with(mContext).load(R.drawable.snow).into(imageView);
                break;
            case '7' : // Atmosphere
                Glide.with(mContext).load(R.drawable.fog).into(imageView);
                break;
        }
        switch (Integer.parseInt(iconId)) {
            case 800:
                Glide.with(mContext).load(R.drawable.sun).into(imageView);
                break;
            case 801:
            case 802:
            case 803:
            case 804:
                Glide.with(mContext).load(R.drawable.cloudy).into(imageView);
                break;
        }
    }
}
