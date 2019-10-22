package com.example.mpcb.reports.air_pollution

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAirPollutionBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class AirFragment : BaseFragment<FragmentAirPollutionBinding, AirViewModel>(), AirNavigator {
    override fun getLayoutId() = R.layout.fragment_air_pollution
    override fun getViewModel() = AirViewModel::class.java
    override fun getNavigator() = this@AirFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_9)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_10) }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }
        mBinding.imgAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvSource.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = AirPollutionAdapter(getBaseActivity(), mViewModel)
        mBinding.rvSource.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }
}