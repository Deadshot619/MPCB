package com.example.mpcb.reports.oms_water


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsWaterBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class OMSWaterFragment : BaseFragment<FragmentOmsWaterBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_oms_water
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@OMSWaterFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_6)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_7)
        }
    }

}
