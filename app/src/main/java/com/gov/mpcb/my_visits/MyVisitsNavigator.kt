package com.gov.mpcb.my_visits

import com.gov.mpcb.base.BaseNavigator
import com.gov.mpcb.network.response.CheckInfoModel
import com.gov.mpcb.network.response.MyVisitModel
import com.gov.mpcb.network.response.Users

interface MyVisitsNavigator : BaseNavigator {

    fun onVisitItemClicked(viewModel: MyVisitModel)

    fun onCheckInClicked(model: MyVisitModel)

    fun onCheckInSuccess(msg: String)

    fun dismissCheckinDialog()

    fun showAlert(message: String)

    fun onAlreadyCheckedIn(model: CheckInfoModel)

    //Sets the data in Spinner
    fun setSpinnerData(users: List<Users>)

    fun checkSubordinateUsers()

    fun openUnvisitReviewDialog(data: MyVisitModel)
}