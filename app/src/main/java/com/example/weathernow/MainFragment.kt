package com.example.weathernow

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weathernow.api.RetrofitHelper
import com.example.weathernow.api.WeatherAPI
import com.example.weathernow.databinding.FragmentMainBinding
import com.example.weathernow.models.Current
import com.example.weathernow.models.Location
import com.example.weathernow.models.WeatherResponse
import com.example.weathernow.repository.Response
import com.example.weathernow.repository.WeatherRepository
import com.example.weathernow.viewmodel.MainViewModel
import com.example.weathernow.viewmodel.MainViewModelFactory
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.Task
import java.util.*

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var currentLocation: Location? = null
    var PERMISSION_ID = 1080

    companion object {
        const val TAG = "MainFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        Log.d("MainFragment:", checkPermission().toString())
//            Log.d("MainFragment:",isLocationEnabled().toString())
        getLastLocation()
//        if (checkPermission()){
//
//        } else {
//            requestPermission()
//        }

    }

    fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    fun isLocationEnabled(): Boolean {
        var locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result == null) {
                        getLocationData()
                    } else {
                        fetchWeather(task.result.latitude, task.result.longitude)
                    }
//                        Log.d(TAG, "lat  => ${task.result.latitude} | long => ${task.result.longitude} ")

                } else {
                    Toast.makeText(requireContext(), "Something Went wrong", Toast.LENGTH_SHORT)
                        .show()
                }


////                    var location:Location? = task.result
//                    if(location == null){
////                        NewLocationData()
//                    }else{
//                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
//                        textView.text = "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)
//                    }
            }
        } else {
            requestPermission()
        }
    }

    private fun fetchWeather(lat: Double, long: Double) {
        Log.d(TAG, "fetched location city: ${getCityName(lat, long)}")
        mainViewModel.getCurrentWeather(getCityName(lat, long))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }

    }

    @SuppressLint("MissingPermission", "InlinedApi")
    fun getLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
//            binding.weatherLocation.text = locationResult.lastLocation
            fetchWeather(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude
            )

        }
    }

//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult?) {
//            super.onLocationResult(locationResult)
//            locationResult?.lastLocation?.let {
//                currentLocation = locationByGps
//            } ?: {
//                Log.d(TAG, "Location information isn't available.")
//            }
//        }
//    }


    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String = ""
        var countryName = ""
        var geoCoder = Geocoder(activity, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat, long, 3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:", "Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }


    private fun initialize() {
        val repository =
            (requireActivity().application as WeatherApplication).currentWeatherRepository
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository = repository)
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        mainViewModel.currentLive.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> {
                    binding.progressBar.visibility = GONE
                    Toast.makeText(activity, it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                }
                is Response.Success -> {
                    binding.progressBar.visibility = GONE
                    bindData(it.data)
                }
            }
        }

//        mainViewModel.getCurrentWeather("guwahati")
    }

    private fun bindData(data: WeatherResponse?) {
        with(binding) {
            weatherLocation.text = data?.request?.query
            Glide.with(requireActivity()).load(data!!.current.weather_icons[0]).into(weatherImage)
            weatherName.text = data?.current!!.weather_descriptions[0]
            weatherPercent.text = ("${data?.current?.temperature}°C")
            pressureValue.text = ("${data.current.pressure} \n Pressure")
            precipValue.text = ("${data?.current?.precip}\nPrecip")
            windValue.text = ("${data?.current?.wind_speed}\nWind Speed")

        }

    }


}