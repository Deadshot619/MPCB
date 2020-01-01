package com.gov.mpcb.utils.locationservice

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper


object LocationHelper {
    private fun getLocation(appContext: Context) {
        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(appContext)

        val location = mFusedLocationProviderClient.lastLocation
        location.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentLocation = task.result
                if (currentLocation != null) {
                    PreferencesHelper.setCurrentLatitude(currentLocation.latitude.toString())
                    PreferencesHelper.setCurrentLongitude(currentLocation.latitude.toString())
                }
            }
        }
    }

    fun isLocationProviderEnabled(mContext: Context): Boolean {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled: Boolean
        val networkEnabled: Boolean
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return (gpsEnabled || networkEnabled)
    }
}
