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

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_12) }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvAmbientAir.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = AmbientAirAdapter(getBaseActivity(), mViewModel)
        mBinding.rvAmbientAir.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }
}
