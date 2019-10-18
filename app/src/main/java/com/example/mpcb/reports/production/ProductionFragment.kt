package com.example.mpcb.reports.production


import android.widget.ArrayAdapter
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProductionBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class ProductionFragment : BaseFragment<FragmentProductionBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_production
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ProductionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_2)

        setAdapterData()

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_3) }
    }

    private fun setAdapterData() {
        val unitList = arrayListOf("Unit 1", "Unit 2", "Unit 3", "Unit 4")
        val unitConsentAdapter = ArrayAdapter(getBaseActivity(), android.R.layout.simple_spinner_item, unitList)
        unitConsentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spnUnitConsent.adapter = unitConsentAdapter

        val unitActualList = arrayListOf("Unit Actual 1", "Unit Actual 2", "Unit Actual 3", "Unit Actual 4")
        val adapter = ArrayAdapter(getBaseActivity(), android.R.layout.simple_spinner_item, unitActualList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spnUnitActual.adapter = adapter
    }
}
