package com.example.mpcb.reports.bank_guarantee_details

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentBankGuaranteeBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class BGDFragment : BaseFragment<FragmentBankGuaranteeBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_bank_guarantee
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@BGDFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_17)


        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_18) }

    }
}