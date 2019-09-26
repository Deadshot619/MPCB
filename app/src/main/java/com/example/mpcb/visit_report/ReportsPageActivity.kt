package com.example.mpcb.visit_report

import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.databinding.ActivityReportsPageBinding
import com.example.mpcb.profile.ProfileFragment
import com.example.mpcb.utils.showMessage

class ReportsPageActivity : BaseActivity<ActivityReportsPageBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    override fun getLayoutId() = R.layout.activity_reports_page
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@ReportsPageActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar()
    }

    private fun setToolbar() {
        mBinding.visitId.text = "#32432423"
        mBinding.visitName.text = "Treatment System"
        mBinding.reportCount.text = "10 / 17"
        mBinding.reportProgress.progress = 10
        addFragment(ProfileFragment(), false)
    }
}
