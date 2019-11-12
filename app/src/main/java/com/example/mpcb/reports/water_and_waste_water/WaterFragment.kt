package com.example.mpcb.reports.water_and_waste_water

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentWasteWaterAspectBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class WaterFragment : BaseFragment<FragmentWasteWaterAspectBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {


    override fun getLayoutId() = R.layout.fragment_waste_water_aspect
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@WaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_4)

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }


    private fun onSubmit() {
        report.data.routineReport.generationIndustrialAsConsent =
            mBinding.edtIndustryConcent.text.toString()
        report.data.routineReport.generationIndustrialActual =
            mBinding.edtIndustryActual.text.toString()
        report.data.routineReport.generationIndustrialAsConsentCooling =
            mBinding.edtIndustrialConcent.text.toString()
        report.data.routineReport.generationIndustrialActualCooling =
            mBinding.edtIndustrialActual.text.toString()
        report.data.routineReport.generationDomesticAsConsent =
            mBinding.edtDomesticConcent.text.toString()
        report.data.routineReport.generationDomesticActual =
            mBinding.edtDomesticActual.text.toString()

        if (validate()) {
            saveReportData()
            addReportFragment(Constants.REPORT_5)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.generationIndustrialAsConsent.isEmpty()) {
            showMessage("Enter Industry Process as per Concent")
            return false
        }
        if (report.data.routineReport.generationIndustrialActual.isEmpty()) {
            showMessage("Enter Industry Process Actual")
            return false
        }
        if (report.data.routineReport.generationIndustrialAsConsentCooling.isEmpty()) {
            showMessage("Enter Industrial Cooling as per Concent")
            return false
        }
        if (report.data.routineReport.generationIndustrialActualCooling.isEmpty()) {
            showMessage("Enter Industrial Cooling Actual")
            return false
        }
        if (report.data.routineReport.generationDomesticAsConsent.isEmpty()) {
            showMessage("Enter Domestic as per Concent")
            return false
        }
        if (report.data.routineReport.generationDomesticActual.isEmpty()) {
            showMessage("Enter Domestic Actual")
            return false
        }
        return true
    }

}