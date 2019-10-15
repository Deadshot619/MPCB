package com.example.mpcb.reports.previous_legal_actions


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentPreviousLegalBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class PreviousLegalFragment : BaseFragment<FragmentPreviousLegalBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_previous_legal
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@PreviousLegalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_16)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_17)
        }
    }


}