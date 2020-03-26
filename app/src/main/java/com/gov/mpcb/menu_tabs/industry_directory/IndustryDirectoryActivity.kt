package com.gov.mpcb.menu_tabs.industry_directory

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivityIndustryDirectoryBinding
import com.gov.mpcb.utils.showMessage

class IndustryDirectoryActivity :
    BaseActivity<ActivityIndustryDirectoryBinding, IndustryDirectoryViewModel>(),
    IndustryDirectoryNavigator {
    override fun getLayoutId() = R.layout.activity_industry_directory
    override fun getViewModel() = IndustryDirectoryViewModel::class.java
    override fun getNavigator() = this@IndustryDirectoryActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

    }
}
