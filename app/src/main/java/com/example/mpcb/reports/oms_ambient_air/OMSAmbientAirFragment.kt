package com.example.mpcb.reports.oms_ambient_air


import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsAmbientAirBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSAmbientAirFragment : BaseFragment<FragmentOmsAmbientAirBinding, OMSAmbientAirViewModel>(),
    OMSAmbientAirNavigator {
    override fun getLayoutId() = R.layout.fragment_oms_ambient_air
    override fun getViewModel() = OMSAmbientAirViewModel::class.java
    override fun getNavigator() = this@OMSAmbientAirFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_11)
        setUpRecyclerView()
        setListeners()

        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvAmbientAir.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = AmbientAirAdapter(getBaseActivity(), mViewModel)
        mBinding.rvAmbientAir.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun setListeners() {
        mBinding.rgOMS.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsamApplicable =
                if (checkedId == R.id.rbOMSApplicable) {
                    showHideView(true)
                    1
                } else {
                    showHideView(false)
                    0
                }

        }
        mBinding.rgOMSInstalled.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsamInstalled =
                if (checkedId == R.id.rbOMSInstalledApplicable) 1 else 0
        }
        mBinding.rgSampleCollected.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.jvsSampleCollectedForAir =
                if (checkedId == R.id.rbSampleYes) {
                    mBinding.rvAmbientAir.visibility = View.VISIBLE
                    mBinding.txtAddMore.visibility = View.VISIBLE
                    mBinding.imgDelete.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.rvAmbientAir.visibility = View.GONE
                    mBinding.txtAddMore.visibility = View.GONE
                    mBinding.imgDelete.visibility = View.GONE
                    0
                }
        }
        mBinding.cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsamCpcb = if (isChecked) 1 else 0
        }
        mBinding.cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsamMpcb = if (isChecked) 1 else 0
        }

    }

    private fun showHideView(showView: Boolean) {
        if (showView) {
            mBinding.txtOMSInstalled.visibility = View.VISIBLE
            mBinding.rgOMSInstalled.visibility = View.VISIBLE
            mBinding.txtConnectivity.visibility = View.VISIBLE
            mBinding.linLayConnectivity.visibility = View.VISIBLE
            mBinding.cbCPCB.visibility = View.VISIBLE
            mBinding.cbMPCB.visibility = View.VISIBLE
        } else {
            mBinding.txtOMSInstalled.visibility = View.GONE
            mBinding.rgOMSInstalled.visibility = View.GONE
            mBinding.txtConnectivity.visibility = View.GONE
            mBinding.linLayConnectivity.visibility = View.GONE
            mBinding.cbCPCB.visibility = View.GONE
            mBinding.cbMPCB.visibility = View.GONE
        }
    }

    private fun onSubmit() {
        if (report.data.routineReport.omsamApplicable == 0) {
            report.data.routineReport.omsamInstalled = 0
            report.data.routineReport.jvsSampleCollectedForAir = 0
            report.data.routineReport.omsamCpcb = 0
            report.data.routineReport.omsamMpcb = 0
        }

        report.data.routineReport.jvsObservation = mBinding.edtRemark.text.toString()

        if (report.data.routineReport.jvsSampleCollectedForAir == 1) {
            report.data.jvsSampleCollectedAirSource = mViewModel.getReportData()
        }

        if (validate()) {
            saveReportData(reportKey = Constants.REPORT_11, reportStatus = true)
            addReportFragment(Constants.REPORT_12)
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
            if (!mBinding.cbCPCB.isChecked && !mBinding.cbMPCB.isChecked) {
                showMessage("Select Connectivity")
                return false
            }
        }
        if (mBinding.edtRemark.text.isNullOrEmpty()) {
            showMessage("Enter Remarks")
            return false
        }
        if (!mBinding.rbSampleYes.isChecked && !mBinding.rbSampleNo.isChecked) {
            showMessage("Select JVS Sample")
            return false
        }

        var isValid = true
        val sampleList = mViewModel.getReportData()

        if (mBinding.rbSampleYes.isChecked) {
            outer@ for (item in sampleList) {
                if (item.nameOfSource.isEmpty()) {
                    showMessage("Enter Source")
                    isValid = false
                    break
                }
                for (childItem in item.ambientAirChild) {
                    if (childItem.prescribedValue.isEmpty()) {
                        showMessage("Enter Prescribed Value")
                        isValid = false
                        break@outer
                    }
                }
            }

        }


        return isValid
    }
}
