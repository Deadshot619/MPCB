package com.example.mpcb.reports.additional_info

import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer


class AdditionalInfoViewModel : BaseViewModel<AdditionalInfoNavigator>() {

    fun submitReport(
        reportRequest: ReportRequest?
    ) {
        val userData = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        val user = Gson().fromJson(userData, LoginResponse::class.java)

//        val reportData = PreferencesHelper.getStringPreference(Constants.REPORT_KEY, "")

        reportRequest?.let {
            reportRequest.userId = user.userId
            reportRequest.visitId = PreferencesHelper.getLongPreference(Constants.VISIT_ID).toInt()
            reportRequest.indusImisId = PreferencesHelper.getStringPreference(Constants.INDUS_IMIS_ID, "")!!
            dialogMessage.value = "Report Submitting..."
            dialogVisibility.value = true

            mDisposable.add(DataProvider.submitReport(
                reportRequest,
                Consumer {
                    dialogVisibility.value = false
                    mNavigator!!.onSubmitReportSuccess(it.message)
                },
                Consumer {
                    checkError(it)
                })
            )
        }
    }
}