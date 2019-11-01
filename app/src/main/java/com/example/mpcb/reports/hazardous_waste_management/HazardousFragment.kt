package com.example.mpcb.reports.hazardous_waste_management

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentHazardiousBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class HazardousFragment : BaseFragment<FragmentHazardiousBinding, HazardousViewModel>(),
    HazardousNavigator {

    override fun getLayoutId() = R.layout.fragment_hazardious
    override fun getViewModel() = HazardousViewModel::class.java
    override fun getNavigator() = this@HazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_12)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.imgAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }

    }

    private fun setUpRecyclerView() {
        mBinding.rvHazardousReports.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = HazardousAdapter(getBaseActivity(), mViewModel)
        mBinding.rvHazardousReports.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        report.data.routineReportHazardousWaste = mViewModel.getSourceList().value!!
        saveReportData()
        addReportFragment(Constants.REPORT_13)
    }
}