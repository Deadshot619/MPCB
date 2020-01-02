package com.gov.mpcb.dashboard

import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.DashboardDataRequest
import com.gov.mpcb.network.request.UserListHodRequest
import com.gov.mpcb.network.response.DashboardDataResponse
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel : BaseViewModel<DashboardNavigator>() {

    private val dashboardModel = DashboardDataResponse()
    fun getDashboardModel() = dashboardModel

    private var user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
    private var userModel = Gson().fromJson(user, LoginResponse::class.java)

    fun getDashboardData(fromDate: String) {
        user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        userModel = Gson().fromJson(user, LoginResponse::class.java)

        val request = DashboardDataRequest()

        request.userId =
            //Check if the user has SubordinateOfficers
            if (userModel.hasSubbordinateOfficers == 1 && userModel.hasSubbordinateOfficers != -1)
                //Check if the user selected is 'ALL', if yes then set userdata as of HOD
                //else set userId of selected user
                if (DashboardUtils.dashboardSpinnerSelectedUserId == 0)
                    userModel.userId.toString()
                else
                    DashboardUtils.dashboardSpinnerSelectedUserId.toString()
            else
                userModel.userId.toString()

        request.fromDate = fromDate

        val time = SimpleDateFormat("yyyy-MM-dd").parse(fromDate)
        val selectedCalender = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        selectedCalender.time = time
        when {
            selectedCalender.get(Calendar.YEAR) < currentCalendar.get(Calendar.YEAR) -> request.toDate =
                selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
                    Calendar.MONTH
                ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ).toString()
            selectedCalender.get(Calendar.MONTH) < currentCalendar.get(Calendar.MONTH) -> request.toDate =
                selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
                    Calendar.MONTH
                ) + 1).toString() + "-" + selectedCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ).toString()
            selectedCalender.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) ->
                request.toDate =
                    selectedCalender.get(Calendar.YEAR).toString() + "-" + (selectedCalender.get(
                    Calendar.MONTH) + 1).toString() + "-" + selectedCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH).toString()
            else -> mNavigator!!.showAlert("Future Date Selected!")
        }

        //Check if the user selected is 'ALL', if yes then set jurisdiction stat as 1
        request.jurisdictionStat = if (DashboardUtils.dashboardSpinnerSelectedUserId == 0) {
            userModel.hasSubbordinateOfficers
        } else 0

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

    /**
     * Method to get User Data List of HOD
     */
    fun getUserListData() {

        val request = UserListHodRequest().apply {
            userId = userModel.userId.toString()
        }

        mDisposable.add(DataProvider.getUserListDataForHods(
            request = request,

            success = Consumer {
                if (it.status == 1 && !it.users.isNullOrEmpty()) {
//                    _userSpinnerData.value = it.users
                    mNavigator?.setSpinnerData(it.users)
                }
            },

            error = Consumer { checkError(it) }
        ))
    }
}