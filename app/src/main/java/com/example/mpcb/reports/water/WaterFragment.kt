package com.example.mpcb.reports.water

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentWasteWaterAspectBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage

class WaterFragment : BaseFragment<FragmentWasteWaterAspectBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_waste_water_aspect
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@WaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }

}