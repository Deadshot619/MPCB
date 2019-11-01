package com.example.mpcb.reports.oms_stack

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

        mBinding.btnSubmit.setOnClickListener { onSubmit() }

    }

    private fun onSubmit() {

        mBinding.rgOnlineSys.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsaApplicable = if (checkedId == R.id.rbOSA) 1 else 0
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
            report.data.routineReport.omsamMpcb = if (isChecked) 1 else 0
        }

        saveReportData()

        addReportFragment(Constants.REPORT_11)
    }

}