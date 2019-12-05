package com.example.mpcb.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.databinding.ActivityReportsPageBinding
import com.example.mpcb.reports.additional_info.AdditionalInfoFragment
import com.example.mpcb.reports.air_pollution.AirFragment
import com.example.mpcb.reports.bank_guarantee_details.BGDFragment
import com.example.mpcb.reports.disposal.DisposalFragment
import com.example.mpcb.reports.electric_meter_details.ElectricFragment
import com.example.mpcb.reports.hazardous_waste_management.HazardousFragment
import com.example.mpcb.reports.industry.IndustryReportFragment
import com.example.mpcb.reports.last_jvs_details.LastJVSFragment
import com.example.mpcb.reports.non_hazardous_waste_management.NonHazardousFragment
import com.example.mpcb.reports.oms_ambient_air.OMSAmbientAirFragment
import com.example.mpcb.reports.oms_stack.OMSStackFragment
import com.example.mpcb.reports.oms_water.OMSWaterFragment
import com.example.mpcb.reports.previous_legal_actions.PreviousLegalFragment
import com.example.mpcb.reports.production.ProductionFragment
import com.example.mpcb.reports.statutory_submissions.StatutoryFragment
import com.example.mpcb.reports.treatment.TreatmentFragment
import com.example.mpcb.reports.tree_plantation.TreePlantationFragment
import com.example.mpcb.reports.water_and_waste_water.WaterFragment
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class ReportsPageActivity : BaseActivity<ActivityReportsPageBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private lateinit var _visitReportId: String
    private val visitReportId: String
        get() = _visitReportId

    override fun getLayoutId() = R.layout.activity_reports_page
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ReportsPageActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    private var reportPageNo = -1

    override fun onBinding() {
        if (intent != null && intent.extras != null) {
            _visitReportId = intent?.extras?.get(Constants.VISIT_REPORT_ID) as String

            mBinding.visitId.text = "#$visitReportId"
            reportPageNo = intent?.extras?.get(Constants.REPORTS_PAGE_KEY) as Int
            addFragment(reportPageNo, visitReportId)
        }
        setToolbar(reportPageNo)

        mBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    internal fun setToolbar(reportPage: Int) {
        mBinding.visitName.text = Constants.getReportByNo(this, reportPage)
        mBinding.reportCount.text = "$reportPage/18"
        mBinding.reportProgress.progress = reportPage
    }

    private fun addFragment(reportPage: Int, visitReportId: String) {
        val fragment: Fragment = when (reportPage) {
            Constants.REPORT_1 -> IndustryReportFragment()
            Constants.REPORT_2 -> ProductionFragment()
            Constants.REPORT_3 -> WaterFragment()
            Constants.REPORT_4 -> TreatmentFragment()
            Constants.REPORT_5 -> DisposalFragment()
            Constants.REPORT_6 -> OMSWaterFragment()
            Constants.REPORT_7 -> ElectricFragment()
            Constants.REPORT_8 -> LastJVSFragment()
            Constants.REPORT_9 -> AirFragment()
            Constants.REPORT_10 -> OMSStackFragment()
            Constants.REPORT_11 -> OMSAmbientAirFragment()
            Constants.REPORT_12 -> HazardousFragment()
            Constants.REPORT_13 -> NonHazardousFragment()
            Constants.REPORT_14 -> TreePlantationFragment()
            Constants.REPORT_15 -> StatutoryFragment()
            Constants.REPORT_16 -> PreviousLegalFragment()
            Constants.REPORT_17 -> BGDFragment()
            Constants.REPORT_18 -> AdditionalInfoFragment()
            else -> Fragment()
        }

        //Put the Visit Report ID in bundle to share to Fragments
        val bundle = Bundle()
        bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)

        //Set fragment arguments
        fragment.arguments = bundle
        addReportFragment(fragment, false, bundle)
    }
}
