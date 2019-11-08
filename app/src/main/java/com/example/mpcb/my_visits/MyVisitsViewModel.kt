package com.example.mpcb.my_visits

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.base.MPCBApp
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.MyVisitRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyVisitsViewModel : BaseViewModel<MyVisitsNavigator>() {

    private val visitList = MutableLiveData<ArrayList<MyVisitModel>>()

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    fun getVisitList() = visitList

    fun getVisitListData() {
        val request = MyVisitRequest()
        request.userId = user.userId.toString()
        request.fromDate = "2017-09-01"
        request.toDate = "2019-09-30"
        dialogVisibility.value = true
        dialogMessage.value = "Fetching List..."
        mDisposable.add(DataProvider.getVisitList(request, Consumer {
            dialogVisibility.value = false
            visitList.value = it
        }, Consumer { checkError(it) }))
    }

    fun getCurrentLocation() {
        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(MPCBApp.instance)

        val location = mFusedLocationProviderClient.lastLocation
        location.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentLocation = task.result
                if (currentLocation != null) {
                    PreferencesHelper.setCurrentLatitude(currentLocation.latitude.toString())
                    PreferencesHelper.setCurrentLongitude(currentLocation.longitude.toString())
                }
            }
        }
    }

    fun onVisitItemClick(visitItem: MyVisitModel) {
        mNavigator!!.onVisitItemClicked(visitItem)
    }

    fun onCheckInClick(model: MyVisitModel) {
        mNavigator!!.onCheckInClicked(model)
    }

    fun onSubmitClicked(path: String, visitSchedulerId: Long) {
        val selfieImageBody: RequestBody
        val selfieImagePart: MultipartBody.Part

        val userId =
            RequestBody.create(MediaType.parse("multipart/form-data"), user.userId.toString())
        val visitId =
            RequestBody.create(MediaType.parse("multipart/form-data"), visitSchedulerId.toString())
        val latitude = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            PreferencesHelper.getCurrentLatitude()
        )
        val longitude = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            PreferencesHelper.getCurrentLongitude()
        )

        val file = File(path)
        selfieImageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        selfieImagePart =
            MultipartBody.Part.createFormData("selfie_img", file.name, selfieImageBody)
        dialogMessage.value = "Checking In..."
        dialogVisibility.value = true
        mDisposable.add(
            DataProvider.checkIn(userId, visitId, latitude, longitude, selfieImagePart, Consumer {
                dialogVisibility.value = false
                mNavigator!!.onCheckInSuccess(it.message)
            }, Consumer {
                dialogVisibility.value = false
                Log.e("Error", it.message)
            })
        )
    }
}