package com.example.mpcb.reports.last_jvs_details


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentLastJvsBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class LastJVSFragment : BaseFragment<FragmentLastJvsBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_last_jvs
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@LastJVSFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_8)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_9)
        }

    }


}
