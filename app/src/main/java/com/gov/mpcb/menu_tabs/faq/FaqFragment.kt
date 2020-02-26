package com.gov.mpcb.menu_tabs.faq


import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentFaqBinding
import com.gov.mpcb.utils.showMessage

class FaqFragment : BaseFragment<FragmentFaqBinding, FaqViewModel>(), FaqNavigator {


    override fun getLayoutId() = R.layout.fragment_faq
    override fun getViewModel() = FaqViewModel::class.java
    override fun getNavigator() = this@FaqFragment
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
    }
}
