package ir.vhamyar.meteorology.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import ir.vhamyar.meteorology.model.LocationEvent
import org.greenrobot.eventbus.EventBus

const val TAG = "tag"

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand:")

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d(TAG, "onStartCommand: lat = $latitude, lon = $longitude")
                    EventBus.getDefault().post(LocationEvent(latitude, longitude))
                }
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        fusedLocationClient.requestLocationUpdates(LocationRequest(), locationCallback, Looper.getMainLooper())

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}


