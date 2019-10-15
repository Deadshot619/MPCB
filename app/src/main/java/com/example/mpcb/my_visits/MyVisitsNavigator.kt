package com.example.mpcb.my_visits

import com.example.mpcb.base.BaseNavigator
import com.example.mpcb.network.response.MyVisitModel

interface MyVisitsNavigator : BaseNavigator {

    fun onVisitItemClicked(viewModel: MyVisitModel)

    fun onCheckInClicked(model: MyVisitModel)
}