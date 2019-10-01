package com.example.mpcb.reports.electric_meter_details


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentElectricBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage


class ElectricFragment : BaseFragment<FragmentElectricBinding, ReportsPageViewModel>(), ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_electric
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ElectricFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }
}
