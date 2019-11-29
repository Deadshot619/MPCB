package com.example.mpcb.my_visits

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.base.MPCBApp
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.MyVisitRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.network.response.MyVisitResponse
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

    private val visitList = MutableLiveData<MyVisitResponse>()

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    fun getVisitList() = visitList

    /**
     * This method is used to get 'My Visits' data from server of the given month & year.
     *
     * @param fromDate Takes the Start date as first parameter.
     * @param toDate Takes the End date as Second parameter.
     */
    fun getVisitListData(fromDate: String, toDate: String) {
        val request = MyVisitRequest()
        request.userId = user.userId.toString()
        //TODO 13/11/19 Change these dates to current month in development build
        request.fromDate = fromDate
        request.toDate = toDate
//        request.fromDate = fromDate
//        val time = SimpleDateFormat("yyyy-MM-dd").parse(fromDate)
//        val selectedCalender = Calendar.getInstance()
//        val currentCalendar = Calendar.getInstance()
//        selectedCalender.time = time
//        when {
//            selectedCalender.get(Calendar.YEAR) < currentCalendar.get(Calendar.YEAR) ->
//                request.toDate =
//                    selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
//                    Calendar.MONTH
//                    ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
//                        Calendar.DAY_OF_MONTH
//                    ).toString()
//            selectedCalender.get(Calendar.MONTH) < currentCalendar.get(Calendar.MONTH) ->
//                request.toDate =
//                    selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
//                        Calendar.MONTH
//                    ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
//                        Calendar.DAY_OF_MONTH
//                    ).toString()
//            selectedCalender.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) ->
//                request.toDate = Constants.getCurrentDate("yyyy-MM-dd")
//            else -> mNavigator!!.showAlert("Future Date Selected!")
//        }
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

        //Make a RequestBody of each value to be sent.
        val requestId = RequestBody.create(
            MediaType.parse("text/plain"), ""
        )
        val userId = RequestBody.create(
            MediaType.parse("text/plain"), user.userId.toString()
        )
        val visitId = RequestBody.create(
            MediaType.parse("text/plain"), visitSchedulerId.toString()
        )
        val latitude = RequestBody.create(
            MediaType.parse("text/plain"), PreferencesHelper.getCurrentLatitude()
        )
        val longitude = RequestBody.create(
            MediaType.parse("text/plain"), PreferencesHelper.getCurrentLongitude()
        )

        val file = File(path)
        selfieImageBody = RequestBody.create(MediaType.parse("image/*"), file)
        selfieImagePart =
            MultipartBody.Part.createFormData("selfie_img", file.name, selfieImageBody)

        dialogMessage.value = "Checking In..."
        dialogVisibility.value = true
        mNavigator!!.dismissCheckinDialog()
        mDisposable.add(DataProvider.checkIn(
            requestId = requestId,
            userId = userId,
            visitId = visitId,
            latitude = latitude,
            longitude = longitude,
            selfieImagePart = selfieImagePart,
            success = Consumer {
                dialogVisibility.value = false
                mNavigator!!.onCheckInSuccess(it.message)
            },
            error = Consumer { checkError(it) })
        )
    }
}