package com.example.mpcb.dashboard

import com.example.mpcb.base.BaseNavigator
import com.example.mpcb.network.response.DashboardDataResponse

interface DashboardNavigator : BaseNavigator {

    fun showAlert(errorMessage: String)

    fun dashBoardTest(DashboardDataResponse: DashboardDataResponse)

}