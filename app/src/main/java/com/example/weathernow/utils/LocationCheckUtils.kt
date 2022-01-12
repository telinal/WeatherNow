package com.example.weathernow.utils

import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

class LocationCheckUtils {
    companion object {
        fun isLocationEnabled(activity: FragmentActivity): Boolean {
            var locationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }
    }
}