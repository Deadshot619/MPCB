package com.example.mpcb.dashboard

import com.example.mpcb.base.BaseNavigator
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.Users

interface DashboardNavigator : BaseNavigator {

    fun showAlert(errorMessage: String)

    fun dashBoardTest(DashboardDataResponse: DashboardDataResponse)

    //Sets the data in Spinner
    fun setSpinnerData(users: List<Users>)

}