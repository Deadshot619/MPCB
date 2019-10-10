package com.example.mpcb.dashboard

import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.DashboardDataRequest
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer

class DashboardViewModel : BaseViewModel<DashboardNavigator>() {

    private val dashboardModel = DashboardDataResponse()

    fun getDashboardModel() = dashboardModel

    fun getDashboardData() {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        val userModel = Gson().fromJson(user, LoginResponse::class.java)
        val request = DashboardDataRequest()
        request.userId = userModel.userId.toString()
        request.fromDate = "2019-10-01"
        request.toDate = "2019-10-31"
        request.jurisdictionStat = 0
        mDisposable.add(DataProvider.getDashboardData(request, Consumer {
            dashboardModel.run {
                totalVisit = it.totalVisit
                completedVisit = it.completedVisit
                pendingVisit = it.pendingVisit
                visitedOnTime = it.visitedOnTime
                reportedUploadedOnTime = it.reportedUploadedOnTime
            }
        }, Consumer { checkError(it) }))
    }
}