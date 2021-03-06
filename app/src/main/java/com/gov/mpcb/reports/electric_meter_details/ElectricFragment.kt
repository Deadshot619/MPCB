package com.gov.mpcb.reports.electric_meter_details


import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentElectricBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.parseToDouble
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal


class ElectricFragment : BaseFragmentReport<FragmentElectricBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_electric
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ElectricFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_7

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_7)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
        }
    }

    private fun setListener() {
        //If visit Status is true, then do not enable these listeners as it will enable
        //the textviews.
        if (!visitStatus) {
            mBinding.rgSeparateMeter.setOnCheckedChangeListener { group, checkedId ->
                report.data.routineReport.electrictMeterProvided =
                    if (checkedId == R.id.rbSeparateYes) {
                        mBinding.edtMeterReading.isEnabled = true
                        1
                    } else {
                        mBinding.edtMeterReading.isEnabled = false
                        mBinding.edtMeterReading.setText("")
                        0
                    }
            }
        }
    }

    private fun onSubmit() {
        report.data.routineReport.electrictMeterReading =
            mBinding.edtMeterReading.text.toString().parseToDouble()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_7,
                reportStatus = true
            )//Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_8, visitReportId)
        }
    }

    private fun validate(): Boolean {
        //If industry category is selected as 'Closed', do not validate the fields
        if (!isSelectedIndustryCategoryClosed(report)) {

            //Electric Meter Provided
            if (report.data.routineReport.electrictMeterProvided == null) {
                showMessage("Select Electrical Meter Provided")
                return false
            }

            if (report.data.routineReport.electrictMeterProvided == 1) {
                if (mBinding.edtMeterReading.text.isNullOrEmpty()) {
                    showMessage("Enter Meter Reading")
                    return false
                } else if (!isDecimal(mBinding.edtMeterReading.text.toString())) {
                    //Check if input value is correct Decimal Value.
                    showMessage("Invalid Meter Reading")
                    return false
                }
            }
        }
        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        //If visit status is Visited, then show the data retrieved from Api
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports != null){
            mBinding.run{
                reports?.data?.routineReport?.run {

//                    Seperate Electrical Meter Provided
                    if (electrictMeterProvided == 1){
                        rgSeparateMeter.check(R.id.rbSeparateYes)
                    }else{
                        rgSeparateMeter.check(R.id.rbSeparateNo)
                    }

//                    Meter Reading
                    edtMeterReading.setText(electrictMeterReading.toString())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //set data to views in onStart
        setDataToViews()
    }
}
