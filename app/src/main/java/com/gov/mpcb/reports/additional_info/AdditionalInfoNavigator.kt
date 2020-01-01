package com.gov.mpcb.reports.additional_info

import com.gov.mpcb.base.BaseNavigator


interface AdditionalInfoNavigator : BaseNavigator {

    fun onSubmitReportSuccess(msg: String)
}