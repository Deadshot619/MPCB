package com.example.mpcb.reports.additional_info

import com.example.mpcb.base.BaseNavigator


interface AdditionalInfoNavigator : BaseNavigator {

    fun onSubmitReportSuccess(msg: String)
}