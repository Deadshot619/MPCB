package com.example.mpcb.reports.oms_stack

import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsStackBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSStackFragment : BaseFragment<FragmentOmsStackBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {


    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_oms_stack
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSStackFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_10)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)

        //set report variable data
        setReportVariableData(visitReportId)

        setListener()
        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_11, visitReportId) }
        }
    }

    private fun setListener() {
        mBinding.rgOnlineSys.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsaApplicable = if (checkedId == R.id.rbOSA) {
                showHideView(true)
                1
            } else {
                showHideView(false)
                0
            }
        }
        mBinding.rgWhetherOnlineSys.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsaInstalled = if (checkedId == R.id.rbWOSA) {
                mBinding.run {
                    //Connectivity Visible
                    txtConnectivity.visibility = View.VISIBLE
                    cbCPCB.visibility = View.VISIBLE
                    cbMPCB.visibility = View.VISIBLE
                }
                1
            } else {
                mBinding.run {
                    //Connectivity not Visible
                    txtConnectivity.visibility = View.GONE
                    cbCPCB.visibility = View.GONE
                    cbMPCB.visibility = View.GONE

                    //clear selection
                    cbCPCB.isChecked = false
                    cbMPCB.isChecked = false
                }
                0
            }
        }
        mBinding.rgRmtCal.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.remoteCalApplicable =
                if (checkedId == R.id.rbRmtCalYes) 1 else 0
        }
        mBinding.rgSensorPlaced.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.sensorPlaced =
                if (checkedId == R.id.rbSensorPlacedYes) 1 else 0
        }
        mBinding.rgWSMS.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.stackFacilityExist = if (checkedId == R.id.rbWSMSYes) 1 else 0
        }
        mBinding.rgWCalSys.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.calFacExist = if (checkedId == R.id.rbWCalSysYes) 1 else 0
        }
        mBinding.cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsaCpcb = if (isChecked) 1 else 0
        }
        mBinding.cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsaMpcb = if (isChecked) 1 else 0
        }
    }

    private fun showHideView(showView: Boolean) {
        if (showView) {
            mBinding.txtWhetherOnlineSys.visibility = View.VISIBLE
            mBinding.rgWhetherOnlineSys.visibility = View.VISIBLE
            mBinding.txtRmtCal.visibility = View.VISIBLE
            mBinding.rgRmtCal.visibility = View.VISIBLE
            mBinding.txtSensorPlaced.visibility = View.VISIBLE
            mBinding.rgSensorPlaced.visibility = View.VISIBLE
            mBinding.txtWSMS.visibility = View.VISIBLE
            mBinding.rgWSMS.visibility = View.VISIBLE
            mBinding.txtWCalSys.visibility = View.VISIBLE
            mBinding.rgWCalSys.visibility = View.VISIBLE

            mBinding.rgWhetherOnlineSys.check(R.id.rbWOSNA)
        } else {
            mBinding.txtWhetherOnlineSys.visibility = View.GONE
            mBinding.rgWhetherOnlineSys.visibility = View.GONE
            mBinding.txtRmtCal.visibility = View.GONE
            mBinding.rgRmtCal.visibility = View.GONE
            mBinding.txtSensorPlaced.visibility = View.GONE
            mBinding.rgSensorPlaced.visibility = View.GONE
            mBinding.txtWSMS.visibility = View.GONE
            mBinding.rgWSMS.visibility = View.GONE
            mBinding.txtWCalSys.visibility = View.GONE
            mBinding.rgWCalSys.visibility = View.GONE
            mBinding.txtConnectivity.visibility = View.GONE
            mBinding.cbCPCB.visibility = View.GONE
            mBinding.cbMPCB.visibility = View.GONE
        }
    }

    private fun onSubmit() {

        if (validate()) {
            if (report.data.routineReport.omsaApplicable == 0) {
                report.data.routineReport.omsaInstalled = 0
                report.data.routineReport.remoteCalApplicable = 0
                report.data.routineReport.sensorPlaced = 0
                report.data.routineReport.stackFacilityExist = 0
                report.data.routineReport.calFacExist = 0
                report.data.routineReport.omsaCpcb = 0
                report.data.routineReport.omsaMpcb = 0
            }
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_10,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_11, visitReportId)
        }
    }

    private fun validate(): Boolean {

        if (!mBinding.rbOSA.isChecked && !mBinding.rbOSNA.isChecked) {
            showMessage("Select Online Monitoring System")
            return false
        }
        if (mBinding.rbOSA.isChecked) {
//            OMS Installed
            if (!mBinding.rbWOSA.isChecked && !mBinding.rbWOSNA.isChecked) {
                showMessage("Select Online Monitoring System Installed")
                return false
            }

            if (mBinding.rbWOSA.isChecked){
//                Connectivity
                if (!mBinding.cbCPCB.isChecked && !mBinding.cbMPCB.isChecked) {
                    showMessage("Select Connectivity")
                    return false
                }
            }

//            Remote Caliberation Applicable
            if (!mBinding.rbRmtCalYes.isChecked && !mBinding.rbRmtCalNo.isChecked) {
                showMessage("Select Remote Caliberation Applicable")
                return false
            }

//            Sensor Properly Placed
            if (!mBinding.rbSensorPlacedYes.isChecked && !mBinding.rbSensorPlacedNo.isChecked) {
                showMessage("Select Sensor Properly Placed")
                return false
            }

//            Stack Monitoring System exists
            if (!mBinding.rbWSMSYes.isChecked && !mBinding.rbWSMSNo.isChecked) {
                showMessage("Select proper stack monitoring system exists")
                return false
            }

//            Calibration Facility exists
            if (!mBinding.rbWCalSysYes.isChecked && !mBinding.rbWCalSysNo.isChecked) {
                showMessage("Select calibration facility exists")
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
        reports = getReportData(visitReportId)

        if (reports != null){
            mBinding.run{
                reports?.data?.routineReport?.run {

                    //                        OMS
                    if (omsaApplicable == 1){
                        rgOnlineSys.check(R.id.rbOSA)
                    }else{
                        rgOnlineSys.check(R.id.rbOSNA)
                    }

//                    OMS Installed
                    if (omsaInstalled == 1){
                        rgWhetherOnlineSys.check(R.id.rbWOSA)
                    }else{
                        rgWhetherOnlineSys.check(R.id.rbWOSNA)
                    }

//                    Remote Calioberation Applicable
                    if (remoteCalApplicable == 1){
                        rgRmtCal.check(R.id.rbRmtCalYes)
                    }else{
                        rgRmtCal.check(R.id.rbRmtCalNo)
                    }

//                    Sensor Properly Placed
                    if (sensorPlaced == 1){
                        rgSensorPlaced.check(R.id.rbSensorPlacedYes)
                    }else{
                        rgSensorPlaced.check(R.id.rbSensorPlacedNo)
                    }

//                     Proper stack monitoring system
                    if (stackFacilityExist == 1){
                        rgWSMS.check(R.id.rbWSMSYes)
                    }else{
                        rgWSMS.check(R.id.rbWSMSNo)
                    }

//                     Proper stack monitoring system
                    if (calFacExist == 1){
                        rgWCalSys.check(R.id.rbWCalSysYes)
                    }else{
                        rgWCalSys.check(R.id.rbWCalSysNo)
                    }

//                    Connectivity
                    cbCPCB.isChecked = omsaCpcb == 1
                    cbMPCB.isChecked = omsaMpcb == 1
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