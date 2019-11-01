package com.example.mpcb.reports.oms_ambient_air


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

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
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
                if (checkedId == R.id.rbOMSApplicable) 1 else 0
        }
        mBinding.rgOMSInstalled.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.omsamInstalled =
                if (checkedId == R.id.rbOMSInstalledApplicable) 1 else 0
        }
        mBinding.rgSampleCollected.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.jvsSampleCollectedForAir =
                if (checkedId == R.id.rbSampleYes) 1 else 0
        }
        mBinding.rgSensorPlaced.setOnCheckedChangeListener { group, checkedId ->
            //            report.data.routineReport.omsamInstalled = if(checkedId == R.id.rbOMSInstalledApplicable) 1 else 0
        }
        mBinding.cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsamCpcb = if (isChecked) 1 else 0
        }
        mBinding.cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
            report.data.routineReport.omsamMpcb = if (isChecked) 1 else 0
        }

    }

    private fun onSubmit() {
        report.data.jvsSampleCollectedAirSource = mViewModel.getReportData()

        saveReportData()
        addReportFragment(Constants.REPORT_12)
    }
}
