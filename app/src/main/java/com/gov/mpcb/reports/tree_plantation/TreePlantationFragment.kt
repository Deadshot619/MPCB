package com.gov.mpcb.reports.tree_plantation


import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentTreePlantationBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal


class TreePlantationFragment : BaseFragmentReport<FragmentTreePlantationBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {


    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_tree_plantation
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreePlantationFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_14)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)


        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_15, visitReportId) }
        }
    }

    private fun onSubmit() {
        report.data.routineReport.treePlantationPlotArea = mBinding.edtTotalPlot.text.toString()
        report.data.routineReport.treePlantationBuiltArea = mBinding.edtBuiltArea.text.toString()
        report.data.routineReport.treePlantationGreenBeltArea =
            mBinding.edtGreenBelt.text.toString()
        report.data.routineReport.treePlantationPlantationNo =
            mBinding.edtPlantationDone.text.toString()
        report.data.routineReport.treePlantationProposedPlantation =
            mBinding.edtProposedPlantation.text.toString()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_14,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_15, visitReportId)
        }
    }

    /**
     * This method is used to validate the input fields
     */
    private fun validate(): Boolean {
        if (report.data.routineReport.treePlantationPlotArea.isNullOrEmpty()) {
            showMessage("Enter Total Plot Area in sq.mt")
            return false
        }else if (!isDecimal(report.data.routineReport.treePlantationPlotArea)){
                showMessage("Please enter valid value")
                return false
        }


        if (report.data.routineReport.treePlantationBuiltArea.isNullOrEmpty()) {
            showMessage("Enter Built Up Area in sq.mt")
            return false
        }else if (!isDecimal(report.data.routineReport.treePlantationBuiltArea)){
            showMessage("Please enter valid value")
            return false
        }


        if (report.data.routineReport.treePlantationGreenBeltArea.isNullOrEmpty()) {
            showMessage("Enter Green Belt Area in sq.mt")
            return false
        }else if (!isDecimal(report.data.routineReport.treePlantationGreenBeltArea)){
            showMessage("Please enter valid value")
            return false
        }


        if (report.data.routineReport.treePlantationPlantationNo.isNullOrEmpty()) {
            showMessage("Enter Plantation done in No")
            return false
        }else if (!isDecimal(report.data.routineReport.treePlantationPlantationNo)){
            showMessage("Please enter valid value")
            return false
        }

        if (report.data.routineReport.treePlantationProposedPlantation.isNullOrEmpty()) {
            showMessage("Enter Proposed Plantation")
            return false
        }else if (!isDecimal(report.data.routineReport.treePlantationProposedPlantation)){
            showMessage("Please enter valid value")
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


        if (reports != null){
            mBinding.run{
                reports?.data?.routineReport?.run{
                    //Set the value to texts
                    edtTotalPlot.setText(treePlantationPlotArea)
                    edtBuiltArea.setText(treePlantationBuiltArea)
                    edtGreenBelt.setText(treePlantationGreenBeltArea)
                    edtPlantationDone.setText(treePlantationPlantationNo)
                    edtProposedPlantation.setText(treePlantationProposedPlantation)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
