package com.example.mpcb.reports.electric_meter_details


import android.os.Bundle
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentElectricBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.parseToDouble
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal


class ElectricFragment : BaseFragment<FragmentElectricBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_electric
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ElectricFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_7)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setListener() {
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

    private fun onSubmit() {
        report.data.routineReport.electrictMeterReading =
            mBinding.edtMeterReading.text.toString().parseToDouble()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_7,
                reportStatus = true
            )//Put the Visit Report ID in bundle to share to Fragments
            val bundle = Bundle()
            bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
            addReportFragment(Constants.REPORT_8, bundle)
        }
    }

    private fun validate(): Boolean {
        //Electric Meter Provided
        if (report.data.routineReport.electrictMeterProvided == null) {
            showMessage("Select Electrical Meter Provided")
            return false
        }

        if (report.data.routineReport.electrictMeterProvided == 1) {
            if (mBinding.edtMeterReading.text.isNullOrEmpty()) {
                showMessage("Enter Meter Reading")
                return false
            }else if (!isDecimal(mBinding.edtMeterReading.text.toString())){
                //Check if input value is correct Decimal Value.
                showMessage("Invalid Meter Reading")
                return false
            }
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
