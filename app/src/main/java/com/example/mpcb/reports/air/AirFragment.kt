package com.example.mpcb.reports.air

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAirPollutionBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class AirFragment : BaseFragment<FragmentAirPollutionBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_air_pollution
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@AirFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_9)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_10)
        }
    }
}