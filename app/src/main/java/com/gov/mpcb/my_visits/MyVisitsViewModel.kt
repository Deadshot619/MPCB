package com.gov.mpcb.my_visits

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.base.MPCBApp
import com.gov.mpcb.my_visits.MyVisitsUtils.Companion.myVisitsSpinnerSelectedUserId
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.MyVisitRequest
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.network.request.UserListHodRequest
import com.gov.mpcb.network.request.ViewVisitRequest
import com.gov.mpcb.network.response.CheckInfoModel
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.MyVisitModel
import com.gov.mpcb.network.response.MyVisitResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyVisitsViewModel : BaseViewModel<MyVisitsNavigator>() {

    private val visitList = MutableLiveData<MyVisitResponse>()
    fun getVisitList() = visitList

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    /**
     * This method is used to get 'My Visits' data from server of the given month & year.
     *
     * @param fromDate Takes the Start date as first parameter.
     * @param toDate Takes the End date as Second parameter.
     */
    fun getVisitListData(fromDate: String, toDate: String) {
        val request = MyVisitRequest()

        //If user has subordinate user, then take their ID
        request.userId =
            if (user.hasSubbordinateOfficers == 1 && user.hasSubbordinateOfficers != -1)
                myVisitsSpinnerSelectedUserId.toString()
            else
                user.userId.toString()

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
        mDisposable.add(DataProvider.getVisitList(
            request = request,
            success = Consumer {
                dialogVisibility.value = false
                visitList.value = it
            },
            error = Consumer {
                checkError(it)
            }))
    }

    /**
     * Method to get VisitReport Data
     */
    private fun getVisitItemData(viewModel: MyVisitModel) {
        //create request data
        val request = ViewVisitRequest().apply {
            userId =
                if (user.hasSubbordinateOfficers == 1 && user.hasSubbordinateOfficers != -1)
                    myVisitsSpinnerSelectedUserId.toString()
                else
                    user.userId.toString()
            indusImisId = viewModel.industryIMISId
            visitId = viewModel.visitSchedulerId
        }

        //show loading dialog
        dialogVisibility.value = true
        dialogMessage.value = "Fetching Data..."

        mDisposable.add(
            DataProvider.viewVisitReport(
                request = request,
                success = Consumer {
                    dialogVisibility.value = false
                    if (it.status) {
                        mNavigator?.onVisitItemClicked(viewModel)

                        Log.i("Hogya BC", it.data.toString())

                        //Store the report data received in Shared Pref
                        PreferencesHelper.setPreferences(
                            key = Constants.TEMP_VISIT_REPORT_DATA,
                            value = Gson().toJson(ReportRequest().apply { data = it.data })
                        )

                        mNavigator?.showAlert(it.message)
                    }
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
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

    /**
     * Method to run on click of Reports icon
     */
    fun onVisitItemClick(visitItem: MyVisitModel) {
//        mNavigator!!.onVisitItemClicked(visitItem)
        if (visitItem.checkInStatus == 1)
            if (visitItem.visitStatus == "Visited")
                getVisitItemData(visitItem)
            else {
                /*
                 * Check if the user is HOD user.
                 * If he is, he can only fill his Data & not other users Data.
                 * HOD can only view other user's Data & not Edit them.
                 */
                if (user.hasSubbordinateOfficers == 1)
                    if (user.userId != myVisitsSpinnerSelectedUserId){
                        mNavigator?.showAlert("HOD user cannot fill other user's data")
                        return
                    }
                mNavigator!!.onVisitItemClicked(visitItem)
            }
        else {
            /*
                 * Check if the user is HOD user.
                 * If he is, he can only fill his Data & not other users Data.
                 * HOD can only view other user's Data & not Edit them.
                 */
            if (user.hasSubbordinateOfficers == 1)
                if (user.userId != myVisitsSpinnerSelectedUserId){
                    mNavigator?.showAlert("HOD user cannot fill other user's data")
                    return
                }
            mNavigator!!.onError("Please Check in first!")
        }
    }

    fun onCheckInClick(model: MyVisitModel) {
        if (model.checkInStatus != 1) {
            if (user.hasSubbordinateOfficers == 1)
                if (user.userId != myVisitsSpinnerSelectedUserId){
                    mNavigator?.showAlert("HOD user cannot fill other user's data")
                    return
                }
            mNavigator!!.onCheckInClicked(model)
        }else {
            // mNavigator!!.onError("Already Checked In!")
             mNavigator!!.onCheckInClicked(model)

           /* val request = MyVisitRequest()
            request.userId = user.userId.toString()
            request.visitId = model.visitSchedulerId.toString()
            request.requestId = ""

            dialogVisibility.value = true
            dialogMessage.value = "Fetching CheckIn info ..."
            mDisposable.add(DataProvider.checkInInfo(request, Consumer {
                //dialogVisibility.value = true
               // mNavigator!!.onCheckInSuccess(it.message)
                mNavigator!!.onAlreadyCheckedIn(it.data)

            },
                Consumer { checkError(it) }))*/
        }
       // mNavigator!!.onAlreadyCheckedIn(model)



    }


    fun onCheckInfoClicked(model: MyVisitModel, dataCall:(CheckInfoModel)->CheckInfoModel)  {

        val request = MyVisitRequest().apply {
            userId =
                if (user.hasSubbordinateOfficers == 1 && user.hasSubbordinateOfficers != -1)
                    myVisitsSpinnerSelectedUserId.toString()
                else
                    user.userId.toString()
            visitId = model.visitSchedulerId.toString()
            requestId = ""
        }

        dialogVisibility.value = true
        dialogMessage.value = "Fetching CheckIn info ..."
        mDisposable.add(DataProvider.checkInInfo(request, Consumer {
            dialogVisibility.value = false
            dataCall(it.data)
            //dialogVisibility.value = true
            // mNavigator!!.onCheckInSuccess(it.message)
           // mNavigator!!.onAlreadyCheckedIn( it.data)
        },
            Consumer { checkError(it) }))
    }

    /**
     *  This method is used to delete file from directory
     */
    private fun deleteImageFile(imagePath: String){
        //Delete image file from directory
        File(imagePath).deleteRecursively()
    }

    fun onSubmitClicked(
        path: String,
        visitSchedulerId: Long
    ) {
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
                deleteImageFile(path)
            },
            error = Consumer {
                if (!it.message.isNullOrEmpty()){
                    if (it.message!!.contains("ENOENT")){
                        mNavigator?.onError("Please click a Picture first.")
                        dialogVisibility.value = false
                    }else{
                        checkError(it)
                    }
                }else{
                    checkError(it)
                }
            })
        )
    }

    /**
     * Method to get User Data List of HOD
     */
    fun getUserListData() {

        val request = UserListHodRequest().apply {
            userId = user.userId.toString()
        }

        mDisposable.add(DataProvider.getUserListDataForHods(
            request = request,

            success = Consumer {
                if (it.status == 1 && !it.users.isNullOrEmpty()) {
                    mNavigator?.setSpinnerData(it.users)
                }
            },

            error = Consumer { checkError(it) }
        ))
    }
}