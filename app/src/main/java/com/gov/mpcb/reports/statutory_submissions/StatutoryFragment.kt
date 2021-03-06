package com.gov.mpcb.reports.statutory_submissions


import android.app.DatePickerDialog
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentStatutoryBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import java.util.*


class StatutoryFragment : BaseFragmentReport<FragmentStatutoryBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private var reports: ReportRequest? = null

    private val HAZARDOUS_WASTE = 1
    private val ENVIRONMENT_REPORT = 2

    override fun getLayoutId() = R.layout.fragment_statutory
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@StatutoryFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_15

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_15)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.edtHazardousWaste.setOnClickListener { showDateDialog(HAZARDOUS_WASTE) }
        mBinding.edtEnvironmentReport.setOnClickListener { showDateDialog(ENVIRONMENT_REPORT) }
        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
        }
    }

    private fun showDateDialog(id: Int) {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    when (id) {
                        HAZARDOUS_WASTE -> mBinding.edtHazardousWaste.setText("$year-${month + 1}-$dayOfMonth")
                        ENVIRONMENT_REPORT -> mBinding.edtEnvironmentReport.setText("$year-${month + 1}-$dayOfMonth")
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun onSubmit() {
        report.data.routineReport.hwAnnualReturnDate = mBinding.edtHazardousWaste.text.toString()
        report.data.routineReport.envStatementReport = mBinding.edtEnvironmentReport.text.toString()

        /*if (validate()) {
        }*/
        saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_15,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_16, visitReportId)

    }

    private fun validate(): Boolean {
        //If industry category is selected as 'Closed'
        if (!isSelectedIndustryCategoryClosed(report)) {

            if (report.data.routineReport.hwAnnualReturnDate.isNullOrEmpty()) {
                showMessage("Enter Hazardous Waste Annual Returns")
                return false
            }
            if (report.data.routineReport.envStatementReport.isNullOrEmpty()) {
                showMessage("Enter Environment Statement Report")
                return false
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
                reports?.data?.routineReport?.run{
                    //Set the value to texts
                    edtHazardousWaste.setText(hwAnnualReturnDate)
                    edtEnvironmentReport.setText(envStatementReport)
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