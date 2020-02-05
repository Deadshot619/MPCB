package com.gov.mpcb.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.gov.mpcb.databinding.ButtonSaveNextLayoutBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.additional_info.AdditionalInfoFragment
import com.gov.mpcb.reports.air_pollution.AirFragment
import com.gov.mpcb.reports.bank_guarantee_details.BGDFragment
import com.gov.mpcb.reports.disposal.DisposalFragment
import com.gov.mpcb.reports.electric_meter_details.ElectricFragment
import com.gov.mpcb.reports.hazardous_waste_management.HazardousFragment
import com.gov.mpcb.reports.industry.IndustryReportFragment
import com.gov.mpcb.reports.last_jvs_details.LastJVSFragment
import com.gov.mpcb.reports.non_hazardous_waste_management.NonHazardousFragment
import com.gov.mpcb.reports.oms_ambient_air.OMSAmbientAirFragment
import com.gov.mpcb.reports.oms_stack.OMSStackFragment
import com.gov.mpcb.reports.oms_water.OMSWaterFragment
import com.gov.mpcb.reports.previous_legal_actions.PreviousLegalFragment
import com.gov.mpcb.reports.production.ProductionFragment
import com.gov.mpcb.reports.statutory_submissions.StatutoryFragment
import com.gov.mpcb.reports.treatment.TreatmentFragment
import com.gov.mpcb.reports.tree_plantation.TreePlantationFragment
import com.gov.mpcb.reports.water_and_waste_water.WaterFragment
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper

abstract class BaseFragmentReport<T : ViewDataBinding, V : BaseViewModel<*>> :
    BaseFragment<T, V>() {

    protected lateinit var report: ReportRequest

    protected var currentReportNumber: Int = -1
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
    protected open fun setDataToViews() {}

    /**
     * This method is used to set data to [report] variable
     */
    protected fun setReportVariableData(visitReportId: String) {
        report = getReportData(visitReportId) ?: ReportRequest()
    }

    private fun addReportFragment(
        reportKey: Int,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    ) {
        Constants.run {
            val fragment = when (reportKey) {
                REPORT_1 -> IndustryReportFragment() //v
                REPORT_2 -> ProductionFragment()// listing //v
                REPORT_3 -> WaterFragment()//v
                REPORT_4 -> TreatmentFragment()//v
                REPORT_5 -> DisposalFragment()//v
                REPORT_6 -> OMSWaterFragment() //v
                REPORT_7 -> ElectricFragment() //v
                REPORT_8 -> LastJVSFragment() // listing
                REPORT_9 -> AirFragment() // listing //v
                REPORT_10 -> OMSStackFragment()//v
                REPORT_11 -> OMSAmbientAirFragment()// listing //v
                REPORT_12 -> HazardousFragment()// listing//v
                REPORT_13 -> NonHazardousFragment()// listing //v
                REPORT_14 -> TreePlantationFragment()//v
                REPORT_15 -> StatutoryFragment() //v
                REPORT_16 -> PreviousLegalFragment() //v
                REPORT_17 -> BGDFragment()// listing //v
                REPORT_18 -> AdditionalInfoFragment() //v
                else -> Fragment()
            }

            getBaseActivity().addReportFragment(fragment, addToBackStack, bundle)
        }
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
        addReportFragment(constantReportValue, bundle, addToBackStack)
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
//        PreferencesHelper.setPreferences(Constants.REPORT_KEY, Gson().toJson(report))

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
                btnNext.visibility = View.VISIBLE
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
                    if (currentReportNumber < 1 || currentReportNumber > 18)
                        activity?.onBackPressed()
                    else
                        addReportFragmentLocal(currentReportNumber - 1, visitReportId)
                }
            }
    }
}