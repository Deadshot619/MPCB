package com.example.mpcb.reports.treatment


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreatmentBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class TreatmentFragment : BaseFragment<FragmentTreatmentBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_treatment
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreatmentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_3)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_4)
        }

    }
}
