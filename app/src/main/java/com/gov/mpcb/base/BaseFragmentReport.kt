package com.gov.mpcb.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.gson.Gson
import com.gov.mpcb.databinding.ButtonSaveNextLayoutBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper

abstract class BaseFragmentReport<T : ViewDataBinding, V : BaseViewModel<*>> :
    BaseFragment<T, V>() {

    protected lateinit var report: ReportRequest

    /**
     * @return returns 'true' if Industry Category is selected as 'Closed' otherwise 'false'
     */
    protected fun isSelectedIndustryCategoryClosed(report: ReportRequest): Boolean {
        return report.data.industryCategoryReselect == "16"
    }

    /**
     * Gets & Saves the current Report number from [ReportsPageActivity]
     */
    protected var currentReportNumber: Int
        get() = (activity as ReportsPageActivity).currentReportNumber
        set(value) {
            (activity as ReportsPageActivity).currentReportNumber = value
        }

    protected lateinit var visitReportId: String

    /**
     * This variable will be used to check if Visit Status if VISITED or not.
     * Mostly used in Reports to then enable/disable views
     */
    protected val visitStatus: Boolean
        get() = PreferencesHelper.getBooleanPreference(Constants.VISIT_STATUS)

    /**
     * This method is to be implemented in the child classes.
     * This method should retrieve & set data to views in Reports.
     */
    protected abstract fun setDataToViews()

    /**
     * This method is used to set data to [report] variable
     */
    protected fun setReportVariableData(visitReportId: String) {
        report = getReportData(visitReportId) ?: ReportRequest()
    }

    /**
     * This method will be used in onClick of Submit/Next button in report fragments to
     * go to next fragment.
     */
    protected fun addReportFragmentLocal(
        constantReportValue: Int,
        visitReportId: String,
        addToBackStack: Boolean = false
    ) {
        //Put the Visit Report ID in bundle to share to Fragments
        val bundle = Bundle()
        bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
        (activity as ReportsPageActivity).addReportFragment(constantReportValue, addToBackStack, bundle)
    }

    /**
     * This method is used to save the reports data & its status in the
     * shared preference.
     * @param reportKey 0 by default. Specifies the Report
     * @param reportStatus false by default. Indicates whether the report status is completed
     *                      or not
     */
    protected fun saveReportData(
        reportNo: String,
        reportKey: Int = 0,
        reportStatus: Boolean = false
    ) {
        //Reports are saved according to their Report No.
        PreferencesHelper.setPreferences(
            key = reportNo,
            value = Gson().toJson(report)
        )

        //saves the status of current report
        PreferencesHelper.setReportFlagStatus(
            reportNo = reportNo,
            reportKey = reportKey,
            reportStatus = reportStatus
        )
    }

    /**
     * This method is used to retrieve Reports data from Shared Prefs
     *
     * @return [ReportRequest] returns an object of ReportRequest
     */
    protected fun getReportData(reportNo: String): ReportRequest? {
//        val reports = PreferencesHelper.getPreferences(Constants.REPORT_KEY, "")
        val reports = PreferencesHelper.getPreferences(reportNo, "")
        return Gson().fromJson(reports as String, ReportRequest::class.java)
    }


    /**
     * This method Enables/Disables the views in  a viewGroup depending on visitStatus
     */
    protected fun disableViews(viewGroup: ViewGroup) {
        //If true, disable all controls!
        if (visitStatus)
            Constants.disableEnableControls(false, viewGroup)
    }

    /**
     * This method shows or hides the Next button depending on Visit Status.
     */
    protected fun showNextAndPreviousButton(
        btnSaveNextLayoutBinding: ButtonSaveNextLayoutBinding,
        showPreviousButton: Boolean = true
    ) {
        //If true show 'Next & Previous' button & hide 'Save' button
        if (visitStatus) {
            btnSaveNextLayoutBinding.run {
                btnNext.run {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        (activity as ReportsPageActivity).goToDirectedReport(currentReportNumber, true)
                    }
                }
                    btnSubmit.visibility = View.GONE
            }
        } else {
            btnSaveNextLayoutBinding.run {
                btnNext.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        }

        //if true, Show previous button & set click listener to it
        if (showPreviousButton)
            btnSaveNextLayoutBinding.btnPrevious.run {
                visibility = View.VISIBLE
                setOnClickListener {
                    (activity as ReportsPageActivity).goToDirectedReport(currentReportNumber)
                }
            }
    }
}