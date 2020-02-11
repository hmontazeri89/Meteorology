package ir.vhamyar.meteorology.util

import android.Manifest
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat

class GpsTracker(private val context: Context) : Service(), LocationListener {
    private lateinit var locationManager: LocationManager
    private var location: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var isGpsEnabled = false
    private var isNetworkEnabled = false
    private var canGetLocation = false
    @Throws(SecurityException::class)
    fun getLocation(): Location? {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGpsEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGpsEnabled && !isNetworkEnabled) {
            canGetLocation = false
            // no provider enabled
        } else {
            canGetLocation = true
            // first , get location using network provider
            if (!hasPermissions()) {
                location = null
                return location
            }
            if (isNetworkEnabled) {
                locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        TIME_BW_UPDATES.toLong(),
                        MIN_DISTANCE_FOR_UPDATE.toFloat(),
                        this)
                if (locationManager != null) {
                    location = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (location != null) {
                        latitude = location!!.latitude
                        longitude = location!!.longitude
                    }
                }
            }
            if (isGpsEnabled && location == null) {
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        TIME_BW_UPDATES.toLong(), MIN_DISTANCE_FOR_UPDATE.toFloat(), this)
                if (locationManager != null) {
                    location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        latitude = location!!.latitude
                        longitude = location!!.longitude
                    }
                }
            }
        }
        return location
    }

    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }
        return latitude
    }

    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }
        return longitude
    }

    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    fun hasPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun showGpsAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("GPS")
                .setMessage("GPS is not enabled. Do you want to go to Settings menu?")
                .setPositiveButton("Settings") { dialog, i ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    context.startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, i -> dialog.cancel() }
        builder.show()
    }

    @Throws(SecurityException::class)
    fun stopUsingGps() {
        if (locationManager != null && hasPermissions()) {
            locationManager!!.removeUpdates(this)
        }
    }

    override fun onLocationChanged(location: Location) {
        if (location != null) {
            Log.i(GpsTracker::class.java.simpleName,
                    "lat : " + location.latitude + ", lon = " + location.longitude)
        } else {
            Log.i(GpsTracker::class.java.simpleName, "location = null")
        }
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
    override fun onProviderEnabled(provider: String) {
        Log.i(GpsTracker::class.java.simpleName, "provider enabled : $provider")
    }

    override fun onProviderDisabled(provider: String) {
        Log.i(GpsTracker::class.java.simpleName, "provider disabled : $provider")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val TIME_BW_UPDATES = 1000 * 10 // 10 seconds
        const val MIN_DISTANCE_FOR_UPDATE = 10 // 10 meter
    }

    init {
        getLocation()
    }
}