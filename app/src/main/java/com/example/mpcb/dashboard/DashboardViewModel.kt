package com.example.mpcb.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.DashboardDataRequest
import com.example.mpcb.network.request.UserListHodRequest
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.Users
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel : BaseViewModel<DashboardNavigator>() {

    private val dashboardModel = DashboardDataResponse()
    fun getDashboardModel() = dashboardModel

    //User List Data
    private val _userSpinnerData = MutableLiveData<List<Users>>()
    val userSpinnerData: LiveData<List<Users>>
        get() = _userSpinnerData

    private var user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
    private var userModel = Gson().fromJson(user, LoginResponse::class.java)

    fun getDashboardData(fromDate: String) {
        user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        userModel = Gson().fromJson(user, LoginResponse::class.java)

        val request = DashboardDataRequest()
        request.userId = userModel.userId.toString()
        request.fromDate = fromDate
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
        request.jurisdictionStat = 0
        dialogMessage.value = "Fetching..."
        dialogVisibility.value = true
        mDisposable.add(DataProvider.getDashboardData(request, Consumer {
            dialogVisibility.value = false


            dashboardModel.run {
                totalVisit = it.totalVisit
                completedVisit = it.completedVisit
                pendingVisit = it.pendingVisit
                visitedOnTime = it.visitedOnTime
                reportedUploadedOnTime = it.reportedUploadedOnTime

                mNavigator!!.dashBoardTest(dashboardModel)

            }

        }, Consumer { checkError(it) }))
    }

    fun getUserListData(){

        val request = UserListHodRequest().apply {
            userId = userModel.userId.toString()
        }

        mDisposable.add(DataProvider.getUserListDataForHods(
            request = request,

            success = Consumer {
                if (it.status == 1)
                    _userSpinnerData.value = it.users
            },
            error = Consumer { checkError(it) }
        ))
    }
}