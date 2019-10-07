package com.example.mpcb.reports.statutory_submissions


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentStatutoryBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage


class StatutoryFragment : BaseFragment<FragmentStatutoryBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_statutory
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@StatutoryFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
    }


}