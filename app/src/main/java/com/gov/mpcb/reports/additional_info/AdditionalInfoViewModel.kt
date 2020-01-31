package com.gov.mpcb.reports.additional_info

import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer


class AdditionalInfoViewModel : BaseViewModel<AdditionalInfoNavigator>() {

    private var userData: String =  PreferencesHelper.getPreferences(Constants.USER, "").toString()
    private var user: LoginResponse = Gson().fromJson(userData, LoginResponse::class.java)


    private val visitId = PreferencesHelper.getLongPreference(Constants.VISIT_ID).toInt()
    private val indusImisId = PreferencesHelper.getStringPreference(Constants.INDUS_IMIS_ID, "")

    fun submitReport(
        reportRequest: ReportRequest?
    ) {
//        val reportData = PreferencesHelper.getStringPreference(Constants.REPORT_KEY, "")

        reportRequest?.let {
            reportRequest.userId = user.userId
            reportRequest.visitId = visitId
            reportRequest.indusImisId = indusImisId!!

            dialogMessage.value = "Report Submitting..."
            dialogVisibility.value = true

            mDisposable.add(DataProvider.submitReport(
                reportRequest,
                Consumer {
                    dialogVisibility.value = false
                    //If report submitted successfully, update the list to show status as visited
                    if (it.status) {
                        PreferencesHelper.setBooleanPreference(Constants.FORM_COMPLETE_STATUS, true)
                        mNavigator!!.onSubmitReportSuccess(it.message)
                    }
                },
                Consumer {
                    checkError(it)
                })
            )
        }
    }
}