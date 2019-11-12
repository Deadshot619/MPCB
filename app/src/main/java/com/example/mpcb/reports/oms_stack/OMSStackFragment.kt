package com.example.mpcb.reports.oms_stack

import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsStackBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSStackFragment : BaseFragment<FragmentOmsStackBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_oms_stack
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSStackFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_10)
        setListener()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }

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
            report.data.routineReport.omsaInstalled = if (checkedId == R.id.rbWOSA) 1 else 0
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
            mBinding.txtConnectivity.visibility = View.VISIBLE
            mBinding.cbCPCB.visibility = View.VISIBLE
            mBinding.cbMPCB.visibility = View.VISIBLE
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
        if (report.data.routineReport.omsaApplicable == 0) {
//            report.data.routineReport.omsaInstalled = 0
//            report.data.routineReport.remoteCalApplicable = 0
//            report.data.routineReport.sensorPlaced = 0
//            report.data.routineReport.stackFacilityExist = 0
//            report.data.routineReport.calFacExist = 0
//            report.data.routineReport.omsaCpcb = 0
//            report.data.routineReport.omsaMpcb = 0
        }

        if (validate()) {
            saveReportData()
            addReportFragment(Constants.REPORT_11)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.omsaApplicable == null) {
            showMessage("Select Online Monitoring System")
            return false
        }
        if (report.data.routineReport.omsaApplicable == 1) {
            if (report.data.routineReport.omsaInstalled == null) {
                showMessage("Select Online Monitoring System Installed")
                return false
            }
            if (report.data.routineReport.remoteCalApplicable == null) {
                showMessage("Select Remote Caliberation Applicable")
                return false
            }
            if (report.data.routineReport.sensorPlaced == null) {
                showMessage("Select Sensor Properly Placed")
                return false
            }
            if (report.data.routineReport.stackFacilityExist == null) {
                showMessage("Select proper stack monitoring system exists")
                return false
            }
            if (report.data.routineReport.calFacExist == null) {
                showMessage("Select calibration facility exists")
                return false
            }
        }


        return true
    }

}