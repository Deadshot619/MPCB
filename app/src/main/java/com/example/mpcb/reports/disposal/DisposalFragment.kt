package com.example.mpcb.reports.disposal

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDisposalBinding
import com.example.mpcb.reports.ReportNavigator
import com.example.mpcb.reports.ReportViewModel
import com.example.mpcb.utils.showMessage

class DisposalFragment : BaseFragment<FragmentDisposalBinding, ReportViewModel>(), ReportNavigator {

    override fun getLayoutId() = R.layout.fragment_disposal
    override fun getViewModel() = ReportViewModel::class.java
    override fun getNavigator() = this@DisposalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }

}