package com.example.mpcb.reports.production


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProductionBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class ProductionFragment : BaseFragment<FragmentProductionBinding, ProductionViewModel>(),
    ProductionNavigator {

    override fun getLayoutId() = R.layout.fragment_production
    override fun getViewModel() = ProductionViewModel::class.java
    override fun getNavigator() = this@ProductionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_2)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_3) }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvProduction.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = ProductionAdapter(getBaseActivity(), mViewModel)
        mBinding.rvProduction.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

}
