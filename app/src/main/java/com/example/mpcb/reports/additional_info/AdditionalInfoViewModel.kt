package com.example.mpcb.reports.additional_info

import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AdditionalInfoViewModel : BaseViewModel<AdditionalInfoNavigator>() {

    private var userData: String =  PreferencesHelper.getPreferences(Constants.USER, "").toString()
    private var user: LoginResponse = Gson().fromJson(userData, LoginResponse::class.java)


    private val visitId = PreferencesHelper.getLongPreference(Constants.VISIT_ID).toInt()
    private val indusImisId = PreferencesHelper.getStringPreference(Constants.INDUS_IMIS_ID, "")

    fun submitReport(
        reportRequest: ReportRequest?,
        file: File
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
                    mNavigator!!.onSubmitReportSuccess(it.message)
                    //If report submitted successfully, update the list to show status as visited
                    if (it.status) {
                        uploadVisitFile(file)
                        PreferencesHelper.setBooleanPreference(Constants.FORM_COMPLETE_STATUS, true)
                    }
                },
                Consumer {
                    checkError(it)
                })
            )
        }
    }


    fun uploadVisitFile(file: File) {
        //Make a RequestBody of each value to be sent.

        //File
        val visitReportBodyLocal: RequestBody
        val visitReportPartLocal: MultipartBody.Part

        val requestIdLocal = RequestBody.create(
            MediaType.parse("text/plain"), ""
        )
        val userIdLocal = RequestBody.create(
            MediaType.parse("text/plain"), user.userId.toString()
        )
        val visitIdLocal = RequestBody.create(
            MediaType.parse("text/plain"), visitId.toString()
        )
        val industryImisIdLocal = RequestBody.create(
            MediaType.parse("text/plain"), indusImisId.toString()
        )

        visitReportBodyLocal = RequestBody.create(MediaType.parse("image/pdf/*"), file)
        visitReportPartLocal =
            MultipartBody.Part.createFormData("file", file.name, visitReportBodyLocal)

        dialogMessage.value = "Uploading File..."
        dialogVisibility.value = true

        mDisposable.add(DataProvider.uploadVisitReportFile(
            requestId = requestIdLocal,
            userId = userIdLocal,
            visitId = visitIdLocal,
            indusImisId = industryImisIdLocal,
            visitReportFile = visitReportPartLocal,
            success = Consumer {
                dialogVisibility.value = false
                mNavigator!!.onSubmitReportSuccess(it.message)
                //If report submitted successfully, update the list to show status as visited
                if (it.status) {
                    PreferencesHelper.setBooleanPreference(Constants.FORM_COMPLETE_STATUS, true)
                }
            },
            error = Consumer { checkError(it) })
        )
    }
}