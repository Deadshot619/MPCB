package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentNonHazardiousBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class NonHazardousFragment : BaseFragment<FragmentNonHazardiousBinding, NonHazardousViewModel>(),
    NonHazardousNavigator {

    override fun getLayoutId() = R.layout.fragment_non_hazardious
    override fun getViewModel() = NonHazardousViewModel::class.java
    override fun getNavigator() = this@NonHazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_13)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.imgAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }

    }

    private fun setUpRecyclerView() {
        mBinding.rvNonHazardous.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = NonHazardousAdapter(getBaseActivity(), mViewModel)
        mBinding.rvNonHazardous.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        report.data.routineReportNonHazardousWaste = mViewModel.getSourceList().value!!
        saveReportData()
        addReportFragment(Constants.REPORT_14)
    }
}