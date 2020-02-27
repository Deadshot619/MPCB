package com.gov.mpcb.menu_tabs.faq


import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.FragmentFaqBinding
import com.gov.mpcb.utils.constants.Constants.Companion.setToolbar
import com.gov.mpcb.utils.showMessage

class FaqActivity : BaseActivity<FragmentFaqBinding, FaqViewModel>(), FaqNavigator {


    override fun getLayoutId() = R.layout.fragment_faq
    override fun getViewModel() = FaqViewModel::class.java
    override fun getNavigator() = this@FaqActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        //Set toolbar
        setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.faq),
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
