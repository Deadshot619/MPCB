package com.example.mpcb.reports.oms_water


import android.view.View
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


    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_oms_water
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_6)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
//        showMessage(visitReportId)

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
                reportNo = visitReportId,
                reportKey = Constants.REPORT_6,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_7)
        }
    }

    private fun validate(): Boolean {
        if (!mBinding.rbOMSApplicable.isChecked && !mBinding.rbOMSNotApplicable.isChecked) {
            showMessage("Select Online Monitoring System")
            return false
        }

        if (mBinding.rbOMSApplicable.isChecked) {
            if (!mBinding.rbOMSInstalledApplicable.isChecked && !mBinding.rbOMSInstalledNotApplicable.isChecked) {
                showMessage("Select Online Monitoring System Installed")
                return false
            }
            if (!mBinding.rbRemoteYes.isChecked && !mBinding.rbRemoteNo.isChecked) {
                showMessage("Select Remote Caliberation Applicable")
                return false
            }
            if (!mBinding.rbSensorYes.isChecked && !mBinding.rbSensorNo.isChecked) {
                showMessage("Sensor Properly Placed")
                return false
            }
            if (!mBinding.cbMPCB.isChecked && !mBinding.cbCPCB.isChecked) {
                showMessage("Select Connectivity")
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
        reports = getReportData()

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
