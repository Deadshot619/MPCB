package com.gov.mpcb.menu_tabs.surprise_inspections

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivitySurpriseInspectionBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class SurpriseInspectionActivity : BaseActivity<ActivitySurpriseInspectionBinding, SurpriseInspectionsViewModel>(),
SurpriseInspectionsNavigator{

    override fun getLayoutId() = R.layout.fragment_faq
    override fun getViewModel() = SurpriseInspectionsViewModel::class.java
    override fun getNavigator() = this@SurpriseInspectionActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.surprise_inspections),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        setUpListeners()
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            finish()
        }
    }
}
