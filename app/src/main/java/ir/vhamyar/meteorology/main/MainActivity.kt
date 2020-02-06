package ir.vhamyar.meteorology.main

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.nlopez.smartlocation.SmartLocation
import ir.vhamyar.meteorology.Adapter
import ir.vhamyar.meteorology.R
import ir.vhamyar.meteorology.model.current.Current
import ir.vhamyar.meteorology.model.forecast.Forecast
import ir.vhamyar.meteorology.model.geocode.GeoCode
import ir.vhamyar.meteorology.retrofit.ApiService
import ir.vhamyar.meteorology.util.Helper.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindWidgets()
        checkPermissions()
    }

    private fun bindWidgets() {
        setSupportActionBar(toolbar_container)
        refresh.setOnRefreshListener {
            if (isConnected(this@MainActivity)) {
                if (isGpsEnabled(this@MainActivity)) {
                    findLocation()
                } else {
                    showSnackBar(container, resources.getString(R.string.gps_connection_text))
                }
            } else {
                showSnackBar(container, resources.getString(R.string.connect_to_network_text))
            }
            refresh.isRefreshing = false
        }

        if (!isConnected(this)) {
            showSnackBar(container, getString(R.string.connect_to_network_text))
            return
        }
        if (!isGpsEnabled(this)) {
            showSnackBar(container, getString(R.string.turn_on_gps))
            return
        }

    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQ_CODE
            )
        } else {
            findLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            findLocation()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ finish() }, 1500)
        }
    }

    private fun findLocation() {
        if (!isGpsEnabled(this)) {
            showSnackBar(container, getString(R.string.gps_connection_text))
            return
        }
        progressBar.visibility = View.VISIBLE

        SmartLocation.with(this).location().oneFix().start {
            val latitude = it.latitude
            val longitude = it.longitude

            findCity(latitude, longitude)
        }

    }

    private fun findCity(lat: Double, lon: Double) {
        Retrofit.Builder()
                .baseUrl(GEO_CODE_BASE_URL)
                .client(OkHttpClient.Builder()
                        .callTimeout(5, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
                .getGeoCode(String.format("%s+%s", lat, lon))
                .enqueue(object : Callback<GeoCode> {

                    override fun onResponse(call: Call<GeoCode>, response: Response<GeoCode>) {
                        if (response.isSuccessful && response.body() != null) {
                            val city = response.body()!!.results[0].components.city
                            getDataByCity(city)
                        }
                        progressBar.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<GeoCode>, t: Throwable) {
                        Log.d(TAG, "onFailure: getGeoCode" + t.localizedMessage!!)
                        progressBar.visibility = View.GONE
                    }
                })
    }

    private fun getDataByCity(city: String) {
        getCurrentData(city)
        getForecastData(city)
    }

    private fun getCurrentData(mCity: String) {
        Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
                .getCurrentData(mCity)
                .enqueue(object : Callback<Current> {
                    override fun onResponse(call: Call<Current>, response: Response<Current>) {
                        if (response.isSuccessful && response.body() != null) {
                            showCurrentWeather(response)
                        } else {
                            showSnackBar(container, resources.getString(R.string.city_not_found))
                            return
                        }
                        Log.d("tag", "onResponse:\ncurrent data ok")
                    }

                    override fun onFailure(call: Call<Current>, t: Throwable) {
                        Log.d("tag", "onFailure: current\n" + t.message)
                        progressBar.visibility = View.GONE
                    }
                })
    }

    private fun showCurrentWeather(response: Response<Current>) {
        if (response.body() != null) {
            tv_city.text = response.body()!!.name
            tv_temp.text = convert(String.format(Locale.getDefault(), "%d${resources.getString(R.string.celsius)}", response.body()!!.main.temp!!.toInt()))
            tv_desc.text = response.body()!!.weather[0].description
            tv_humidity_value.text = String.format("%s%%", convert(response.body()!!.main.humidity!!.toString()))
            findViewById<View>(R.id.img_humidity).visibility = View.VISIBLE
            tv_pressure_value.text = String.format("%shPa", convert(response.body()!!.main.pressure!!.toString()))
            findViewById<View>(R.id.img_pressure).visibility = View.VISIBLE
            tv_wind_speed_value.text = String.format("%sm/sec", convert(response.body()!!.wind.speed!!.toString()))
            findViewById<View>(R.id.img_wind_speed).visibility = View.VISIBLE

            val icon = response.body()!!.weather[0].id.toString()
            Log.d("icon", "id = $icon")
            setWeatherIcon(this, weather_icon, icon)
        }
    }

    private fun getForecastData(mCity: String) {
        Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
                .getForecastData(mCity)
                .enqueue(object : Callback<Forecast> {
                    override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("tag", "onResponse: forecast\n" + response.raw().toString())
                            showForecastOnList(response)
                        }
                    }

                    override fun onFailure(call: Call<Forecast>, t: Throwable) {
                        Log.d("tag", "onFailure: forecast\n" + t.message)
                        progressBar.visibility = View.GONE
                    }
                })
    }

    private fun showForecastOnList(response: Response<Forecast>) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = Adapter(this, response.body()!!.list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options search_menu from XML
        menuInflater.inflate(R.menu.search_menu, menu)
        menuInflater.inflate(R.menu.option_menu, menu)

        // Get the SearchView and set the searchable configuration
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // Assumes current activity is the searchable activity
        searchView.isIconifiedByDefault = false // Do not iconify the widget; expand it by default
        searchView.queryHint = resources.getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (isConnected(this@MainActivity)) {
                    if (!progressBar.isShown) {
                        progressBar.visibility = View.VISIBLE
                    }
                    getCurrentData(query.trim().toLowerCase(Locale.getDefault()))
                    getForecastData(query.trim().toLowerCase(Locale.getDefault()))

                    searchView.setQuery("", true)
                    searchView.clearFocus()

                    if (progressBar.isShown) {
                        progressBar.visibility = View.GONE
                    }
                } else {
                    showSnackBar(container, resources.getString(R.string.connect_to_network_text))
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // TODO: 23/04/2019 suggest cities from text file
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.other_products) {
            showOtherProducts(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
