package com.example.mpcb.my_visits

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
import java.text.SimpleDateFormat
import java.util.*

class MyVisitsViewModel : BaseViewModel<MyVisitsNavigator>() {

    private val visitList = MutableLiveData<ArrayList<MyVisitModel>>()

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    fun getVisitList() = visitList

    fun getVisitListData(fromDate: String) {
        val request = MyVisitRequest()
        request.userId = user.userId.toString()
        //TODO 13/11/19 Change these dates to current month in development build
        request.fromDate = "2017-11-01"
        request.toDate = "2019-11-11"
//        request.fromDate = fromDate
        val time = SimpleDateFormat("yyyy-MM-dd").parse(fromDate)
        val selectedCalender = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        selectedCalender.time = time
        if (selectedCalender.get(Calendar.YEAR) < currentCalendar.get(Calendar.YEAR))
            request.toDate =
                selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
                    Calendar.MONTH
                ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ).toString()
        else if (selectedCalender.get(Calendar.MONTH) < currentCalendar.get(Calendar.MONTH))
            request.toDate =
                selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
                    Calendar.MONTH
                ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ).toString()
        else if (selectedCalender.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH))
            request.toDate = Constants.getCurrentDate("yyyy-MM-dd")
        else
            mNavigator!!.showAlert("Future Date Selected!")
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
//        if (visitItem.checkInStatus == 1)
//            if (visitItem.visitStatus == "Visited")
//                mNavigator!!.onError("Already Visited!")
//            else
//                mNavigator!!.onVisitItemClicked(visitItem)
//        else
//            mNavigator!!.onError("Please Check in first!")
    }

    fun onCheckInClick(model: MyVisitModel) {

        if (model.checkInStatus != 1)
            mNavigator!!.onCheckInClicked(model)
        else
            mNavigator!!.onError("Already Checked In!")
    }

    fun onSubmitClicked(path: String, visitSchedulerId: Long) {
        val selfieImageBody: RequestBody
        val selfieImagePart: MultipartBody.Part

        val userId = user.userId.toString()
        val visitId = visitSchedulerId.toString()
        val latitude = PreferencesHelper.getCurrentLatitude()
        val longitude = PreferencesHelper.getCurrentLongitude()

        val file = File(path)
        selfieImageBody = RequestBody.create(MediaType.parse("image/*"), file)
        selfieImagePart =
            MultipartBody.Part.createFormData("selfie_img", file.name, selfieImageBody)

        dialogMessage.value = "Checking In..."
        dialogVisibility.value = true
        mNavigator!!.dismissCheckinDialog()
        mDisposable.add(DataProvider.checkIn(userId, visitId, latitude, longitude, selfieImagePart,
            Consumer {
                dialogVisibility.value = false
                mNavigator!!.onCheckInSuccess(it.message)
            }, Consumer { checkError(it) })
        )
    }
}