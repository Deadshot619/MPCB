package com.gov.mpcb.reports.previous_legal_actions

import android.app.DatePickerDialog
import android.util.Log
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentPreviousLegalBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import kotlinx.android.synthetic.main.button_save_next_layout.*
import java.util.*

class PreviousLegalFragment : BaseFragmentReport<FragmentPreviousLegalBinding, ReportsPageViewModel>(),
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
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_16)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.edtActionInitiated.setOnClickListener { showDateDialog() }

        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_17, visitReportId) }
        }
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

        if (/*validate() ||*/ true) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_16,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_17, visitReportId)
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
        //If visit status is Visited, then show the data retrieved from Api
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }


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