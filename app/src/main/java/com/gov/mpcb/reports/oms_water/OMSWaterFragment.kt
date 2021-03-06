package com.gov.mpcb.reports.oms_water


import android.view.View
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentOmsWaterBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class OMSWaterFragment : BaseFragmentReport<FragmentOmsWaterBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {


    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_oms_water
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_6

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_6)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setListener()
        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
        }
    }

    private fun setListener() {
        mBinding.rgOMS.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omswApplicable =
                if (checkedId == R.id.rbOMSApplicable) {
                    showHideView(true)
                    1
                } else {
                    showHideView(false)
                    0
                }
        }
        mBinding.rgOMSInstalled.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omswInstalled =
                if (checkedId == R.id.rbOMSInstalledApplicable) {
                    mBinding.run {
                        //Connectivity Visible
                        txtConnectivity.visibility = View.VISIBLE
                        linLayConnectivity.visibility = View.VISIBLE
                    }
                    1
                } else {
                    mBinding.run {
                        //Connectivity not Visible
                        txtConnectivity.visibility = View.GONE
                        linLayConnectivity.visibility = View.GONE

                        //clear selection
                        cbCPCB.isChecked = false
                        cbMPCB.isChecked = false
                    }
                    0
                }
        }
        mBinding.rgRemoteCalliberation.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.remoteCalApplicableWater =
                if (checkedId == R.id.rbRemoteYes) 1 else 0
        }
        mBinding.rgSensorPlaced.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.sensorPlacedWater =
                if (checkedId == R.id.rbSensorYes) 1 else 0
        }
        mBinding.cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omswCpcb = if (isChecked) 1 else 0
        }
        mBinding.cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omswMpcb = if (isChecked) 1 else 0
        }
    }

    private fun showHideView(showView: Boolean) {
        if (showView) {
            mBinding.txtOMSInstalled.visibility = View.VISIBLE
            mBinding.rgOMSInstalled.visibility = View.VISIBLE
            mBinding.txtRemoteCaliber.visibility = View.VISIBLE
            mBinding.rgRemoteCalliberation.visibility = View.VISIBLE
            mBinding.txtSensorPlaced.visibility = View.VISIBLE
            mBinding.rgSensorPlaced.visibility = View.VISIBLE
//            mBinding.txtConnectivity.visibility = View.VISIBLE
//            mBinding.linLayConnectivity.visibility = View.VISIBLE

            //Check Not installed Radio B   utton
            mBinding.rgOMSInstalled.check(R.id.rbOMSInstalledNotApplicable)
        } else {
            mBinding.txtOMSInstalled.visibility = View.GONE
            mBinding.rgOMSInstalled.visibility = View.GONE
            mBinding.txtRemoteCaliber.visibility = View.GONE
            mBinding.rgRemoteCalliberation.visibility = View.GONE
            mBinding.txtSensorPlaced.visibility = View.GONE
            mBinding.rgSensorPlaced.visibility = View.GONE
            mBinding.txtConnectivity.visibility = View.GONE
            mBinding.linLayConnectivity.visibility = View.GONE
        }
    }

    private fun onSubmit() {
        if (report.data.routineReport.omswApplicable == 0) {
            report.data.routineReport.omswInstalled = 0
            report.data.routineReport.remoteCalApplicableWater = 0
            report.data.routineReport.sensorPlacedWater = 0
            report.data.routineReport.omswCpcb = 0
            report.data.routineReport.omswMpcb = 0
        }

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_6,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_7, visitReportId)
        }
    }

    private fun validate(): Boolean {

        //If industry category is selected as 'Closed', do not validate the fields
        if (!isSelectedIndustryCategoryClosed(report)) {

            mBinding.run {
                //OMS
                if (!rbOMSApplicable.isChecked && !rbOMSNotApplicable.isChecked) {
                    showMessage("Select Online Monitoring System")
                    return false
                }

                if (rbOMSApplicable.isChecked) {
                    //OMS Installed
                    if (!rbOMSInstalledApplicable.isChecked && !rbOMSInstalledNotApplicable.isChecked) {
                        showMessage("Select Online Monitoring System Installed")
                        return false
                    }

                    if (rbOMSInstalledApplicable.isChecked) {
                        //Connectivity
                        if (!cbMPCB.isChecked && !cbCPCB.isChecked) {
                            showMessage("Select Connectivity")
                            return false
                        }
                    }

//              Remote Caliberation Applicable
                    if (!rbRemoteYes.isChecked && !rbRemoteNo.isChecked) {
                        showMessage("Select Remote Caliberation Applicable")
                        return false
                    }

                    //Sensor Properly Placed
                    if (!rbSensorYes.isChecked && !rbSensorNo.isChecked) {
                        showMessage("Sensor Properly Placed")
                        return false
                    }
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

//                    OMS
                    if (omswApplicable == 1){
                        rgOMS.check(R.id.rbOMSApplicable)
                    }else{
                        rgOMS.check(R.id.rbOMSNotApplicable)
                    }

//                    OMS Installed
                    if (omswInstalled == 1){
                        rgOMSInstalled.check(R.id.rbOMSInstalledApplicable)
                    }else{
                        rgOMSInstalled.check(R.id.rbOMSInstalledNotApplicable)
                    }

//                    Remote Calioberation Applicable
                    if (remoteCalApplicableWater == 1){
                        rgRemoteCalliberation.check(R.id.rbRemoteYes)
                    }else{
                        rgRemoteCalliberation.check(R.id.rbRemoteNo)
                    }

//                    Sensor Properly Placed
                    if (sensorPlacedWater == 1){
                        rgSensorPlaced.check(R.id.rbSensorYes)
                    }else{
                        rgSensorPlaced.check(R.id.rbSensorNo)
                    }

//                    Connectivity
                    cbCPCB.isChecked = omswCpcb == 1
                    cbMPCB.isChecked = omswMpcb == 1
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
