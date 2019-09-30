package com.example.mpcb.reports.industry

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentIndustryCategoryBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage

class IndustryReportFragment : BaseFragment<FragmentIndustryCategoryBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }

}