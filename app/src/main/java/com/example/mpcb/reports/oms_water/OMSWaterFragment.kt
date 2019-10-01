package com.example.mpcb.reports.oms_water


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmswaterBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage

class OMSWaterFragment : BaseFragment<FragmentOmswaterBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_omswater
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }

}
