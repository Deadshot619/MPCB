package com.gov.mpcb.dashboard

import com.gov.mpcb.base.BaseNavigator
import com.gov.mpcb.network.response.DashboardDataResponse
import com.gov.mpcb.network.response.Users

interface DashboardNavigator : BaseNavigator {

    fun showAlert(errorMessage: String)

    fun dashBoardTest(DashboardDataResponse: DashboardDataResponse)

    //Sets the data in Spinner
    fun setSpinnerData(users: List<Users>)

}