package com.example.mpcb.reports.oms_water


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsWaterBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSWaterFragment : BaseFragment<FragmentOmsWaterBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private lateinit var report: ReportRequest

    override fun getLayoutId() = R.layout.fragment_oms_water
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_6)
        report = ReportRequest()

        setListener()
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setListener() {
        mBinding.rgOMS.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omswApplicable = if (checkedId == R.id.rbOMSApplicable) 1 else 0
        }
        mBinding.rgOMSInstalled.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omswInstalled = if (checkedId == R.id.rbOMSInstalledApplicable) 1 else 0
        }
        mBinding.rgRemoteCalliberation.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.remoteCalApplicableWater = if (checkedId == R.id.rbRemoteYes) 1 else 0
        }
        mBinding.rgSensorPlaced.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.sensorPlacedWater = if (checkedId == R.id.rbSensorYes) 1 else 0
        }
        mBinding.cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omswCpcb = if (isChecked) 1 else 0
        }
        mBinding.cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omswMpcb = if (isChecked) 1 else 0
        }
    }

    private fun onSubmit() {

        addReportFragment(Constants.REPORT_7)
    }
}
