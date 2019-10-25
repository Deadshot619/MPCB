package com.example.mpcb.reports.production


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProductionBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.google.gson.Gson

class ProductionFragment : BaseFragment<FragmentProductionBinding, ProductionViewModel>(),
    ProductionNavigator {

    lateinit var adapter: ProductionAdapter

    override fun getLayoutId() = R.layout.fragment_production
    override fun getViewModel() = ProductionViewModel::class.java
    override fun getNavigator() = this@ProductionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_2)
        setUpRecyclerView()

        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }

        mBinding.btnSubmit.setOnClickListener {
            mViewModel.getSourceList().value
            PreferencesHelper.setPreferences(Constants.PRODUCTION_KEY, Gson().toJson(mViewModel.getSourceList().value))

            addReportFragment(Constants.REPORT_3)
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProductionAdapter(getBaseActivity(), mViewModel)
        mBinding.rvProduction.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        mBinding.rvProduction.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }


}
