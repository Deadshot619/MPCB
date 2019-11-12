package com.example.mpcb.reports.oms_water


import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsWaterBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSWaterFragment : BaseFragment<FragmentOmsWaterBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_oms_water
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_6)

        setListener()
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
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
                if (checkedId == R.id.rbOMSInstalledApplicable) 1 else 0
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
            mBinding.txtConnectivity.visibility = View.VISIBLE
            mBinding.linLayConnectivity.visibility = View.VISIBLE
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
                reportKey = Constants.REPORT_6,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_7)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.omsaApplicable == 1) {
            if (report.data.routineReport.omswInstalled == null) {
                showMessage("Select Online Monitoring System Installed")
                return false
            }
            if (report.data.routineReport.remoteCalApplicableWater == null) {
                showMessage("Select Remote Caliberation Applicable")
                return false
            }
            if (report.data.routineReport.sensorPlacedWater == null) {
                showMessage("Sensor Properly Placed")
                return false
            }
        }

        return true
    }
}
