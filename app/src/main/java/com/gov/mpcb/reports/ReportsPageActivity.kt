package com.gov.mpcb.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivityReportsPageBinding
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
import com.gov.mpcb.utils.showMessage

class ReportsPageActivity : BaseActivity<ActivityReportsPageBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private lateinit var _visitReportId: String
    private val visitReportId: String
        get() = _visitReportId

    //Variable to hold current report number
    var currentReportNumber: Int = -1

    //Bundle to be used for fragments
    private lateinit var bundle: Bundle

    override fun getLayoutId() = R.layout.activity_reports_page
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ReportsPageActivity
    override fun onError(message: String) = showMessage(message)

    /**
     * This method navigates user to previous Report, if no previous report is available it
     * redirects to Visit Report page
     *
     * @param currentReportNo Takes currentReport Number as an argument
     */
    fun goToPreviousReport(currentReportNo: Int) {
        if (currentReportNo <= 1 || currentReportNo > 18)
            finish()
        else
            addReportFragment(currentReportNo - 1, false, bundle)
    }

    override fun onInternetError() {}

    private var reportPageNo = -1

    override fun onBinding() {
        if (intent != null && intent.extras != null) {
            _visitReportId = intent?.extras?.get(Constants.VISIT_REPORT_ID) as String

            mBinding.visitId.text = "#$visitReportId"
            reportPageNo = intent?.extras?.get(Constants.REPORTS_PAGE_KEY) as Int

            //Put the Visit Report ID in bundle to share to Fragments
            bundle = Bundle()
            bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)

            addReportFragment(reportPageNo, false, bundle)
        }

        setToolbar(reportPageNo)

        mBinding.run {
            //Back Button
            imgBack.setOnClickListener {
                onBackPressed()
            }

            //Exit Button
            btnClose.setOnClickListener {
                finish()
            }
        }
    }

    internal fun setToolbar(reportPage: Int) {
        mBinding.visitName.text = Constants.getReportByNo(this, reportPage)
        mBinding.reportCount.text = "$reportPage/18"
        mBinding.reportProgress.progress = reportPage
    }

    internal fun addReportFragment(
        reportKey: Int,
        addToBackStack: Boolean = false,
        bundle: Bundle? = null
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
            super.addReportFragment(fragment, addToBackStack, bundle)
        }
    }

    override fun onBackPressed() {  //GoTo previous report on click of back button
        goToPreviousReport(currentReportNumber)
    }
}
