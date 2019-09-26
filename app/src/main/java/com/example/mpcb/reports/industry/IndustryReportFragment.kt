package com.example.mpcb.reports.industry

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentIndustryCategoryBinding
import com.example.mpcb.databinding.FragmentProfileBinding
import com.example.mpcb.reports.ReportNavigator
import com.example.mpcb.reports.ReportViewModel
import com.example.mpcb.utils.showMessage

class IndustryReportFragment : BaseFragment<FragmentIndustryCategoryBinding, ReportViewModel>(), ReportNavigator {

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
gi
    }

}