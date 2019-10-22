package com.example.mpcb.reports.bank_guarantee_details

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentBankGuaranteeBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class BGDFragment : BaseFragment<FragmentBankGuaranteeBinding, BGDViewModel>(), BGDNavigator {
    override fun getLayoutId() = R.layout.fragment_bank_guarantee
    override fun getViewModel() = BGDViewModel::class.java
    override fun getNavigator() = this@BGDFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_17)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_18) }
        mBinding.tvAddMore.setOnClickListener { mViewModel.addItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvBank.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = BGDAdapter(getBaseActivity(), mViewModel)
        mBinding.rvBank.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }
}