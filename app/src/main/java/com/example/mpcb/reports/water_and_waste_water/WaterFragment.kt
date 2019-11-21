package com.example.mpcb.reports.water_and_waste_water

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentWasteWaterAspectBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

//import com.example.mpcb.utils.validations.isDecimalInputCorrect

class WaterFragment : BaseFragment<FragmentWasteWaterAspectBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_waste_water_aspect
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@WaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_4)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
//        showMessage(visitReportId)

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }


    private fun onSubmit() {
        report.data.routineReport.generationIndustrialAsConsent =
            mBinding.edtIndustryProcessConcent.text.toString()
        report.data.routineReport.generationIndustrialActual =
            mBinding.edtIndustryProcessActual.text.toString()
        report.data.routineReport.generationIndustrialAsConsentCooling =
            mBinding.edtIndustrialCoolingConcent.text.toString()
        report.data.routineReport.generationIndustrialActualCooling =
            mBinding.edtIndustrialCoolingActual.text.toString()
        report.data.routineReport.generationDomesticAsConsent =
            mBinding.edtDomesticConcent.text.toString()
        report.data.routineReport.generationDomesticActual =
            mBinding.edtDomesticActual.text.toString()

        if (validate() && validateFieldsFilledCorrect()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_4,
                reportStatus = true
            )
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

    /**
     * Method to check if the fields are correctly filled
     */
    private fun validateFieldsFilledCorrect(): Boolean {
        if(!isDecimal(report.data.routineReport.generationIndustrialAsConsent)){
            showMessage("Invalid Industry Process Input")
            return false
        }
        if (!isDecimal(report.data.routineReport.generationIndustrialActual)){
            showMessage("Invalid Industry Process Input")
            return false
        }
        if (!isDecimal(report.data.routineReport.generationIndustrialAsConsentCooling)){
            showMessage("Invalid Industry Cooling Input")
            return false
        }
        if (!isDecimal(report.data.routineReport.generationIndustrialActualCooling)){
            showMessage("Invalid Industry Cooling Input")
            return false
        }
        if (!isDecimal(report.data.routineReport.generationDomesticAsConsent)){
            showMessage("Invalid Domestic Input")
            return false
        }
        if (!isDecimal(report.data.routineReport.generationDomesticActual)){
            showMessage("Invalid Domestic Input")
            return false
        }
        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        super.setDataToViews()
        reports = getReportData()

        if (reports != null){
            mBinding.run{
                reports?.data?.routineReport?.run{
                    edtIndustryProcessConcent.setText(generationIndustrialAsConsent)
                    edtIndustryProcessActual.setText(generationIndustrialActual)
                    edtIndustrialCoolingConcent.setText(generationIndustrialAsConsentCooling)
                    edtIndustrialCoolingActual.setText(generationIndustrialActualCooling)
                    edtDomesticConcent.setText(generationDomesticAsConsent)
                    edtDomesticActual.setText(generationDomesticActual)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}