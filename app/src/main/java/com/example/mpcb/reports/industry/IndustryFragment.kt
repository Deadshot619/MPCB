package com.example.mpcb.reports.industry

import android.widget.ArrayAdapter
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentIndustryCategoryBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage

class IndustryReportFragment :
    BaseFragment<FragmentIndustryCategoryBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_1)





        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_2) }

        val adapter = ArrayAdapter(getBaseActivity(), android.R.layout.simple_spinner_item, Constants.CATEGORY_LIST)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.catSpinner.adapter = adapter
    }

}