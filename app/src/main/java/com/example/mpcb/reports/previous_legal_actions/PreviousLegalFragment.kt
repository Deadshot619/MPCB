package com.example.mpcb.reports.previous_legal_actions

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentPreviousLegalBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import java.util.*

class PreviousLegalFragment : BaseFragment<FragmentPreviousLegalBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_previous_legal
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@PreviousLegalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        if (visitStatus)
            disableEnableControls(false, mBinding.categoryParentLay)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_16)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.edtActionInitiated.setOnClickListener { showDateDialog() }

        mBinding.btnSaveNext.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                    mBinding.edtActionInitiated.setText("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun onSubmit() {
        report.data.routineReport.actionInitiatedDate = mBinding.edtActionInitiated.text.toString()
        report.data.routineReport.specialCompliance = mBinding.edtSpecificCompliance.text.toString()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_16,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            val bundle = Bundle()
            bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
            addReportFragment(Constants.REPORT_17, bundle)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.actionInitiatedDate.isNullOrEmpty()) {
            showMessage("Enter Action Initiated Date")
            return false
        }
        if (report.data.routineReport.specialCompliance.isNullOrEmpty()) {
            showMessage("Enter Specific Compliance")
            return false
        }

        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        super.setDataToViews()
        reports = getReportData(visitReportId)

        if (reports != null) {
            mBinding.run {
                reports?.data?.routineReport?.run {
                    //Set the value to texts
                    edtActionInitiated.setText(actionInitiatedDate)
                    edtSpecificCompliance.setText(specialCompliance)
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